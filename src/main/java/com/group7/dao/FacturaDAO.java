package com.group7.dao;

import java.util.List;

import com.group7.entity.Factura;

public class FacturaDAO extends AbstractDAO<Factura> implements DaoInterface<Factura, Integer> {

	@Override
	public Factura buscarPorId(Integer id) {
		return (Factura) getCurrentSession().get(Factura.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Factura> buscarTodos() {
		return (List<Factura>)getCurrentSession().createQuery("from Factura ").list();
	}

	@Override
	public void borrarTodos() {
		List<Factura> entityList = buscarTodos();
		for (Factura entity : entityList) {
			this.borrar(entity);
		}
	}

}