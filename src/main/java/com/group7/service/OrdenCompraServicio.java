package com.group7.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.group7.dao.OrdenCompraDAO;
import com.group7.entity.ItemOrdenPedido;
import com.group7.entity.OrdenCompra;
import com.group7.entity.OrdenPedido;

public class OrdenCompraServicio {

	private static OrdenCompraServicio instancia;
	
	public static OrdenCompraServicio getInstancia(){
		if(instancia == null)
			instancia = new OrdenCompraServicio();
		return instancia;
	}

	public void generarOrden(OrdenPedido ordenDePedido) {
		Calendar fechaActual = Calendar.getInstance();
		Date fecha = fechaActual.getTime();
		
		OrdenCompraDAO miOrdenDAO = new OrdenCompraDAO();
		OrdenCompra ordenDeCompra = new OrdenCompra();
			
		for(int i = 0; ordenDePedido.getItems().size()-1 >= i; i++){
			List<ItemOrdenPedido> itemsTemp = ItemOrdenPedidoServicio.getInstancia().dameTemporales(ordenDePedido.getIdOrdenPedido(), ordenDePedido.getItems().get(i).getProveedor());
			if(itemsTemp != null){
				ordenDeCompra.setFecha(fecha);
				miOrdenDAO.persistir(ordenDeCompra);
				for(int j = 0; itemsTemp.size() - 1>= j; j++){
					ItemOrdenCompraServicio.getInstancia().guardarlos(ordenDeCompra.getNroOrdenCompra(), itemsTemp.get(j));
					miOrdenDAO.actualizarProveedor(itemsTemp.get(j).getProveedor());
				}
			}
		}
	}
}
