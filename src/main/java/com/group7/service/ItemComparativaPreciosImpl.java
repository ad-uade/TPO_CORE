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

public class ItemComparativaPreciosImpl {

	private static ItemComparativaPreciosImpl instancia;

	public static ItemComparativaPreciosImpl getInstancia() {
		if (instancia == null)
			instancia = new ItemComparativaPreciosImpl();
		return instancia;
	}

	public List<ItemsComparativaPreciosVO> itemsComparativaHAVO(List<ItemsComparativaPrecio> items) {
		List<ItemsComparativaPreciosVO> itemsListVO = new ArrayList<ItemsComparativaPreciosVO>();
		for (int i = 0; items.size() - 1 >= i; i++) {
			ItemsComparativaPreciosVO itemsVO = new ItemsComparativaPreciosVO();
			RodamientoVO rodamiento = new RodamientoVO();
			ProveedorVO proveedor = new ProveedorVO();
			itemsVO.setIdComparativa(items.get(i).getId().getIdComparativa());
			rodamiento = RodamientoImpl.getInstancia().rodamientoToVo(items.get(i).getId().getRodamiento());
			itemsVO.setRodamiento(rodamiento);
			itemsVO.setMejorPrecio(items.get(i).getMejorPrecio());
			itemsVO.setNumListaPrecios(items.get(i).getNumeroListaPrecios());
			proveedor = ProveedorImpl.getInstancia().proveedorToVo(items.get(i).getProveedorListaPrecios());
			itemsVO.setProveedor(proveedor);
			itemsListVO.add(itemsVO);
		}
		return itemsListVO;
	}

	public List<ItemsComparativaPrecio> dameItems() {
		ItemsComparativaPrecioDAO itemComparativaDAO = new ItemsComparativaPrecioDAO();
		List<ItemsComparativaPrecio> itemsComparativa = itemComparativaDAO.getItemsComparativa();
		return itemsComparativa;
	}

	public void guardarItem(ItemsListaPrecios itemsListaPrecios, Proveedor proveedor) {
		ItemsComparativaPrecioDAO itemsCDAO = new ItemsComparativaPrecioDAO();
		ItemsComparativaPrecio itemH = new ItemsComparativaPrecio();
		ItemComparativaPrecioId itemId = new ItemComparativaPrecioId();
		itemId.setIdComparativa(1);
		itemId.setRodamiento(itemsListaPrecios.getId().getRodamiento());
		itemH.setId(itemId);
		itemH.setMejorPrecio(itemsListaPrecios.getPrecioVenta());
		itemH.setNumeroListaPrecios(itemsListaPrecios.getId().getNroLista());
		itemH.setProveedorListaPrecios(proveedor);
		itemsCDAO.guardarItemComparativa(itemH);
	}

	public void actualizarItem(ItemsComparativaPrecio itemsComparativaPrecio, float precioVenta, ListaPrecios lista) {
		ItemsComparativaPrecioDAO itemsCDAO = new ItemsComparativaPrecioDAO();
		itemsCDAO.actualizarItem(itemsComparativaPrecio.getId().getRodamiento(), precioVenta, lista.getProveedor(),
				lista.getNroLista());
	}

	public boolean existe(Rodamiento rodamiento) {
		ItemsComparativaPrecioDAO itemsCDAO = new ItemsComparativaPrecioDAO();
		return itemsCDAO.existeItemConRodamiento(rodamiento);
	}

	public ItemsComparativaPrecio dameItemsProveedor(Rodamiento rodamiento) {
		ItemsComparativaPrecioDAO itemCompDAO = new ItemsComparativaPrecioDAO();
		ItemsComparativaPrecio item = itemCompDAO.dameItemConProveedor(rodamiento);
		return item;
	}

	public void eliminarItems(ListaPrecios lista1) {
		ItemsComparativaPrecioDAO itemCompDAO = new ItemsComparativaPrecioDAO();
		itemCompDAO.eliminar(lista1.getNroLista());
	}

}
