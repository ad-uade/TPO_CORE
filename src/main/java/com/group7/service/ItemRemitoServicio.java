package com.group7.service;

import java.util.ArrayList;
import java.util.List;

import com.group7.business.ItemRemitoVO;
import com.group7.dao.ItemRemitoDAO;
import com.group7.entity.ItemOrdenCompra;
import com.group7.entity.ItemOrdenPedido;
import com.group7.entity.ItemRemito;
import com.group7.entity.Remito;
import com.group7.entity.enbeddable.ItemRemitoId;

public class ItemRemitoServicio {

	private static ItemRemitoDAO itemRemitoDAO;
	private static ItemRemitoServicio instancia;

	public static ItemRemitoServicio getInstancia() {
		if (instancia == null)
			instancia = new ItemRemitoServicio();
		return instancia;
	}
	
	private ItemRemitoServicio() {
		itemRemitoDAO = new ItemRemitoDAO();
	}

	/**
	 * 
	 * @param entity
	 */
	public void persist(ItemRemito entity) {
		itemRemitoDAO.openCurrentSessionwithTransaction();
		itemRemitoDAO.persistir(entity);
		itemRemitoDAO.closeCurrentSessionwithTransaction();
	}

	/**
	 * 
	 * @param remito
	 * @return
	 */
	public List<ItemRemito> buscarItems(Remito remito) {
		itemRemitoDAO.openCurrentSessionwithTransaction();
		List<ItemRemito> list = itemRemitoDAO.dameItemsRemito(remito.getNroRemito());
		itemRemitoDAO.closeCurrentSessionwithTransaction();
		return list;
	}
	
	public List<ItemRemito> VoAHibernate(List<ItemRemitoVO> items) {
		List<ItemRemito> itemsH = new ArrayList<ItemRemito>();
		for(int i = 0; items.size() - 1 >= i; i++){
			ItemRemito item = new ItemRemito();
			ItemRemitoId itemId = new ItemRemitoId();
			itemId.setNroRemito(items.get(i).getNroRemito());
			itemId.setRodamiento(RodamientoServicio.getInstancia().VoAHibernate(items.get(i).getRodamiento()));
			item.setId(itemId);
			item.setCantidad(items.get(i).getCantidad());
			itemsH.add(item);
		}
		return itemsH;
	}

	/**
	 * 
	 * @param items
	 * @return
	 */
	public List<ItemRemitoVO> HibernateAVo(List<ItemRemito> items) {
		List<ItemRemitoVO> itemsVO = new ArrayList<ItemRemitoVO>();
		for(int i = 0; items.size() - 1 >= i; i++){
			ItemRemitoVO item = new ItemRemitoVO();
			item.setNroRemito(items.get(i).getId().getNroRemito());
			item.setRodamiento(RodamientoServicio.getInstancia().HibernateAVo(items.get(i).getId().getRodamiento()));
			item.setCantidad(items.get(i).getCantidad());
			itemsVO.add(item);
		}
		return itemsVO;
	}

	public ItemRemito guardarItemsInterior(int nroRemito, ItemOrdenCompra itemOrdenCompra) {
		ItemRemito item = new ItemRemito();
		ItemRemitoId itemId = new ItemRemitoId();
		itemId.setNroRemito(nroRemito);
		itemId.setRodamiento(itemOrdenCompra.getId().getRodamiento());
		item.setId(itemId);
		item.setCantidad(itemOrdenCompra.getCantidad());
		this.persist(item);
		return item;
	}

	/**
	 * 
	 * @param nroRemito
	 * @param itemOrdenPedido
	 */
	public void guardarItemExterior(int nroRemito, ItemOrdenPedido itemOrdenPedido) {
		ItemRemito itemR = new ItemRemito();
		ItemRemitoId itemId = new ItemRemitoId();
		itemId.setNroRemito(nroRemito);
		itemId.setRodamiento(itemOrdenPedido.getId().getRodamiento());
		itemR.setId(itemId);
		itemR.setCantidad(itemOrdenPedido.getCantidad());
		this.persist(itemR);
	}
	
}
