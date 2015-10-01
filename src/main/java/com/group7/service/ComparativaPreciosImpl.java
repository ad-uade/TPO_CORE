package com.group7.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.group7.business.ComparativaPreciosVO;
import com.group7.dao.ComparativaPreciosDAO;
import com.group7.entity.ComparativaPrecios;
import com.group7.entity.ItemsComparativaPrecio;
import com.group7.entity.ListaPrecios;

public class ComparativaPreciosImpl {

	private static ComparativaPreciosImpl instancia;

	public static ComparativaPreciosImpl getInstancia() {
		if (instancia == null)
			instancia = new ComparativaPreciosImpl();
		return instancia;
	}

	public ComparativaPreciosVO comparativaHibernateAVO(ComparativaPrecios comparativa) {
		ComparativaPreciosVO comparativaVO = new ComparativaPreciosVO();
		comparativaVO.setIdComparativa(comparativa.getComparativaPrecioId());
		comparativaVO.setFechaActualizacion(comparativa.getFechaActualizacion());
		return comparativaVO;
	}

	public ComparativaPrecios dameComparativa() {
		ComparativaPreciosDAO comparativaDAO = new ComparativaPreciosDAO();
		ComparativaPrecios comparativa = comparativaDAO.getComparativa();
		return comparativa;
	}

	public void actualizarComparativa(ListaPrecios lista) {
		ComparativaPreciosDAO comparativaDAO = new ComparativaPreciosDAO();
		ComparativaPrecios comparativaH = comparativaDAO.getComparativa();
		List<ItemsComparativaPrecio> itemsH = ItemComparativaPreciosImpl.getInstancia().dameItems();
		if (comparativaH == null) {
			this.publicarComparativaPrecios();
		}
		if (itemsH.size() == 0) {
			for (int i = 0; lista.getItems().size() - 1 >= i; i++) {
				ItemComparativaPreciosImpl.getInstancia().guardarItem(lista.getItems().get(i),lista.getProveedor());
			}
		} else {
			for (int i = 0; itemsH.size() - 1 >= i; i++) {
				for (int j = 0; lista.getItems().size() - 1 >= j; j++) {
					if (itemsH.get(i).getId().getRodamiento().getRodamientoId().getCodigoSFK().equalsIgnoreCase(
							lista.getItems().get(j).getId().getRodamiento().getRodamientoId().getCodigoSFK())) {
						if (itemsH.get(i).getMejorPrecio() >= lista.getItems().get(j).getPrecioVenta()) {
							ItemComparativaPreciosImpl.getInstancia().actualizarItem(itemsH.get(i),
									lista.getItems().get(j).getPrecioVenta(), lista);
						}
					}
					if (!ItemComparativaPreciosImpl.getInstancia().existe(lista.getItems().get(j).getId().getRodamiento())) {
						ItemComparativaPreciosImpl.getInstancia().guardarItem(lista.getItems().get(j),
								lista.getProveedor());
					}
				}
			}
		}
		comparativaDAO.actualizarFecha();
	}

	private void publicarComparativaPrecios() {
		Calendar fechaActual = Calendar.getInstance();
		Date fechaPublicacion = fechaActual.getTime();
		ComparativaPreciosDAO comparativaDAO = new ComparativaPreciosDAO();
		ComparativaPrecios comparativaH = new ComparativaPrecios();
		comparativaH.setComparativaPrecioId(1);
		comparativaH.setFechaActualizacion(fechaPublicacion);
		comparativaH.setItems(null);
		comparativaDAO.altaComparativaPrecios(comparativaH);
	}

}
