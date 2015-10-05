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

	private static CotizacionImpl instancia;
	
	public static CotizacionImpl getInstancia(){
		if(instancia == null)
			instancia = new CotizacionImpl();
		return instancia;
	}

	public CotizacionVO HibernateAVo(Cotizacion cotizacion) {
		CotizacionVO cotizacionVO = new CotizacionVO();
		cotizacionVO.setNroCotizacion(cotizacion.getId());
		cotizacionVO.setDiasValidez(cotizacion.getDiasValidez());
		cotizacionVO.setFecha(cotizacion.getFecha());
		cotizacionVO.setCliente(ClienteServicio.getInstancia().clienteToVO(cotizacion.getCliente()));
		cotizacionVO.setSolicitud(SolicitudCotizacionServicio.getInstancia().solicitudCotizacionToVo(cotizacion.getSC()));
		cotizacionVO.setItems(ItemCotizacionServicio.getInstancia().HibernateAVo(cotizacion.getItems()));
		return cotizacionVO;
	}

	public void altaCotizacion(SolicitudCotizacion solicitud, int diasValidez) {
		Calendar fechaActual = Calendar.getInstance();
		Date fecha = fechaActual.getTime();
		
		CotizacionDAO cotizacionDAO = new CotizacionDAO();
		
		Cotizacion cotizacion = new Cotizacion();
		cotizacion.setDiasValidez(diasValidez);
		cotizacion.setFecha(fecha);
		cotizacion.setCliente(solicitud.getCliente());
		cotizacion.setSC(solicitud);
		cotizacion.setODV(solicitud.getCliente().getOficinaVentas());
		cotizacionDAO.generarCotizacion(cotizacion);
		
		ComparativaPrecios comparativa = ComparativaPreciosServicio.getInstancia().dameComparativa();
		List<ItemsComparativaPrecio> itemsComparativa = ItemComparativaPreciosServicio.getInstancia().dameItems();
		
		ComparativaPreciosVO comparativaVO = ComparativaPreciosServicio.getInstancia().comparativaHibernateAVO(comparativa);
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
	
	public void revisarCotizacion(CotizacionVO cotizacion) {
		Cotizacion cotizacionH = this.VoAHibernate(cotizacion);
		for (int i = 0; cotizacionH.getItems().size() - 1 >= i; i++) {
			ItemCotizacionServicio.getInstancia().actualizarItems(cotizacionH.getItems().get(i));
		}
	}

	public Cotizacion VoAHibernate(CotizacionVO cotizacion) {
		Cotizacion cot = new Cotizacion();
		cot.setId(cotizacion.getNroCotizacion());
		cot.setDiasValidez(cotizacion.getDiasValidez());
		cot.setFecha(cotizacion.getFecha());
		cot.setSC(SolicitudCotizacionServicio.getInstancia().VoAHibernate(cotizacion.getSolicitud()));
		cot.setCliente(ClienteServicio.getInstancia().clienteVOtoCliente(cotizacion.getCliente()));
		cot.setItems(ItemCotizacionServicio.getInstancia().VoAHibernate(cotizacion.getItems()));
		return cot;
	}

	public CotizacionVO dameCotizacion(int nroCotizacion) {
		CotizacionDAO cotizacionDAO = new CotizacionDAO();
		Cotizacion cotizacionHibernate = cotizacionDAO.dameCotizacion(nroCotizacion);
		if (cotizacionHibernate == null)
			return null;
		CotizacionVO cotizacionVO = this.HibernateAVo(cotizacionHibernate);
		return cotizacionVO;
	}

	public List<CotizacionVO> cotizaciones() {
		CotizacionDAO cotizacion = new CotizacionDAO();
		List<CotizacionVO> cotizaciones = new ArrayList<CotizacionVO>();
		List<Cotizacion> cotizacionesHibernate = cotizacion.dameCotizaciones();
		
		for(int i = 0; i<cotizacionesHibernate.size(); i++){ 
			List<ItemCotizacion> items = ItemCotizacionServicio.getInstancia().dameItems(cotizacionesHibernate.get(i).getId());
			cotizacionesHibernate.get(i).setItems(items);
			CotizacionVO coti = this.HibernateAVo(cotizacionesHibernate.get(i));
			cotizaciones.add(coti);
		}
		return cotizaciones;
	}
	
	
	
}
