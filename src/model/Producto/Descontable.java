/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Producto;

/**
 * Interfaz que define el comportamiento de aplicar un descuento especial a un producto.
 * 
 * @author joaxx
 */
public interface Descontable {
    /**
     * Calcula el descuento especial aplicado a un producto.
     *
     * @return Valor del descuento calculado.
     */
    double calcularDescuentoEspecial();
}
