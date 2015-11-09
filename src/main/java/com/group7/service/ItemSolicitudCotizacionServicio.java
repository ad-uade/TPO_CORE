package com.group7.service;

import java.util.ArrayList;
import java.util.List;

import com.group7.business.ItemSolicitudCotizacionVO;
import com.group7.dao.ItemSolicitudCotizacionDAO;
import com.group7.entity.CondicionVenta;
import com.group7.entity.ItemSolicitudCotizacion;
import com.group7.entity.Rodamiento;
import com.group7.entity.SolicitudCotizacion;
import com.group7.entity.enbeddable.ItemSolicitudCotizacionId;

public class ItemSolicitudCotizacionServicio {

	private static ItemSolicitudCotizacionServicio instancia;
	private static ItemSolicitudCotizacionDAO itemSolicitudCotizacionDAO;
	
	public static ItemSolicitudCotizacionServicio getInstancia(){
		if(instancia == null)
			instancia = new ItemSolicitudCotizacionServicio();
		return instancia;
	}
	
	private ItemSolicitudCotizacionServicio(){
		itemSolicitudCotizacionDAO = new ItemSolicitudCotizacionDAO();
	}

	/**
	 * 
	 * @param sc
	 * @param rodamiento
	 * @param cantidad
	 * @param con
	 */
	public void guardarItem(SolicitudCotizacion sc, Rodamiento rodamiento, Integer cantidad, CondicionVenta con) {
		  ItemSolicitudCotizacion item = new ItemSolicitudCotizacion();
		  ItemSolicitudCotizacionId idItem = new ItemSolicitudCotizacionId();
		  idItem.setNroSolicitudCotizacion(sc.getNroSolicitudCotizacion());
		  idItem.setRodamiento(rodamiento);
		  item.setId(idItem);
		  item.setCantidad(cantidad);
		  item.setCondicion(con);
		  itemSolicitudCotizacionDAO.openCurrentSessionwithTransaction();
		  itemSolicitudCotizacionDAO.persistir(item);
		  itemSolicitudCotizacionDAO.closeCurrentSessionwithTransaction();
	}

	public List<ItemSolicitudCotizacionVO> itemsHibernateAVo(List<ItemSolicitudCotizacion> items) {
		List<ItemSolicitudCotizacionVO> itemsVO = new ArrayList<ItemSolicitudCotizacionVO>();
		
		for(int i = 0; items.size() - 1>= i; i++){
			ItemSolicitudCotizacionVO itemVO = new ItemSolicitudCotizacionVO();
			itemVO.setNroSolicitudCotizacion(items.get(i).getId().getNroSolicitudCotizacion());
			itemVO.setCantidad(items.get(i).getCantidad());
			itemVO.setRodamiento(RodamientoServicio.getInstancia().HibernateAVo(items.get(i).getId().getRodamiento()));
			itemVO.setCondicion(CondicionVentaServicio.getInstancia().convertirAVO(items.get(i).getCondicion()));
			itemsVO.add(itemVO);
		}
		return itemsVO;
	}

	public ItemSolicitudCotizacionVO itemHibernateAVo(ItemSolicitudCotizacion itemSolicitudCotizacion) {
		ItemSolicitudCotizacionVO it = new ItemSolicitudCotizacionVO();
		it.setNroSolicitudCotizacion(itemSolicitudCotizacion.getId().getNroSolicitudCotizacion());
		it.setRodamiento(RodamientoServicio.getInstancia().HibernateAVo(itemSolicitudCotizacion.getId().getRodamiento()));
		it.setCantidad(itemSolicitudCotizacion.getCantidad());
		return it;
	}
	
}