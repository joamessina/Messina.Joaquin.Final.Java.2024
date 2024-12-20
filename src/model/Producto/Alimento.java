/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Producto;
import model.enums.TipoAlimento;
import exceptions.PrecioInvalidoException;

/**
 * Clase que representa un producto del tipo Alimento.
 * Contiene atributos específicos como tipo y calorías, e implementa la interfaz Descontable.
 * 
 * @author joaxx
 */
public class Alimento extends Producto implements Descontable {
    private TipoAlimento tipo; // Tipo de alimento (perecedero o no perecedero)
    private int calorias; // Cantidad de calorías del producto

    /**
     * Constructor que inicializa todos los atributos de la clase.
     *
     * @param id Identificador único del producto.
     * @param nombre Nombre del producto.
     * @param precio Precio del producto.
     * @param tipo Tipo de alimento (perecedero o no perecedero).
     * @param calorias Cantidad de calorías.
     * @throws PrecioInvalidoException Si el precio es menor o igual a cero.
     */
    public Alimento(int id, String nombre, double precio, TipoAlimento tipo, int calorias) throws PrecioInvalidoException {
        super(id, nombre, precio);
        this.tipo = tipo;
        this.calorias = calorias;
    }

    /**
     * Constructor que inicializa el producto con un nombre y tipo.
     * Utiliza valores predeterminados para el precio y las calorías.
     *
     * @param id Identificador único del producto.
     * @param nombre Nombre del producto.
     * @param tipo Tipo de alimento (perecedero o no perecedero).
     * @throws PrecioInvalidoException Si el precio predeterminado es inválido.
     */
    public Alimento(int id, String nombre, TipoAlimento tipo) throws PrecioInvalidoException {
        super(id, nombre);
        this.tipo = tipo;
        this.calorias = 100;
    }

    /**
     * Constructor sin parámetros que inicializa el producto con valores predeterminados.
     *
     * @throws PrecioInvalidoException Si el precio predeterminado es inválido.
     */
    public Alimento() throws PrecioInvalidoException {
        super();
        this.tipo = TipoAlimento.NO_PERECEDERO;
        this.calorias = 100;
    }

    /**
     * Obtiene el tipo de alimento.
     * 
     * @return Tipo de alimento.
     */
    public TipoAlimento getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo de alimento.
     * 
     * @param tipo Tipo de alimento.
     */
    public void setTipo(TipoAlimento tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene la cantidad de calorías del alimento.
     * 
     * @return Cantidad de calorías.
     */
    public int getCalorias() {
        return calorias;
    }

    /**
     * Establece la cantidad de calorías del alimento.
     * 
     * @param calorias Cantidad de calorías.
     */
    public void setCalorias(int calorias) {
        this.calorias = calorias;
    }

    /**
     * Obtiene una descripción detallada del alimento.
     * 
     * @return Descripción del producto de tipo Alimento.
     */
    @Override
    public String getDescripcion() {
        return "Alimento " + getNombre() + " (" + tipo + "), " + calorias + " calorías.";
    }

    /**
     * Calcula un descuento especial según el tipo de alimento.
     * 
     * @return Valor del descuento.
     */
    @Override
    public double calcularDescuentoEspecial() {
        if (tipo == TipoAlimento.PERECEDERO) {
            return getPrecio() * 0.15;
        } else {
            return getPrecio() * 0.05;
        }
    }
}
