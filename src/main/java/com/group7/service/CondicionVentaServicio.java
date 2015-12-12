package com.group7.service;

import java.util.ArrayList;
import java.util.List;

import com.group7.business.CondicionVentaVO;
import com.group7.dao.CondicionVentaDAO;
import com.group7.entity.CondicionVenta;

public class CondicionVentaServicio {

	private static CondicionVentaServicio instancia;
	private static CondicionVentaDAO condicionVentaDAO;
	
	public static CondicionVentaServicio getInstancia() {
		if (instancia == null)
			instancia = new CondicionVentaServicio();
		return instancia;
	}
	
	private CondicionVentaServicio() {
		condicionVentaDAO = new CondicionVentaDAO();
	}

	public List<CondicionVentaVO> buscarCondiciones() {
		condicionVentaDAO.openCurrentSessionwithTransaction();
		List<CondicionVenta> con = condicionVentaDAO.dameCondiciones();
		List<CondicionVentaVO> conVO = new ArrayList<CondicionVentaVO>();
		for (CondicionVenta condicionVenta : con) {
			CondicionVentaVO condicion = this.convertirAVO(condicionVenta);
			conVO.add(condicion);
		}
		condicionVentaDAO.closeCurrentSessionwithTransaction();
		return conVO;
	}
	
	public List<CondicionVenta> buscarTodas() {
		condicionVentaDAO.openCurrentSessionwithTransaction();
		List<CondicionVenta> con = condicionVentaDAO.dameCondiciones();
		condicionVentaDAO.closeCurrentSessionwithTransaction();
		return con;
	}

	public List<CondicionVenta> VoAHibernate(List<CondicionVentaVO> condiciones) {
		List<CondicionVenta> condicionesv = new ArrayList<CondicionVenta>();
		for (int i = 0; condiciones.size() - 1 >= i; i++) {
			CondicionVenta condi = new CondicionVenta();
			condi.setNroCondicion(condiciones.get(i).getNroCondicion());
			condi.setFechaDesde(condiciones.get(i).getFechaDesde());
			condi.setFechaHasta(condiciones.get(i).getFechaHasta());
			condi.setIva(condiciones.get(i).getIVA());
//			condi.setFormaPago(FormaPagoServicio.getInstancia().VoAFormaPago(condiciones.get(i).getFormaPago()));
			condicionesv.add(condi);
		}
		return condicionesv;
	}

	public CondicionVentaVO buscarCondicion(int nroCondicion) {
		condicionVentaDAO.openCurrentSessionwithTransaction();
		CondicionVenta con = condicionVentaDAO.dameCondicion(nroCondicion);
		CondicionVentaVO conVO = this.convertirAVO(con);
		condicionVentaDAO.closeCurrentSessionwithTransaction();
		return conVO;
	}

	public CondicionVentaVO convertirAVO(CondicionVenta condicionVenta) {
		CondicionVentaVO condicionVO = new CondicionVentaVO();
		condicionVO.setNroCondicion(condicionVenta.getNroCondicion());
		condicionVO.setFechaDesde(condicionVenta.getFechaDesde());
		condicionVO.setFechaHasta(condicionVenta.getFechaHasta());
		condicionVO.setIVA(condicionVenta.getIva());
		condicionVO.setFormaPago(FormaPagoServicio.getInstancia().formaPagoAVo(condicionVenta.getFormaPago()));
		return condicionVO;
	}

	public void persistirTodos(List<CondicionVenta> listadoOficinaVentas) {
		condicionVentaDAO.openCurrentSessionwithTransaction();
		for (CondicionVenta condicionVenta : listadoOficinaVentas){
			condicionVentaDAO.persistir(condicionVenta);
		}
		condicionVentaDAO.closeCurrentSessionwithTransaction();	
	}

}
