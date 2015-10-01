package com.group7.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.group7.business.OrdenCompraVO;
import com.group7.business.OrdenPedidoVO;
import com.group7.business.RemitoExteriorVO;
import com.group7.dao.ItemRemitoDAO;
import com.group7.dao.RemitoDAO;
import com.group7.dao.RemitoExteriorDAO;
import com.group7.entity.ItemOrdenPedido;
import com.group7.entity.ItemRemito;
import com.group7.entity.MovimientoStock;
import com.group7.entity.OrdenCompra;
import com.group7.entity.OrdenPedido;
import com.group7.entity.RemitoExterior;
import com.group7.entity.RemitoInterior;

public class RemitoImpl {

	private static RemitoImpl instancia;
	
	public static RemitoImpl getInstancia(){
		if(instancia == null)
			instancia = new RemitoImpl();
		return instancia;
	}

	public RemitoExterior VoAHibernate(RemitoExteriorVO remito) {
		RemitoExterior rem = new RemitoExterior();
		rem.setNroRemito(remito.getNroRemito());
		rem.setFecha(remito.getFecha());
		rem.setConformeCliente(remito.isConformeCliente());
		rem.setCliente(ClienteImpl.getInstancia().clienteVOtoCliente(remito.getCliente()));
		rem.setOP(OrdenPedidoImpl.getInstancia().VoHibernate(remito.getOrdenPedido()));
		rem.setItems(ItemRemitoImpl.getInstancia().VoAHibernate(remito.getItems()));
		return rem;
	}

	public RemitoExteriorVO dameRemitoVO(int nroRemito) {
		RemitoExteriorDAO remito = new RemitoExteriorDAO();
		ItemRemitoDAO itemDAO = new ItemRemitoDAO();
		RemitoExterior remi = remito.dameRemito(nroRemito);
		List<ItemRemito> items = itemDAO.dameItemsRemito(nroRemito);
		remi.setItems(items);
		RemitoExteriorVO remitoVO = this.remitoToVo(remi);
		return remitoVO;
	}

	private RemitoExteriorVO remitoToVo(RemitoExterior remi) {
		RemitoExteriorVO remito = new RemitoExteriorVO();
		remito.setNroRemito(remi.getNroRemito());
		remito.setFecha(remi.getFecha());
		remito.setConformeCliente(remi.isConformeCliente());
		remito.setCliente(ClienteImpl.getInstancia().clienteToVO(remi.getCliente()));
		remito.setOrdenPedido(OrdenPedidoImpl.getInstancia().HibernateAVo(remi.getOP()));
		remito.setItems(ItemRemitoImpl.getInstancia().HibernateAVo(remi.getItems()));
		return remito;
	}
	
	public List<RemitoExteriorVO> dameRemitos() {
		RemitoExteriorDAO miDAO = new RemitoExteriorDAO();
		List<RemitoExteriorVO> remitosVO = new ArrayList<RemitoExteriorVO>();
		List<RemitoExterior> remitos = miDAO.dameRemitosConformados();
		for (int i = 0; remitos.size() - 1 >= i; i++)
			remitosVO.add(this.remitoToVo(remitos.get(i)));
		return remitosVO;
	}

	public void conformarRemito(int nroRemito) {
		RemitoExteriorDAO miDAO = new RemitoExteriorDAO();
		RemitoExterior remito = miDAO.dameRemito(nroRemito);
		List<ItemRemito> items = ItemRemitoImpl.getInstancia().dameItems(remito); 
		remito.setItems(items);
		miDAO.conformar(remito);
	}

	public void recibirMercaderia(OrdenCompraVO ordenVO) {
		OrdenCompra orden = OrdenCompraImpl.getInstancia().VoAHibernate(ordenVO);
		RemitoInterior remito = this.generarRemito(orden);
		for(int i = 0; remito.getItems().size() - 1 >= i; i++){
			MovimientoStockImpl.getInstancia().altaMovimiento(remito.getItems().get(i));
		}
	}
	
