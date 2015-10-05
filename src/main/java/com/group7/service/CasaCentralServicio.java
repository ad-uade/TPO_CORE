package com.group7.service;

import com.group7.dao.CasaCentralDAO;
import com.group7.dao.FacturaDAO;
import com.group7.entity.CasaCentral;
import com.group7.entity.Factura;

public class CasaCentralServicio {

	private static CasaCentralDAO facturaDAO;

	public CasaCentralServicio() {
		facturaDAO = new CasaCentralDAO();
	}

	public CasaCentral obtenerCasaCentral() {
		return facturaDAO.dameCasaCentral();
	}
	
}
