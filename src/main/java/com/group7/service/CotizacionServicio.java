package com.group7.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.group7.business.ComparativaPreciosVO;
import com.group7.business.CotizacionVO;
import com.group7.business.ItemsComparativaPreciosVO;
import com.group7.dao.CotizacionDAO;
import com.group7.entity.ComparativaPrecios;
import com.group7.entity.Cotizacion;
import com.group7.entity.ItemComparativaPrecio;
import com.group7.entity.ItemCotizacion;
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
		Calendar fechaActual = Calendar.getInstance();
		Date fecha = fechaActual.getTime();
		
		Cotizacion cotizacion = new Cotizacion();
		cotizacion.setDiasValidez(diasValidez);
		cotizacion.setFecha(fecha);
		cotizacion.setCliente(solicitud.getCliente());
		cotizacion.setSolicitudCotizacion(solicitud);
		cotizacion.setOficinaVentas(solicitud.getCliente().getOficinaVentas());
		this.persist(cotizacion);
		
		ComparativaPrecios comparativa = ComparativaPreciosServicio.getInstancia().dameComparativa();
		List<ItemComparativaPrecio> itemsComparativa = ItemComparativaPreciosServicio.getInstancia().dameItems();
		
		ComparativaPreciosVO comparativaVO = ComparativaPreciosServicio.getInstancia().modelToView(comparativa);
		List<ItemsComparativaPreciosVO> itemsVO = ItemComparativaPreciosServicio.getInstancia().itemsComparativaHAVO(itemsComparativa);
		comparativaVO.setItems(itemsVO);
		
		for(int i = 0; comparativaVO.getItems().size() - 1>= i; i++){
			for(int j = 0; solicitud.getItems().size() - 1>= j; j++){
				if(comparativaVO.getItems().get(i).getRodamiento().getCodigoSFK().equalsIgnoreCase(solicitud.getItems().get(j).getId().getRodamiento().getRodamientoId().getCodigoSFK())){
					ItemCotizacionServicio.getInstancia().guardarItem(cotizacion, comparativaVO.getItems().get(i), solicitud.getItems().get(j));
				}
			}
		}
	}
	
	/**
	 * 
	 * @param cotizacionVO
	 */
	public void actualizarCotizacion(CotizacionVO cotizacionVO) {
		Cotizacion cotizacion = new Cotizacion(cotizacionVO);
		for (int i = 0; cotizacion.getItems().size() - 1 >= i; i++) {
			ItemCotizacionServicio.getInstancia().actualizarItems(cotizacion.getItems().get(i));
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
			List<ItemCotizacion> items = ItemCotizacionServicio.getInstancia().buscarItems(cotizacion.getId());
			cotizacion.setItems(items);
			CotizacionVO coti = cotizacion.getView();
			cotizacionesVO.add(coti);
		}
		cotizacionDAO.closeCurrentSessionwithTransaction();
		return cotizacionesVO;
	}
	
}
