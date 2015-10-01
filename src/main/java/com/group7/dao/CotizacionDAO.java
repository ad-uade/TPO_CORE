package com.group7.dao;

import com.group7.entity.Cotizacion;

public class CotizacionDAO extends AbstractDAO<Cotizacion> implements DaoInterface<Cotizacion, Integer> {

	@Override
	public Cotizacion buscarPorId(Integer id) {
		return (Cotizacion) getCurrentSession().get(Cotizacion.class, id);
	}
	
}
