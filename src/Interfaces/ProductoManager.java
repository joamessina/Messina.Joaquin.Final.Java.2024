/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces;

import model.Producto.Producto;
import model.Producto.Alimento; 
import exceptions.ProductoNoEncontradoException;
import java.util.*;
import java.util.function.*;
import java.io.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import exceptions.PrecioInvalidoException;
import model.Producto.Electronico;
import model.Producto.Ropa;
import model.enums.MarcaElectronicos;
import model.enums.TallaRopa;
import model.enums.TipoAlimento;

/**
 * Clase que gestiona una lista de productos, proporcionando operaciones CRUD,
 * filtrado, ordenamiento, persistencia en diferentes formatos, y exportación de datos.
 * 
 * @author joaxx
 */
public class ProductoManager implements CrudRepository<Producto>, Serializable {

    private List<Producto> productos = new ArrayList<>();
    private int nextId = 1; // Id autoincremental

    /**
     * Agrega un producto a la lista, asignándole un ID único.
     * 
     * @param p Producto a agregar.
     */
    @Override
    public void agregar(Producto p) {
        p.setId(nextId++);
        productos.add(p);
    }

    /**
     * Obtiene un producto por su ID.
     * 
     * @param id ID del producto a buscar.
     * @return Producto encontrado.
     * @throws ProductoNoEncontradoException Si no se encuentra el producto.
     */
    @Override
    public Producto obtenerPorId(int id) {
        return productos.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ProductoNoEncontradoException("No se encontró producto con id " + id));
    }

    /**
     * Devuelve una lista inmodificable de todos los productos.
     * 
     * @return Lista de productos.
     */
    @Override
    public List<Producto> obtenerTodos() {
        return Collections.unmodifiableList(productos);
    }

    /**
     * Actualiza un producto existente.
     * 
     * @param elemento Producto con los datos actualizados.
     * @throws ProductoNoEncontradoException Si no se encuentra el producto a actualizar.
     */
    @Override
    public void actualizar(Producto elemento) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getId() == elemento.getId()) {
                productos.set(i, elemento);
                return;
            }
        }
        throw new ProductoNoEncontradoException("No se encontró producto para actualizar con id " + elemento.getId());
    }

    /**
     * Elimina un producto por su ID.
     * 
     * @param id ID del producto a eliminar.
     * @throws ProductoNoEncontradoException Si no se encuentra el producto a eliminar.
     */
    @Override
    public void eliminar(int id) {
        Producto p = obtenerPorId(id);
        productos.remove(p);
    }

    /**
     * Ordena la lista de productos según un comparador dado.
     * 
     * @param comparator Comparador para determinar el orden de los productos.
     */
    public void ordenarPor(Comparator<Producto> comparator) {
        productos.sort(comparator);
    }

    /**
     * Filtra los productos según un criterio especificado.
     * 
     * @param filtro Predicado que define el criterio de filtrado.
     * @return Lista de productos que cumplen el criterio.
     */
    public List<Producto> filtrar(Predicate<Producto> filtro) {
        List<Producto> resultado = new ArrayList<>();
        for (Producto p : productos) {
            if (filtro.test(p)) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    /**
     * Aplica una acción a todos los productos de la lista.
     * 
     * @param accion Acción a aplicar a cada producto.
     */
    public void aplicar(Consumer<Producto> accion) {
        productos.forEach(accion);
    }

    /**
     * Exporta una lista de productos filtrados a un archivo de texto.
     * 
     * @param nombreArchivo Ruta del archivo donde se exportarán los datos.
     * @param listaFiltrada Lista de productos a exportar.
     * @throws IOException Si ocurre un error al escribir el archivo.
     */
    public void exportarTxt(String nombreArchivo, List<Producto> listaFiltrada) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivo))) {
            bw.write("Reporte de Productos Filtrados\n");
            bw.write("-----------------------------\n");
            for (Producto p : listaFiltrada) {
                bw.write(p.getClass().getSimpleName() + ": " + p.getDescripcion() + " - Precio: " + p.getPrecio() + "\n");
            }
        }
    }

    /**
     * Guarda la lista de productos en un archivo CSV.
     * 
     * @param nombreArchivo Ruta del archivo CSV.
     * @throws IOException Si ocurre un error al escribir el archivo.
     */
    public void guardarCSV(String nombreArchivo) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivo))) {
            for (Producto p : productos) {
                bw.write(p.getId() + "," + p.getNombre() + "," + p.getPrecio() + "," + p.getClass().getSimpleName());
                bw.newLine();
            }
        }
    }

    /**
     * Carga productos desde un archivo CSV.
     * 
     * @param nombreArchivo Ruta del archivo CSV.
     * @throws IOException Si ocurre un error al leer el archivo.
     * @throws PrecioInvalidoException Si algún precio es inválido.
     */
    public void cargarCSV(String nombreArchivo) throws IOException, PrecioInvalidoException {
        productos.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] parts = linea.split(",");
                int id = Integer.parseInt(parts[0]);
                String nombre = parts[1];
                double precio = Double.parseDouble(parts[2]);
                String tipo = parts[3];

                switch (tipo) {
                    case "Alimento":
                        productos.add(new Alimento(id, nombre, precio, TipoAlimento.NO_PERECEDERO, 100));
                        break;
                    case "Electronico":
                        productos.add(new Electronico(id, nombre, precio, MarcaElectronicos.SAMSUNG, 12));
                        break;
                    case "Ropa":
                        productos.add(new Ropa(id, nombre, precio, TallaRopa.M, "Algodón"));
                        break;
                    default:
                        throw new IllegalArgumentException("Tipo desconocido: " + tipo);
                }
            }
        }
    }

    /**
     * Guarda los productos en formato JSON.
     * 
     * @param nombreArchivo Ruta del archivo JSON.
     * @throws IOException Si ocurre un error al escribir el archivo.
     */
    public void guardarJSON(String nombreArchivo) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter fw = new FileWriter(nombreArchivo)) {
            JsonArray jsonArray = new JsonArray();
            for (Producto p : productos) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("tipo", p.getClass().getSimpleName());
                jsonObject.add("datos", gson.toJsonTree(p));
                jsonArray.add(jsonObject);
            }
            gson.toJson(jsonArray, fw);
        }
    }


        /**
     * Carga productos desde un archivo JSON.
     * 
     * @param nombreArchivo Ruta del archivo JSON.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public void cargarJSON(String nombreArchivo) throws IOException {
        Gson gson = new Gson();
        try (FileReader fr = new FileReader(nombreArchivo)) {
            productos.clear();
            JsonArray jsonArray = JsonParser.parseReader(fr).getAsJsonArray();
            for (JsonElement element : jsonArray) {
                JsonObject obj = element.getAsJsonObject();
                String tipo = obj.get("tipo").getAsString();
                JsonObject datos = obj.getAsJsonObject("datos");

                Producto p;
                switch (tipo) {
                    case "Alimento":
                        p = gson.fromJson(datos, Alimento.class);
                        break;
                    case "Electronico":
                        p = gson.fromJson(datos, Electronico.class);
                        break;
                    case "Ropa":
                        p = gson.fromJson(datos, Ropa.class);
                        break;
                    default:
                        throw new IllegalArgumentException("Tipo de producto desconocido: " + tipo);
                }
                productos.add(p);
            }
        }
    }

    /**
     * Serializa la lista de productos a un archivo binario.
     * 
     * @param nombreArchivo Ruta del archivo binario.
     * @throws IOException Si ocurre un error al escribir el archivo.
     */
    public void serializar(String nombreArchivo) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {
            oos.writeObject(productos);
        }
    }

    /**
     * Deserializa la lista de productos desde un archivo binario.
     * 
     * @param nombreArchivo Ruta del archivo binario.
     * @throws IOException Si ocurre un error al leer el archivo.
     * @throws ClassNotFoundException Si no se puede encontrar la clase durante la deserialización.
     */
    public void deserializar(String nombreArchivo) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nombreArchivo))) {
            List<Producto> lista = (List<Producto>) ois.readObject();
            productos.clear();
            productos.addAll(lista);
        }
    }

    /**
     * Filtra productos por tipo específico.
     * 
     * @param lista Lista de productos a filtrar.
     * @param tipo Clase del tipo de producto deseado.
     * @param <U> Tipo de producto filtrado.
     * @return Lista de productos del tipo especificado.
     */
    public <U extends Producto> List<U> filtrarPorTipo(List<? extends Producto> lista, Class<U> tipo) {
        List<U> result = new ArrayList<>();
        for (Producto p : lista) {
            if (tipo.isInstance(p)) {
                result.add(tipo.cast(p));
            }
        }
        return result;
    }

    /**
     * Aplica un descuento a todos los alimentos de una lista.
     * 
     * @param lista Lista que puede contener alimentos.
     * @param porcentaje Porcentaje de descuento a aplicar.
     */
    public void aplicarDescuentoAlimentos(List<? super Alimento> lista, double porcentaje) {
        for (Object obj : lista) {
            if (obj instanceof Alimento) {
                Alimento a = (Alimento) obj;
                a.aplicarDescuento(porcentaje);
            }
        }
    }

    /**
     * Devuelve un iterador para recorrer la lista de productos.
     * 
     * @return Iterador de productos.
     */
    public Iterator<Producto> iterator() {
        return new ProductoIterator(productos);
    }

    /**
     * Clase interna que implementa un iterador para la lista de productos.
     */
    private class ProductoIterator implements Iterator<Producto> {
        private int index = 0;
        private List<Producto> lista;

        /**
         * Constructor del iterador.
         * 
         * @param lista Lista de productos a iterar.
         */
        public ProductoIterator(List<Producto> lista) {
            this.lista = lista;
        }

        /**
         * Verifica si hay más elementos en la lista.
         * 
         * @return true si hay más elementos, false en caso contrario.
         */
        @Override
        public boolean hasNext() {
            return index < lista.size();
        }

        /**
         * Devuelve el siguiente producto de la lista.
         * 
         * @return Producto siguiente.
         * @throws NoSuchElementException Si no hay más elementos.
         */
        @Override
        public Producto next() {
            if (!hasNext()) throw new NoSuchElementException();
            return lista.get(index++);
        }
    }
}
