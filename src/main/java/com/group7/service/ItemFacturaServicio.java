package com.group7.service;

import com.group7.dao.ItemFacturaDAO;
import com.group7.entity.Cotizacion;
import com.group7.entity.Factura;
import com.group7.entity.ItemCotizacion;
import com.group7.entity.ItemFactura;
import com.group7.entity.ItemRemito;
import com.group7.entity.Remito;
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
	
	public ItemFactura guardarItem(ItemRemito itemRemito, Factura factura, Remito remExterior) {
		ItemFactura item = new ItemFactura();
		ItemFacturaId itemId = new ItemFacturaId();
		itemId.setNroFactura(factura.getNroFactura());
		itemId.setRodamiento(itemRemito.getId().getRodamiento());
		item.setId(itemId);
		item.setCantidad(itemRemito.getCantidad());
		Cotizacion cotizacion = CotizacionServicio.getInstancia().buscarCotizacionPorCuil(remExterior.getCliente().getCuilCliente());
		item.setPrecioUnitario(this.precioRodamiento(cotizacion, itemRemito.getId().getRodamiento()));
		item.setCondVenta(CondicionVentaServicio.getInstancia().dameCondicionVenta(cotizacion.getSolicitudCotizacion(),itemRemito.getId().getRodamiento()));
		itemFacturaDAO.openCurrentSessionwithTransaction();
		itemFacturaDAO.persistir(item);
		itemFacturaDAO.closeCurrentSessionwithTransaction();
		return item;
	}

	private float precioRodamiento(Cotizacion cotizacion, Rodamiento rodamiento) {
		float precio = 0;
		for (ItemCotizacion item : cotizacion.getItems()) {
			if (item.getId().getRodamiento().getRodamientoId().getCodigoSFK().equalsIgnoreCase(rodamiento.getRodamientoId().getCodigoSFK())	&& item.getId().getRodamiento().getRodamientoId().getCodigoPieza().equalsIgnoreCase(rodamiento.getRodamientoId().getCodigoPieza())) {
				precio = item.getPrecioUnitario();
			}
		}
		return precio;
	}
}
