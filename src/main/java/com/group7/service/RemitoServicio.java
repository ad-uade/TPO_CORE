package com.group7.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.group7.dao.RemitoDAO;
import com.group7.dao.RemitoExteriorDAO;
import com.group7.entity.ItemRemito;
import com.group7.entity.OrdenCompra;
import com.group7.entity.Remito;
import com.group7.entity.RemitoExterior;
import com.group7.entity.RemitoInterior;

public class RemitoServicio {

	private static RemitoDAO remitoDAO;
	private static RemitoExteriorDAO remitoExteriorDAO;

	public RemitoServicio() {
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
		RemitoExterior remito = remitoExteriorDAO.buscarPorId(nroRemito);
		List<ItemRemito> items = ItemRemitoServicio.getInstancia().dameItems(remito); 
		remito.setItems(items);
		remitoExteriorDAO.conformar(remito);
	}

	public RemitoInterior generarRemito(OrdenCompra orden) { 
		Calendar fechaActual = Calendar.getInstance();
		Date fecha = fechaActual.getTime();
		RemitoInterior remito = new RemitoInterior();
		remito.setFecha(fecha);
		remito.setCasaCentral(CasaCentralServicio.getInstancia().obtenerCasaCentral());
		remitoDAO.persistir(remito);
		
		List<ItemRemito> items = new ArrayList<ItemRemito>();
		for(int i = 0; orden.getItems().size() - 1 >= i; i++){
			ItemRemito item = ItemRemitoServicio.getInstancia().guardarItemsInterior(remito.getNroRemito(), orden.getItems().get(i));
			items.add(item);
		}
		remito.setItems(items);
		return remito;
	}

}
