package com.group7.service;

import java.util.ArrayList;
import java.util.List;

import com.group7.business.ItemRemitoVO;
import com.group7.dao.ItemRemitoDAO;
import com.group7.entity.ItemOrdenCompra;
import com.group7.entity.ItemOrdenPedido;
import com.group7.entity.ItemRemito;
import com.group7.entity.RemitoExterior;
import com.group7.entity.enbeddable.ItemRemitoId;

public class ItemRemitoImpl {

	private static ItemRemitoImpl instancia;
	
	public static ItemRemitoImpl getInstancia(){
		if(instancia == null)
			instancia = new ItemRemitoImpl();
		return instancia;
	}

	public List<ItemRemito> VoAHibernate(List<ItemRemitoVO> items) {
		List<ItemRemito> itemsH = new ArrayList<ItemRemito>();
		for(int i = 0; items.size() - 1 >= i; i++){
			ItemRemito item = new ItemRemito();
			ItemRemitoId itemId = new ItemRemitoId();
			itemId.setNroRemito(items.get(i).getNroRemito());
			itemId.setRodamiento(RodamientoImpl.getInstancia().rodamientoVoToRodamiento(items.get(i).getRodamiento()));
			item.setId(itemId);
			item.setCantidad(items.get(i).getCantidad());
			itemsH.add(item);
		}
		return itemsH;
	}

	public List<ItemRemitoVO> HibernateAVo(List<ItemRemito> items) {
		List<ItemRemitoVO> itemsVO = new ArrayList<ItemRemitoVO>();
		for(int i = 0; items.size() - 1 >= i; i++){
			ItemRemitoVO item = new ItemRemitoVO();
			item.setNroRemito(items.get(i).getId().getNroRemito());
			item.setRodamiento(RodamientoImpl.getInstancia().rodamientoToVo(items.get(i).getId().getRodamiento()));
			item.setCantidad(items.get(i).getCantidad());
			itemsVO.add(item);
		}
		return itemsVO;
	}

	public List<ItemRemito> dameItems(RemitoExterior remito) {
		ItemRemitoDAO itemDAO = new ItemRemitoDAO();
		List<ItemRemito> items = itemDAO.dameItemsRemito(remito.getNroRemito());
		return items;
	}

	public ItemRemito guardarItemsInterior(int nroRemito, ItemOrdenCompra itemOrdenCompra) {
		ItemRemitoDAO itemDAO = new ItemRemitoDAO();
		ItemRemito item = new ItemRemito();
		ItemRemitoId itemId = new ItemRemitoId();
		itemId.setNroRemito(nroRemito);
		itemId.setRodamiento(itemOrdenCompra.getId().getRodamiento());
		item.setId(itemId);
		item.setCantidad(itemOrdenCompra.getCantidad());
		itemDAO.guardarItem(item);
		return item;
	}

	public void guardarItemExterior(int nroRemito, ItemOrdenPedido itemOrdenPedido) {
		ItemRemitoDAO itemDAO = new ItemRemitoDAO();
		ItemRemito itemR = new ItemRemito();
		ItemRemitoId itemId = new ItemRemitoId();
		itemId.setNroRemito(nroRemito);
		itemId.setRodamiento(itemOrdenPedido.getId().getRodamiento());
		itemR.setId(itemId);
		itemR.setCantidad(itemOrdenPedido.getCantidad());
		itemDAO.guardarItem(itemR);
	}
}
