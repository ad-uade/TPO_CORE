package com.group7.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.group7.business.OrdenPedidoVO;
import com.group7.dao.OrdenPedidoDAO;
import com.group7.entity.Cotizacion;
import com.group7.entity.OrdenPedido;

public class OrdenPedidoServicio {

	private static OrdenPedidoServicio instancia;
	
	public static OrdenPedidoServicio getInstancia(){
		if(instancia == null)
			instancia = new OrdenPedidoServicio();
		return instancia;
	}
	
	public OrdenPedido VoHibernate(OrdenPedidoVO ordenVO){
		OrdenPedido op = new OrdenPedido();
		op.setCliente(ClienteServicio.getInstancia().clienteVOtoCliente(ordenVO.getCliente()));
		op.setIdOrdenPedido(ordenVO.getNroOrdenPedido());
		op.setEstado(ordenVO.isEstado());
		op.setFecha(ordenVO.getFecha());
		op.setItems(ItemOrdenPedidoServicio.getInstancia().VoAHibernate(ordenVO.getItems()));
		op.setCotizacion(CotizacionImpl.getInstancia().VoAHibernate(ordenVO.getCotizacion()));
		return op;
	}

	public OrdenPedidoVO HibernateAVo(OrdenPedido orden) {
		OrdenPedidoVO ordenVO = new OrdenPedidoVO();
		ordenVO.setNroOrdenPedido(orden.getIdOrdenPedido());
		ordenVO.setFecha(orden.getFecha());
		ordenVO.setEstado(orden.isEstado());
		ordenVO.setCliente(ClienteServicio.getInstancia().clienteToVO(orden.getCliente()));
		ordenVO.setItems(ItemOrdenPedidoServicio.getInstancia().HibernateAVo(orden.getItems()));
		ordenVO.setCotizacion(CotizacionImpl.getInstancia().HibernateAVo(orden.getCotizacion()));
		return ordenVO;
	}
	
	public void guardarOrdenPedido(Cotizacion cotizacionH) {
		  OrdenPedidoDAO ordenDAO = new OrdenPedidoDAO();
		  Calendar fechaActual = Calendar.getInstance();
		  Date fecha = (Date) fechaActual.getTime();
		  
		  OrdenPedido ordenDePedido = new OrdenPedido();
		  ordenDePedido.setCliente(cotizacionH.getCliente());
		  ordenDePedido.setCotizacion(cotizacionH);
		  ordenDePedido.setEstado(false);
		  ordenDePedido.setFecha(fecha); 
		  ordenDePedido.setCasaCentral(CasaCentralServicio.getInstancia().obtenerCasaCentral());
		  ordenDAO.altaOrdenPedido(ordenDePedido);
		  
		  for(int i = 0; cotizacionH.getItems().size() - 1>= i; i++){
			  if(cotizacionH.getItems().get(i).getEstado().equalsIgnoreCase("APROBADO"))
				  ItemOrdenPedidoServicio.getInstancia().guardarItems(cotizacionH.getItems().get(i), ordenDePedido);
		  }
	}

	public void updateEstado(OrdenPedido op) {
		OrdenPedidoDAO opDAO = new OrdenPedidoDAO();
		opDAO.cambiarEstado(op);
	}

	public OrdenPedidoVO dameOrdenVO(int nroOrdenPedido) {
		OrdenPedidoDAO miDAO = new OrdenPedidoDAO();
		OrdenPedido orden = miDAO.dameOrden(nroOrdenPedido);
		OrdenPedidoVO ordenVO = this.HibernateAVo(orden);
		return ordenVO;
	}

	public List<OrdenPedidoVO> dameOrdenes() {
		try{
			OrdenPedidoDAO miOrdenDePedido = new OrdenPedidoDAO();
			List<OrdenPedido> ordenes = miOrdenDePedido.getOrdenesPedido();
			List<OrdenPedidoVO> ordenesVO = new ArrayList<OrdenPedidoVO>();
			for (int i=0; i<ordenes.size();i++){
				OrdenPedidoVO op = new OrdenPedidoVO();
				op = this.HibernateAVo(ordenes.get(i));
				ordenesVO.add(op);
			}
			return ordenesVO;
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public List<OrdenPedidoVO> dameOrdenesARemitir() {
		try{
			OrdenPedidoDAO miOrdenDePedido = new OrdenPedidoDAO();
			List<OrdenPedido> ordenes = miOrdenDePedido.getOrdenesPedidoARemitir();
			List<OrdenPedidoVO> ordenesVO = new ArrayList<OrdenPedidoVO>();
			for (int i=0; i<ordenes.size();i++){
				OrdenPedidoVO op = new OrdenPedidoVO();
				op = OrdenPedidoServicio.getInstancia().HibernateAVo(ordenes.get(i));
				ordenesVO.add(op);
			}
			return ordenesVO;
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public OrdenPedido dameOrden(int nroOrdenPedido) {
		OrdenPedidoDAO miPedidoDAO = new OrdenPedidoDAO();
		OrdenPedido pedido = miPedidoDAO.dameOrden(nroOrdenPedido);
		return pedido;
	}
}
