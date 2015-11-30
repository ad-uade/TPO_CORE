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

	/**
	 * 
	 * @return
	 */
	public List<ItemsComparativaPrecio> dameItems() {
		itemsComparativaPrecioDAO.openCurrentSessionwithTransaction();
		List<ItemsComparativaPrecio> itemsComparativa = itemsComparativaPrecioDAO.buscarTodos();
		itemsComparativaPrecioDAO.closeCurrentSessionwithTransaction();
		return itemsComparativa;
	}

	/**
	 * 
	 * @param itemsListaPrecios
	 * @param proveedor
	 */
	public void guardarItem(ItemsListaPrecios itemsListaPrecios, Proveedor proveedor) {
		ItemsComparativaPrecio itemH = new ItemsComparativaPrecio();
		ItemComparativaPrecioId itemId = new ItemComparativaPrecioId();
		itemId.setIdComparativa(1);
		itemId.setRodamiento(itemsListaPrecios.getId().getRodamiento());
		itemH.setId(itemId);
		itemH.setMejorPrecio(itemsListaPrecios.getPrecioVenta());
		itemH.setNumeroListaPrecios(itemsListaPrecios.getId().getNroLista());
		itemH.setProveedorListaPrecios(proveedor);
		itemsComparativaPrecioDAO.openCurrentSessionwithTransaction();
		itemsComparativaPrecioDAO.persistir(itemH);
		itemsComparativaPrecioDAO.closeCurrentSessionwithTransaction();
	}

	public List<ItemsComparativaPreciosVO> itemsComparativaHAVO(List<ItemsComparativaPrecio> items) {
		List<ItemsComparativaPreciosVO> itemsListVO = new ArrayList<ItemsComparativaPreciosVO>();
		for(int i = 0; items.size() - 1 >= i; i++){
			ItemsComparativaPreciosVO itemsVO = new ItemsComparativaPreciosVO();
			RodamientoVO rodamiento = new RodamientoVO();
			ProveedorVO proveedor = new ProveedorVO();
			itemsVO.setIdComparativa(items.get(i).getId().getIdComparativa());
			rodamiento = RodamientoServicio.getInstancia().modelToView(items.get(i).getId().getRodamiento());
			itemsVO.setRodamiento(rodamiento);
			itemsVO.setMejorPrecio(items.get(i).getMejorPrecio());
			itemsVO.setNumListaPrecios(items.get(i).getNumeroListaPrecios());
			proveedor = ProveedorServicio.getInstancia().HibernateAVo(items.get(i).getProveedorListaPrecios());
			itemsVO.setProveedor(proveedor);
			itemsListVO.add(itemsVO);
		}
		return itemsListVO;
	}
	
	/**
	 * 
	 * @param itemsComparativaPrecio
	 * @param precioVenta
	 * @param lista
	 */
	public void actualizarItem(ItemsComparativaPrecio itemsComparativaPrecio, float precioVenta, ListaPrecios lista) {
		itemsComparativaPrecioDAO.openCurrentSessionwithTransaction();
		itemsComparativaPrecioDAO.actualizarItem(itemsComparativaPrecio.getId().getRodamiento(), precioVenta, lista.getProveedor(),lista.getNroLista());
		itemsComparativaPrecioDAO.closeCurrentSessionwithTransaction();
	}

	/**
	 * 
	 * @param rodamiento
	 * @return
	 */
	public boolean existe(Rodamiento rodamiento) {
		itemsComparativaPrecioDAO.openCurrentSessionwithTransaction();
		Boolean flag = itemsComparativaPrecioDAO.existeItemConRodamiento(rodamiento);
		itemsComparativaPrecioDAO.closeCurrentSessionwithTransaction();
		return flag;
	}
	
	/**
	 * 
	 * @param rodamiento
	 * @return
	 */
	public ItemsComparativaPrecio dameItemsProveedor(Rodamiento rodamiento) {
		itemsComparativaPrecioDAO.openCurrentSessionwithTransaction();
		ItemsComparativaPrecio item = itemsComparativaPrecioDAO.dameItemConProveedor(rodamiento);
		itemsComparativaPrecioDAO.closeCurrentSessionwithTransaction();
		return item;
	}

	/**
	 * 
	 * @param lista1
	 */
	public void eliminarItems(ListaPrecios lista1) {
		itemsComparativaPrecioDAO.openCurrentSessionwithTransaction();
		itemsComparativaPrecioDAO.eliminar(lista1.getNroLista());
		itemsComparativaPrecioDAO.closeCurrentSessionwithTransaction();
	}

}
