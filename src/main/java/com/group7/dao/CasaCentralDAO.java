package com.group7.dao;

import com.group7.entity.CasaCentral;

public class CasaCentralDAO extends AbstractDAO<CasaCentral>implements DaoInterface<CasaCentral, Integer> {

	@Override
	public CasaCentral buscarPorId(Integer id) {
		return (CasaCentral) getCurrentSession().get(CasaCentral.class, id);
	}

}
