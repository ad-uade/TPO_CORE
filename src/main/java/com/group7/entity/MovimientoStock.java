package com.group7.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table (name = "movimientosStock")
public class MovimientoStock implements Serializable{
	
	private static final long serialVersionUID = -2333215608858606450L;

	@Id
	@GeneratedValue
	@Column (name = "nroMovimiento")
	private Integer movimientoId;
	@ManyToOne (cascade = CascadeType.ALL)
	@JoinColumns ({@JoinColumn (name = "codigoSFK", referencedColumnName="codigoSFK"), @JoinColumn (name = "codigoPieza", referencedColumnName="codigoPieza")})
	private Rodamiento rodamiento;
	@Column (name = "cantidad")
	private Integer cantidad;
	@Column (name = "tipo")
	private String tipo;
	@ManyToOne
	@JoinColumn (name = "idCasaCentral")
	private CasaCentral casaCentral;
	
	public MovimientoStock(){
		
	}
	
	public CasaCentral getCasaCentral() {
		return casaCentral;
	}

	public void setCasaCentral(CasaCentral casaCentral) {
		this.casaCentral = casaCentral;
	}

	public int getMovimientoId() {
		return movimientoId;
	}

	public void setMovimientoId(int movimientoId) {
		this.movimientoId = movimientoId;
	}

	public Rodamiento getRodamiento() {
		return rodamiento;
	}

	public void setRodamiento(Rodamiento rodamiento) {
		this.rodamiento = rodamiento;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
}