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
	
	public OrdenPedido VoHibernate(OrdenPedidoVO ordenPedidoVO){
		OrdenPedido op = new OrdenPedido();
		op.setCliente(ClienteServicio.getInstancia().convertir(ordenPedidoVO.getCliente()));
		op.setIdOrdenPedido(ordenPedidoVO.getNroOrdenPedido());
		op.setEstado(ordenPedidoVO.isEstado());
		op.setFecha(ordenPedidoVO.getFecha());
		op.setItems(ItemOrdenPedidoServicio.getInstancia().VoAHibernate(ordenPedidoVO.getItems()));
		return op;
	}

	public OrdenPedidoVO convertirAVO(OrdenPedido orden) {
		OrdenPedidoVO ordenVO = new OrdenPedidoVO();
		ordenVO.setNroOrdenPedido(orden.getIdOrdenPedido());
		ordenVO.setFecha(orden.getFecha());
		ordenVO.setEstado(orden.isEstado());
		ordenVO.setCliente(ClienteServicio.getInstancia().clienteToVO(orden.getCliente()));
		ordenVO.setItems(ItemOrdenPedidoServicio.getInstancia().HibernateAVo(orden.getItems()));
		return ordenVO;
	}
	
	public void guardarOrdenPedido(Cotizacion cotizacionH) {
		ordenPedidoDAO.openCurrentSessionwithTransaction();
		Calendar fechaActual = Calendar.getInstance();
		Date fecha = (Date) fechaActual.getTime();
		  
		OrdenPedido ordenDePedido = new OrdenPedido();
		ordenDePedido.setCliente(cotizacionH.getCliente());
		ordenDePedido.setEstado(false);
		ordenDePedido.setFecha(fecha); 
		ordenPedidoDAO.persistir(ordenDePedido);
		ordenPedidoDAO.closeCurrentSessionwithTransaction();
		for(int i = 0; cotizacionH.getItems().size() - 1>= i; i++){
			if(cotizacionH.getItems().get(i).getEstado().equalsIgnoreCase("APROBADO"))
				ItemOrdenPedidoServicio.getInstancia().guardarItems(cotizacionH.getItems().get(i), ordenDePedido);
		}		
	}

	public void updateEstado(OrdenPedido op) {
		ordenPedidoDAO.openCurrentSessionwithTransaction();
		ordenPedidoDAO.cambiarEstado(op);
		ordenPedidoDAO.closeCurrentSessionwithTransaction();
	}

	public OrdenPedidoVO dameOrdenVO(int nroOrdenPedido) {
		ordenPedidoDAO.openCurrentSessionwithTransaction();
		OrdenPedido orden = ordenPedidoDAO.buscarPorId(nroOrdenPedido);
		OrdenPedidoVO ordenVO = this.convertirAVO(orden);
		ordenPedidoDAO.closeCurrentSessionwithTransaction();
		return ordenVO;
	}

	public List<OrdenPedidoVO> dameOrdenes() {
		ordenPedidoDAO.openCurrentSessionwithTransaction();
		List<OrdenPedido> ordenes = ordenPedidoDAO.buscarTodos();
		List<OrdenPedidoVO> ordenesVO = new ArrayList<OrdenPedidoVO>();
		for (OrdenPedido ordenPedido : ordenes){
			OrdenPedidoVO op = new OrdenPedidoVO();
			op = this.convertirAVO(ordenPedido);
			ordenesVO.add(op);
		}
		ordenPedidoDAO.closeCurrentSessionwithTransaction();
		return ordenesVO;
	}

	public List<OrdenPedidoVO> obtenerOrdenesARemitir() {
		ordenPedidoDAO.openCurrentSessionwithTransaction();
		List<OrdenPedido> ordenes = ordenPedidoDAO.getOrdenesPedidoARemitir();
		List<OrdenPedidoVO> ordenesVO = new ArrayList<OrdenPedidoVO>();
		for (OrdenPedido ordenPedido :ordenes){
			OrdenPedidoVO ordenPedidoVO = new OrdenPedidoVO();
			ordenPedidoVO = OrdenPedidoServicio.getInstancia().convertirAVO(ordenPedido);
			ordenesVO.add(ordenPedidoVO);
		}
		ordenPedidoDAO.closeCurrentSessionwithTransaction();
		return ordenesVO;
	}
	
}
