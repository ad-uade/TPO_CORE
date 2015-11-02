/**
 * 
 */
package com.group7.dao;

import java.io.Serializable;
import java.util.List;

/**
 * @author huicha
 *
 */
public interface DaoInterface<T, Id extends Serializable> {

	public void persistir(T entity);

	public void actualizar(T entity);

	public T buscarPorId(Id id);

	public void borrar(T entity);

	public List<T> buscarTodos();

	public void borrarTodos();

}
