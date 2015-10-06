package com.group7.service;

public class ListaPreciosServicio {
	
	private static ListaPreciosServicio instancia;

	public static ListaPreciosServicio getInstancia() {
		if (instancia == null)
			instancia = new ListaPreciosServicio();
		return instancia;
	}

}
