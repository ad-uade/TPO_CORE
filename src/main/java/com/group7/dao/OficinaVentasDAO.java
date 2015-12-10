package com.group7.dao;

import com.group7.entity.OficinaVenta;

public class OficinaVentasDAO extends AbstractDAO<OficinaVenta> implements DaoInterface<OficinaVenta, Integer>  {

	@Override
	public OficinaVenta buscarPorId(Integer id) {
		return (OficinaVenta) getCurrentSession().get(OficinaVenta.class, id);
	}

	@Override
	protected String getClassName() {
		return OficinaVenta.class.getSimpleName();
	}
	
}