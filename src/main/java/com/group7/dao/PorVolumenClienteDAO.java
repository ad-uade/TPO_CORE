package com.group7.dao;

import com.group7.entity.PorVolumen;

public class PorVolumenClienteDAO extends AbstractDAO<PorVolumen>implements DaoInterface<PorVolumen, Integer> {

	@Override
	public PorVolumen buscarPorId(Integer id) {
		return (PorVolumen) getCurrentSession().get(PorVolumen.class, id);
	}

	public PorVolumen dameEstrategiaPorVolumen() {
		String senten = " FROM PorVolumen";
		PorVolumen volumen = (PorVolumen) getCurrentSession().createQuery(senten).uniqueResult();
		return volumen;
	}
	
	@Override
	protected String getClassName() {
		return PorVolumen.class.getSimpleName();
	}
	
}
