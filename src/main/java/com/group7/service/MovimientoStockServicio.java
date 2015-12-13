package com.group7.service;

import java.util.List;

import com.group7.dao.MovimientoStockDAO;
import com.group7.entity.Stock;

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

	public void persist(Stock entity) {
		movimientoStockDAO.openCurrentSessionwithTransaction();
		movimientoStockDAO.persistir(entity);
		movimientoStockDAO.closeCurrentSessionwithTransaction();
	}
	
	public Integer sumarStockEgreso(List<Stock> egreso) {
		int cant = 0;
		if(egreso.size() != 0){
			for(int i = 0; egreso.size() - 1 >= i; i++){
				cant = cant + egreso.get(i).getCantidad();
			}
			return cant;
		}else
			return 0;
	}

	public Integer sumarStockIngreso(List<Stock> ingreso) {
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
	 * @param codigoSFK
	 * @param codigoPieza
	 * @return
	 */
	public List<Stock> stockIngreso(String codigoSFK, String codigoPieza) {
		movimientoStockDAO.openCurrentSessionwithTransaction();
		List<Stock> ingreso = movimientoStockDAO.verificarStockIngreso(codigoSFK, codigoPieza);
		movimientoStockDAO.closeCurrentSessionwithTransaction();
		return ingreso;
	}

	/**
	 * 
	 * @param codigoSFK
	 * @param codigoPieza
	 * @return
	 */
	public List<Stock> stockEgreso(String codigoSFK, String codigoPieza) {
		movimientoStockDAO.openCurrentSessionwithTransaction();
		List<Stock> egreso = movimientoStockDAO.verificarStockEgreso(codigoSFK, codigoPieza);
		movimientoStockDAO.closeCurrentSessionwithTransaction();
		return egreso;
	}
	
}
