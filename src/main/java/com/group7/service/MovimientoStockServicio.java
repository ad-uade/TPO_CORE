package com.group7.service;

import java.util.List;

import com.group7.dao.MovimientoStockDAO;
import com.group7.entity.ItemOrdenPedido;
import com.group7.entity.ItemRemito;
import com.group7.entity.MovimientoStock;

public class MovimientoStockServicio {

	private static MovimientoStockDAO movimientoStockDAO;

	private static MovimientoStockServicio instancia;

	public static MovimientoStockServicio getInstancia() {
		if (instancia == null)
			instancia = new MovimientoStockServicio();
		return instancia;
	}
	
	private MovimientoStockServicio() {
		movimientoStockDAO = new MovimientoStockDAO();
	}

	public void persist(MovimientoStock entity) {
		movimientoStockDAO.openCurrentSessionwithTransaction();
		movimientoStockDAO.persistir(entity);
		movimientoStockDAO.closeCurrentSessionwithTransaction();
	}

	public void update(MovimientoStock entity) {
		movimientoStockDAO.openCurrentSessionwithTransaction();
		movimientoStockDAO.actualizar(entity);
		movimientoStockDAO.closeCurrentSessionwithTransaction();
	}

	public MovimientoStock buscarClientePorCuil(Integer CUIL) {
		movimientoStockDAO.openCurrentSession();
		MovimientoStock movimientoStock = movimientoStockDAO.buscarPorId(CUIL);
		movimientoStockDAO.closeCurrentSession();
		return movimientoStock;
	}
	
	public MovimientoStock buscarPorId(String id) {
		movimientoStockDAO.openCurrentSession();
		MovimientoStock movimientoStock = movimientoStockDAO.buscarPorId(Integer.valueOf(id));
		movimientoStockDAO.closeCurrentSession();
		return movimientoStock;
	}

	public void borrar(String id) {
		movimientoStockDAO.openCurrentSessionwithTransaction();
		MovimientoStock movimientoStock = movimientoStockDAO.buscarPorId(Integer.valueOf(id));
		movimientoStockDAO.borrar(movimientoStock);
		movimientoStockDAO.closeCurrentSessionwithTransaction();
	}
	
	public void altaMovimiento(ItemRemito itemRemito) {
		MovimientoStock movimiento = new MovimientoStock();
		movimiento.setTipo("ingreso");
		movimiento.setRodamiento(itemRemito.getId().getRodamiento());
		movimiento.setCantidad(itemRemito.getCantidad());
		movimiento.setCasaCentral(CasaCentralServicio.getInstancia().obtenerCasaCentral());
		movimientoStockDAO.persistir(movimiento);
	}
	
	public int sumarStockEgreso(List<MovimientoStock> egreso) {
		int cant = 0;
		if(egreso.size() != 0){
			for(int i = 0; egreso.size() - 1 >= i; i++){
				cant = cant + egreso.get(i).getCantidad();
			}
			return cant;
		}else
			return 0;
	}

	public int sumarStockIngreso(List<MovimientoStock> ingreso) {
		int cant = 0;
		if(ingreso.size() != 0){
			for(int i = 0; ingreso.size() - 1 >= i; i++){
				cant = cant + ingreso.get(i).getCantidad();
			}
			return cant;
		}else
			return 0;
	}

	public void guardarMovimiento(ItemOrdenPedido itemOrdenPedido) {
		MovimientoStock movimiento = new MovimientoStock();
		movimiento.setTipo("egreso");
		movimiento.setRodamiento(itemOrdenPedido.getId().getRodamiento());
		movimiento.setCantidad(itemOrdenPedido.getCantidad());
		movimiento.setCasaCentral(CasaCentralServicio.getInstancia().obtenerCasaCentral());
		this.persist(movimiento);
	}
	
	public List<MovimientoStock> verStockIngreso(String codigoSFK, String codigoPieza) {
		movimientoStockDAO.openCurrentSessionwithTransaction();
		List<MovimientoStock> ingreso = movimientoStockDAO.verificarStockIngreso(codigoSFK, codigoPieza);
		movimientoStockDAO.closeCurrentSessionwithTransaction();
		return ingreso;
	}

	public List<MovimientoStock> verStockEgreso(String codigoSFK, String codigoPieza) {
		movimientoStockDAO.openCurrentSessionwithTransaction();
		List<MovimientoStock> egreso = movimientoStockDAO.verificarStockEgreso(codigoSFK, codigoPieza);
		movimientoStockDAO.closeCurrentSessionwithTransaction();
		return egreso;
	}
	
}
