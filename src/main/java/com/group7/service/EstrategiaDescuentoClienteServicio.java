package com.group7.service;

import com.group7.dao.PorMontoDAO;
import com.group7.dao.PorVolumenClienteDAO;
import com.group7.entity.PorMonto;
import com.group7.entity.PorVolumen;


public class EstrategiaDescuentoClienteServicio {

	private static PorVolumenClienteDAO porVolumenClienteDAO;
	private static PorMontoDAO porMontoDAO;
	private static EstrategiaDescuentoClienteServicio instancia;
	
	public static EstrategiaDescuentoClienteServicio getInstancia(){
		if(instancia == null)
			instancia = new EstrategiaDescuentoClienteServicio();
		return instancia;
	}
	
	private EstrategiaDescuentoClienteServicio() {
		porVolumenClienteDAO = new PorVolumenClienteDAO();
		porMontoDAO = new PorMontoDAO();
	}

	public PorVolumen obtenerEstrategiaPorVolumen(int cantidad) {
		porVolumenClienteDAO.openCurrentSessionwithTransaction();
		PorVolumen estrategia = porVolumenClienteDAO.dameEstrategiaPorVolumen();
		porVolumenClienteDAO.closeCurrentSessionwithTransaction();
		if(estrategia.getVolumen() <= cantidad)
			return estrategia;
		return null;
	}

	public PorMonto obtenerEstrategiaMonto(int cantidad, float mejorPrecio) {
		porMontoDAO.openCurrentSessionwithTransaction();
		PorMonto estrategia = porMontoDAO.dameEstrategiaPorMonto();
		porMontoDAO.closeCurrentSessionwithTransaction();
		if(estrategia.getMonto() <= (cantidad*mejorPrecio))
			return estrategia;
		return null;
	}
	
}
