package com.group7.service;

import com.group7.business.SolicitudCotizacionVO;
import com.group7.dao.SolicitudCotizacionDAO;
import com.group7.entity.SolicitudCotizacion;

public class SolicitudCotizacionServicio {

	private static SolicitudCotizacionDAO solicitudCotizacionDAO;
	private static SolicitudCotizacionServicio instancia;
	
	public static SolicitudCotizacionServicio getInstancia(){
		if(instancia == null)
			instancia = new SolicitudCotizacionServicio();
		return instancia;
	}
	
	private SolicitudCotizacionServicio() {
		solicitudCotizacionDAO = new SolicitudCotizacionDAO();
	}
	
	public void generarSolicitud(SolicitudCotizacionVO solicitudCotizacionVO){  
		SolicitudCotizacion solicitudCotizacion = new SolicitudCotizacion(solicitudCotizacionVO);
		this.generarSolicitud(solicitudCotizacion);
	}
	
	public void generarSolicitud(SolicitudCotizacion solicitudCotizacion){  
		solicitudCotizacionDAO.openCurrentSessionwithTransaction();
		solicitudCotizacionDAO.persistir(solicitudCotizacion);
		solicitudCotizacionDAO.closeCurrentSessionwithTransaction();
	}
	
}
