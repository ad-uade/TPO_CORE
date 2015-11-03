package com.group7.service;

import java.util.ArrayList;
import java.util.List;

import com.group7.business.ItemsListaPreciosVO;
import com.group7.dao.ItemsListaPreciosDAO;
import com.group7.entity.ItemsListaPrecios;
import com.group7.entity.Rodamiento;
import com.group7.entity.enbeddable.ItemListaPreciosId;

public class ItemListaPreciosServicio {

	private static ItemListaPreciosServicio instancia;
	private static ItemsListaPreciosDAO itemsListaPreciosDAO;

	public static ItemListaPreciosServicio getInstancia() {
		if (instancia == null)
			instancia = new ItemListaPreciosServicio();
		return instancia;
	}

	private ItemListaPreciosServicio() {
		itemsListaPreciosDAO = new ItemsListaPreciosDAO();
	}

	public List<ItemsListaPrecios> VoAHibernate(List<ItemsListaPreciosVO> items) {
		List<ItemsListaPrecios> itemsH = new ArrayList<ItemsListaPrecios>();
		for (int i = 0; items.size() - 1 >= i; i++) {
			ItemsListaPrecios item = new ItemsListaPrecios();
			ItemListaPreciosId itemId = new ItemListaPreciosId();
			itemId.setNroLista(items.get(i).getNroLista());
			itemId.setRodamiento(RodamientoServicio.getInstancia().VoAHibernate(items.get(i).getRodamiento()));
			item.setId(itemId);
			item.setDescuento(items.get(i).getDescuento());
			item.setPrecioVenta(items.get(i).getPrecioVenta());
			itemsH.add(item);
		}
		return itemsH;
	}

	public List<ItemsListaPreciosVO> itemsHibernateAVO(List<ItemsListaPrecios> items) {
		List<ItemsListaPreciosVO> itemsVO = new ArrayList<ItemsListaPreciosVO>();
		for (int i = 0; items.size() - 1 >= i; i++) {
			ItemsListaPreciosVO itemVO = new ItemsListaPreciosVO();
			itemVO.setNroLista(items.get(i).getId().getNroLista());
			itemVO.setDescuento(items.get(i).getDescuento());
			itemVO.setPrecioVenta(items.get(i).getPrecioVenta());
			itemVO.setRodamiento(RodamientoServicio.getInstancia().HibernateAVo(items.get(i).getId().getRodamiento()));
			itemsVO.add(itemVO);
		}
		return itemsVO;
	}

	public ItemsListaPrecios guardarItem(int nroLista, Rodamiento rodamientoHibernate, Float precio, float descuento, String tipo) {
		ItemsListaPrecios item = new ItemsListaPrecios();
		ItemListaPreciosId itemId = new ItemListaPreciosId();
		float p = 0;
		if (tipo.equalsIgnoreCase("OFERTA")) {
			p = precio * (1 - (descuento / 100));
		} else {
			p = precio;
		}
		itemId.setNroLista(nroLista);
		itemId.setRodamiento(rodamientoHibernate);
		item.setId(itemId);
		item.setPrecioVenta(p);
		item.setDescuento(descuento);
		itemsListaPreciosDAO.openCurrentSessionwithTransaction();
		itemsListaPreciosDAO.persistir(item);
		itemsListaPreciosDAO.closeCurrentSessionwithTransaction();
		return item;
	}

}
