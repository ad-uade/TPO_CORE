package com.group7.dao;

import java.util.List;

import com.group7.entity.Cliente;

/**
 * 
 * @author huicha
 *
 */
public class ClienteDAO extends AbstractDAO<Cliente> implements DaoInterface<Cliente, Long> {

	@Override
	public void persistir(Cliente entity) {
		getCurrentSession().save(entity);
	}
	
	@Override
	public void actualizar(Cliente entity) {
		getCurrentSession().update(entity);
	}

	@Override
	public Cliente buscarPorId(Long id) {
		return (Cliente) getCurrentSession().get(Cliente.class, id);
	}

	@Override
	public void borrar(Cliente entity) {
		getCurrentSession().delete(entity);
	}
	
	public void bajaCliente(Long cuil) {
		String sql1 = " FROM Cliente c WHERE c.cuilCliente = ?";
		Cliente entity = (Cliente) getCurrentSession().createQuery(sql1).setLong(0, cuil).uniqueResult();
		entity.setEstado(false);
		this.actualizar(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Cliente> buscarTodos() {
		return getCurrentSession().createQuery("FROM Cliente WHERE estado = 1").list();
	}

	@Override
	public void borrarTodos() {
		List<Cliente> entityList = buscarTodos();
		for (Cliente entity : entityList) {
			this.borrar(entity);
		}
	}

	@Override
	protected String getClassName() {
		return Cliente.class.getSimpleName();
	}

}