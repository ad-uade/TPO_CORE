package com.group7.dao;

import com.group7.entity.CuentaCorriente;

public class CuentaCorrienteDAO extends FormaPagoDAO {

	@Override
	public CuentaCorriente buscarPorId(Integer id) {
		return (CuentaCorriente) getCurrentSession().get(CuentaCorriente.class, id);
	}

	public CuentaCorriente obtenerCuentaCorriente() {
		String senten = " FROM CuentaCorriente";
		return (CuentaCorriente) getCurrentSession().createQuery(senten).uniqueResult();
	}
	
}