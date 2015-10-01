package com.group7.dao;

import com.group7.entity.PorVolumen;

public class PorVolumenClienteDAO extends AbstractDAO<PorVolumen>implements DaoInterface<PorVolumen, Integer> {

	@Override
	public PorVolumen buscarPorId(Integer id) {
		return (PorVolumen) getCurrentSession().get(PorVolumen.class, id);
	}

}
