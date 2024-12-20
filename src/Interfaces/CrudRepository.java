/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces;

import java.util.List;

/**
 * Interfaz genérica que define las operaciones básicas de un CRUD.
 * Permite realizar las operaciones de agregar, obtener, actualizar y eliminar elementos.
 *
 * @param <T> Tipo de elemento que será gestionado por el repositorio.
 * @author joaxx
 */
public interface CrudRepository<T> {

    /**
     * Agrega un nuevo elemento al repositorio.
     *
     * @param elemento Elemento a agregar.
     */
    void agregar(T elemento);

    /**
     * Obtiene un elemento por su identificador único.
     *
     * @param id Identificador del elemento a buscar.
     * @return Elemento correspondiente al identificador.
     */
    T obtenerPorId(int id);

    /**
     * Obtiene una lista de todos los elementos en el repositorio.
     *
     * @return Lista de elementos en el repositorio.
     */
    List<T> obtenerTodos();

    /**
     * Actualiza un elemento existente en el repositorio.
     *
     * @param elemento Elemento con los datos actualizados.
     */
    void actualizar(T elemento);

    /**
     * Elimina un elemento del repositorio por su identificador.
     *
     * @param id Identificador del elemento a eliminar.
     */
    void eliminar(int id);
}
