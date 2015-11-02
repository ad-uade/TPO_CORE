package com.group7.entity;

import java.io.Serializable;
import java.util.Date;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table (name = "comparativaPrecios")
public class ComparativaPrecios implements Serializable{ 

	private static final long serialVersionUID = -8774207616754720373L;

	private int ComparativaPrecioId;
	private Date fechaActualizacion;
	private List<ItemsComparativaPrecio> items;
	
	@Column (name = "fechaActualizacion")
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}
	
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public void setComparativaPrecioId(int comparativaPrecioId) {
		ComparativaPrecioId = comparativaPrecioId;
	}

	@Id
	@Column (name = "idComparativa")
	public int getComparativaPrecioId() {
		return ComparativaPrecioId;
	}

	public void setItems(List<ItemsComparativaPrecio> items) {
		this.items = items;
	}

	@OneToMany (cascade = CascadeType.ALL)
    @JoinColumn(name="idComparativa")
	public List<ItemsComparativaPrecio> getItems() {
		return items;
	}
	
}
