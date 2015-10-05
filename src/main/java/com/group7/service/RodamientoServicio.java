package com.group7.service;

import java.util.ArrayList;
import java.util.List;

import com.group7.business.RodamientoVO;
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

	public RodamientoVO rodamientoToVo(Rodamiento rodamiento) {
		RodamientoVO rodaVO = new RodamientoVO();
		rodaVO.setCodigoPieza(rodamiento.getRodamientoId().getCodigoPieza());
		rodaVO.setCodigoSFK(rodamiento.getRodamientoId().getCodigoSFK());
		rodaVO.setDescripcion(rodamiento.getDescripcion());
		rodaVO.setEstado(rodamiento.isEstado());
		rodaVO.setMarca(rodamiento.getMarca());
		rodaVO.setPaisOrigen(rodamiento.getPaisOrigen());
		return rodaVO;
	}

	public Rodamiento rodamientoVoToRodamiento(RodamientoVO rodamiento) {
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
		
		miRodamientoDAO.altaRodamiento(r);
	}

	public RodamientoVO obtenerRodamiento(String SFK, String codigo) {
		try {
			RodamientoDAO miRodamiento = new RodamientoDAO();
			Rodamiento rodamiento = miRodamiento.getRodamiento(SFK,codigo);
			RodamientoVO r = this.rodamientoToVo(rodamiento);
			return r;
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<RodamientoVO> dameRodamientos() {
		try {
			RodamientoDAO miRodamiento = new RodamientoDAO();
			List<Rodamiento> rodamientos = miRodamiento.getRodamientos();
			List<RodamientoVO> rodamientosVO = new ArrayList<RodamientoVO>();
			for (int i=0;i<rodamientos.size();i++)
			{
				RodamientoVO r = this.rodamientoToVo(rodamientos.get(i));
				rodamientosVO.add(r);
			}
			return rodamientosVO;
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
