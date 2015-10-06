package com.group7.service;

import java.util.Calendar;
import java.util.Date;

import com.group7.dao.OrdenPedidoDAO;
import com.group7.entity.Cotizacion;
import com.group7.entity.OrdenPedido;

public class OrdenPedidoServicio {

	private static OrdenPedidoServicio instancia;
	
	public static OrdenPedidoServicio getInstancia(){
		if(instancia == null)
			instancia = new OrdenPedidoServicio();
		return instancia;
	}
		
	public void guardarOrdenPedido(Cotizacion cotizacionH) {
		  OrdenPedidoDAO ordenDAO = new OrdenPedidoDAO();
		  Calendar fechaActual = Calendar.getInstance();
		  Date fecha = (Date) fechaActual.getTime();
		  
		  OrdenPedido ordenDePedido = new OrdenPedido();
		  ordenDePedido.setCliente(cotizacionH.getCliente());
		  ordenDePedido.setCotizacion(cotizacionH);
		  ordenDePedido.setEstado(false);
		  ordenDePedido.setFecha(fecha); 
		  ordenDePedido.setCasaCentral(CasaCentralServicio.getInstancia().obtenerCasaCentral());
		  ordenDAO.persistir(ordenDePedido);
		  
		  for(int i = 0; cotizacionH.getItems().size() - 1>= i; i++){
			  if(cotizacionH.getItems().get(i).getEstado().equalsIgnoreCase("APROBADO"))
				  ItemOrdenPedidoServicio.getInstancia().guardarItems(cotizacionH.getItems().get(i), ordenDePedido);
		  }
	}

	public void updateEstado(OrdenPedido op) {
		OrdenPedidoDAO opDAO = new OrdenPedidoDAO();
		opDAO.cambiarEstado(op);
	}

	public OrdenPedido dameOrden(int nroOrdenPedido) {
		return null;
	}

}
