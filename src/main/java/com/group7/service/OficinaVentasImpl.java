package com.group7.service;

import java.util.ArrayList;
import java.util.List;

import com.group7.business.OficinaVentasVO;
import com.group7.dao.OficinaVentasDAO;
import com.group7.entity.OficinaVentas;

public class OficinaVentasImpl {

	private static OficinaVentasImpl instancia;

	public static OficinaVentasImpl getInstancia() {
		if (instancia == null)
			instancia = new OficinaVentasImpl();
		return instancia;
	}

	public List<OficinaVentasVO> dameOficinas() {
		OficinaVentasDAO miDAO = new OficinaVentasDAO();
		List<OficinaVentas> ofi = miDAO.obtenerOficinas();
		List<OficinaVentasVO> oficinas = new ArrayList<OficinaVentasVO>();
		for (int i = 0; ofi.size() - 1 >= i; i++) {
			OficinaVentasVO oficinaVO = this.HibernateAVo(ofi.get(i));
			oficinas.add(oficinaVO);
		}
		return oficinas;
	}

	public OficinaVentasVO HibernateAVo(OficinaVentas oficinaVentas) {
		OficinaVentasVO oficina = new OficinaVentasVO();
		oficina.setIdOficina(oficinaVentas.getIdOficinaVenta());
		oficina.setSucursal(oficinaVentas.getSucursal());
		return oficina;
	}

	public OficinaVentas oficinaVOaH(OficinaVentasVO of) {
		OficinaVentas oficina = new OficinaVentas();
		oficina.setIdOficinaVenta(of.getIdOficina());
		oficina.setSucursal(of.getSucursal());
		return oficina;
	}

	public OficinaVentasVO buscaOficina(int idOficina) {
		OficinaVentasDAO miDAO = new OficinaVentasDAO();
		OficinaVentas oficina = miDAO.dameOficina(idOficina);
		OficinaVentasVO oficinaVO = this.HibernateAVo(oficina);
		return oficinaVO;
	}

}
