package com.group7.service;

import java.rmi.RemoteException;
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

	private static SolicitudCotizacionServicio instancia;
	
	public static SolicitudCotizacionServicio getInstancia(){
		if(instancia == null)
			instancia = new SolicitudCotizacionServicio();
		return instancia;
	}

	public SolicitudCotizacionVO solicitudCotizacionToVo(SolicitudCotizacion sc) {
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
		solicitud.setCliente(ClienteServicio.getInstancia().clienteVOtoCliente(SC.getCliente()));
		solicitud.setFecha(SC.getFecha());
		solicitud.setItems(itemsDAO.getItems(SC.getNroSolicitudCotizacion()));
		return solicitud;
	}
	
	public void generarSolicitudCotizacion(ClienteVO cliente,List<RodamientoVO> rodamientos, List<Integer> cantidades,	List<CondicionVentaVO> condiciones) throws RemoteException { // cambiar nombre en UML 
		Calendar fechaActual = Calendar.getInstance();
		Date fecha = fechaActual.getTime();
		List<Rodamiento> rodamientosNegocio = new ArrayList<Rodamiento>();
		SolicitudCotizacion SCHibernate = new SolicitudCotizacion();
		SolicitudCotizacionDAO miSCDAO = new SolicitudCotizacionDAO();

		Cliente clienteH = ClienteServicio.getInstancia().clienteVOtoCliente(cliente);

		SCHibernate.setCliente(clienteH);
		SCHibernate.setFecha(fecha);
		SCHibernate.setODV(clienteH.getOficinaVentas());

		miSCDAO.generarSolicitud(SCHibernate);

		for (int k = 0; rodamientos.size() - 1 >= k; k++) {
			Rodamiento rodamiento = RodamientoServicio.getInstancia().rodamientoVoToRodamiento(rodamientos.get(k));
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
		SolicitudCotizacionDAO miDao = new SolicitudCotizacionDAO();
		SolicitudCotizacion solicitud = miDao.getSolicitud(nroSolicitud);
		SolicitudCotizacionVO solicitudVO = this.solicitudCotizacionToVo(solicitud);
		return solicitudVO;
	}

	public List<SolicitudCotizacionVO> dameSolicitudes() {
		try {
			SolicitudCotizacionDAO miSolicitud = new SolicitudCotizacionDAO();
			List<SolicitudCotizacion> solicitudes = miSolicitud.getSolicitudes();
			List<SolicitudCotizacionVO> solicitudesVO = new ArrayList<SolicitudCotizacionVO>();
			for (int i=0;i<solicitudes.size();i++)
			{
				SolicitudCotizacionVO sc = this.solicitudCotizacionToVo(solicitudes.get(i));
				List<ItemSolicitudCotizacionVO> itemsSolicitud = new ArrayList<ItemSolicitudCotizacionVO>();
				for(int j=0; j<solicitudes.get(i).getItems().size(); j++){
					ItemSolicitudCotizacionVO it = ItemSolicitudCotizacionServicio.getInstancia().itemHibernateAVo(solicitudes.get(i).getItems().get(j));
					itemsSolicitud.add(it);
				}
				sc.setItems(itemsSolicitud);
				
				solicitudesVO.add(sc);
			}
			return solicitudesVO;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
