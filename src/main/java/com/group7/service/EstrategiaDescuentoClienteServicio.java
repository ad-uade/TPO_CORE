package com.group7.service;

import com.group7.dao.PorMontoDAO;
import com.group7.dao.PorVolumenClienteDAO;
import com.group7.entity.PorMonto;
import com.group7.entity.PorVolumen;


public class EstrategiaDescuentoClienteServicio {

	private static EstrategiaDescuentoClienteServicio instancia;
	
	public static EstrategiaDescuentoClienteServicio getInstancia(){
		if(instancia == null)
			instancia = new EstrategiaDescuentoClienteServicio();
		return instancia;
	}

	public PorVolumen obtenerEstrategia(int cantidad) {
		PorVolumenClienteDAO miDAO = new PorVolumenClienteDAO();
		PorVolumen estrategia = miDAO.dameEstrategiaPorVolumen();
		if(estrategia.getVolumen() <= cantidad)
			return estrategia;
		return null;
	}

	public PorMonto obtenerEstrategiaMonto(int cantidad, float mejorPrecio) {
		PorMontoDAO miDAO = new PorMontoDAO();
		PorMonto estrategia = miDAO.dameEstrategiaPorMonto();
		if(estrategia.getMonto() <= (cantidad*mejorPrecio))
			return estrategia;
		return null;
	}
	
	
	
}