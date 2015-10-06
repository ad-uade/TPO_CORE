package com.group7.service;

import com.group7.dao.ItemOrdenCompraDAO;
import com.group7.entity.ItemOrdenCompra;
import com.group7.entity.ItemOrdenPedido;
import com.group7.entity.ItemsComparativaPrecio;
import com.group7.entity.enbeddable.ItemOrdenCompraId;

public class ItemOrdenCompraServicio {

	private static ItemOrdenCompraServicio instancia;
	private static ItemOrdenCompraDAO itemOrdenCompraDAO;
	
	public static ItemOrdenCompraServicio getInstancia(){
		if(instancia == null)
			instancia = new ItemOrdenCompraServicio();
		return instancia;
	}

	private ItemOrdenCompraServicio(){
		itemOrdenCompraDAO = new ItemOrdenCompraDAO();
	}
	
	public void guardarItem(int nroOrdenCompra, ItemsComparativaPrecio item, Integer cantidad) {
		ItemOrdenCompra itemOrden = new ItemOrdenCompra();
		ItemOrdenCompraId itemId = new ItemOrdenCompraId();
		itemId.setNroOrdenCompra(nroOrdenCompra);
		itemId.setRodamiento(item.getId().getRodamiento());
		itemOrden.setId(itemId);
		itemOrden.setCantidad(cantidad);
		itemOrdenCompraDAO.persistir(itemOrden);
	}

	public void guardarlos(int nroOrdenCompra, ItemOrdenPedido itemOrdenPedido) {
		ItemOrdenCompra item = new ItemOrdenCompra();
		ItemOrdenCompraId itemId = new ItemOrdenCompraId();
		itemId.setNroOrdenCompra(nroOrdenCompra);
		itemId.setRodamiento(itemOrdenPedido.getId().getRodamiento());
		item.setId(itemId);
		item.setCantidad(itemOrdenPedido.getCantidad());
		itemOrdenCompraDAO.persistir(item);
	}
}
