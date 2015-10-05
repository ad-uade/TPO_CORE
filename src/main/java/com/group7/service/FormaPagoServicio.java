package com.group7.service;

import com.group7.business.FormaPagoVO;
import com.group7.dao.ContadoDAO;
import com.group7.dao.CuentaCorrienteDAO;
import com.group7.entity.Contado;
import com.group7.entity.CuentaCorriente;
import com.group7.entity.FormaPago;

public class FormaPagoServicio {

	private static FormaPagoServicio instancia;
	
	public static FormaPagoServicio getInstancia(){
		if(instancia == null)
			instancia = new FormaPagoServicio();
		return instancia;
	}

	public CuentaCorriente obtenerCuentaCorriente() {
		CuentaCorrienteDAO miDAO = new CuentaCorrienteDAO();
		CuentaCorriente cuenta = miDAO.obtenerCuentaCorriente();
		return cuenta;
	}

	public Contado obtenerPagoContado() {
		ContadoDAO miDAO =  new ContadoDAO();
		Contado contado = miDAO.obtenerContado();
		return contado;
	}

	public FormaPagoVO HibernateAVo(FormaPago formaPago) {
		FormaPagoVO forma = new FormaPagoVO();
		forma.setIdFormaPago(formaPago.getId());
		forma.setDescripcion(formaPago.getDescripcion());
		return forma;
	}
	
	public FormaPago VoAHibernate(FormaPagoVO formaPago) {
		  FormaPago forma = new FormaPago();
		  forma.setId(formaPago.getIdFormaPago());
		  forma.setDescripcion(formaPago.getDescripcion());
		  return forma;
	}
	
}
