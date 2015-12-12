package com.group7.service;

import java.util.List;

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
	
	/**
	 * 
	 * @param cotizacion
	 * @param itemsComparativaPreciosVO
	 * @param itemSolicitudCotizacion
	 */
	public void guardarItem(Cotizacion cotizacion, ItemsComparativaPreciosVO itemsComparativaPreciosVO, ItemSolicitudCotizacion itemSolicitudCotizacion) {
		PorVolumen estrategy = EstrategiaDescuentoClienteServicio.getInstancia().obtenerEstrategiaPorVolumen(itemSolicitudCotizacion.getCantidad());
		PorMonto estrategyMonto = EstrategiaDescuentoClienteServicio.getInstancia().obtenerEstrategiaMonto(itemSolicitudCotizacion.getCantidad(), itemsComparativaPreciosVO.getMejorPrecio());
		float precio = 0;
		/*
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
		*/
		ItemCotizacion item = new ItemCotizacion();
		ItemCotizacionId itemId = new ItemCotizacionId();
		
		Rodamiento rodamiento = RodamientoServicio.getInstancia().viewToModel(itemsComparativaPreciosVO.getRodamiento());
		Proveedor itemProveedor = ProveedorServicio.getInstancia().viewToModel(itemsComparativaPreciosVO.getProveedor());
		itemId.setIdCotizacion(cotizacion);
		itemId.setRodamiento(rodamiento);
		item.setId(itemId);
		item.setEstrategyPorMonto(estrategyMonto);
		item.setEstrategyPorVolumen(estrategy);
		item.setCantidad(itemSolicitudCotizacion.getCantidad());
		item.setPrecioUnitario(precio);
		item.setItemProveedor(itemProveedor);
		item.setEstado("PENDIENTE DE APROBACION");
		itemCotizacionDAO.openCurrentSessionwithTransaction();
		itemCotizacionDAO.persistir(item);
		itemCotizacionDAO.closeCurrentSessionwithTransaction();
	}
	
	/**
	 * 
	 * @param itemCotizacion
	 */
	public void actualizarItems(ItemCotizacion itemCotizacion) {
		itemCotizacionDAO.openCurrentSessionwithTransaction();
		itemCotizacionDAO.actualizar(itemCotizacion);
		itemCotizacionDAO.closeCurrentSessionwithTransaction();  
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public List<ItemCotizacion> buscarItems(int id) {
		itemCotizacionDAO.openCurrentSessionwithTransaction();
		List<ItemCotizacion> items = itemCotizacionDAO.dameItemsCotizacion(id);
		itemCotizacionDAO.closeCurrentSessionwithTransaction();
		return items;
	}
	
}
