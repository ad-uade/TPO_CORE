package com.group7.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.group7.dao.FacturaDAO;
import com.group7.entity.Factura;
import com.group7.entity.ItemFactura;
import com.group7.entity.Remito;

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

	public void persist(Factura entity) {
		facturaDAO.openCurrentSessionwithTransaction();
		facturaDAO.persistir(entity);
		facturaDAO.closeCurrentSessionwithTransaction();
	}

	public void update(Factura entity) {
		facturaDAO.openCurrentSessionwithTransaction();
		facturaDAO.actualizar(entity);
		facturaDAO.closeCurrentSessionwithTransaction();
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

	public void borrar(String id) {
		facturaDAO.openCurrentSessionwithTransaction();
		Factura factura = facturaDAO.buscarPorId(Integer.valueOf(id));
		facturaDAO.borrar(factura);
		facturaDAO.closeCurrentSessionwithTransaction();
	}

	public List<Factura> findAll() {
		facturaDAO.openCurrentSession();
		List<Factura> facturas = facturaDAO.buscarTodos();
		facturaDAO.closeCurrentSession();
		return facturas;
	}

	public void deleteAll() {
		facturaDAO.openCurrentSessionwithTransaction();
		facturaDAO.borrarTodos();
		facturaDAO.closeCurrentSessionwithTransaction();
	}

	public void actualizarPrecioTotal(Factura factura) {
		facturaDAO.openCurrentSessionwithTransaction();
		float precio = 0;
		for (ItemFactura item : factura.getItems()){
			precio = precio + (item.getPrecioUnitario() * item.getCantidad());
		}
		facturaDAO.actualizarPrecioFactura(factura, precio);
		facturaDAO.closeCurrentSessionwithTransaction();
	}

	public void guardarFactura(Remito remExterior) {
		Calendar fechaActual = Calendar.getInstance();
		Date fecha = fechaActual.getTime();

		Factura factura = new Factura();
		factura.setFecha(fecha);
		factura.setCliente(remExterior.getCliente());
		factura.setRemito(remExterior);
		facturaDAO.openCurrentSessionwithTransaction();
		facturaDAO.persistir(factura);
		facturaDAO.closeCurrentSessionwithTransaction();
		
		List<ItemFactura> itemsFactura = new ArrayList<ItemFactura>();
		for (int i = 0; remExterior.getItems().size() - 1 >= i; i++) {
			ItemFactura item = ItemFacturaServicio.getInstancia().guardarItem(remExterior.getItems().get(i), factura,remExterior);
			itemsFactura.add(item);
		}
		factura.setItems(itemsFactura);
		this.actualizarPrecioTotal(factura);
	}
	
}
