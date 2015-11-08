package com.group7.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.group7.business.OrdenCompraVO;
import com.group7.business.OrdenPedidoVO;
import com.group7.business.RemitoExteriorVO;
import com.group7.business.RemitoVO;
import com.group7.dao.ItemRemitoDAO;
import com.group7.dao.RemitoDAO;
import com.group7.dao.RemitoExteriorDAO;
import com.group7.entity.ItemOrdenPedido;
import com.group7.entity.ItemRemito;
import com.group7.entity.MovimientoStock;
import com.group7.entity.OrdenCompra;
import com.group7.entity.OrdenPedido;
import com.group7.entity.Remito;
import com.group7.entity.RemitoExterior;

public class RemitoServicio {

	private static RemitoDAO remitoDAO;
	private static ItemRemitoDAO itemRemitoDAO;
	private static RemitoExteriorDAO remitoExteriorDAO;

	private static RemitoServicio instancia;

	public static RemitoServicio getInstancia() {
		if (instancia == null)
			instancia = new RemitoServicio();
		return instancia;
	}
	
	private RemitoServicio() {
		itemRemitoDAO = new ItemRemitoDAO();
		remitoDAO = new RemitoDAO();
		remitoExteriorDAO = new RemitoExteriorDAO();
	}

	public void persist(Remito entity) {
		remitoDAO.openCurrentSessionwithTransaction();
		remitoDAO.persistir(entity);
		remitoDAO.closeCurrentSessionwithTransaction();
	}

	public void update(Remito entity) {
		remitoDAO.openCurrentSessionwithTransaction();
		remitoDAO.actualizar(entity);
		remitoDAO.closeCurrentSessionwithTransaction();
	}

	public Remito buscarFacturaPorCuil(Integer CUIL) {
		remitoDAO.openCurrentSession();
		Remito remito = remitoDAO.buscarPorId(CUIL);
		remitoDAO.closeCurrentSession();
		return remito;
	}
	
	public Remito buscarPorId(String id) {
		remitoDAO.openCurrentSession();
		Remito remito = remitoDAO.buscarPorId(Integer.valueOf(id));
		remitoDAO.closeCurrentSession();
		return remito;
	}

	public void borrar(String id) {
		remitoDAO.openCurrentSessionwithTransaction();
		Remito remito = remitoDAO.buscarPorId(Integer.valueOf(id));
		remitoDAO.borrar(remito);
		remitoDAO.closeCurrentSessionwithTransaction();
	}

	public List<Remito> findAll() {
		remitoDAO.openCurrentSession();
		List<Remito> remitos = remitoDAO.buscarTodos();
		remitoDAO.closeCurrentSession();
		return remitos;
	}

	public void deleteAll() {
		remitoDAO.openCurrentSessionwithTransaction();
		remitoDAO.borrarTodos();
		remitoDAO.closeCurrentSessionwithTransaction();
	}

	public void conformarRemito(int nroRemito) {
		remitoDAO.openCurrentSessionwithTransaction();
		Remito remito = remitoDAO.buscarPorId(nroRemito);
		List<ItemRemito> items = ItemRemitoServicio.getInstancia().buscarItems(remito); 
		remito.setItems(items);
		remitoExteriorDAO.conformar(remito);
		remitoDAO.closeCurrentSessionwithTransaction();
	}

	public Remito generarRemito(OrdenCompra orden) {
		remitoDAO.openCurrentSessionwithTransaction();
		Calendar fechaActual = Calendar.getInstance();
		Date fecha = fechaActual.getTime();
		Remito remito = new Remito();
		remito.setFecha(fecha);
		remitoDAO.persistir(remito);
		remitoDAO.closeCurrentSessionwithTransaction();
		List<ItemRemito> items = new ArrayList<ItemRemito>();
		for(int i = 0; orden.getItems().size() - 1 >= i; i++){
			ItemRemito item = ItemRemitoServicio.getInstancia().guardarItemsInterior(remito.getNroRemito(), orden.getItems().get(i));
			items.add(item);
		}
		remito.setItems(items);
		return remito;
	}

	public RemitoExterior VoAHibernate(RemitoVO remito) {
		RemitoExterior rem = new RemitoExterior();
		rem.setNroRemito(remito.getNroRemito());
		rem.setFecha(remito.getFecha());
		rem.setConformeCliente(remito.isConformeCliente());
		rem.setCliente(ClienteServicio.getInstancia().convertir(remito.getCliente()));
		rem.setOP(OrdenPedidoServicio.getInstancia().VoHibernate(remito.getOrdenPedido()));
		rem.setItems(ItemRemitoServicio.getInstancia().VoAHibernate(remito.getItems()));
		return rem;
	}
	
	public RemitoExterior VoAHibernate(RemitoExteriorVO remito) {
		RemitoExterior rem = new RemitoExterior();
		rem.setNroRemito(remito.getNroRemito());
		rem.setFecha(remito.getFecha());
		rem.setConformeCliente(remito.isConformeCliente());
		rem.setCliente(ClienteServicio.getInstancia().convertir(remito.getCliente()));
		rem.setOP(OrdenPedidoServicio.getInstancia().VoHibernate(remito.getOrdenPedido()));
		rem.setItems(ItemRemitoServicio.getInstancia().VoAHibernate(remito.getItems()));
		return rem;
	}

