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

	private static final long serialVersionUID = 68233671178771415L;
	
	@Id
	@Column (name = "nroCondicionVenta")
	private Integer nroCondicion;
	@Column (name = "fechaDesde")
	private Date fechaDesde;
	@Column (name = "fechaHasta")
	private Date fechaHasta;
	@Column (name = "iva")
	private Float iva;
	@ManyToOne
	@JoinColumn(name = "idFormaPago")
	private FormaPago formaPago;

	public CondicionVenta(){
		
	}

	/**
	 * @return the nroCondicion
	 */
	public Integer getNroCondicion() {
		return nroCondicion;
	}

	/**
	 * @param nroCondicion the nroCondicion to set
	 */
	public void setNroCondicion(Integer nroCondicion) {
		this.nroCondicion = nroCondicion;
	}

	/**
	 * @return the fechaDesde
	 */
	public Date getFechaDesde() {
		return fechaDesde;
	}

	/**
	 * @param fechaDesde the fechaDesde to set
	 */
	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	/**
	 * @return the fechaHasta
	 */
	public Date getFechaHasta() {
		return fechaHasta;
	}

	/**
	 * @param fechaHasta the fechaHasta to set
	 */
	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	/**
	 * @return the iva
	 */
	public Float getIva() {
		return iva;
	}

	/**
	 * @param iva the iva to set
	 */
	public void setIva(Float iva) {
		this.iva = iva;
	}

	/**
	 * @return the formaPago
	 */
	public FormaPago getFormaPago() {
		return formaPago;
	}

	/**
	 * @param formaPago the formaPago to set
	 */
	public void setFormaPago(FormaPago formaPago) {
		this.formaPago = formaPago;
	}
	
}
