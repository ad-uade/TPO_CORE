package com.group7.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.group7.business.RodamientoVO;
import com.group7.entity.enbeddable.RodamientoId;

@Entity
@Table (name = "rodamientos")
public class Rodamiento implements Serializable{

	private static final long serialVersionUID = -9130251938584651859L;

	@EmbeddedId
	private RodamientoId rodamientoId;
	@Column (name = "descripcion")
	private String descripcion;
	@Column (name = "marca")
	private String marca;
	@Column (name = "paisOrigen")
	private String paisOrigen;
	@Column (name = "estado")
	private Boolean estado;
	
	public Rodamiento(){
		
	}

	public RodamientoId getRodamientoId() {
		return rodamientoId;
	}

	public void setRodamientoId(RodamientoId rodamientoId) {
		this.rodamientoId = rodamientoId;
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getPaisOrigen() {
		return paisOrigen;
	}

	public void setPaisOrigen(String paisOrigen) {
		this.paisOrigen = paisOrigen;
	}
	
	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	
	@Override
	public String toString() {
		return "Rodamiento [descripcion=" + descripcion + ", marca=" + marca + ", paisOrigen=" + paisOrigen + ", estado=" + estado + "]";
	}
	
	public RodamientoVO getView(){
		RodamientoVO rodamientoVO = new RodamientoVO();
		rodamientoVO.setCodigoSFK(this.getRodamientoId().getCodigoSFK());
		rodamientoVO.setCodigoPieza(this.getRodamientoId().getCodigoPieza());
		rodamientoVO.setDescripcion(this.getDescripcion());
		rodamientoVO.setMarca(this.getMarca());
		rodamientoVO.setPaisOrigen(this.getPaisOrigen());
//		StockDTO stkDTO = new StockDTO();
//		stkDTO.setCantidad(this.getStock().getCantidad());
//		stkDTO.setPrecio(this.getStock().getPrecio());
//		rodDTO.setStock(stkDTO);
		return rodamientoVO;
	}
	
}
