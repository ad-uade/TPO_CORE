package com.group7.service;

import java.util.ArrayList;
import java.util.List;

import com.group7.business.ItemCotizacionVO;
import com.group7.business.ItemsComparativaPreciosVO;
import com.group7.dao.ItemCotizacionDAO;
import com.group7.entity.Contado;
import com.group7.entity.Cotizacion;
import com.group7.entity.CuentaCorriente;
import com.group7.entity.ItemCotizacion;
import com.group7.entity.ItemSolicitudCotizacion;
import com.group7.entity.PorMonto;
import com.group7.entity.PorVolumen;
import com.group7.entity.Proveedor;
import com.group7.entity.Rodamiento;
import com.group7.entity.enbeddable.ItemCotizacionId;

public class ItemCotizacionServicio {

	private static ItemCotizacionServicio instancia;
	private static ItemCotizacionDAO itemCotizacionDAO;
	
	public static ItemCotizacionServicio getInstancia(){
		if(instancia == null)
			instancia = new ItemCotizacionServicio();
		return instancia;
	}
	
	private ItemCotizacionServicio() {
		itemCotizacionDAO = new ItemCotizacionDAO();
	}
	
	public List<ItemCotizacionVO> HibernateAVo(List<ItemCotizacion> items) {
		List<ItemCotizacionVO> itemsVO = new ArrayList<ItemCotizacionVO>();
		for(int i = 0; items.size() - 1>= i; i++){
			ItemCotizacionVO itemVO = new ItemCotizacionVO();
			itemVO.setNroCotizacion(items.get(i).getId().getIdCotizacion());
			itemVO.setCantidad(items.get(i).getCantidad());
			itemVO.setPrecio(items.get(i).getPrecioUnitario());
			itemVO.setEstado(items.get(i).getEstado());
			itemVO.setRodamiento(RodamientoServicio.getInstancia().HibernateAVo(items.get(i).getId().getRodamiento()));
			itemVO.setProveedor(ProveedorServicio.getInstancia().HibernateAVo(items.get(i).getItemProveedor()));
			itemsVO.add(itemVO);
		}
		return itemsVO;
	}

