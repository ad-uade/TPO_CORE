package com.group7.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
/**
 * 
 *
 */
@Entity
@Table(name="condicionVentas")
public class CondicionVenta implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 68233671178771415L;

	private int nroCondicion;
	private Date fechaDesde;
	private Date fechaHasta;
	private float iva;
	private FormaPago formaPago;

	public CondicionVenta(){
		
	}
	
	@Id
	@Column (name = "nroCondicionVenta")
	public int getNroCondicion() {
		return nroCondicion;
	}

	public void setNroCondicion(int nroCondicion) {
		this.nroCondicion = nroCondicion;
	}

	@Column (name = "fechaDesde")
	public Date getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	@Column (name = "fechaHasta")
	public Date getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	@Column (name = "iva")
	public float getIva() {
		return iva;
	}

	public void setIva(float iVA) {
		iva = iVA;
	}

	@ManyToOne
	@JoinColumn(name = "idFormaPago")
	public FormaPago getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(FormaPago formaPago) {
		this.formaPago = formaPago;
	}
	
}
