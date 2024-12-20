/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Producto;
import model.enums.MarcaElectronicos;
import exceptions.PrecioInvalidoException;

/**
 * Clase que representa un producto del tipo Electrónico.
 * Contiene atributos específicos como marca y garantía.
 * 
 * @author joaxx
 */
public class Electronico extends Producto {
    private MarcaElectronicos marca; // Marca del producto electrónico
    private int garantiaMeses; // Duración de la garantía en meses

    /**
     * Constructor que inicializa todos los atributos de la clase.
     *
     * @param id Identificador único del producto.
     * @param nombre Nombre del producto.
     * @param precio Precio del producto.
     * @param marca Marca del producto.
     * @param garantiaMeses Duración de la garantía en meses.
     * @throws PrecioInvalidoException Si el precio es menor o igual a cero.
     */
    public Electronico(int id, String nombre, double precio, MarcaElectronicos marca, int garantiaMeses) throws PrecioInvalidoException {
        super(id, nombre, precio);
        this.marca = marca;
        this.garantiaMeses = garantiaMeses;
    }

    /**
     * Constructor que inicializa el producto con un nombre y marca.
     * Utiliza valores predeterminados para el precio y la garantía.
     *
     * @param id Identificador único del producto.
     * @param nombre Nombre del producto.
     * @param marca Marca del producto.
     * @throws PrecioInvalidoException Si el precio predeterminado es inválido.
     */
    public Electronico(int id, String nombre, MarcaElectronicos marca) throws PrecioInvalidoException {
        super(id, nombre);
        this.marca = marca;
        this.garantiaMeses = 12;
    }

    /**
     * Constructor sin parámetros que inicializa el producto con valores predeterminados.
     *
     * @throws PrecioInvalidoException Si el precio predeterminado es inválido.
     */
    public Electronico() throws PrecioInvalidoException {
        super();
        this.marca = MarcaElectronicos.SAMSUNG;
        this.garantiaMeses = 12;
    }

    /**
     * Obtiene la marca del producto electrónico.
     * 
     * @return Marca del producto.
     */
    public MarcaElectronicos getMarca() {
        return marca;
    }

    /**
     * Establece la marca del producto electrónico.
     * 
     * @param marca Marca del producto.
     */
    public void setMarca(MarcaElectronicos marca) {
        this.marca = marca;
    }

    /**
     * Obtiene la duración de la garantía en meses.
     * 
     * @return Duración de la garantía en meses.
     */
    public int getGarantiaMeses() {
        return garantiaMeses;
    }

    /**
     * Establece la duración de la garantía en meses.
     * 
     * @param garantiaMeses Duración de la garantía en meses.
     */
    public void setGarantiaMeses(int garantiaMeses) {
        this.garantiaMeses = garantiaMeses;
    }

    /**
     * Obtiene una descripción detallada del producto electrónico.
     * 
     * @return Descripción del producto de tipo Electrónico.
     */
    @Override
    public String getDescripcion() {
        return "Electrónico " + getNombre() + " de marca " + marca + ", garantía: " + garantiaMeses + " meses";
    }
}
