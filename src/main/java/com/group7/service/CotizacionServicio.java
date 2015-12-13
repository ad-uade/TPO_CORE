package com.group7.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.group7.business.CotizacionVO;
import com.group7.business.ItemCotizacionVO;
import com.group7.dao.CotizacionDAO;
import com.group7.entity.ComparativaPrecios;
import com.group7.entity.Cotizacion;
import com.group7.entity.ItemComparativaPrecio;
import com.group7.entity.ItemCotizacion;
import com.group7.entity.ItemSolicitudCotizacion;
import com.group7.entity.SolicitudCotizacion;

public class CotizacionServicio{

	private static CotizacionDAO cotizacionDAO;
	private static CotizacionServicio instancia;

	public static CotizacionServicio getInstancia() {
		if (instancia == null)
			instancia = new CotizacionServicio();
		return instancia;
	}
	
	private CotizacionServicio() {
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

	public Cotizacion buscarCotizacionPorCuil(Long CUIL) {
		cotizacionDAO.openCurrentSession();
		Cotizacion cotizacion = cotizacionDAO.buscarPorCuil(CUIL);
		cotizacionDAO.closeCurrentSession();
		return cotizacion;
	}
	
	public Cotizacion buscarPorId(String id) {
		cotizacionDAO.openCurrentSession();
		Cotizacion cotizacion = cotizacionDAO.buscarPorCuil(Long.valueOf(id));
		cotizacionDAO.closeCurrentSession();
		return cotizacion;
	}

	public void borrar(String id) {
		cotizacionDAO.openCurrentSessionwithTransaction();
		Cotizacion cotizacion = cotizacionDAO.buscarPorCuil(Long.valueOf(id));
		cotizacionDAO.borrar(cotizacion);
		cotizacionDAO.closeCurrentSessionwithTransaction();
	}

	/**
	 * 
	 * @param solicitud
	 * @param diasValidez
	 */
	public void altaCotizacion(SolicitudCotizacion solicitud, int diasValidez) {
		Cotizacion cotizacion = new Cotizacion();
		cotizacion.setCliente(solicitud.getCliente());
		cotizacion.setDiasValidez(diasValidez);
		cotizacion.setFecha(Calendar.getInstance().getTime());
		cotizacion.setSolicitudCotizacion(solicitud);
		for (ItemSolicitudCotizacion itemSolicitud : solicitud.getItems()){
			ComparativaPrecios comparativaPrecios = new ComparativaPrecios();
			ItemComparativaPrecio itemComparativaPrecio = comparativaPrecios.getMejorPrecio(itemSolicitud);
			cotizacion.add(itemSolicitud.getId().getRodamiento(), itemSolicitud.getCantidad(), itemComparativaPrecio.getProveedor(), itemComparativaPrecio.getMejorPrecio());
		}
	}
	
	public CotizacionVO buscarCotizacion(int nroCotizacion) {
		cotizacionDAO.openCurrentSessionwithTransaction();
		Cotizacion cotizacionHibernate = cotizacionDAO.buscarPorId(nroCotizacion);
		cotizacionDAO.closeCurrentSessionwithTransaction();
		return cotizacionHibernate.getView();
	}

	/**
	 * 
	 * @return
	 */
	public List<CotizacionVO> buscarCotizaciones() {
		cotizacionDAO.openCurrentSessionwithTransaction();
		List<CotizacionVO> cotizacionesVO = new ArrayList<CotizacionVO>();
		List<Cotizacion> cotizaciones = cotizacionDAO.buscarTodos();
		for(Cotizacion cotizacion :cotizaciones){ 
			CotizacionVO coti = cotizacion.getView();
			for (ItemCotizacion items : cotizacion.getItems()){
				List<ItemCotizacionVO> colleccion = new ArrayList<ItemCotizacionVO>();
				colleccion.add(items.getView());
			}
			cotizacionesVO.add(coti);
		}
		cotizacionDAO.closeCurrentSessionwithTransaction();
		return cotizacionesVO;
	}
	
}
