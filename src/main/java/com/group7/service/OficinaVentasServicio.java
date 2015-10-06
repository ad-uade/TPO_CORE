package com.group7.service;

public class OficinaVentasServicio {

	private static OficinaVentasServicio instancia;

	public static OficinaVentasServicio getInstancia() {
		if (instancia == null)
			instancia = new OficinaVentasServicio();
		return instancia;
	}

}
