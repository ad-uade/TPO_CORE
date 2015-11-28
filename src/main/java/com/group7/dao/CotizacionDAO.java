package com.group7.dao;

import com.group7.entity.Cotizacion;

public class CotizacionDAO extends AbstractDAO<Cotizacion> implements DaoInterface<Cotizacion, Integer> {

	public Cotizacion buscarPorCuil(Long cuil) {
		String sql1 = " FROM Cotizacion c WHERE c.CUILCliente = ?";
		Cotizacion entity = (Cotizacion) getCurrentSession().createQuery(sql1).setLong(0, cuil).uniqueResult();
		return entity;
	}
	
	@Override
	public Cotizacion buscarPorId(Integer id) {
		return (Cotizacion) getCurrentSession().get(Cotizacion.class, id);
	}
	
	@Override
	protected String getClassName() {
		return Cotizacion.class.getSimpleName();
	}
}
