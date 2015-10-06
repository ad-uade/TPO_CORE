package com.group7.service;

public class SolicitudCotizacionServicio {

	private static SolicitudCotizacionServicio instancia;
	
	public static SolicitudCotizacionServicio getInstancia(){
		if(instancia == null)
			instancia = new SolicitudCotizacionServicio();
		return instancia;
	}

	
	
}
