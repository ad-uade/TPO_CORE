package com.group7.service;

import java.util.List;

import com.group7.dao.MovimientoStockDAO;
import com.group7.entity.ItemOrdenPedido;
import com.group7.entity.ItemRemito;
import com.group7.entity.MovimientoStock;

public class MovimientoStockImpl {

	private static MovimientoStockImpl instancia;
	
	public static MovimientoStockImpl getInstancia(){
		if(instancia == null)
			instancia = new MovimientoStockImpl();
		return instancia;
	}

	public void altaMovimiento(ItemRemito itemRemito) {
		MovimientoStockDAO moviDAO = new MovimientoStockDAO();
		MovimientoStock movimiento = new MovimientoStock();
		movimiento.setTipo("ingreso");
		movimiento.setRodamiento(itemRemito.getId().getRodamiento());
		movimiento.setCantidad(itemRemito.getCantidad());
		movimiento.setCasaCentral(CasaCentralImpl.getInstancia().obtenerCasaCentral());
		moviDAO.guardarStock(movimiento);
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

	public List<MovimientoStock> verStockIngreso(String codigoSFK, String codigoPieza) {
		MovimientoStockDAO moviDAO = new MovimientoStockDAO();
		List<MovimientoStock> ingreso = moviDAO.verificarStockIngreso(codigoSFK, codigoPieza);
		return ingreso;
	}

	public List<MovimientoStock> verStockEgreso(String codigoSFK, String codigoPieza) {
		MovimientoStockDAO moviDAO = new MovimientoStockDAO();
		List<MovimientoStock> egreso = moviDAO.verificarStockEgreso(codigoSFK, codigoPieza);
		return egreso;
	}

	public void guardarMovimiento(ItemOrdenPedido itemOrdenPedido) {
		MovimientoStockDAO moviDAO = new MovimientoStockDAO();
		MovimientoStock movimientoH = new MovimientoStock();
		movimientoH.setTipo("egreso");
		movimientoH.setRodamiento(itemOrdenPedido.getId().getRodamiento());
		movimientoH.setCantidad(itemOrdenPedido.getCantidad());
		movimientoH.setCasaCentral(CasaCentralImpl.getInstancia().obtenerCasaCentral());
		moviDAO.guardarStock(movimientoH);
	}
	
	
}
