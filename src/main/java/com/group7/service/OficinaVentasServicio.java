package com.group7.service;

import java.util.ArrayList;
import java.util.List;

import com.group7.business.OficinaVentasVO;
import com.group7.dao.OficinaVentasDAO;
import com.group7.entity.OficinaVenta;

public class OficinaVentasServicio {

	private static OficinaVentasDAO oficinaVentasDAO;
	private static OficinaVentasServicio instancia;
	
	public static OficinaVentasServicio getInstancia() {
		if (instancia == null)
			instancia = new OficinaVentasServicio();
		return instancia;
	}
	
	private OficinaVentasServicio(){
		oficinaVentasDAO = new OficinaVentasDAO();
	}

	public List<OficinaVentasVO> buscarOficinas() {
		oficinaVentasDAO.openCurrentSessionwithTransaction();
		List<OficinaVenta> oficinas = oficinaVentasDAO.buscarTodos();
		List<OficinaVentasVO> oficinasVO = new ArrayList<OficinaVentasVO>();
		for (OficinaVenta oficinaVentas : oficinas){
			oficinasVO.add(this.popular(oficinaVentas));
		}
		oficinaVentasDAO.closeCurrentSessionwithTransaction();
		return oficinasVO;
	}

	public OficinaVentasVO popular(OficinaVenta oficinaVentas) {
		OficinaVentasVO oficina = new OficinaVentasVO();
		oficina.setIdOficina(oficinaVentas.getIdOficinaVenta());
		oficina.setSucursal(oficinaVentas.getSucursal());
		return oficina;
	}
	
	public OficinaVenta convertir(OficinaVentasVO of){
		OficinaVenta oficina = new OficinaVenta();
		oficina.setIdOficinaVenta(of.getIdOficina());
		oficina.setSucursal(of.getSucursal());
		return oficina;
	}

	public OficinaVentasVO buscarOficina(int idOficina) {
		oficinaVentasDAO.openCurrentSessionwithTransaction();
		OficinaVenta oficina = oficinaVentasDAO.buscarPorId(idOficina);
		oficinaVentasDAO.closeCurrentSessionwithTransaction();
		OficinaVentasVO oficinaVO = this.popular(oficina);
		return oficinaVO;
	}
	
	public void persistirTodasLasOficinas(List<OficinaVenta> listadoDeOficina) {
		oficinaVentasDAO.openCurrentSessionwithTransaction();
		for (OficinaVenta oficinaVentas : listadoDeOficina){
			oficinaVentasDAO.persistir(oficinaVentas);
		}
		oficinaVentasDAO.closeCurrentSessionwithTransaction();
	}
	
	public void persist(OficinaVenta entity) {
		oficinaVentasDAO.openCurrentSessionwithTransaction();
		oficinaVentasDAO.persistir(entity);
		oficinaVentasDAO.closeCurrentSessionwithTransaction();
	}
}
