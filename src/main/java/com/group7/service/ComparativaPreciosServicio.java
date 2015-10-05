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

	public ComparativaPreciosServicio() {
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

	public ComparativaPrecios buscarPorId(Integer id) {
		comparativaPrecioDao.openCurrentSession();
		ComparativaPrecios comparativaPrecios = comparativaPrecioDao.buscarPorId(id);
		comparativaPrecioDao.closeCurrentSession();
		return comparativaPrecios;
	}
	
	public ComparativaPrecios buscarPorId(String id) {
		comparativaPrecioDao.openCurrentSession();
		ComparativaPrecios comparativaPrecios = comparativaPrecioDao.buscarPorId(Integer.valueOf(id));
		comparativaPrecioDao.closeCurrentSession();
		return comparativaPrecios;
	}

	public void borrar(String id) {
		comparativaPrecioDao.openCurrentSessionwithTransaction();
		ComparativaPrecios comparativaPrecios = comparativaPrecioDao.buscarPorId(Integer.valueOf(id));
		comparativaPrecioDao.borrar(comparativaPrecios);
		comparativaPrecioDao.closeCurrentSessionwithTransaction();
	}

	public List<ComparativaPrecios> findAll() {
		comparativaPrecioDao.openCurrentSession();
		List<ComparativaPrecios> comparativaPrecios = comparativaPrecioDao.buscarTodos();
		comparativaPrecioDao.closeCurrentSession();
		return comparativaPrecios;
	}

	public void deleteAll() {
		comparativaPrecioDao.openCurrentSessionwithTransaction();
		comparativaPrecioDao.borrarTodos();
		comparativaPrecioDao.closeCurrentSessionwithTransaction();
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
					if (itemsH.get(i).getId().getRodamiento().getRodamientoId().getCodigoSFK().equalsIgnoreCase(
							lista.getItems().get(j).getId().getRodamiento().getRodamientoId().getCodigoSFK())) {
						if (itemsH.get(i).getMejorPrecio() >= lista.getItems().get(j).getPrecioVenta()) {
							ItemComparativaPreciosServicio.getInstancia().actualizarItem(itemsH.get(i),
									lista.getItems().get(j).getPrecioVenta(), lista);
						}
					}
					if (!ItemComparativaPreciosServicio.getInstancia().existe(lista.getItems().get(j).getId().getRodamiento())) {
						ItemComparativaPreciosServicio.getInstancia().guardarItem(lista.getItems().get(j),
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
		ComparativaPrecios comparativaH = new ComparativaPrecios();
		comparativaH.setComparativaPrecioId(1);
		comparativaH.setFechaActualizacion(fechaPublicacion);
		comparativaH.setItems(null);
		this.persist(comparativaH);
	}

}
