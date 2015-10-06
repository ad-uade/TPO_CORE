package com.group7.service;

import java.util.List;

import com.group7.dao.ItemOrdenPedidoDAO;
import com.group7.entity.ItemCotizacion;
import com.group7.entity.ItemOrdenPedido;
import com.group7.entity.OrdenPedido;
import com.group7.entity.Proveedor;
import com.group7.entity.Rodamiento;
import com.group7.entity.enbeddable.ItemOrdenPedidoId;

public class ItemOrdenPedidoServicio {

	private static ItemOrdenPedidoServicio instancia;
	private static ItemOrdenPedidoDAO itemOrdenPedidoDAO;
	
	public static ItemOrdenPedidoServicio getInstancia(){
		if(instancia == null)
			instancia = new ItemOrdenPedidoServicio();
		return instancia;
	}
	
	private ItemOrdenPedidoServicio(){
		itemOrdenPedidoDAO = new ItemOrdenPedidoDAO();
	}
	
	public void guardarItems(ItemCotizacion itemCotizacion, OrdenPedido ordenDePedido) {
		  ItemOrdenPedido item = new ItemOrdenPedido();
		  ItemOrdenPedidoId itemId = new ItemOrdenPedidoId();
		  itemId.setNroOrdenPedido(ordenDePedido.getIdOrdenPedido());
		  itemId.setRodamiento(itemCotizacion.getId().getRodamiento());
		  item.setId(itemId);
		  item.setCantidad(itemCotizacion.getCantidad());
		  item.setProveedor(itemCotizacion.getItemProveedor());
		  itemOrdenPedidoDAO.persistir(item);
	}

	public void updateEstados(int nroOrdenPedido, Rodamiento rodamiento) {
		itemOrdenPedidoDAO.actualizarEstado(nroOrdenPedido, rodamiento);
	}

	public List<ItemOrdenPedido> itemsFalse(int idOrdenPedido) {
		List<ItemOrdenPedido> itemsEstado = itemOrdenPedidoDAO.dameItemsConEstadoFalse(idOrdenPedido);
		return itemsEstado;
	}

	public List<ItemOrdenPedido> dameTemporales(int nroOrdenPedido, Proveedor proveedor) {
		List<ItemOrdenPedido> items = itemOrdenPedidoDAO.dameItemsTemporales(nroOrdenPedido, proveedor);
		return items;
	}
	
}
