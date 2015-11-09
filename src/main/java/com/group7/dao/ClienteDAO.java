package com.group7.dao;

import java.util.List;

import com.group7.entity.Cliente;

/**
 * 
 * @author huicha
 *
 */
public class ClienteDAO extends AbstractDAO<Cliente> implements DaoInterface<Cliente, Integer> {

	public void agregarOficina(Cliente c) {
		String sql1 = " UPDATE Cliente SET ODV = ? WHERE CUILCliente = ?";
		getCurrentSession().createQuery(sql1).setInteger(0, c.getOficinaVentas().getIdOficinaVenta()).setInteger(1, c.getCuilCliente()).executeUpdate();
	}

	@Override
	public void persistir(Cliente entity) {
		getCurrentSession().save(entity);
	}

	@Override
	public void actualizar(Cliente entity) {
		getCurrentSession().update(entity);
	}

	@Override
	public Cliente buscarPorId(Integer id) {
		return (Cliente) getCurrentSession().get(Cliente.class, id);
	}

	@Override
	public void borrar(Cliente entity) {
		getCurrentSession().delete(entity);
	}
	
	public void bajaCliente(Integer cuil) {
		String sql1 = " FROM Cliente c WHERE c.CUILCliente = ?";
		Cliente entity = (Cliente) getCurrentSession().createQuery(sql1).setInteger(0, cuil).uniqueResult();
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

}