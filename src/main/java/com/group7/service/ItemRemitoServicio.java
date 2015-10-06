package com.group7.service;

import java.util.List;

import com.group7.dao.ItemRemitoDAO;
import com.group7.entity.ItemOrdenCompra;
import com.group7.entity.ItemOrdenPedido;
import com.group7.entity.ItemRemito;
import com.group7.entity.RemitoExterior;
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

	public void persist(ItemRemito entity) {
		itemRemitoDAO.openCurrentSessionwithTransaction();
		itemRemitoDAO.persistir(entity);
		itemRemitoDAO.closeCurrentSessionwithTransaction();
	}

	public void update(ItemRemito entity) {
		itemRemitoDAO.openCurrentSessionwithTransaction();
		itemRemitoDAO.actualizar(entity);
		itemRemitoDAO.closeCurrentSessionwithTransaction();
	}

	public ItemRemito buscarItemRemitoPorCuil(Integer CUIL) {
		itemRemitoDAO.openCurrentSession();
		ItemRemito itemRemito = itemRemitoDAO.buscarPorId(CUIL);
		itemRemitoDAO.closeCurrentSession();
		return itemRemito;
	}
	
	public ItemRemito buscarPorId(String id) {
		itemRemitoDAO.openCurrentSession();
		ItemRemito itemRemito = itemRemitoDAO.buscarPorId(Integer.valueOf(id));
		itemRemitoDAO.closeCurrentSession();
		return itemRemito;
	}

	public void borrar(String id) {
		itemRemitoDAO.openCurrentSessionwithTransaction();
		ItemRemito book = itemRemitoDAO.buscarPorId(Integer.valueOf(id));
		itemRemitoDAO.borrar(book);
		itemRemitoDAO.closeCurrentSessionwithTransaction();
	}

	public List<ItemRemito> findAll() {
		itemRemitoDAO.openCurrentSession();
		List<ItemRemito> itemRemitos = itemRemitoDAO.buscarTodos();
		itemRemitoDAO.closeCurrentSession();
		return itemRemitos;
	}

	public void deleteAll() {
		itemRemitoDAO.openCurrentSessionwithTransaction();
		itemRemitoDAO.borrarTodos();
		itemRemitoDAO.closeCurrentSessionwithTransaction();
	}

	public List<ItemRemito> buscarItems(RemitoExterior remito) {
		List<ItemRemito> items = itemRemitoDAO.dameItemsRemito(remito.getNroRemito());
		return items;
	}

	public ItemRemito guardarItemsInterior(int nroRemito, ItemOrdenCompra itemOrdenCompra) {
		ItemRemito item = new ItemRemito();
		ItemRemitoId itemId = new ItemRemitoId();
		itemId.setNroRemito(nroRemito);
		itemId.setRodamiento(itemOrdenCompra.getId().getRodamiento());
		item.setId(itemId);
		item.setCantidad(itemOrdenCompra.getCantidad());
		itemRemitoDAO.persistir(item);
		return item;
	}

	public void guardarItemExterior(int nroRemito, ItemOrdenPedido itemOrdenPedido) {
		ItemRemito itemR = new ItemRemito();
		ItemRemitoId itemId = new ItemRemitoId();
		itemId.setNroRemito(nroRemito);
		itemId.setRodamiento(itemOrdenPedido.getId().getRodamiento());
		itemR.setId(itemId);
		itemR.setCantidad(itemOrdenPedido.getCantidad());
		itemRemitoDAO.persistir(itemR);
	}

	public List<ItemRemito> dameItems(RemitoExterior remito) {
		ItemRemitoDAO itemDAO = new ItemRemitoDAO();
		List<ItemRemito> items = itemDAO.dameItemsRemito(remito.getNroRemito());
		return items;
	}

}
