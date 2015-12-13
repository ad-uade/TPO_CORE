package com.group7.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

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
	@ManyToOne (optional = false, targetEntity=Rodamiento.class)
	@JoinColumns ({@JoinColumn (name = "codigoSFK", referencedColumnName="codigoSFK"), @JoinColumn (name = "codigoPieza", referencedColumnName="codigoPieza")})
	private Rodamiento rodamiento;
	@Column(name="fechaMovimiento")
	private Date fechaMovimiento = Calendar.getInstance().getTime();
	@Enumerated
	private MovimientoStock movientoStock;
	@Column (name = "cantidad")
	private Integer cantidad;
	
	public Stock(){
		
	}

	/**
	 * @return the nroMovimiento
	 */
	public Integer getNroMovimiento() {
		return nroMovimiento;
	}

	/**
	 * @param nroMovimiento the nroMovimiento to set
	 */
	public void setNroMovimiento(Integer nroMovimiento) {
		this.nroMovimiento = nroMovimiento;
	}

	/**
	 * @return the rodamiento
	 */
	public Rodamiento getRodamiento() {
		return rodamiento;
	}

	/**
	 * @param rodamiento the rodamiento to set
	 */
	public void setRodamiento(Rodamiento rodamiento) {
		this.rodamiento = rodamiento;
	}

	/**
	 * @return the fechaMovimiento
	 */
	public Date getFechaMovimiento() {
		return fechaMovimiento;
	}

	/**
	 * @param fechaMovimiento the fechaMovimiento to set
	 */
	public void setFechaMovimiento(Date fechaMovimiento) {
		this.fechaMovimiento = fechaMovimiento;
	}

	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
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
	
	public void registrarIngreso(int cantidad2) {
		this.setCantidad(cantidad2);
		this.movientoStock = MovimientoStock.INGRESO;
	}
	
	public void registrarSolicitud(int cantidad2) {
		this.setCantidad(cantidad2);
		this.movientoStock = MovimientoStock.SOLICITADO;
	}
	
}