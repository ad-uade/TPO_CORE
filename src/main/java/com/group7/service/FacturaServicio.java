package com.group7.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.group7.dao.FacturaDAO;
import com.group7.entity.Factura;
import com.group7.entity.ItemFactura;
import com.group7.entity.RemitoExterior;

public class FacturaServicio {
	
	private static FacturaServicio instancia;

	public static FacturaServicio getInstancia() {
		if (instancia == null)
			instancia = new FacturaServicio();
		return instancia;
	}

	public void actualizarPrecioTotal(Factura factura) {
		FacturaDAO miDAO = new FacturaDAO();
		float precio = 0;
		for (int i = 0; factura.getItems().size() - 1 >= i; i++)
			precio = precio + (factura.getItems().get(i).getPrecioUnitario() * factura.getItems().get(i).getCantidad());
		miDAO.actualizarPrecioFactura(factura, precio);
	}

	public void guardarFactura(RemitoExterior remExterior) {
		Calendar fechaActual = Calendar.getInstance();
		Date fecha = fechaActual.getTime();

		FacturaDAO miDAO = new FacturaDAO();

		Factura factura = new Factura();
		factura.setFecha(fecha);
		factura.setCliente(remExterior.getCliente());
		factura.setRemito(remExterior);
		factura.setODV(remExterior.getOP().getCliente().getOficinaVentas());
		miDAO.altaFactura(factura);

		List<ItemFactura> itemsFactura = new ArrayList<ItemFactura>();
		for (int i = 0; remExterior.getItems().size() - 1 >= i; i++) {
			ItemFactura item = ItemFacturaServicio.getInstancia().guardarItem(remExterior.getItems().get(i), factura,remExterior);
			itemsFactura.add(item);
		}
		factura.setItems(itemsFactura);
		this.actualizarPrecioTotal(factura);
	}
	
}
