package com.group7.service;

import java.util.List;

import com.group7.business.RodamientoVO;
import com.group7.dao.RodamientoDAO;
import com.group7.entity.Rodamiento;
import com.group7.entity.enbeddable.RodamientoId;

public class RodamientoServicio {

	private static RodamientoDAO rodamientoDAO;
	private static RodamientoServicio instancia;

	public static RodamientoServicio getInstancia() {
		if (instancia == null)
			instancia = new RodamientoServicio();
		return instancia;
	}

	private RodamientoServicio() {
		rodamientoDAO = new RodamientoDAO();
	}

	/**
	 * 
	 * @param codigoSFK
	 * @param codigoPieza
	 * @param descripcion
	 * @param paisOrigen
	 * @param marca
	 * @param estado
	 */
	public void guardarRodamiento(String codigoSFK, String codigoPieza, String descripcion, String paisOrigen, String marca, Boolean estado) {
		rodamientoDAO.openCurrentSessionwithTransaction();
		Rodamiento r = new Rodamiento();
		RodamientoId rId = new RodamientoId();
		rId.setCodigoPieza(codigoPieza);
		rId.setCodigoSFK(codigoSFK);
		r.setRodamientoId(rId);
		r.setDescripcion(descripcion);
		r.setMarca(marca);
		r.setPaisOrigen(paisOrigen);
		r.setEstado(estado);
		rodamientoDAO.persistir(r);
		rodamientoDAO.closeCurrentSessionwithTransaction();
	}

	public void persistir(Rodamiento rodamiento) {
		rodamientoDAO.openCurrentSessionwithTransaction();
		rodamientoDAO.persistir(rodamiento);
		rodamientoDAO.closeCurrentSessionwithTransaction();
	}
	
	/**
	 * 
	 * @param SFK
	 * @param codigo
	 * @return
	 */
	public RodamientoVO buscarRodamiento(String SFK, String codigo) {
		rodamientoDAO.openCurrentSessionwithTransaction();
		Rodamiento rodamiento = rodamientoDAO.getRodamiento(SFK, codigo);
		rodamientoDAO.closeCurrentSessionwithTransaction();
		return rodamiento.getView();
	}

	/**
	 * 
	 * @param SFK
	 * @param codigo
	 * @return
	 */
	public Rodamiento buscarPorId(String SFK, String codigo) {
		rodamientoDAO.openCurrentSessionwithTransaction();
		Rodamiento rodamiento = rodamientoDAO.getRodamiento(SFK, codigo);
		rodamientoDAO.closeCurrentSessionwithTransaction();
		return rodamiento;
	}

	public List<Rodamiento> buscarTodos() {
		rodamientoDAO.openCurrentSessionwithTransaction();
		List<Rodamiento> rodamientos = rodamientoDAO.buscarTodos();
		rodamientoDAO.closeCurrentSessionwithTransaction();
		return rodamientos;
	}
	
}
