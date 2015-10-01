package com.group7.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.group7.entity.enbeddable.RodamientoId;

@Entity
@Table (name = "rodamientos")
public class Rodamiento implements Serializable{

	private static final long serialVersionUID = -9130251938584651859L;

	@EmbeddedId
	private RodamientoId rodamientoId;
	private String descripcion;
	private String marca;
	private String paisOrigen;
	private boolean estado;
	
	public Rodamiento(){
		
	}

	public RodamientoId getRodamientoId() {
		return rodamientoId;
	}

	public void setRodamientoId(RodamientoId rodamientoId) {
		this.rodamientoId = rodamientoId;
	}

	@Column (name = "descripcion")
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column (name = "marca")
	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	@Column (name = "paisOrigen")
	public String getPaisOrigen() {
		return paisOrigen;
	}

	public void setPaisOrigen(String paisOrigen) {
		this.paisOrigen = paisOrigen;
	}

	@Column (name = "estado")
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
	
}
