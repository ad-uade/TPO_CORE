package com.group7.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.group7.business.ListaPreciosVO;
import com.group7.business.ProveedorVO;
import com.group7.business.RodamientoVO;
import com.group7.dao.ListaPreciosDAO;
import com.group7.entity.ItemsListaPrecios;
import com.group7.entity.ListaPrecios;
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
	
	public ListaPrecios VoAHibernate(ListaPreciosVO listaVO) {
		ListaPrecios lista = new ListaPrecios();
		lista.setNroLista(listaVO.getNroLista());
		lista.setFechaPublicacion(listaVO.getFechaPublicacion());
		lista.setEstado(listaVO.isEstado());
		lista.setTipo(listaVO.getTipo());
		lista.setVigencia(listaVO.getVigencia());
		lista.setProveedor(ProveedorServicio.getInstancia().VoAHibernate(listaVO.getProveedor()));
		lista.setItems(ItemListaPreciosServicio.getInstancia().VoAHibernate(listaVO.getItems()));
		return lista;
	}

	private ListaPreciosVO listaHibernateAVO(ListaPrecios lista) {
		ListaPreciosVO listaVO = new ListaPreciosVO();
		listaVO.setNroLista(lista.getNroLista());
		listaVO.setFechaPublicacion(lista.getFechaPublicacion());
		listaVO.setTipo(lista.getTipo());
		listaVO.setVigencia(lista.getVigencia());
		listaVO.setEstado(lista.isEstado());
		listaVO.setProveedor(ProveedorServicio.getInstancia().HibernateAVo(lista.getProveedor()));
		listaVO.setItems(ItemListaPreciosServicio.getInstancia().itemsHibernateAVO(lista.getItems()));
		return listaVO;
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
	public ListaPreciosVO generarLista(ProveedorVO proveedor, List<RodamientoVO> rodamientos, List<Float> precios, Float descuento, String tipo, String vigencia) {
		listaPreciosDAO.openCurrentSessionwithTransaction();
		Calendar fechaActual = Calendar.getInstance();
		Date fechaPublicacion = fechaActual.getTime();
		ListaPrecios lista = new ListaPrecios();
		ListaPrecios lista1 = listaPreciosDAO.existeListaConIgualTipo(proveedor.getCuilProveedor(), tipo);
		if (lista1 != null) {
			listaPreciosDAO.bajaListaPrecios(lista1);
			ItemComparativaPreciosServicio.getInstancia().eliminarItems(lista1);
		}
		lista.setProveedor(ProveedorServicio.getInstancia().VoAHibernate(proveedor));
		lista.setTipo(tipo);
		lista.setVigencia(vigencia);
		lista.setEstado(true);
		lista.setFechaPublicacion(fechaPublicacion);
		listaPreciosDAO.persistir(lista);
		listaPreciosDAO.actualizarProveedor(lista);

		List<Rodamiento> rodamientosH = new ArrayList<Rodamiento>();
		for (int k = 0; rodamientos.size() - 1 >= k; k++) {
			Rodamiento rodamiento = RodamientoServicio.getInstancia().VoAHibernate(rodamientos.get(k));
			rodamientosH.add(rodamiento);
		}

		List<ItemsListaPrecios> items = new ArrayList<ItemsListaPrecios>();
		int i = 0;
		while (rodamientosH.size() - 1 >= i) {
			for (int j = 0; precios.size() - 1 >= j; j++) {
				ItemsListaPrecios item = ItemListaPreciosServicio.getInstancia().guardarItem(lista.getNroLista(),
						rodamientosH.get(i), precios.get(j), descuento, tipo);
				items.add(item);
				i++;
			}
		}
		lista.setItems(items);
		listaPreciosDAO.closeCurrentSessionwithTransaction();
		return this.listaHibernateAVO(lista);
	}

	public ListaPreciosVO obtenerLista(Integer nro) {
		listaPreciosDAO.openCurrentSessionwithTransaction();
		ListaPrecios lista = listaPreciosDAO.getListaDePrecios(nro);
		ListaPreciosVO lp = new ListaPreciosVO();
		lp.setNroLista(lista.getNroLista());
		lp.setProveedor(ProveedorServicio.getInstancia().HibernateAVo(lista.getProveedor()));
		lp.setTipo(lista.getTipo());
		lp.setVigencia(lista.getVigencia());
		lp.setFechaPublicacion(lista.getFechaPublicacion());
		lp.setEstado(lista.isEstado());
		lp.setItems(ItemListaPreciosServicio.getInstancia().itemsHibernateAVO(lista.getItems()));
		listaPreciosDAO.closeCurrentSessionwithTransaction();
		return lp;
	}

	public List<ListaPreciosVO> obtenerListas() {
		listaPreciosDAO.openCurrentSessionwithTransaction();
		List<ListaPrecios> listas = listaPreciosDAO.buscarTodos();
		List<ListaPreciosVO> listasVO = new ArrayList<ListaPreciosVO>();
		for (int i = 0; i < listas.size(); i++) {
			ListaPreciosVO lp = new ListaPreciosVO();
			lp.setNroLista(listas.get(i).getNroLista());
			lp.setProveedor(ProveedorServicio.getInstancia().HibernateAVo(listas.get(i).getProveedor()));
			lp.setTipo(listas.get(i).getTipo());
			lp.setVigencia(listas.get(i).getVigencia());
			lp.setFechaPublicacion(listas.get(i).getFechaPublicacion());
			lp.setEstado(listas.get(i).isEstado());
			lp.setItems(ItemListaPreciosServicio.getInstancia().itemsHibernateAVO(listas.get(i).getItems())); // ManagementItemLisraPrecios
			listasVO.add(lp);
		}
		listaPreciosDAO.closeCurrentSessionwithTransaction();
		return listasVO;
	}

}
