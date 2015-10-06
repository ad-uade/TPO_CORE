package com.group7.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.group7.dao.CotizacionDAO;
import com.group7.entity.Cotizacion;
import com.group7.entity.SolicitudCotizacion;

public class CotizacionServicio{

	private static CotizacionDAO cotizacionDAO;

	public CotizacionServicio() {
		cotizacionDAO = new CotizacionDAO();
	}

	public void persist(Cotizacion entity) {
		cotizacionDAO.openCurrentSessionwithTransaction();
		cotizacionDAO.persistir(entity);
		cotizacionDAO.closeCurrentSessionwithTransaction();
	}

	public void update(Cotizacion entity) {
		cotizacionDAO.openCurrentSessionwithTransaction();
		cotizacionDAO.actualizar(entity);
		cotizacionDAO.closeCurrentSessionwithTransaction();
	}

	public Cotizacion buscarCotizacionPorCuil(Integer CUIL) {
		cotizacionDAO.openCurrentSession();
		Cotizacion cotizacion = cotizacionDAO.buscarPorId(CUIL);
		cotizacionDAO.closeCurrentSession();
		return cotizacion;
	}
	
	public Cotizacion buscarPorId(String id) {
		cotizacionDAO.openCurrentSession();
		Cotizacion cotizacion = cotizacionDAO.buscarPorId(Integer.valueOf(id));
		cotizacionDAO.closeCurrentSession();
		return cotizacion;
	}

	public void borrar(String id) {
		cotizacionDAO.openCurrentSessionwithTransaction();
		Cotizacion cotizacion = cotizacionDAO.buscarPorId(Integer.valueOf(id));
		cotizacionDAO.borrar(cotizacion);
		cotizacionDAO.closeCurrentSessionwithTransaction();
	}

	public List<Cotizacion> findAll() {
		cotizacionDAO.openCurrentSession();
		List<Cotizacion> cotizacions = cotizacionDAO.buscarTodos();
		cotizacionDAO.closeCurrentSession();
		return cotizacions;
	}

	public void deleteAll() {
		cotizacionDAO.openCurrentSessionwithTransaction();
		cotizacionDAO.borrarTodos();
		cotizacionDAO.closeCurrentSessionwithTransaction();
	}

	public void altaCotizacion(SolicitudCotizacion solicitud, int diasValidez) {
		Calendar fechaActual = Calendar.getInstance();
		Date fecha = fechaActual.getTime();
		
		Cotizacion cotizacion = new Cotizacion();
		cotizacion.setDiasValidez(diasValidez);
		cotizacion.setFecha(fecha);
		cotizacion.setCliente(solicitud.getCliente());
		cotizacion.setSC(solicitud);
		cotizacion.setODV(solicitud.getCliente().getOficinaVentas());
		cotizacionDAO.persistir(cotizacion);
	}
	
}
