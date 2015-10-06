package com.group7.service;

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

	private ItemListaPreciosServicio(){
		itemsListaPreciosDAO = new ItemsListaPreciosDAO();
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
		itemsListaPreciosDAO.persistir(item);
		return item;
	}
}