	public RemitoInterior generarRemito(OrdenCompra orden) { 
		Calendar fechaActual = Calendar.getInstance();
		Date fecha = fechaActual.getTime();
		RemitoDAO miDAO = new RemitoDAO();
		RemitoInterior remito = new RemitoInterior();
		remito.setFecha(fecha);
		remito.setCasaCentral(CasaCentralImpl.getInstancia().obtenerCasaCentral());
		miDAO.guardarRemito(remito);
		
		List<ItemRemito> items = new ArrayList<ItemRemito>();
		for(int i = 0; orden.getItems().size() - 1 >= i; i++){
			ItemRemito item = ItemRemitoImpl.getInstancia().guardarItemsInterior(remito.getNroRemito(), orden.getItems().get(i));
			items.add(item);
		}
		remito.setItems(items);
		return remito;
	}

	public boolean generarRemitoExterior(OrdenPedidoVO ordenPedido) {
		OrdenPedido op = OrdenPedidoImpl.getInstancia().VoHibernate(ordenPedido);
		Calendar fechaActual = Calendar.getInstance();
		Date fecha = fechaActual.getTime();
		RemitoDAO miRemitoDAO = new RemitoDAO();
		RemitoExterior remito = new RemitoExterior();
		
		remito.setOP(op);
		remito.setCliente(op.getCliente());
		remito.setFecha(fecha);
		remito.setODV(op.getCliente().getOficinaVentas());
		
		List<ItemOrdenPedido> itemsParaRemitir = new ArrayList<ItemOrdenPedido>();
		for(int i = 0; op.getItems().size() - 1>= i; i++){
			List<MovimientoStock> ingreso = MovimientoStockImpl.getInstancia().verStockIngreso(op.getItems().get(i).getId().getRodamiento().getRodamientoId().getCodigoSFK(), op.getItems().get(i).getId().getRodamiento().getRodamientoId().getCodigoPieza());
			List<MovimientoStock> egreso = MovimientoStockImpl.getInstancia().verStockEgreso(op.getItems().get(i).getId().getRodamiento().getRodamientoId().getCodigoSFK(), op.getItems().get(i).getId().getRodamiento().getRodamientoId().getCodigoPieza());
			if(ingreso.size() != 0 || egreso.size() != 0){
				int ingresoStock = MovimientoStockImpl.getInstancia().sumarStockIngreso(ingreso);
				int egresoStock = MovimientoStockImpl.getInstancia().sumarStockEgreso(egreso);
				if(op.getItems().get(i).getCantidad() <= (ingresoStock-egresoStock)){
					itemsParaRemitir.add(op.getItems().get(i));
				}
			}
		}
		if(itemsParaRemitir.size() != 0){
			miRemitoDAO.guardarRemito(remito);
			for(int j = 0; itemsParaRemitir.size() - 1>= j; j++){
				ItemRemitoImpl.getInstancia().guardarItemExterior(remito.getNroRemito(),itemsParaRemitir.get(j));
				MovimientoStockImpl.getInstancia().guardarMovimiento(itemsParaRemitir.get(j));
				ItemOrdenPedidoImpl.getInstancia().updateEstados(itemsParaRemitir.get(j).getId().getNroOrdenPedido(), itemsParaRemitir.get(j).getId().getRodamiento());
			}
		}else{
			return false;
		}
		List<ItemOrdenPedido> itemsEstado = ItemOrdenPedidoImpl.getInstancia().itemsFalse(op.getIdOrdenPedido());
		if(itemsEstado.size() == 0)
			OrdenPedidoImpl.getInstancia().updateEstado(op);
		return true;
	}
	
	public List<RemitoExteriorVO> dameRemitosNoConformados() {
		RemitoExteriorDAO miDAO = new RemitoExteriorDAO();
		List<RemitoExteriorVO> remitosVO = new ArrayList<RemitoExteriorVO>();
		List<RemitoExterior> remitos = miDAO.dameRemitosNoConformados();
		for (int i = 0; remitos.size() - 1 >= i; i++)
			remitosVO.add(this.remitoToVo(remitos.get(i)));
		return remitosVO;
	}
	
}
