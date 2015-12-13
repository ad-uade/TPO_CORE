package com.group7.service;

import java.util.Calendar;
import java.util.Date;

import com.group7.dao.FacturaDAO;
import com.group7.entity.Cotizacion;
import com.group7.entity.EstadoCotizacion;
import com.group7.entity.Factura;
import com.group7.entity.ItemCotizacion;
import com.group7.entity.RemitoExterior;

public class FacturaServicio {
	
	private static FacturaDAO facturaDAO;

	private static FacturaServicio instancia;
	
	public static FacturaServicio getInstancia(){
		if(instancia == null)
			instancia = new FacturaServicio();
		return instancia;
	}
	
	private FacturaServicio() {
		facturaDAO = new FacturaDAO();
	}

	public Factura buscarFacturaPorCuil(Integer CUIL) {
		facturaDAO.openCurrentSession();
		Factura factura = facturaDAO.buscarPorId(CUIL);
		facturaDAO.closeCurrentSession();
		return factura;
	}
	
	public Factura buscarPorId(String id) {
		facturaDAO.openCurrentSession();
		Factura factura = facturaDAO.buscarPorId(Integer.valueOf(id));
		facturaDAO.closeCurrentSession();
		return factura;
	}


	public void guardarFactura(Cotizacion cotizacion) {
		Calendar fechaActual = Calendar.getInstance();
		Date fecha = fechaActual.getTime();
		Factura factura = new Factura();
		factura.setFecha(fecha);
		factura.setCliente(cotizacion.getCliente());
		RemitoExterior remito = new RemitoExterior();
		for (ItemCotizacion itemCotizacion : cotizacion.getItems()){
			if (itemCotizacion.getEstadoCotizacion().equals(EstadoCotizacion.APROBADA)){
				factura.add(itemCotizacion.getId().getRodamiento(), itemCotizacion.getCantidad(), itemCotizacion.getPrecioUnitario());
				remito.add(itemCotizacion.getId().getRodamiento(), itemCotizacion.getCantidad());
			}
		}
		factura.setRemito(remito);
		facturaDAO.openCurrentSessionwithTransaction();
		facturaDAO.persistir(factura);
		facturaDAO.closeCurrentSessionwithTransaction();
	}
	
}
