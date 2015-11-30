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
import com.group7.entity.ItemCotizacion;
import com.group7.entity.ItemsComparativaPrecio;
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

	public CotizacionVO modelToView(Cotizacion cotizacion) {
		CotizacionVO cotizacionVO = new CotizacionVO();
		cotizacionVO.setNroCotizacion(cotizacion.getId());
		cotizacionVO.setDiasValidez(cotizacion.getDiasValidez());
		cotizacionVO.setFecha(cotizacion.getFecha());
		cotizacionVO.setCliente(ClienteServicio.getInstancia().clienteToVO(cotizacion.getCliente()));
		cotizacionVO.setSolicitud(SolicitudCotizacionServicio.getInstancia().HibernateAVo(cotizacion.getSolicitudCotizacion()));
		cotizacionVO.setItems(ItemCotizacionServicio.getInstancia().HibernateAVo(cotizacion.getItems()));
		return cotizacionVO;
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
		List<ItemsComparativaPrecio> itemsComparativa = ItemComparativaPreciosServicio.getInstancia().dameItems();
		
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
	 * @param cotizacion
	 */
	public void actualizarCotizacion(CotizacionVO cotizacion) {
		Cotizacion cotizacionH = this.viewToModel(cotizacion);
		for (int i = 0; cotizacionH.getItems().size() - 1 >= i; i++) {
			ItemCotizacionServicio.getInstancia().actualizarItems(cotizacionH.getItems().get(i));
		}
	}

	public Cotizacion viewToModel(CotizacionVO cotizacion) {
		Cotizacion cot = new Cotizacion();
		cot.setId(cotizacion.getNroCotizacion());
		cot.setDiasValidez(cotizacion.getDiasValidez());
		cot.setFecha(cotizacion.getFecha());
		cot.setSolicitudCotizacion(SolicitudCotizacionServicio.getInstancia().VoAHibernate(cotizacion.getSolicitud()));
		cot.setCliente(ClienteServicio.getInstancia().convertir(cotizacion.getCliente()));
		cot.setItems(ItemCotizacionServicio.getInstancia().VoAHibernate(cotizacion.getItems()));
		return cot;
	}

	public CotizacionVO buscarCotizacion(int nroCotizacion) {
		cotizacionDAO.openCurrentSessionwithTransaction();
		Cotizacion cotizacionHibernate = cotizacionDAO.buscarPorId(nroCotizacion);
		cotizacionDAO.closeCurrentSessionwithTransaction();
		return this.modelToView(cotizacionHibernate);
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
			CotizacionVO coti = this.modelToView(cotizacion);
			cotizacionesVO.add(coti);
		}
		cotizacionDAO.closeCurrentSessionwithTransaction();
		return cotizacionesVO;
	}
	
}
