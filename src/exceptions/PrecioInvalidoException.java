/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exceptions;

/**
 * Excepción personalizada para manejar precios no válidos en productos.
 * Se utiliza principalmente cuando se intenta establecer un precio negativo
 * en la clase Producto.
 * 
 * Hereda de Exception ya que es una excepción comprobada (checked exception),
 * lo que obliga a manejarla explícitamente en el código.
 * 
 * @author joaxx
 */
public class PrecioInvalidoException extends Exception {

    /**
     * Constructor que inicializa la excepción con un mensaje personalizado.
     * 
     * @param msg Mensaje que describe el error del precio no válido.
     */
    public PrecioInvalidoException(String msg) {
        super(msg);
    }
}
