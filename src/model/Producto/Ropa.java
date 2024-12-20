/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Producto;
import model.enums.TallaRopa;
import exceptions.PrecioInvalidoException;

/**
 * Clase que representa un producto del tipo Ropa.
 * Contiene atributos específicos como talla y material.
 * 
 * @author joaxx
 */
public class Ropa extends Producto {
    private TallaRopa talla; // Talla de la prenda
    private String material; // Material de la prenda

    /**
     * Constructor que inicializa todos los atributos de la clase.
     *
     * @param id Identificador único del producto.
     * @param nombre Nombre del producto.
     * @param precio Precio del producto.
     * @param talla Talla de la prenda.
     * @param material Material de la prenda.
     * @throws PrecioInvalidoException Si el precio es menor o igual a cero.
     */
    public Ropa(int id, String nombre, double precio, TallaRopa talla, String material) throws PrecioInvalidoException {
        super(id, nombre, precio);
        this.talla = talla;
        this.material = material;
    }

    /**
     * Constructor que inicializa la talla y el nombre del producto.
     * Utiliza valores predeterminados para el precio y el material.
     *
     * @param id Identificador único del producto.
     * @param nombre Nombre del producto.
     * @param talla Talla de la prenda.
     * @throws PrecioInvalidoException Si el precio predeterminado es inválido.
     */
    public Ropa(int id, String nombre, TallaRopa talla) throws PrecioInvalidoException {
        super(id, nombre);
        this.talla = talla;
        this.material = "Algodón";
    }

    /**
     * Constructor sin parámetros que inicializa el producto con valores predeterminados.
     * 
     * @throws PrecioInvalidoException Si el precio predeterminado es inválido.
     */
    public Ropa() throws PrecioInvalidoException {
        super();
        this.talla = TallaRopa.M;
        this.material = "Algodón";
    }

    /**
     * Obtiene la talla de la prenda.
     * 
     * @return Talla de la prenda.
     */
    public TallaRopa getTalla() {
        return talla;
    }

    /**
     * Establece la talla de la prenda.
     * 
     * @param talla Talla de la prenda.
     */
    public void setTalla(TallaRopa talla) {
        this.talla = talla;
    }

    /**
     * Obtiene el material de la prenda.
     * 
     * @return Material de la prenda.
     */
    public String getMaterial() {
        return material;
    }

    /**
     * Establece el material de la prenda.
     * 
     * @param material Material de la prenda.
     */
    public void setMaterial(String material) {
        this.material = material;
    }

    /**
     * Obtiene una descripción detallada de la prenda.
     * 
     * @return Descripción del producto de tipo Ropa.
     */
    @Override
    public String getDescripcion() {
        return "Ropa: " + getNombre() + " - Talla: " + talla + " - Material: " + material;
    }
}
