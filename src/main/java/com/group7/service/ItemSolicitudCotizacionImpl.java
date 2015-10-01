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

public class ItemSolicitudCotizacionImpl {

	private static ItemSolicitudCotizacionImpl instancia;
	
	public static ItemSolicitudCotizacionImpl getInstancia(){
		if(instancia == null)
			instancia = new ItemSolicitudCotizacionImpl();
		return instancia;
	}

	public List<ItemSolicitudCotizacionVO> itemsHibernateAVo(List<ItemSolicitudCotizacion> items) {
		List<ItemSolicitudCotizacionVO> itemsVO = new ArrayList<ItemSolicitudCotizacionVO>();
		
		for(int i = 0; items.size() - 1>= i; i++){
			ItemSolicitudCotizacionVO itemVO = new ItemSolicitudCotizacionVO();
			itemVO.setNroSolicitudCotizacion(items.get(i).getId().getNroSolicitudCotizacion());
			itemVO.setCantidad(items.get(i).getCantidad());
			itemVO.setRodamiento(RodamientoImpl.getInstancia().rodamientoToVo(items.get(i).getId().getRodamiento()));
			itemVO.setCondicion(CondicionVentaImpl.getInstancia().HibernateAVo(items.get(i).getCondicion()));
			itemsVO.add(itemVO);
		}
		return itemsVO;
	}

	public void guardarItem(SolicitudCotizacion sc, Rodamiento rodamiento, Integer cantidad, CondicionVenta con) {
		  ItemSolicitudCotizacionDAO itemDAO = new ItemSolicitudCotizacionDAO();
		  ItemSolicitudCotizacion item = new ItemSolicitudCotizacion();
		  ItemSolicitudCotizacionId idItem = new ItemSolicitudCotizacionId();
		  idItem.setNroSolicitudCotizacion(sc.getNroSolicitudCotizacion());
		  idItem.setRodamiento(rodamiento);
		  item.setId(idItem);
		  item.setCantidad(cantidad);
		  item.setCondicion(con);
		  itemDAO.altaItem(item);
	}

	public ItemSolicitudCotizacionVO itemHibernateAVo(ItemSolicitudCotizacion itemSolicitudCotizacion) {
		ItemSolicitudCotizacionVO it = new ItemSolicitudCotizacionVO();
		it.setNroSolicitudCotizacion(itemSolicitudCotizacion.getId().getNroSolicitudCotizacion());
		it.setRodamiento(RodamientoImpl.getInstancia().rodamientoToVo(itemSolicitudCotizacion.getId().getRodamiento()));
		it.setCantidad(itemSolicitudCotizacion.getCantidad());
		return it;
	}
}
