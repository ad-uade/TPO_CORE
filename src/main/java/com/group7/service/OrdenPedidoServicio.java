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

	private static OrdenPedidoDAO ordenPedidoDAO;
	private static OrdenPedidoServicio instancia;
	
	public static OrdenPedidoServicio getInstancia(){
		if(instancia == null)
			instancia = new OrdenPedidoServicio();
		return instancia;
	}
		
	private OrdenPedidoServicio() {
		ordenPedidoDAO = new OrdenPedidoDAO();
	}

	public void persist(OrdenPedido entity) {
		ordenPedidoDAO.openCurrentSessionwithTransaction();
		ordenPedidoDAO.persistir(entity);
		ordenPedidoDAO.closeCurrentSessionwithTransaction();
	}

	public void update(OrdenPedido entity) {
		ordenPedidoDAO.openCurrentSessionwithTransaction();
		ordenPedidoDAO.actualizar(entity);
		ordenPedidoDAO.closeCurrentSessionwithTransaction();
	}

	public OrdenPedido dameOrden(int nroOrdenPedido) {
		ordenPedidoDAO.openCurrentSession();
		OrdenPedido ordenPedido = ordenPedidoDAO.buscarPorId(nroOrdenPedido);
		ordenPedidoDAO.closeCurrentSession();
		return ordenPedido;
	}

	public void borrar(String id) {
		ordenPedidoDAO.openCurrentSessionwithTransaction();
		OrdenPedido ordenPedido = ordenPedidoDAO.buscarPorId(Integer.valueOf(id));
		ordenPedidoDAO.borrar(ordenPedido);
		ordenPedidoDAO.closeCurrentSessionwithTransaction();
	}
	
	public OrdenPedido VoHibernate(OrdenPedidoVO ordenVO){
		OrdenPedido op = new OrdenPedido();
		op.setCliente(ClienteServicio.getInstancia().popular(ordenVO.getCliente()));
		op.setIdOrdenPedido(ordenVO.getNroOrdenPedido());
		op.setEstado(ordenVO.isEstado());
		op.setFecha(ordenVO.getFecha());
		op.setItems(ItemOrdenPedidoServicio.getInstancia().VoAHibernate(ordenVO.getItems()));
		op.setCotizacion(CotizacionServicio.getInstancia().VoAHibernate(ordenVO.getCotizacion()));
		return op;
	}

	public OrdenPedidoVO HibernateAVo(OrdenPedido orden) {
		OrdenPedidoVO ordenVO = new OrdenPedidoVO();
		ordenVO.setNroOrdenPedido(orden.getIdOrdenPedido());
		ordenVO.setFecha(orden.getFecha());
		ordenVO.setEstado(orden.isEstado());
		ordenVO.setCliente(ClienteServicio.getInstancia().clienteToVO(orden.getCliente()));
		ordenVO.setItems(ItemOrdenPedidoServicio.getInstancia().HibernateAVo(orden.getItems()));
		ordenVO.setCotizacion(CotizacionServicio.getInstancia().HibernateAVo(orden.getCotizacion()));
		return ordenVO;
	}
	
	public void guardarOrdenPedido(Cotizacion cotizacionH) {
		  Calendar fechaActual = Calendar.getInstance();
		  Date fecha = (Date) fechaActual.getTime();
		  
		  OrdenPedido ordenDePedido = new OrdenPedido();
		  ordenDePedido.setCliente(cotizacionH.getCliente());
		  ordenDePedido.setCotizacion(cotizacionH);
		  ordenDePedido.setEstado(false);
		  ordenDePedido.setFecha(fecha); 
		  ordenDePedido.setCasaCentral(CasaCentralServicio.getInstancia().obtenerCasaCentral());
		  ordenPedidoDAO.persistir(ordenDePedido);
		  
		  for(int i = 0; cotizacionH.getItems().size() - 1>= i; i++){
			  if(cotizacionH.getItems().get(i).getEstado().equalsIgnoreCase("APROBADO"))
				  ItemOrdenPedidoServicio.getInstancia().guardarItems(cotizacionH.getItems().get(i), ordenDePedido);
		  }
	}

	public void updateEstado(OrdenPedido op) {
		ordenPedidoDAO.openCurrentSession();
		ordenPedidoDAO.cambiarEstado(op);
		ordenPedidoDAO.closeCurrentSessionwithTransaction();
	}

	public OrdenPedidoVO dameOrdenVO(int nroOrdenPedido) {
		OrdenPedido orden = ordenPedidoDAO.buscarPorId(nroOrdenPedido);
		OrdenPedidoVO ordenVO = this.HibernateAVo(orden);
		return ordenVO;
	}

	public List<OrdenPedidoVO> dameOrdenes() {
		ordenPedidoDAO.openCurrentSession();
		try{
			List<OrdenPedido> ordenes = ordenPedidoDAO.buscarTodos();
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
		ordenPedidoDAO.closeCurrentSessionwithTransaction();
		return null;
	}

	public List<OrdenPedidoVO> dameOrdenesARemitir() {
		ordenPedidoDAO.openCurrentSession();
		try{
			List<OrdenPedido> ordenes = ordenPedidoDAO.getOrdenesPedidoARemitir();
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
		ordenPedidoDAO.closeCurrentSessionwithTransaction();
		return null;
	}
	
}
