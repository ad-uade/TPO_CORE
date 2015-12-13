package com.group7.service;

import java.util.ArrayList;
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

	public RodamientoVO modelToView(Rodamiento rodamiento) {
		RodamientoVO rodaVO = new RodamientoVO();
		rodaVO.setCodigoPieza(rodamiento.getRodamientoId().getCodigoPieza());
		rodaVO.setCodigoSFK(rodamiento.getRodamientoId().getCodigoSFK());
		rodaVO.setEstado(rodamiento.isEstado());
		rodaVO.setMarca(rodamiento.getMarca());
		rodaVO.setDescripcion(rodamiento.getDescripcion());
		rodaVO.setPaisOrigen(rodamiento.getPaisOrigen());
		return rodaVO;
	}

	public Rodamiento viewToModel(RodamientoVO rodamiento) {
		Rodamiento rodamientoH = new Rodamiento();
		RodamientoId rodamientoId = new RodamientoId();
		rodamientoId.setCodigoPieza(rodamiento.getCodigoPieza());
		rodamientoId.setCodigoSFK(rodamiento.getCodigoSFK());
		rodamientoH.setRodamientoId(rodamientoId);
		rodamientoH.setDescripcion(rodamiento.getDescripcion());
		rodamientoH.setMarca(rodamiento.getMarca());
		rodamientoH.setPaisOrigen(rodamiento.getPaisOrigen());
		rodamientoH.setEstado(rodamiento.isEstado());
		return rodamientoH;
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

	/**
	 * 
	 * @param SFK
	 * @param codigo
	 * @return
	 */
	public RodamientoVO buscarRodamiento(String SFK, String codigo) {
		rodamientoDAO.openCurrentSessionwithTransaction();
		Rodamiento rodamiento = rodamientoDAO.getRodamiento(SFK, codigo);
		RodamientoVO r = this.modelToView(rodamiento);
		rodamientoDAO.closeCurrentSessionwithTransaction();
		return r;
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
	
	/**
	 * 
	 * @return
	 */
	public List<RodamientoVO> dameRodamientos() {
		rodamientoDAO.openCurrentSessionwithTransaction();
		List<Rodamiento> rodamientos = rodamientoDAO.buscarTodos();
		List<RodamientoVO> rodamientosVO = new ArrayList<RodamientoVO>();
		for (int i = 0; i < rodamientos.size(); i++) {
			RodamientoVO r = this.modelToView(rodamientos.get(i));
			rodamientosVO.add(r);
		}
		rodamientoDAO.closeCurrentSessionwithTransaction();
		return rodamientosVO;
	}

	public List<Rodamiento> buscarTodos() {
		rodamientoDAO.openCurrentSessionwithTransaction();
		List<Rodamiento> rodamientos = rodamientoDAO.buscarTodos();
		rodamientoDAO.closeCurrentSessionwithTransaction();
		return rodamientos;
	}
	
}
