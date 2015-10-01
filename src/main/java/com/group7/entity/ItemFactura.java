package com.group7.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.group7.entity.enbeddable.ItemFacturaId;

@Entity
@Table (name = "itemsFactura")
public class ItemFactura implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6301951054889369307L;

	@EmbeddedId
	private ItemFacturaId id;
	
	@Column (name = "cantidad")
	private int cantidad;
	
	@Column (name = "precioUnitario")
	private float precioUnitario;
	
	@OneToOne
	@JoinColumn (name = "nroCondicion")
	private CondicionVenta condVenta;
	
	public ItemFactura(){
		
	}

	public ItemFacturaId getId() {
		return id;
	}

	public void setId(ItemFacturaId id) {
		this.id = id;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public float getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(float precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public CondicionVenta getCondVenta() {
		return condVenta;
	}

	public void setCondVenta(CondicionVenta condVenta) {
		this.condVenta = condVenta;
	}
	
}
