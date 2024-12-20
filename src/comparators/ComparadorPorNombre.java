/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comparators;

import model.Producto.Producto;
import java.util.Comparator;

/**
 * Clase comparadora para ordenar productos por su nombre.
 * Implementa la interfaz Comparator y define el criterio de comparación
 * basado en el nombre de los productos.
 * 
 * El ordenamiento es alfabético y distingue entre mayúsculas y minúsculas.
 * 
 * @author joaxx
 */
public class ComparadorPorNombre implements Comparator<Producto> {

    /**
     * Compara dos productos según su nombre.
     * 
     * @param o1 Primer producto a comparar.
     * @param o2 Segundo producto a comparar.
     * @return Un valor negativo si el nombre de o1 es lexicográficamente menor que el de o2,
     *         cero si son iguales, y un valor positivo si el nombre de o1
     *         es mayor que el de o2.
     */
    @Override
    public int compare(Producto o1, Producto o2) {
        return o1.getNombre().compareTo(o2.getNombre());
    }
}
