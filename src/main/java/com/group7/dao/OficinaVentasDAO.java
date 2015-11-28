package com.group7.dao;

import com.group7.entity.OficinaVentas;

public class OficinaVentasDAO extends AbstractDAO<OficinaVentas> implements DaoInterface<OficinaVentas, Integer>  {

	@Override
	public OficinaVentas buscarPorId(Integer id) {
		return (OficinaVentas) getCurrentSession().get(OficinaVentas.class, id);
	}

	@Override
	protected String getClassName() {
		return OficinaVentas.class.getSimpleName();
	}
	
}