package com.group7.service;

import java.util.ArrayList;
import java.util.List;

import com.group7.business.ItemOrdenPedidoVO;
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

	public static ItemOrdenPedidoServicio getInstancia() {
		if (instancia == null)
			instancia = new ItemOrdenPedidoServicio();
		return instancia;
	}

	private ItemOrdenPedidoServicio() {
		itemOrdenPedidoDAO = new ItemOrdenPedidoDAO();
	}

	public void guardarItems(ItemCotizacion itemCotizacion, OrdenPedido ordenDePedido) {
		itemOrdenPedidoDAO.openCurrentSessionwithTransaction();
		ItemOrdenPedido item = new ItemOrdenPedido();
		ItemOrdenPedidoId itemId = new ItemOrdenPedidoId();
		itemId.setNroOrdenPedido(ordenDePedido.getIdOrdenPedido());
		itemId.setRodamiento(itemCotizacion.getId().getRodamiento());
		item.setId(itemId);
		item.setCantidad(itemCotizacion.getCantidad());
		item.setProveedor(itemCotizacion.getItemProveedor());
		itemOrdenPedidoDAO.persistir(item);
		itemOrdenPedidoDAO.closeCurrentSessionwithTransaction();
	}

	public void updateEstados(int nroOrdenPedido, Rodamiento rodamiento) {
		itemOrdenPedidoDAO.openCurrentSessionwithTransaction();
		itemOrdenPedidoDAO.actualizarEstado(nroOrdenPedido, rodamiento);
		itemOrdenPedidoDAO.closeCurrentSessionwithTransaction();
	}

	public List<ItemOrdenPedido> itemsFalse(int idOrdenPedido) {
		itemOrdenPedidoDAO.openCurrentSessionwithTransaction();
		List<ItemOrdenPedido> itemsEstado = itemOrdenPedidoDAO.dameItemsConEstadoFalse(idOrdenPedido);
		itemOrdenPedidoDAO.closeCurrentSessionwithTransaction();
		return itemsEstado;
	}

	public List<ItemOrdenPedido> dameTemporales(int nroOrdenPedido, Proveedor proveedor) {
		itemOrdenPedidoDAO.openCurrentSessionwithTransaction();
		List<ItemOrdenPedido> items = itemOrdenPedidoDAO.dameItemsTemporales(nroOrdenPedido, proveedor);
		itemOrdenPedidoDAO.closeCurrentSessionwithTransaction();
		return items;
	}
	
	public List<ItemOrdenPedido> VoAHibernate(List<ItemOrdenPedidoVO> items) {
		List<ItemOrdenPedido> itemsH = new ArrayList<ItemOrdenPedido>();
		for(int i = 0; items.size() - 1>= i; i++){
			ItemOrdenPedido itemH = new ItemOrdenPedido();
			ItemOrdenPedidoId itemId = new ItemOrdenPedidoId();
			itemId.setNroOrdenPedido(items.get(i).getNroOrdenPedido());
			itemId.setRodamiento(RodamientoServicio.getInstancia().VoAHibernate(items.get(i).getRodamiento()));
			itemH.setId(itemId);
			itemH.setCantidad(items.get(i).getCantidad());
			itemH.setEstado(items.get(i).isEstado());
			itemH.setProveedor(ProveedorServicio.getInstancia().VoAHibernate(items.get(i).getProveedor()));
			itemsH.add(itemH);
		}
		return itemsH;
	}

	public List<ItemOrdenPedidoVO> HibernateAVo(List<ItemOrdenPedido> items) {
		List<ItemOrdenPedidoVO> itemsVO = new ArrayList<ItemOrdenPedidoVO>();
		for(int i = 0; items.size() - 1 >= i; i++){
			ItemOrdenPedidoVO itemVO = new ItemOrdenPedidoVO();
			itemVO.setNroOrdenPedido(items.get(i).getId().getNroOrdenPedido());
			itemVO.setEstado(items.get(i).isEstado());
			itemVO.setCantidad(items.get(i).getCantidad());
			itemVO.setProveedor(ProveedorServicio.getInstancia().HibernateAVo(items.get(i).getProveedor()));
			itemVO.setRodamiento(RodamientoServicio.getInstancia().HibernateAVo(items.get(i).getId().getRodamiento()));
			itemsVO.add(itemVO);
		}
		return itemsVO;
	}

}
