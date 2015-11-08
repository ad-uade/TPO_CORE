package com.group7.service;

import java.util.ArrayList;
import java.util.List;

import com.group7.business.ItemOrdenCompraVO;
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
	
	public List<ItemOrdenCompra> VoAHibernate(List<ItemOrdenCompraVO> items){
		List<ItemOrdenCompra> itemsH = new ArrayList<ItemOrdenCompra>();
		for(int i = 0; items.size() - 1 >= i; i++){
			ItemOrdenCompra item = new ItemOrdenCompra();
			ItemOrdenCompraId itemId = new ItemOrdenCompraId();
			itemId.setNroOrdenCompra(items.get(i).getNroOrdenCompra());
			itemId.setRodamiento(RodamientoServicio.getInstancia().VoAHibernate(items.get(i).getRodamiento()));
			item.setId(itemId);
			item.setCantidad(items.get(i).getCantidad());
			itemsH.add(item);
		}
		return itemsH;
	}

	public List<ItemOrdenCompraVO> HibernateAVo(List<ItemOrdenCompra> items) {
		List<ItemOrdenCompraVO> itemsVO = new ArrayList<ItemOrdenCompraVO>();
		for(int i = 0; items.size() - 1 >= i; i++){
			ItemOrdenCompraVO itemVO = new ItemOrdenCompraVO();
			itemVO.setNroOrdenCompra(items.get(i).getId().getNroOrdenCompra());
			itemVO.setRodamiento(RodamientoServicio.getInstancia().HibernateAVo(items.get(i).getId().getRodamiento()));
			itemVO.setCantidad(items.get(i).getCantidad());
			itemsVO.add(itemVO);
		}
		return itemsVO;
	}
	
	public void guardarItem(int nroOrdenCompra, ItemsComparativaPrecio item, Integer cantidad) {
		ItemOrdenCompra itemOrden = new ItemOrdenCompra();
		ItemOrdenCompraId itemId = new ItemOrdenCompraId();
		itemId.setNroOrdenCompra(nroOrdenCompra);
		itemId.setRodamiento(item.getId().getRodamiento());
		itemOrden.setId(itemId);
		itemOrden.setCantidad(cantidad);
		itemOrdenCompraDAO.openCurrentSessionwithTransaction();
		itemOrdenCompraDAO.persistir(itemOrden);
		itemOrdenCompraDAO.closeCurrentSessionwithTransaction();
	}

	public void guardar(int nroOrdenCompra, ItemOrdenPedido itemOrdenPedido) {
		ItemOrdenCompra item = new ItemOrdenCompra();
		ItemOrdenCompraId itemId = new ItemOrdenCompraId();
		itemId.setNroOrdenCompra(nroOrdenCompra);
		itemId.setRodamiento(itemOrdenPedido.getId().getRodamiento());
		item.setId(itemId);
		item.setCantidad(itemOrdenPedido.getCantidad());
		itemOrdenCompraDAO.openCurrentSessionwithTransaction();
		itemOrdenCompraDAO.persistir(item);
		itemOrdenCompraDAO.closeCurrentSessionwithTransaction();
	}
}
