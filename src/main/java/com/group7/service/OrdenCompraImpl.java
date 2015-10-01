package com.group7.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.group7.business.OrdenCompraVO;
import com.group7.business.OrdenPedidoVO;
import com.group7.business.RodamientoVO;
import com.group7.dao.OrdenCompraDAO;
import com.group7.entity.ItemOrdenPedido;
import com.group7.entity.ItemsComparativaPrecio;
import com.group7.entity.OrdenCompra;
import com.group7.entity.OrdenPedido;

public class OrdenCompraImpl {

	private static OrdenCompraImpl instancia;
	
	public static OrdenCompraImpl getInstancia(){
		if(instancia == null)
			instancia = new OrdenCompraImpl();
		return instancia;
	}
	
	public OrdenCompra VoAHibernate(OrdenCompraVO ordenVO){
		OrdenCompra orden = new OrdenCompra();
		orden.setNroOrdenCompra(ordenVO.getNroOrdenCompra());
		orden.setFecha(ordenVO.getFecha());
		orden.setProveedor(ProveedorImpl.getInstancia().VoAHibernate(ordenVO.getProveedor()));
		orden.setItems(ItemOrdenCompraImpl.getInstancia().VoAHibernate(ordenVO.getItems()));
		return orden;
	}

	public OrdenCompraVO HibernateAVo(OrdenCompra orden) {
		OrdenCompraVO ordenVO = new OrdenCompraVO();
		ordenVO.setNroOrdenCompra(orden.getNroOrdenCompra());
		ordenVO.setFecha(orden.getFecha());
		ordenVO.setProveedor(ProveedorImpl.getInstancia().proveedorToVo(orden.getProveedor()));
		ordenVO.setItems(ItemOrdenCompraImpl.getInstancia().HibernateAVo(orden.getItems()));
		return ordenVO;
	}
	
	public List<OrdenCompraVO> dameOrdenes() {
	  OrdenCompraDAO ordenDAO = new OrdenCompraDAO();
	  List<OrdenCompra> ordenes = ordenDAO.dameOrdenesCompra();
	  List<OrdenCompraVO> ordenesVO = new ArrayList<OrdenCompraVO>();
	  for(int i = 0; ordenes.size() - 1 >= i; i++){
	   ordenesVO.add(this.HibernateAVo(ordenes.get(i)));
	  }
	  return ordenesVO;
	}

	public void altaOrdenCompraManual(List<RodamientoVO> rodamientos, List<Integer> cantidades) {
		Calendar fechaActual = Calendar.getInstance();
		Date fecha = fechaActual.getTime();
		OrdenCompraDAO ordenDAO = new OrdenCompraDAO();
		
		int i = 0;
		while(rodamientos.size() - 1 >= i){
			for(int j = 0; cantidades.size() -1 >= j; j++){
				ItemsComparativaPrecio item = ItemComparativaPreciosImpl.getInstancia().dameItemsProveedor(RodamientoImpl.getInstancia().rodamientoVoToRodamiento(rodamientos.get(i)));
				OrdenCompra orden = new OrdenCompra();
				orden.setFecha(fecha);
				orden.setProveedor(item.getProveedorListaPrecios());
				ordenDAO.altaOrdenCompra(orden);
				ItemOrdenCompraImpl.getInstancia().guardarItem(orden.getNroOrdenCompra(), item, cantidades.get(j));
				i++;
			}
		}
	}

	public OrdenCompraVO dameOrden(int nroOrdenCompra) {
		OrdenCompraDAO ordenDAO = new OrdenCompraDAO();
		OrdenCompra orden = ordenDAO.dameOrdenCompra(nroOrdenCompra);
		OrdenCompraVO ordenVO = this.HibernateAVo(orden);
		return ordenVO;
	}

	public void generarOrden(OrdenPedidoVO ordenDePedido) {
		Calendar fechaActual = Calendar.getInstance();
		Date fecha = fechaActual.getTime();
		
		OrdenCompraDAO miOrdenDAO = new OrdenCompraDAO();
		OrdenCompra ordenDeCompra = new OrdenCompra();
		
		OrdenPedido ordenPedido = OrdenPedidoImpl.getInstancia().dameOrden(ordenDePedido.getNroOrdenPedido());
			
		for(int i = 0; ordenPedido.getItems().size()-1 >= i; i++){
			List<ItemOrdenPedido> itemsTemp = ItemOrdenPedidoImpl.getInstancia().dameTemporales(ordenDePedido.getNroOrdenPedido(), ordenPedido.getItems().get(i).getProveedor());
			if(itemsTemp != null){
				ordenDeCompra.setFecha(fecha);
				miOrdenDAO.altaOrdenCompra(ordenDeCompra);
				for(int j = 0; itemsTemp.size() - 1>= j; j++){
					ItemOrdenCompraImpl.getInstancia().guardarlos(ordenDeCompra.getNroOrdenCompra(), itemsTemp.get(j));
					miOrdenDAO.actualizarProveedor(itemsTemp.get(j).getProveedor());
				}
			}
		}
	}
}