	public RemitoExteriorVO dameRemitoVO(int nroRemito) {
		remitoExteriorDAO.openCurrentSessionwithTransaction();
		itemRemitoDAO.openCurrentSessionwithTransaction();
		RemitoExterior remi = remitoExteriorDAO.buscarPorId(nroRemito);
		List<ItemRemito> items = itemRemitoDAO.dameItemsRemito(nroRemito);
		remi.setItems(items);
		RemitoExteriorVO remitoVO = this.HibernateAVo(remi);
		remitoExteriorDAO.closeCurrentSessionwithTransaction();
		itemRemitoDAO.closeCurrentSessionwithTransaction();
		return remitoVO;
	}

	private RemitoExteriorVO HibernateAVo(RemitoExterior remi) {
		RemitoExteriorVO remito = new RemitoExteriorVO();
		remito.setNroRemito(remi.getNroRemito());
		remito.setFecha(remi.getFecha());
		remito.setConformeCliente(remi.isConformeCliente());
		remito.setCliente(ClienteServicio.getInstancia().clienteToVO(remi.getCliente()));
		remito.setOrdenPedido(OrdenPedidoServicio.getInstancia().convertirAVO(remi.getOP()));
		remito.setItems(ItemRemitoServicio.getInstancia().HibernateAVo(remi.getItems()));
		return remito;
	}
	
	public List<RemitoExteriorVO> dameRemitos() {
		remitoExteriorDAO.openCurrentSessionwithTransaction();
		List<RemitoExteriorVO> remitosVO = new ArrayList<RemitoExteriorVO>();
		List<RemitoExterior> remitos = remitoExteriorDAO.dameRemitosConformados();
		for (RemitoExterior remito : remitos){
			remitosVO.add(this.HibernateAVo(remito));
		}
		remitoExteriorDAO.closeCurrentSessionwithTransaction();
		return remitosVO;
	}

	public void recibirMercaderia(OrdenCompraVO ordenVO) {
		OrdenCompra orden = OrdenCompraServicio.getInstancia().VoAHibernate(ordenVO);
		Remito remito = this.generarRemito(orden);
		for(int i = 0; remito.getItems().size() - 1 >= i; i++){
			MovimientoStockServicio.getInstancia().altaMovimiento(remito.getItems().get(i));
		}
	}

	public boolean generarRemitoExterior(OrdenPedidoVO ordenPedido) {
		OrdenPedido op = OrdenPedidoServicio.getInstancia().VoHibernate(ordenPedido);
		Calendar fechaActual = Calendar.getInstance();
		Date fecha = fechaActual.getTime();
		RemitoExterior remito = new RemitoExterior();
		remito.setOP(op);
		remito.setCliente(op.getCliente());
		remito.setFecha(fecha);
		remito.setODV(op.getCliente().getOficinaVentas());
		
		List<ItemOrdenPedido> itemsParaRemitir = new ArrayList<ItemOrdenPedido>();
		for(int i = 0; op.getItems().size() - 1>= i; i++){
			List<MovimientoStock> ingreso = MovimientoStockServicio.getInstancia().verStockIngreso(op.getItems().get(i).getId().getRodamiento().getRodamientoId().getCodigoSFK(), op.getItems().get(i).getId().getRodamiento().getRodamientoId().getCodigoPieza());
			List<MovimientoStock> egreso = MovimientoStockServicio.getInstancia().verStockEgreso(op.getItems().get(i).getId().getRodamiento().getRodamientoId().getCodigoSFK(), op.getItems().get(i).getId().getRodamiento().getRodamientoId().getCodigoPieza());
			if(ingreso.size() != 0 || egreso.size() != 0){
				int ingresoStock = MovimientoStockServicio.getInstancia().sumarStockIngreso(ingreso);
				int egresoStock = MovimientoStockServicio.getInstancia().sumarStockEgreso(egreso);
				if(op.getItems().get(i).getCantidad() <= (ingresoStock-egresoStock)){
					itemsParaRemitir.add(op.getItems().get(i));
				}
			}
		}
		if(itemsParaRemitir.size() != 0){
			remitoDAO.openCurrentSessionwithTransaction(); 
			remitoDAO.persistir(remito);
			remitoDAO.openCurrentSessionwithTransaction();
			for(int j = 0; itemsParaRemitir.size() - 1>= j; j++){
				ItemRemitoServicio.getInstancia().guardarItemExterior(remito.getNroRemito(),itemsParaRemitir.get(j));
				MovimientoStockServicio.getInstancia().guardarMovimiento(itemsParaRemitir.get(j));
				ItemOrdenPedidoServicio.getInstancia().actualizarEstados(itemsParaRemitir.get(j).getId().getNroOrdenPedido(), itemsParaRemitir.get(j).getId().getRodamiento());
			}
		}else{
			return false;
		}
		
		List<ItemOrdenPedido> itemsEstado = ItemOrdenPedidoServicio.getInstancia().itemsFalse(op.getIdOrdenPedido());
		if(itemsEstado.size() == 0)
			OrdenPedidoServicio.getInstancia().updateEstado(op);
		return true;
	}
	
	public List<RemitoExteriorVO> dameRemitosNoConformados() {
		remitoExteriorDAO.openCurrentSessionwithTransaction();
		List<RemitoExteriorVO> remitosVO = new ArrayList<RemitoExteriorVO>();
		List<RemitoExterior> remitos = remitoExteriorDAO.dameRemitosNoConformados();
		for (int i = 0; remitos.size() - 1 >= i; i++){
			remitosVO.add(this.HibernateAVo(remitos.get(i)));
		}
		remitoExteriorDAO.closeCurrentSessionwithTransaction();
		return remitosVO;
	}
	
}
