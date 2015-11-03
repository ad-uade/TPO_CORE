package com.group7.service;

import java.util.ArrayList;
import java.util.List;

import com.group7.business.ItemsComparativaPreciosVO;
import com.group7.business.ProveedorVO;
import com.group7.business.RodamientoVO;
import com.group7.dao.ItemsComparativaPrecioDAO;
import com.group7.entity.ItemsComparativaPrecio;
import com.group7.entity.ItemsListaPrecios;
import com.group7.entity.ListaPrecios;
import com.group7.entity.Proveedor;
import com.group7.entity.Rodamiento;
import com.group7.entity.enbeddable.ItemComparativaPrecioId;

public class ItemComparativaPreciosServicio {

	private static ItemComparativaPreciosServicio instancia;
	private static ItemsComparativaPrecioDAO itemsComparativaPrecioDAO;

	public static ItemComparativaPreciosServicio getInstancia() {
		if (instancia == null)
			instancia = new ItemComparativaPreciosServicio();
		return instancia;
	}
	
	public ItemComparativaPreciosServicio() {
		itemsComparativaPrecioDAO = new ItemsComparativaPrecioDAO();
	}

	public List<ItemsComparativaPrecio> dameItems() {
		List<ItemsComparativaPrecio> itemsComparativa = itemsComparativaPrecioDAO.buscarTodos();
		return itemsComparativa;
	}

	public void guardarItem(ItemsListaPrecios itemsListaPrecios, Proveedor proveedor) {
		ItemsComparativaPrecio itemH = new ItemsComparativaPrecio();
		ItemComparativaPrecioId itemId = new ItemComparativaPrecioId();
		itemId.setIdComparativa(1);
		itemId.setRodamiento(itemsListaPrecios.getId().getRodamiento());
		itemH.setId(itemId);
		itemH.setMejorPrecio(itemsListaPrecios.getPrecioVenta());
		itemH.setNumeroListaPrecios(itemsListaPrecios.getId().getNroLista());
		itemH.setProveedorListaPrecios(proveedor);
		itemsComparativaPrecioDAO.persistir(itemH);
	}

	public List<ItemsComparativaPreciosVO> itemsComparativaHAVO(List<ItemsComparativaPrecio> items) {
		List<ItemsComparativaPreciosVO> itemsListVO = new ArrayList<ItemsComparativaPreciosVO>();
		for(int i = 0; items.size() - 1 >= i; i++){
			ItemsComparativaPreciosVO itemsVO = new ItemsComparativaPreciosVO();
			RodamientoVO rodamiento = new RodamientoVO();
			ProveedorVO proveedor = new ProveedorVO();
			itemsVO.setIdComparativa(items.get(i).getId().getIdComparativa());
			rodamiento = RodamientoServicio.getInstancia().HibernateAVo(items.get(i).getId().getRodamiento());
			itemsVO.setRodamiento(rodamiento);
			itemsVO.setMejorPrecio(items.get(i).getMejorPrecio());
			itemsVO.setNumListaPrecios(items.get(i).getNumeroListaPrecios());
			proveedor = ProveedorServicio.getInstancia().HibernateAVo(items.get(i).getProveedorListaPrecios());
			itemsVO.setProveedor(proveedor);
			itemsListVO.add(itemsVO);
		}
		return itemsListVO;
	}
	
	public void actualizarItem(ItemsComparativaPrecio itemsComparativaPrecio, float precioVenta, ListaPrecios lista) {
		itemsComparativaPrecioDAO.actualizarItem(itemsComparativaPrecio.getId().getRodamiento(), precioVenta, lista.getProveedor(),
				lista.getNroLista());
	}

	public boolean existe(Rodamiento rodamiento) {
		return itemsComparativaPrecioDAO.existeItemConRodamiento(rodamiento);
	}

	public ItemsComparativaPrecio dameItemsProveedor(Rodamiento rodamiento) {
		ItemsComparativaPrecio item = itemsComparativaPrecioDAO.dameItemConProveedor(rodamiento);
		return item;
	}

	public void eliminarItems(ListaPrecios lista1) {
		itemsComparativaPrecioDAO.eliminar(lista1.getNroLista());
	}

}
