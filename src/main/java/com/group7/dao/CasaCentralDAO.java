package com.group7.dao;

import com.group7.entity.CasaCentral;

public class CasaCentralDAO extends AbstractDAO<CasaCentral>implements DaoInterface<CasaCentral, Integer> {

	@Override
	public CasaCentral buscarPorId(Integer id) {
		return (CasaCentral) getCurrentSession().get(CasaCentral.class, id);
	}

	public CasaCentral dameCasaCentral() {
		String senten = " FROM CasaCentral";
		return (CasaCentral) getCurrentSession().createQuery(senten).uniqueResult();
	}

	@Override
	protected String getClassName() {
		return CasaCentral.class.getSimpleName();
	}
	
	@Override
	public void actualizar(CasaCentral casaCentral){
		getCurrentSession().saveOrUpdate(casaCentral);
	}
	
}