package com.group7.dao;

import com.group7.entity.PorMonto;

public class PorMontoDAO extends AbstractDAO<PorMonto> implements DaoInterface<PorMonto, Integer> {

	@Override
	public PorMonto buscarPorId(Integer id) {
		return (PorMonto) getCurrentSession().get(PorMonto.class, id);
	}

	public PorMonto dameEstrategiaPorMonto() {
		String senten = " FROM PorMonto";
		PorMonto estrategia = (PorMonto) getCurrentSession().createQuery(senten).uniqueResult();
		return estrategia;
	}
	
}
