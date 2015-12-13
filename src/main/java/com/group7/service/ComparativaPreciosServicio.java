package com.group7.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.group7.dao.ComparativaPreciosDAO;
import com.group7.entity.ComparativaPrecios;
import com.group7.entity.ItemComparativaPrecio;
import com.group7.entity.ListaPrecios;
import com.group7.entity.Rodamiento;

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
		List<ItemComparativaPrecio> itemsH = comparativaH.getItems();
		comparativaPrecioDao.closeCurrentSessionwithTransaction();
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

	public ItemComparativaPrecio buscarMejorPrecio(Rodamiento rodamiento) {
		return null;
	}

}
