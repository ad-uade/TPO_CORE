package com.group7.service;

import com.group7.dao.CasaCentralDAO;
import com.group7.entity.CasaCentral;

public class CasaCentralServicio {

	private static CasaCentralServicio instancia;
	
	public static CasaCentralServicio getInstancia(){
		if(instancia == null)
			instancia = new CasaCentralServicio();
		return instancia;
	}

	public CasaCentral obtenerCasaCentral() {
		CasaCentralDAO miDAO = new CasaCentralDAO();
		CasaCentral casa = miDAO.dameCasaCentral();
		return casa;
	}
	
}
