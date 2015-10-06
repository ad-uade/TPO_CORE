package com.group7.service;

import com.group7.dao.ItemSolicitudCotizacionDAO;
import com.group7.entity.CondicionVenta;
import com.group7.entity.ItemSolicitudCotizacion;
import com.group7.entity.Rodamiento;
import com.group7.entity.SolicitudCotizacion;
import com.group7.entity.enbeddable.ItemSolicitudCotizacionId;

public class ItemSolicitudCotizacionServicio {

	private static ItemSolicitudCotizacionServicio instancia;
	private static ItemSolicitudCotizacionDAO itemSolicitudCotizacionDAO;
	
	public static ItemSolicitudCotizacionServicio getInstancia(){
		if(instancia == null)
			instancia = new ItemSolicitudCotizacionServicio();
		return instancia;
	}
	
	private ItemSolicitudCotizacionServicio(){
		itemSolicitudCotizacionDAO = new ItemSolicitudCotizacionDAO();
	}

	public void guardarItem(SolicitudCotizacion sc, Rodamiento rodamiento, Integer cantidad, CondicionVenta con) {
		  ItemSolicitudCotizacion item = new ItemSolicitudCotizacion();
		  ItemSolicitudCotizacionId idItem = new ItemSolicitudCotizacionId();
		  idItem.setNroSolicitudCotizacion(sc.getNroSolicitudCotizacion());
		  idItem.setRodamiento(rodamiento);
		  item.setId(idItem);
		  item.setCantidad(cantidad);
		  item.setCondicion(con);
		  itemSolicitudCotizacionDAO.persistir(item);
	}

}
