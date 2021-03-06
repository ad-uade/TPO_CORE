package com.group7.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.group7.business.ComparativaPreciosVO;
import com.group7.dao.ComparativaPreciosDAO;
import com.group7.entity.ComparativaPrecios;
import com.group7.entity.ItemsComparativaPrecio;
import com.group7.entity.ListaPrecios;

public class ComparativaPreciosServicio {

	private static ComparativaPreciosDAO comparativaPrecioDao;
	private static ComparativaPreciosServicio instancia;
	
	public static ComparativaPreciosServicio getInstancia(){
		if(instancia == null)
			instancia = new ComparativaPreciosServicio();
		return instancia;
	}
	
	private ComparativaPreciosServicio() {
		comparativaPrecioDao = new ComparativaPreciosDAO();
	}
	
	public void persist(ComparativaPrecios entity) {
		comparativaPrecioDao.openCurrentSessionwithTransaction();
		comparativaPrecioDao.persistir(entity);
		comparativaPrecioDao.closeCurrentSessionwithTransaction();
	}

	public void update(ComparativaPrecios entity) {
		comparativaPrecioDao.openCurrentSessionwithTransaction();
		comparativaPrecioDao.actualizar(entity);
		comparativaPrecioDao.closeCurrentSessionwithTransaction();
	}

	public ComparativaPrecios dameComparativa() {
		comparativaPrecioDao.openCurrentSessionwithTransaction();
		ComparativaPrecios comparativa = comparativaPrecioDao.getComparativa();
		comparativaPrecioDao.closeCurrentSessionwithTransaction();
		return comparativa;
	}

	public void actualizarComparativa(ListaPrecios lista) {
		comparativaPrecioDao.openCurrentSessionwithTransaction();
		ComparativaPrecios comparativaH = comparativaPrecioDao.getComparativa();
		List<ItemsComparativaPrecio> itemsH = ItemComparativaPreciosServicio.getInstancia().dameItems();
		if (comparativaH == null) {
			this.publicarComparativaPrecios();
		}
		if (itemsH.size() == 0) {
			for (int i = 0; lista.getItems().size() - 1 >= i; i++) {
				ItemComparativaPreciosServicio.getInstancia().guardarItem(lista.getItems().get(i),lista.getProveedor());
			}
		} else {
			for (int i = 0; itemsH.size() - 1 >= i; i++) {
				for (int j = 0; lista.getItems().size() - 1 >= j; j++) {
					if (itemsH.get(i).getId().getRodamiento().getRodamientoId().getCodigoSFK().equalsIgnoreCase(lista.getItems().get(j).getId().getRodamiento().getRodamientoId().getCodigoSFK())) {
						if (itemsH.get(i).getMejorPrecio() >= lista.getItems().get(j).getPrecioVenta()) {
							ItemComparativaPreciosServicio.getInstancia().actualizarItem(itemsH.get(i), lista.getItems().get(j).getPrecioVenta(), lista);
						}
					}
					if (!ItemComparativaPreciosServicio.getInstancia().existe(lista.getItems().get(j).getId().getRodamiento())) {
						ItemComparativaPreciosServicio.getInstancia().guardarItem(lista.getItems().get(j), lista.getProveedor());
					}
				}
			}
		}
		//comparativaPrecioDao.actualizarFecha();
		comparativaPrecioDao.closeCurrentSessionwithTransaction();
	}
	
	public ComparativaPreciosVO modelToView(ComparativaPrecios comparativa) {
		ComparativaPreciosVO comparativaVO = new ComparativaPreciosVO();
		comparativaVO.setIdComparativa(comparativa.getComparativaPrecioId());
		comparativaVO.setFechaActualizacion(comparativa.getFechaActualizacion());
		return comparativaVO;
	}

	private void publicarComparativaPrecios() {
		Calendar fechaActual = Calendar.getInstance();
		Date fechaPublicacion = fechaActual.getTime();
		ComparativaPrecios comparativaH = new ComparativaPrecios();
		comparativaH.setComparativaPrecioId(1);
		comparativaH.setFechaActualizacion(fechaPublicacion);
		comparativaH.setItems(null);
		this.persist(comparativaH);
	}

}
