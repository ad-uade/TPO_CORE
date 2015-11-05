package com.group7.dao;

import com.group7.entity.Contado;

public class ContadoDAO extends FormaPagoDAO {

	public Contado obtenerContado() {
		String senten = " FROM Contado";
		return(Contado) getCurrentSession().createQuery(senten).uniqueResult();
	}

}
