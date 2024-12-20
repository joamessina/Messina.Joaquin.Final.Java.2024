/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.Producto to edit this template
 */
package model.Producto;
import java.io.Serializable;
import exceptions.PrecioInvalidoException;
/**
 * Clase abstracta que representa un producto genérico.
 * Sirve como base para otros tipos específicos de productos.
 * Implementa la interfaz Comparable para permitir su ordenamiento por ID.
 * Implementa Serializable para habilitar la persistencia.
 * 
 * @author joaxx
 */
public abstract class Producto implements Comparable<Producto>, Serializable {
    private int id; // Identificador único del producto
    private String nombre; // Nombre del producto
    private double precio; // Precio del producto

    /**
     * Constructor que inicializa todos los atributos del producto.
     * 
     * @param id Identificador único del producto.
     * @param nombre Nombre del producto.
     * @param precio Precio del producto.
     * @throws PrecioInvalidoException Si el precio es menor a cero.
     */
    public Producto(int id, String nombre, double precio) throws PrecioInvalidoException {
        this.id = id;
        this.nombre = nombre;
        setPrecio(precio);
    }

    /**
     * Constructor que inicializa el producto con un ID y nombre.
     * Utiliza un precio predeterminado de 0.0.
     * 
     * @param id Identificador único del producto.
     * @param nombre Nombre del producto.
     * @throws PrecioInvalidoException Si el precio predeterminado es menor a cero.
     */
    public Producto(int id, String nombre) throws PrecioInvalidoException {
        this(id, nombre, 0.0);
    }

    /**
     * Constructor sin parámetros que inicializa el producto con valores predeterminados.
     * El ID es 0, el nombre es "Sin nombre" y el precio es 0.0.
     * 
     * @throws PrecioInvalidoException Si el precio predeterminado es menor a cero.
     */
    public Producto() throws PrecioInvalidoException {
        this(0, "Sin nombre", 0.0);
    }

    /**
     * Obtiene el identificador único del producto.
     * 
     * @return El ID del producto.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador único del producto.
     * 
     * @param id Nuevo ID del producto.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el precio del producto.
     * 
     * @return El precio del producto.
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Establece el precio del producto.
     * 
     * @param precio Nuevo precio del producto.
     * @throws PrecioInvalidoException Si el precio es menor a cero.
     */
    public void setPrecio(double precio) throws PrecioInvalidoException {
        if (precio < 0) {
            throw new PrecioInvalidoException("El precio no puede ser negativo");
        }
        this.precio = precio;
    }

    /**
     * Obtiene el nombre del producto.
     * 
     * @return El nombre del producto.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del producto.
     * 
     * @param nombre Nuevo nombre del producto.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
    * Aplica un descuento al producto basado en un porcentaje.
    * Si el descuento hace que el precio sea negativo, el precio se establece en 0.
    * 
    * @param porcentaje Porcentaje del descuento a aplicar.
    */
    public void aplicarDescuento(double porcentaje) {
        try {
            setPrecio(this.precio - (this.precio * porcentaje / 100.0));
        } catch (PrecioInvalidoException e) {
            // Si el descuento genera un precio negativo, establecer el precio a 0.
            try {
                setPrecio(0);
            } catch (PrecioInvalidoException ex) {
                // Esto no debería suceder, pero se incluye por seguridad.
                System.err.println("Error al establecer el precio: " + ex.getMessage());
            }
        }
    }


    /**
     * Método abstracto para obtener una descripción del producto.
     * Debe ser implementado por las subclases.
     * 
     * @return Descripción del producto.
     */
    public abstract String getDescripcion();

    /**
     * Compara dos productos por su ID.
     * 
     * @param o Otro producto a comparar.
     * @return Un valor negativo, cero o positivo si este producto es menor, igual o mayor que el producto comparado.
     */
    @Override
    public int compareTo(Producto o) {
        return Integer.compare(this.id, o.getId());
    }
}
