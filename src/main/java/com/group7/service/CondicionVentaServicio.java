package com.group7.service;

import java.util.ArrayList;
import java.util.List;

import com.group7.business.CondicionVentaVO;
import com.group7.dao.CondicionVentaDAO;
import com.group7.entity.CondicionVenta;
import com.group7.entity.Rodamiento;
import com.group7.entity.SolicitudCotizacion;

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

	public CondicionVenta dameCondicionVenta(SolicitudCotizacion sc, Rodamiento rodamiento) {
		CondicionVenta condicion = new CondicionVenta();
		for (int i = 0; sc.getItems().size() - 1 >= i; i++) {
			if (sc.getItems().get(i).getId().getRodamiento().getRodamientoId().getCodigoSFK()
					.equalsIgnoreCase(rodamiento.getRodamientoId().getCodigoSFK())
					&& sc.getItems().get(i).getId().getRodamiento().getRodamientoId().getCodigoPieza()
							.equalsIgnoreCase(rodamiento.getRodamientoId().getCodigoPieza())) {
				condicion.setNroCondicion(sc.getItems().get(i).getCondicion().getNroCondicion());
				condicion.setFechaDesde(sc.getItems().get(i).getCondicion().getFechaDesde());
				condicion.setFechaHasta(sc.getItems().get(i).getCondicion().getFechaHasta());
				condicion.setIva(sc.getItems().get(i).getCondicion().getIva());
				condicion.setFormaPago(sc.getItems().get(i).getCondicion().getFormaPago());
			}
		}
		return condicion;
	}

	public List<CondicionVentaVO> dameCondiciones() {
		CondicionVentaDAO miDAO = new CondicionVentaDAO();
		List<CondicionVenta> con = miDAO.dameCondiciones();
		List<CondicionVentaVO> conVO = new ArrayList<CondicionVentaVO>();
		for (int i = 0; con.size() - 1 >= i; i++) {
			CondicionVentaVO condicion = this.HibernateAVo(con.get(i));
			conVO.add(condicion);
		}
		return conVO;
	}

	public CondicionVentaVO HibernateAVo(CondicionVenta condicionVenta) {
		CondicionVentaVO condicionVO = new CondicionVentaVO();
		condicionVO.setNroCondicion(condicionVenta.getNroCondicion());
		condicionVO.setFechaDesde(condicionVenta.getFechaDesde());
		condicionVO.setFechaHasta(condicionVenta.getFechaHasta());
		condicionVO.setIVA(condicionVenta.getIva());
		condicionVO.setFormaPago(FormaPagoServicio.getInstancia().HibernateAVo(condicionVenta.getFormaPago()));
		return condicionVO;
	}

	public List<CondicionVenta> VoAHibernate(List<CondicionVentaVO> condiciones) {
		List<CondicionVenta> condicionesv = new ArrayList<CondicionVenta>();
		for (int i = 0; condiciones.size() - 1 >= i; i++) {
			CondicionVenta condi = new CondicionVenta();
			condi.setNroCondicion(condiciones.get(i).getNroCondicion());
			condi.setFechaDesde(condiciones.get(i).getFechaDesde());
			condi.setFechaHasta(condiciones.get(i).getFechaHasta());
			condi.setIva(condiciones.get(i).getIVA());
			condi.setFormaPago(FormaPagoServicio.getInstancia().VoAHibernate(condiciones.get(i).getFormaPago()));
			condicionesv.add(condi);
		}
		return condicionesv;
	}

	public CondicionVentaVO dameCondicion(int nroCondicion) {
		condicionVentaDAO.openCurrentSessionwithTransaction();
		CondicionVenta con = condicionVentaDAO.dameCondicion(nroCondicion);
		CondicionVentaVO conVO = this.HibernateAVo(con);
		condicionVentaDAO.closeCurrentSessionwithTransaction();
		return conVO;
	}

}
