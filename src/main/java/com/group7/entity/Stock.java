package com.group7.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table (name = "movimientosStock")
public class Stock implements Serializable{
	
	private static final long serialVersionUID = -2333215608858606450L;

	@Id
	@GeneratedValue
	@Column (name = "nroMovimiento")
	private Integer nroMovimiento;
	@ManyToOne (cascade = CascadeType.ALL)
	@JoinColumns ({@JoinColumn (name = "codigoSFK", referencedColumnName="codigoSFK"), @JoinColumn (name = "codigoPieza", referencedColumnName="codigoPieza")})
	private Rodamiento rodamiento;
	@Enumerated
	private MovimientoStock movientoStock;
	@Column (name = "cantidad")
	private Integer cantidad;
	
	public Stock(){
		
	}

	public int getMovimientoId() {
		return nroMovimiento;
	}

	/**
	 * @return the movientoStock
	 */
	public MovimientoStock getMovientoStock() {
		return movientoStock;
	}

	/**
	 * @param movientoStock the movientoStock to set
	 */
	public void setMovientoStock(MovimientoStock movientoStock) {
		this.movientoStock = movientoStock;
	}

	public void setMovimientoId(int movimientoId) {
		this.nroMovimiento = movimientoId;
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

	public void registrarEgreso(int cantidad2) {
		this.setCantidad(cantidad2);
		this.movientoStock = MovimientoStock.EGRESO;
	}
	
}