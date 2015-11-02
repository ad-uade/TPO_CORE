package com.group7.service;

import java.util.List;

import com.group7.dao.ItemCotizacionDAO;
import com.group7.entity.ItemCotizacion;

public class ItemCotizacionServicio {

	private static ItemCotizacionServicio instancia;
	private static ItemCotizacionDAO itemCotizacionDAO;
	
	public static ItemCotizacionServicio getInstancia(){
		if(instancia == null)
			instancia = new ItemCotizacionServicio();
		return instancia;
	}
	
	public ItemCotizacionServicio() {
		itemCotizacionDAO = new ItemCotizacionDAO();
	}

	
	public void actualizarItems(ItemCotizacion itemCotizacion) {
		itemCotizacionDAO.actualizar(itemCotizacion);
	}

	public List<ItemCotizacion> dameItems(int id) {
		List<ItemCotizacion> items = itemCotizacionDAO.dameItemsCotizacion(id);
		return items;
	}
}
