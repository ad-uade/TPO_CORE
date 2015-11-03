package com.group7.service;

import com.group7.dao.ItemFacturaDAO;
import com.group7.entity.Cotizacion;
import com.group7.entity.Factura;
import com.group7.entity.ItemFactura;
import com.group7.entity.ItemRemito;
import com.group7.entity.RemitoExterior;
import com.group7.entity.Rodamiento;
import com.group7.entity.enbeddable.ItemFacturaId;

public class ItemFacturaServicio {
	
	private static ItemFacturaServicio instancia;
	private static ItemFacturaDAO itemFacturaDAO;

	public static ItemFacturaServicio getInstancia() {
		if (instancia == null)
			instancia = new ItemFacturaServicio();
		return instancia;
	}

	private ItemFacturaServicio(){
		itemFacturaDAO = new ItemFacturaDAO();
	}
	
	public ItemFactura guardarItem(ItemRemito itemRemito, Factura factura,RemitoExterior remExterior) {
		ItemFactura item = new ItemFactura();
		ItemFacturaId itemId = new ItemFacturaId();
		itemId.setNroFactura(factura.getNroFactura());
		itemId.setRodamiento(itemRemito.getId().getRodamiento());
		item.setId(itemId);
		item.setCantidad(itemRemito.getCantidad());
		item.setPrecioUnitario(this.precioRodamiento(remExterior.getOP().getCotizacion(), itemRemito.getId().getRodamiento()));
		item.setCondVenta(CondicionVentaServicio.getInstancia().dameCondicionVenta(remExterior.getOP().getCotizacion().getSC(),itemRemito.getId().getRodamiento()));
		itemFacturaDAO.persistir(item);
		return item;
	}

	private float precioRodamiento(Cotizacion cotizacion,Rodamiento rodamiento) {
		float precio = 0;
		for (int i = 0; cotizacion.getItems().size() - 1 >= i; i++) {
			if (cotizacion.getItems().get(i).getId().getRodamiento().getRodamientoId().getCodigoSFK().equalsIgnoreCase(rodamiento.getRodamientoId().getCodigoSFK())	&& cotizacion.getItems().get(i).getId().getRodamiento().getRodamientoId().getCodigoPieza().equalsIgnoreCase(rodamiento.getRodamientoId().getCodigoPieza())) {
				precio = cotizacion.getItems().get(i).getPrecioUnitario();
			}
		}
		return precio;
	}
}