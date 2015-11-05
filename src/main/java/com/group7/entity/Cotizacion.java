package com.group7.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table (name = "cotizaciones")
public class Cotizacion implements Serializable{
	
	private static final long serialVersionUID = 9064892059887261666L;

	@Id
	@Column (name = "nroCotizacion")
	@GeneratedValue
	private Integer id;
	
	@Column (name = "fecha")
	private Date fecha;
	
	@Column (name = "diasValidez")
	private int diasValidez;
	
	@ManyToOne
	@JoinColumn (name = "CUILCliente")
	private Cliente cliente;
	
	@OneToOne
	@JoinColumn (name = "nroSolicitudCotizacion")
	private SolicitudCotizacion SC;
	
	@OneToMany (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn (name = "nroCotizacion")
	private List<ItemCotizacion>items;
	
	@ManyToOne
	@JoinColumn (name = "idOficina")
	private OficinaVentas ODV;
	
	public Cotizacion(){
			
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getDiasValidez() {
		return diasValidez;
	}

	public void setDiasValidez(int diasValidez) {
		this.diasValidez = diasValidez;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public List<ItemCotizacion> getItems() {
		return items;
	}

	public void setItems(List<ItemCotizacion> items) {
		this.items = items;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public OficinaVentas getOficinaVentas() {
		return ODV;
	}

	public void setOficinaVentas(OficinaVentas oDV) {
		ODV = oDV;
	}

	public SolicitudCotizacion getSolicitudCotizacion() {
		return SC;
	}

	public void setSolicitudCotizacion(SolicitudCotizacion sC) {
		SC = sC;
	}
	
}