	public void guardarItem(Cotizacion cotizacion, ItemsComparativaPreciosVO itemsComparativaPreciosVO, ItemSolicitudCotizacion itemSolicitudCotizacion) {
		PorVolumen estrategy = EstrategiaDescuentoClienteServicio.getInstancia().obtenerEstrategia(itemSolicitudCotizacion.getCantidad());
		PorMonto estrategyMonto = EstrategiaDescuentoClienteServicio.getInstancia().obtenerEstrategiaMonto(itemSolicitudCotizacion.getCantidad(), itemsComparativaPreciosVO.getMejorPrecio());
		float precio = 0;
		if(itemSolicitudCotizacion.getCondicion().getFormaPago().getDescripcion().equalsIgnoreCase("cuenta corriente")){
			CuentaCorriente cuenta = FormaPagoServicio.getInstancia().obtenerCuentaCorriente();
			if(estrategy != null && estrategyMonto != null){
				precio = (itemsComparativaPreciosVO.getMejorPrecio() * (1 - (estrategy.getDescuento()/100)) * (1 - (estrategyMonto.getDescuento()/100)));
				precio = precio + (precio * ((100 + cuenta.getRecargo())/100));
			}
			else if (estrategy != null && estrategyMonto == null){
				precio = itemsComparativaPreciosVO.getMejorPrecio() * (1 - (estrategy.getDescuento()/100));
				precio = precio + (precio * ((100 + cuenta.getRecargo())/100));
			}
			else if (estrategy == null && estrategyMonto != null){
				precio = itemsComparativaPreciosVO.getMejorPrecio() * (1 - (estrategyMonto.getDescuento()/100));
				precio = precio + (precio * ((100 + cuenta.getRecargo())/100));
			}
			else
				precio = itemsComparativaPreciosVO.getMejorPrecio() + ((itemsComparativaPreciosVO.getMejorPrecio()) * ((100 + cuenta.getRecargo())/100));
		}else{
			Contado contado = FormaPagoServicio.getInstancia().obtenerPagoContado();
			if(estrategy != null && estrategyMonto != null){
				precio = (itemsComparativaPreciosVO.getMejorPrecio() * (1 - (estrategy.getDescuento()/100)) * (1 - (estrategyMonto.getDescuento()/100)));
				precio = precio - (precio * (1 - (contado.getDescuento())/100));
			}
			else if (estrategy != null && estrategyMonto == null){
				precio = itemsComparativaPreciosVO.getMejorPrecio() * (1 - (estrategy.getDescuento()/100));
				precio = precio - (precio * (1 - (contado.getDescuento()/100)));
			}
			else if (estrategy == null && estrategyMonto != null){
				precio = itemsComparativaPreciosVO.getMejorPrecio() * (1 - (estrategyMonto.getDescuento()/100));
				precio = precio + (precio * (1 - (contado.getDescuento()/100)));
			}
			else
				precio = itemsComparativaPreciosVO.getMejorPrecio() - ((itemsComparativaPreciosVO.getMejorPrecio()) * (1 - (contado.getDescuento()/100)));
		}
		ItemCotizacion item = new ItemCotizacion();
		ItemCotizacionId itemId = new ItemCotizacionId();
		
		Rodamiento rodamiento = RodamientoServicio.getInstancia().VoAHibernate(itemsComparativaPreciosVO.getRodamiento());
		Proveedor itemProveedor = ProveedorServicio.getInstancia().VoAHibernate(itemsComparativaPreciosVO.getProveedor());
		itemId.setIdCotizacion(cotizacion.getId());
		itemId.setRodamiento(rodamiento);
		item.setId(itemId);
		item.setCantidad(itemSolicitudCotizacion.getCantidad());
		item.setEstado("PENDIENTE DE APROBACION");
		item.setPrecioUnitario(precio);
		item.setItemProveedor(itemProveedor);
		item.setEstrategyPorMonto(estrategyMonto);
		item.setEstrategyPorVolumen(estrategy);
		itemCotizacionDAO.openCurrentSessionwithTransaction();
		itemCotizacionDAO.persistir(item);
		itemCotizacionDAO.closeCurrentSessionwithTransaction();
	}
	
	public List<ItemCotizacion> VoAHibernate(List<ItemCotizacionVO> items) {
		List<ItemCotizacion> itemsH = new ArrayList<ItemCotizacion>();
		for(int i = 0; items.size() - 1 >= i; i++){
		   ItemCotizacion item = new ItemCotizacion();
		   ItemCotizacionId itemId = new ItemCotizacionId();
		   itemId.setIdCotizacion(items.get(i).getNroCotizacion());
		   itemId.setRodamiento(RodamientoServicio.getInstancia().VoAHibernate(items.get(i).getRodamiento()));
		   item.setId(itemId);
		   item.setCantidad(items.get(i).getCantidad());
		   item.setEstado(items.get(i).getEstado());
		   item.setItemProveedor(ProveedorServicio.getInstancia().VoAHibernate(items.get(i).getProveedor()));
		   item.setPrecioUnitario(items.get(i).getPrecio());
		   itemsH.add(item);
		}
		return itemsH;
	}

	public void actualizarItems(ItemCotizacion itemCotizacion) {
		itemCotizacionDAO.openCurrentSessionwithTransaction();
		itemCotizacionDAO.actualizarEstado(itemCotizacion);
		itemCotizacionDAO.closeCurrentSessionwithTransaction();  
	}

	public List<ItemCotizacion> dameItems(int id) {
		itemCotizacionDAO.openCurrentSessionwithTransaction();
		List<ItemCotizacion> items = itemCotizacionDAO.dameItemsCotizacion(id);
		itemCotizacionDAO.closeCurrentSessionwithTransaction();
		return items;
	}
	
}
