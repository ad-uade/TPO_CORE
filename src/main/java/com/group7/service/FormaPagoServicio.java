package com.group7.service;

import com.group7.business.FormaPagoVO;
import com.group7.dao.ContadoDAO;
import com.group7.dao.CuentaCorrienteDAO;
import com.group7.entity.Contado;
import com.group7.entity.CuentaCorriente;
import com.group7.entity.FormaPago;

public class FormaPagoServicio {

	private static ContadoDAO contadoDAO;
	private static CuentaCorrienteDAO cuentaCorrienteDAO;
	private static FormaPagoServicio instancia;
	
	public static FormaPagoServicio getInstancia(){
		if(instancia == null)
			instancia = new FormaPagoServicio();
		return instancia;
	}

	private FormaPagoServicio() {
		contadoDAO = new ContadoDAO();
		cuentaCorrienteDAO = new CuentaCorrienteDAO();
	}
	
	public CuentaCorriente obtenerCuentaCorriente() {
		cuentaCorrienteDAO.openCurrentSessionwithTransaction();
		CuentaCorriente cuenta = cuentaCorrienteDAO.obtenerCuentaCorriente();
		cuentaCorrienteDAO.closeCurrentSessionwithTransaction();
		return cuenta;
	}

	public Contado obtenerPagoContado() {
		contadoDAO.openCurrentSessionwithTransaction();
		Contado contado = contadoDAO.obtenerContado();
		contadoDAO.closeCurrentSessionwithTransaction();
		return contado;
	}
	
	public FormaPagoVO formaPagoAVo(FormaPago formaPago) {
		FormaPagoVO forma = new FormaPagoVO();
		forma.setIdFormaPago(formaPago.getId());
		forma.setDescripcion(formaPago.getDescripcion());
		return forma;
	}
	
	public FormaPago VoAFormaPago(FormaPagoVO formaPago) {
		  FormaPago forma = new FormaPago();
		  forma.setId(formaPago.getIdFormaPago());
		  forma.setDescripcion(formaPago.getDescripcion());
		  return forma;
	}

}
