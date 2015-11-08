package com.group7.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.group7.business.ClienteVO;
import com.group7.business.CondicionVentaVO;
import com.group7.business.ItemSolicitudCotizacionVO;
import com.group7.business.RodamientoVO;
import com.group7.business.SolicitudCotizacionVO;
import com.group7.dao.ItemSolicitudCotizacionDAO;
import com.group7.dao.SolicitudCotizacionDAO;
import com.group7.entity.Cliente;
import com.group7.entity.CondicionVenta;
import com.group7.entity.Rodamiento;
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
	
	public void generarSolicitudCotizacion(ClienteVO cliente,List<RodamientoVO> rodamientos, List<Integer> cantidades,	List<CondicionVentaVO> condiciones){  
		Calendar fechaActual = Calendar.getInstance();
		Date fecha = fechaActual.getTime();
		List<Rodamiento> rodamientosNegocio = new ArrayList<Rodamiento>();
		SolicitudCotizacion SCHibernate = new SolicitudCotizacion();

		Cliente clienteH = ClienteServicio.getInstancia().convertir(cliente);

		SCHibernate.setCliente(clienteH);
		SCHibernate.setFecha(fecha);
		solicitudCotizacionDAO.openCurrentSessionwithTransaction();
		solicitudCotizacionDAO.persistir(SCHibernate);
		solicitudCotizacionDAO.closeCurrentSessionwithTransaction();
		for (int k = 0; rodamientos.size() - 1 >= k; k++) {
			Rodamiento rodamiento = RodamientoServicio.getInstancia().VoAHibernate(rodamientos.get(k));
			rodamientosNegocio.add(rodamiento);
		}

		List<CondicionVenta> condicionesH = CondicionVentaServicio.getInstancia().VoAHibernate(condiciones);
		int i = 0;
		while (rodamientosNegocio.size() - 1 >= i) {
			for (int j = 0; cantidades.size() - 1 >= j; j++) {
				ItemSolicitudCotizacionServicio.getInstancia().guardarItem(SCHibernate, rodamientosNegocio.get(i),cantidades.get(j), condicionesH.get(i));
				i++;
			}
		}
	}

	public SolicitudCotizacionVO dameSolicitud(int nroSolicitud) {
		solicitudCotizacionDAO.openCurrentSessionwithTransaction();
		SolicitudCotizacion solicitud = solicitudCotizacionDAO.buscarPorId(nroSolicitud);
		SolicitudCotizacionVO solicitudVO = this.HibernateAVo(solicitud);
		solicitudCotizacionDAO.closeCurrentSessionwithTransaction();
		return solicitudVO;
	}

	public List<SolicitudCotizacionVO> dameSolicitudes() {
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
