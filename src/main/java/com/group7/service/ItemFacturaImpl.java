package com.group7.service;

import com.group7.dao.ItemFacturaDAO;
import com.group7.entity.Cotizacion;
import com.group7.entity.Factura;
import com.group7.entity.ItemFactura;
import com.group7.entity.ItemRemito;
import com.group7.entity.RemitoExterior;
import com.group7.entity.Rodamiento;
import com.group7.entity.enbeddable.ItemFacturaId;

public class ItemFacturaImpl {
	
	private static ItemFacturaImpl instancia;

	public static ItemFacturaImpl getInstancia() {
		if (instancia == null)
			instancia = new ItemFacturaImpl();
		return instancia;
	}

	public ItemFactura guardarItem(ItemRemito itemRemito, Factura factura,RemitoExterior remExterior) {
		ItemFacturaDAO itemDAO = new ItemFacturaDAO();
		ItemFactura item = new ItemFactura();
		ItemFacturaId itemId = new ItemFacturaId();
		itemId.setNroFactura(factura.getNroFactura());
		itemId.setRodamiento(itemRemito.getId().getRodamiento());
		item.setId(itemId);
		item.setCantidad(itemRemito.getCantidad());
		item.setPrecioUnitario(this.precioRodamiento(remExterior.getOP().getCotizacion(), itemRemito.getId().getRodamiento()));
		item.setCondVenta(CondicionVentaImpl.getInstancia().dameCondicionVenta(remExterior.getOP().getCotizacion().getSC(),itemRemito.getId().getRodamiento()));
		itemDAO.altaItemFactura(item);
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
