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

public class ItemCotizacionImpl {

	private static ItemCotizacionImpl instancia;
	
	public static ItemCotizacionImpl getInstancia(){
		if(instancia == null)
			instancia = new ItemCotizacionImpl();
		return instancia;
	}

	public List<ItemCotizacionVO> HibernateAVo(List<ItemCotizacion> items) {
		List<ItemCotizacionVO> itemsVO = new ArrayList<ItemCotizacionVO>();
		
		for(int i = 0; items.size() - 1>= i; i++){
			ItemCotizacionVO itemVO = new ItemCotizacionVO();
			itemVO.setNroCotizacion(items.get(i).getId().getIdCotizacion());
			itemVO.setCantidad(items.get(i).getCantidad());
			itemVO.setPrecio(items.get(i).getPrecioUnitario());
			itemVO.setEstado(items.get(i).getEstado());
			itemVO.setRodamiento(RodamientoImpl.getInstancia().rodamientoToVo(items.get(i).getId().getRodamiento()));
			itemVO.setProveedor(ProveedorImpl.getInstancia().proveedorToVo(items.get(i).getItemProveedor()));
			itemsVO.add(itemVO);
		}
		return itemsVO;
	}

	public void guardarItem(Cotizacion cotizacion, ItemsComparativaPreciosVO itemsComparativaPreciosVO, ItemSolicitudCotizacion itemSolicitudCotizacion) {
		PorVolumen estrategy = EstrategiaDescuentoClienteImpl.getInstancia().obtenerEstrategia(itemSolicitudCotizacion.getCantidad());
		PorMonto estrategyMonto = EstrategiaDescuentoClienteImpl.getInstancia().obtenerEstrategiaMonto(itemSolicitudCotizacion.getCantidad(), itemsComparativaPreciosVO.getMejorPrecio());
		float precio = 0;
		if(itemSolicitudCotizacion.getCondicion().getFormaPago().getDescripcion().equalsIgnoreCase("cuenta corriente")){
			CuentaCorriente cuenta = FormaPagoImpl.getInstancia().obtenerCuentaCorriente();
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
			Contado contado = FormaPagoImpl.getInstancia().obtenerPagoContado();
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
		ItemCotizacionDAO itemCDAO = new ItemCotizacionDAO();
		Rodamiento rodamiento = RodamientoImpl.getInstancia().rodamientoVoToRodamiento(itemsComparativaPreciosVO.getRodamiento());
		Proveedor itemProveedor = ProveedorImpl.getInstancia().VoAHibernate(itemsComparativaPreciosVO.getProveedor());
		itemId.setIdCotizacion(cotizacion.getId());
		itemId.setRodamiento(rodamiento);
		item.setId(itemId);
		item.setCantidad(itemSolicitudCotizacion.getCantidad());
		item.setEstado("PENDIENTE DE APROBACION");
		item.setPrecioUnitario(precio);
		item.setItemProveedor(itemProveedor);
		item.setEstrategyPorMonto(estrategyMonto);
		item.setEstrategyPorVolumen(estrategy);
		itemCDAO.altaItem(item);
	}
	
	
	
	public List<ItemCotizacion> VoAHibernate(List<ItemCotizacionVO> items) {
		  List<ItemCotizacion> itemsH = new ArrayList<ItemCotizacion>();
		  for(int i = 0; items.size() - 1 >= i; i++){
		   ItemCotizacion item = new ItemCotizacion();
		   ItemCotizacionId itemId = new ItemCotizacionId();
		   itemId.setIdCotizacion(items.get(i).getNroCotizacion());
		   itemId.setRodamiento(RodamientoImpl.getInstancia().rodamientoVoToRodamiento(items.get(i).getRodamiento()));
		   item.setId(itemId);
		   item.setCantidad(items.get(i).getCantidad());
		   item.setEstado(items.get(i).getEstado());
		   item.setItemProveedor(ProveedorImpl.getInstancia().VoAHibernate(items.get(i).getProveedor()));
		   item.setPrecioUnitario(items.get(i).getPrecio());
		   itemsH.add(item);
		  }
		  return itemsH;
		 }

	public void actualizarItems(ItemCotizacion itemCotizacion) {
		  ItemCotizacionDAO itemDAO = new ItemCotizacionDAO();
		  itemDAO.actualizarEstado(itemCotizacion);
	}

	public List<ItemCotizacion> dameItems(int id) {
		ItemCotizacionDAO itemDAO = new ItemCotizacionDAO();
		List<ItemCotizacion> items = itemDAO.dameItemsCotizacion(id);
		return items;
	}
}
