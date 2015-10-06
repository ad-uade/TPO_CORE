package com.group7.service;

import com.group7.dao.RodamientoDAO;
import com.group7.entity.Rodamiento;
import com.group7.entity.enbeddable.RodamientoId;

public class RodamientoServicio {

	private static RodamientoServicio instancia;
	
	public static RodamientoServicio getInstancia(){
		if(instancia == null)
			instancia = new RodamientoServicio();
		return instancia;
	}

	public void guardarRodamiento(String codigoSFK, String codigoPieza,	String descripcion, String paisOrigen, String marca, boolean estado) {
		RodamientoDAO miRodamientoDAO = new RodamientoDAO();
		Rodamiento r = new Rodamiento();
		RodamientoId rId = new RodamientoId();
		rId.setCodigoPieza(codigoPieza);
		rId.setCodigoSFK(codigoSFK);
		r.setRodamientoId(rId);
		r.setDescripcion(descripcion);
		r.setMarca(marca);
		r.setPaisOrigen(paisOrigen);
		r.setEstado(estado);
		
		miRodamientoDAO.persistir(r);
	}

}
