package com.group7.service;

import com.group7.dao.CasaCentralDAO;
import com.group7.entity.CasaCentral;

public class CasaCentralImpl {

	private static CasaCentralImpl instancia;
	
	public static CasaCentralImpl getInstancia(){
		if(instancia == null)
			instancia = new CasaCentralImpl();
		return instancia;
	}

	public CasaCentral obtenerCasaCentral() {
		CasaCentralDAO miDAO = new CasaCentralDAO();
		CasaCentral casa = miDAO.dameCasaCentral();
		return casa;
	}
	
}
