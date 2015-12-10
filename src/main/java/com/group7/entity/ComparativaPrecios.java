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

	@Id
	@Column (name = "idComparativa")
	private Integer ComparativaPrecioId;
	@Column (name = "fechaActualizacion")
	private Date fechaActualizacion;
	@OneToMany (cascade = CascadeType.ALL)
    @JoinColumn(name="idComparativa")
	private List<ItemComparativaPrecio> items;
	/**
	 * @return the comparativaPrecioId
	 */
	public Integer getComparativaPrecioId() {
		return ComparativaPrecioId;
	}
	/**
	 * @param comparativaPrecioId the comparativaPrecioId to set
	 */
	public void setComparativaPrecioId(Integer comparativaPrecioId) {
		ComparativaPrecioId = comparativaPrecioId;
	}
	/**
	 * @return the fechaActualizacion
	 */
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}
	/**
	 * @param fechaActualizacion the fechaActualizacion to set
	 */
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	/**
	 * @return the items
	 */
	public List<ItemComparativaPrecio> getItems() {
		return items;
	}
	/**
	 * @param items the items to set
	 */
	public void setItems(List<ItemComparativaPrecio> items) {
		this.items = items;
	}
	
}
