package com.group7.service;

import java.util.ArrayList;
import java.util.List;

import com.group7.business.OrdenCompraVO;
import com.group7.dao.OrdenCompraDAO;
import com.group7.entity.OrdenCompra;

public class OrdenCompraServicio {

	private static OrdenCompraDAO ordenCompraDAO;
	private static OrdenCompraServicio instancia;

	public static OrdenCompraServicio getInstancia() {
		if (instancia == null)
			instancia = new OrdenCompraServicio();
		return instancia;
	}

	private OrdenCompraServicio() {
		ordenCompraDAO = new OrdenCompraDAO();
	}

	/**
	 * 
	 * @return
	 */
	public List<OrdenCompraVO> buscarOrdenes() {
		ordenCompraDAO.openCurrentSessionwithTransaction();
		List<OrdenCompra> ordenes = ordenCompraDAO.buscarTodos();
		List<OrdenCompraVO> ordenesVO = new ArrayList<OrdenCompraVO>();
		for (OrdenCompra ordenCompra : ordenes) {
			ordenesVO.add(ordenCompra.getView());
		}
		ordenCompraDAO.closeCurrentSession();
		return ordenesVO;
	}

	/**
	 * 
	 * @param nroOrdenCompra
	 * @return
	 */
	public OrdenCompraVO dameOrden(int nroOrdenCompra) {
		ordenCompraDAO.openCurrentSessionwithTransaction();
		OrdenCompra orden = ordenCompraDAO.dameOrdenCompra(nroOrdenCompra);
		ordenCompraDAO.closeCurrentSessionwithTransaction();
		return orden.getView();
	}

}
