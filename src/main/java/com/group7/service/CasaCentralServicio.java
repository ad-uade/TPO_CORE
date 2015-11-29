package com.group7.service;

import com.group7.dao.CasaCentralDAO;
import com.group7.entity.CasaCentral;

public class CasaCentralServicio {

	private static CasaCentralServicio instancia;
	private static CasaCentralDAO facturaDAO;
	
	public static CasaCentralServicio getInstancia(){
		if(instancia == null)
			instancia = new CasaCentralServicio();
		return instancia;
	}
	
	private CasaCentralServicio() {
		facturaDAO = new CasaCentralDAO();
	}

	public CasaCentral obtenerCasaCentral() {
		facturaDAO.openCurrentSessionwithTransaction();
		CasaCentral casaCentral = facturaDAO.dameCasaCentral();
		facturaDAO.closeCurrentSessionwithTransaction();
		return casaCentral;
	}

	public void fabricar(CasaCentral crearUnicaCasaCentral) {
		facturaDAO.openCurrentSessionwithTransaction();
		facturaDAO.persistir(crearUnicaCasaCentral);
		facturaDAO.closeCurrentSessionwithTransaction();
	}

	public void actualizar(CasaCentral cc) {
		facturaDAO.openCurrentSessionwithTransaction();
		facturaDAO.actualizar(cc);
		facturaDAO.closeCurrentSessionwithTransaction();
	}

}
