/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exceptions;

/**
 * Excepción personalizada para indicar que un producto no fue encontrado.
 * Se utiliza principalmente en operaciones de búsqueda y eliminación
 * dentro de la clase ProductoManager.
 * 
 * Hereda de RuntimeException ya que representa un error en tiempo de ejecución.
 * 
 * @author joaxx
 */
public class ProductoNoEncontradoException extends RuntimeException {

    /**
     * Constructor que inicializa la excepción con un mensaje personalizado.
     * 
     * @param mensaje Mensaje que describe el error.
     */
    public ProductoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
