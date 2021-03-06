package com.group7.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.group7.entity.enbeddable.ItemSolicitudCotizacionId;

@Entity
@Table (name = "itemsSolicitudCotizacion")
public class ItemSolicitudCotizacion implements Serializable{
	
	private static final long serialVersionUID = -5706020496650795598L;

	@EmbeddedId
	private ItemSolicitudCotizacionId id;
	@Column (name = "cantidad")
	private Integer cantidad;
	@ManyToOne
	@JoinColumn (name = "nroCondicionVenta")
	private CondicionVenta condicion;
	
	public ItemSolicitudCotizacion(){
		
	}

	public ItemSolicitudCotizacionId getId() {
		return id;
	}

	public void setId(ItemSolicitudCotizacionId id) {
		this.id = id;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public CondicionVenta getCondicion() {
		return condicion;
	}

	public void setCondicion(CondicionVenta condicion) {
		this.condicion = condicion;
	}
	
}