package com.group7.service;

import java.util.ArrayList;
import java.util.List;

import com.group7.business.ItemSolicitudCotizacionVO;
import com.group7.business.SolicitudCotizacionVO;
import com.group7.dao.ItemSolicitudCotizacionDAO;
import com.group7.dao.SolicitudCotizacionDAO;
import com.group7.entity.SolicitudCotizacion;

public class SolicitudCotizacionServicio {

	private static SolicitudCotizacionDAO solicitudCotizacionDAO;
	private static SolicitudCotizacionServicio instancia;
	
	public static SolicitudCotizacionServicio getInstancia(){
		if(instancia == null)
			instancia = new SolicitudCotizacionServicio();
		return instancia;
	}
	
	private SolicitudCotizacionServicio() {
		solicitudCotizacionDAO = new SolicitudCotizacionDAO();
	}

	public SolicitudCotizacionVO HibernateAVo(SolicitudCotizacion sc) {
		SolicitudCotizacionVO solicitud = new SolicitudCotizacionVO();
		solicitud.setNroSolicitudCotizacion(sc.getNroSolicitudCotizacion());
		solicitud.setFecha(sc.getFecha());
		solicitud.setCliente(ClienteServicio.getInstancia().clienteToVO(sc.getCliente()));
		solicitud.setItems(ItemSolicitudCotizacionServicio.getInstancia().itemsHibernateAVo(sc.getItems()));
		return solicitud;
	}

	public SolicitudCotizacion VoAHibernate(SolicitudCotizacionVO SC) {
		ItemSolicitudCotizacionDAO itemsDAO = new ItemSolicitudCotizacionDAO();
		SolicitudCotizacion solicitud = new SolicitudCotizacion();
		solicitud.setNroSolicitudCotizacion(SC.getNroSolicitudCotizacion());
		solicitud.setCliente(ClienteServicio.getInstancia().convertir(SC.getCliente()));
		solicitud.setFecha(SC.getFecha());
		solicitud.setItems(itemsDAO.getItems(SC.getNroSolicitudCotizacion()));
		return solicitud;
	}
	
	public void generarSolicitud(SolicitudCotizacionVO solicitudCotizacionVO){  
		solicitudCotizacionDAO.openCurrentSessionwithTransaction();
		SolicitudCotizacion solicitudCotizacion = new SolicitudCotizacion(solicitudCotizacionVO);
		solicitudCotizacionDAO.persistir(solicitudCotizacion);
		solicitudCotizacionDAO.closeCurrentSessionwithTransaction();
	}
	
	public void generarSolicitud(SolicitudCotizacion solicitudCotizacion){  
		solicitudCotizacionDAO.openCurrentSessionwithTransaction();
		solicitudCotizacionDAO.persistir(solicitudCotizacion);
		solicitudCotizacionDAO.closeCurrentSessionwithTransaction();
	}

	/**
	 * 
	 * @param nroSolicitud
	 * @return
	 */
	public SolicitudCotizacionVO buscarSolicitud(int nroSolicitud) {
		solicitudCotizacionDAO.openCurrentSessionwithTransaction();
		SolicitudCotizacion solicitud = solicitudCotizacionDAO.buscarPorId(nroSolicitud);
		SolicitudCotizacionVO solicitudVO = this.HibernateAVo(solicitud);
		solicitudCotizacionDAO.closeCurrentSessionwithTransaction();
		return solicitudVO;
	}

	public List<SolicitudCotizacionVO> obtenerSolicitudes() {
		solicitudCotizacionDAO.openCurrentSessionwithTransaction();
		List<SolicitudCotizacion> solicitudes = solicitudCotizacionDAO.buscarTodos();
		List<SolicitudCotizacionVO> solicitudesVO = new ArrayList<SolicitudCotizacionVO>();
		for (SolicitudCotizacion solicitud : solicitudes){
			SolicitudCotizacionVO sc = this.HibernateAVo(solicitud);
			List<ItemSolicitudCotizacionVO> itemsSolicitud = new ArrayList<ItemSolicitudCotizacionVO>();
			for(int j=0; j<solicitud.getItems().size(); j++){
				ItemSolicitudCotizacionVO it = ItemSolicitudCotizacionServicio.getInstancia().itemHibernateAVo(solicitud.getItems().get(j));
				itemsSolicitud.add(it);
			}
			sc.setItems(itemsSolicitud);
			solicitudesVO.add(sc);
		}
		solicitudCotizacionDAO.closeCurrentSessionwithTransaction();
		return solicitudesVO;
	}
	
}
