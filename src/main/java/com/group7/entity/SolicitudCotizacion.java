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
import javax.persistence.Table;

@Entity
@Table (name = "solicitudesCotizacion")
public class SolicitudCotizacion implements Serializable{
	
	private static final long serialVersionUID = -1899638291364688150L;

	@Id
	@GeneratedValue
	@Column (name = "nroSolicitudCotizacion")
	private Integer nroSolicitudCotizacion;
	@Column (name = "fecha")
	private Date fecha;
	@ManyToOne
	@JoinColumn (name = "CUILCliente")
	private Cliente cliente;
	@OneToMany (cascade = CascadeType.ALL, fetch=FetchType.EAGER) 
	@JoinColumn (name = "nroSolicitudCotizacion")
	private List<ItemSolicitudCotizacion>items;
	
	public SolicitudCotizacion(){
		
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public int getNroSolicitudCotizacion() {
		return nroSolicitudCotizacion;
	}

	public void setNroSolicitudCotizacion(int nroSolicitudCotizacion) {
		this.nroSolicitudCotizacion = nroSolicitudCotizacion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public List<ItemSolicitudCotizacion> getItems() {
		return items;
	}

	public void setItems(List<ItemSolicitudCotizacion> items) {
		this.items = items;
	}
	
}
