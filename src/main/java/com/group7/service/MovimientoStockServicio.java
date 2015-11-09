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
	
	/**
	 * 
	 * @param itemRemito
	 */
	public void registrarMovimiento(ItemRemito itemRemito) {
		movimientoStockDAO.openCurrentSessionwithTransaction();
		MovimientoStock movimiento = new MovimientoStock();
		movimiento.setTipo("ingreso");
		movimiento.setRodamiento(itemRemito.getId().getRodamiento());
		movimiento.setCantidad(itemRemito.getCantidad());
		movimiento.setCasaCentral(CasaCentralServicio.getInstancia().obtenerCasaCentral());
		movimientoStockDAO.persistir(movimiento);
		movimientoStockDAO.closeCurrentSessionwithTransaction();
	}
	
	public Integer sumarStockEgreso(List<MovimientoStock> egreso) {
		int cant = 0;
		if(egreso.size() != 0){
			for(int i = 0; egreso.size() - 1 >= i; i++){
				cant = cant + egreso.get(i).getCantidad();
			}
			return cant;
		}else
			return 0;
	}

	public Integer sumarStockIngreso(List<MovimientoStock> ingreso) {
		int cant = 0;
		if(ingreso.size() != 0){
			for(int i = 0; ingreso.size() - 1 >= i; i++){
				cant = cant + ingreso.get(i).getCantidad();
			}
			return cant;
		}else
			return 0;
	}

	/**
	 * 
	 * @param itemOrdenPedido
	 */
	public void guardarMovimiento(ItemOrdenPedido itemOrdenPedido) {
		MovimientoStock movimiento = new MovimientoStock();
		movimiento.setTipo("egreso");
		movimiento.setRodamiento(itemOrdenPedido.getId().getRodamiento());
		movimiento.setCantidad(itemOrdenPedido.getCantidad());
		movimiento.setCasaCentral(CasaCentralServicio.getInstancia().obtenerCasaCentral());
		this.persist(movimiento);
	}
	
	/**
	 * 
	 * @param codigoSFK
	 * @param codigoPieza
	 * @return
	 */
	public List<MovimientoStock> stockIngreso(String codigoSFK, String codigoPieza) {
		movimientoStockDAO.openCurrentSessionwithTransaction();
		List<MovimientoStock> ingreso = movimientoStockDAO.verificarStockIngreso(codigoSFK, codigoPieza);
		movimientoStockDAO.closeCurrentSessionwithTransaction();
		return ingreso;
	}

	/**
	 * 
	 * @param codigoSFK
	 * @param codigoPieza
	 * @return
	 */
	public List<MovimientoStock> stockEgreso(String codigoSFK, String codigoPieza) {
		movimientoStockDAO.openCurrentSessionwithTransaction();
		List<MovimientoStock> egreso = movimientoStockDAO.verificarStockEgreso(codigoSFK, codigoPieza);
		movimientoStockDAO.closeCurrentSessionwithTransaction();
		return egreso;
	}
	
}
