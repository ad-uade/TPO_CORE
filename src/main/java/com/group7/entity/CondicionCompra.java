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

	/**
	 * 
	 */
	private static final long serialVersionUID = -1237362852855130123L;

	@Id
	@Column (name = "nroCondicionCompra")
	private int nroCondicionCompra;
	
	@Column (name = "fechaDesde")
	private Date fechaDesde;
	
	@Column (name = "fechaHasta")
	private Date fechaHasta;
	
	@ManyToOne
	@JoinColumn (name = "idFormaPago")
	private FormaPago formaPago;
	
	private CondicionCompra(){
		
	}

	public int getNroCondicionCompra() {
		return nroCondicionCompra;
	}

	public void setNroCondicionCompra(int nroCondicionCompra) {
		this.nroCondicionCompra = nroCondicionCompra;
	}

	public Date getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public Date getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public FormaPago getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(FormaPago formaPago) {
		this.formaPago = formaPago;
	}
	
}
