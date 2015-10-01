package com.group7.dao;

import java.util.List;

import org.hibernate.Transaction;

import com.group7.entity.Cliente;

public class ClienteDAO extends AbstractDAO<Cliente> implements DaoInterface<Cliente, Integer> {

	public void agregarOficina(Cliente c) {
		Transaction t = getCurrentSession().beginTransaction();
		String sql1 = " UPDATE Cliente SET ODV = ? WHERE CUILCliente = ?";
		getCurrentSession().createQuery(sql1).setInteger(0, c.getOficinaVentas().getIdOficinaVenta()).setInteger(1, c.getcUILCliente()).executeUpdate();
		t.commit();
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Cliente> buscarTodos() {
		return (List<Cliente>)getCurrentSession().createQuery("FROM Cliente WHERE estado = 1").list();
	}

	@Override
	public void borrarTodos() {
		List<Cliente> entityList = buscarTodos();
		for (Cliente entity : entityList) {
			this.borrar(entity);
		}
	}

}