package com.group7.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.group7.dao.ListaPreciosDAO;
import com.group7.entity.ListaPrecios;
import com.group7.entity.Proveedor;
import com.group7.entity.Rodamiento;

public class ListaPreciosServicio {

	private static ListaPreciosDAO listaPreciosDAO;
	private static ListaPreciosServicio instancia;

	public static ListaPreciosServicio getInstancia() {
		if (instancia == null)
			instancia = new ListaPreciosServicio();
		return instancia;
	}

	private ListaPreciosServicio() {
		listaPreciosDAO = new ListaPreciosDAO();
	}

	public void persistir(ListaPrecios listaPrecios){
		listaPreciosDAO.openCurrentSessionwithTransaction();
		listaPreciosDAO.persistir(listaPrecios);
		listaPreciosDAO.closeCurrentSessionwithTransaction();
	}
	
	/**
	 * 
	 * @param proveedor
	 * @param rodamientos
	 * @param precios
	 * @param tipo
	 * @param vigencia
	 * @param descuento
	 * @return
	 */
	public ListaPrecios generarLista(Proveedor proveedor, Rodamiento rodamiento, Float precio, Float descuento, Integer vigencia) {
		listaPreciosDAO.openCurrentSessionwithTransaction();
		Calendar fechaActual = Calendar.getInstance();
		Date fechaPublicacion = fechaActual.getTime();
		ListaPrecios lista = new ListaPrecios();
//		ListaPrecios lista1 = listaPreciosDAO.existeListaConIgualTipo(proveedor.getCuilProveedor(), tipo);
//		if (lista1 != null) {
//			listaPreciosDAO.bajaListaPrecios(lista1);
//			ItemComparativaPreciosServicio.getInstancia().eliminarItems(lista1);
//		}
		lista.setProveedor(proveedor);
		lista.setVigencia(vigencia);
		lista.setEstado(true);
		lista.setFechaPublicacion(fechaPublicacion);
		listaPreciosDAO.persistir(lista);
//		listaPreciosDAO.actualizarProveedor(lista);

//		List<Rodamiento> rodamientosH = new ArrayList<Rodamiento>();
//		for (int k = 0; rodamientos.size() - 1 >= k; k++) {
//			Rodamiento rodamiento = RodamientoServicio.getInstancia().viewToModel(rodamientos.get(k));
//			rodamientosH.add(rodamiento);
//		}
//
//		List<ItemListaPrecios> items = new ArrayList<ItemListaPrecios>();
//		int i = 0;
//		while (rodamientosH.size() - 1 >= i) {
//			for (int j = 0; precios.size() - 1 >= j; j++) {
//				ItemListaPrecios item = ItemListaPreciosServicio.getInstancia().guardarItem(lista.getNroLista(), rodamientosH.get(i), precios.get(j), descuento, tipo);
//				items.add(item);
//				i++;
//			}
//		}
//		lista.setItems(items);
		listaPreciosDAO.closeCurrentSessionwithTransaction();
		return lista;
	}

	public List<ListaPrecios> obtenerListas() {
		listaPreciosDAO.openCurrentSessionwithTransaction();
		List<ListaPrecios> listas = listaPreciosDAO.buscarTodos();
		listaPreciosDAO.closeCurrentSessionwithTransaction();
		return listas;
	}

}
