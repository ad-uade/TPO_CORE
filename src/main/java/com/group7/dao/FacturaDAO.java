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
		return getCurrentSession().createQuery("from Factura ").list();
	}

	@Override
	public void borrarTodos() {
		List<Factura> entityList = buscarTodos();
		for (Factura entity : entityList) {
			this.borrar(entity);
		}
	}
	
	public void actualizarPrecioFactura(Factura factura, float precio) {
		  String senten = "UPDATE Factura SET precioTotal = ? WHERE nroFactura = ?";
		  getCurrentSession().createQuery(senten).setFloat(0, precio).setInteger(1, factura.getNroFactura()).executeUpdate();
	}
	
	@Override
	protected String getClassName() {
		return Factura.class.getSimpleName();
	}

}