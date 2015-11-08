package com.group7.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table (name = "condicionCompras")
public class CondicionCompra implements Serializable{

	private static final long serialVersionUID = -1237362852855130123L;

	@Id
	@Column (name = "nroCondicionCompra")
	private Integer nroCondicionCompra;
	@Column (name = "fechaDesde")
	private Date fechaDesde;
	@Column (name = "fechaHasta")
	private Date fechaHasta;
	@ManyToOne
	@JoinColumn (name = "idFormaPago")
	private FormaPago formaPago;
	
	private CondicionCompra(){
		
	}

	/**
	 * @return the nroCondicionCompra
	 */
	public Integer getNroCondicionCompra() {
		return nroCondicionCompra;
	}

	/**
	 * @param nroCondicionCompra the nroCondicionCompra to set
	 */
	public void setNroCondicionCompra(Integer nroCondicionCompra) {
		this.nroCondicionCompra = nroCondicionCompra;
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
