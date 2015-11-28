package com.group7.dao;

import com.group7.entity.FormaPago;

public class FormaPagoDAO extends AbstractDAO<FormaPago> implements DaoInterface<FormaPago, Integer> {

	@Override
	public FormaPago buscarPorId(Integer id) {
		return (FormaPago) getCurrentSession().get(FormaPago.class, id);
	}

	@Override
	protected String getClassName() {
		return FormaPago.class.getSimpleName();
	}
	
}
