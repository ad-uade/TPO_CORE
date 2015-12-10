package com.group7.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.group7.entity.enbeddable.ItemListaPreciosId;

@Entity
@Table (name = "itemsListaPrecios")
public class ItemListaPrecios implements Serializable{

	private static final long serialVersionUID = 2505180163992322995L;

	@EmbeddedId
	private ItemListaPreciosId id;
	@Column (name = "precioVenta")
	private Float precioVenta;
	@Column (name = "descuento")
	private Float descuento;
	
	public ItemListaPrecios(){
		
	}

	public ItemListaPreciosId getId() {
		return id;
	}

	public void setId(ItemListaPreciosId id) {
		this.id = id;
	}

	public float getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(float precioVenta) {
		this.precioVenta = precioVenta;
	}

	public float getDescuento() {
		return descuento;
	}

	public void setDescuento(float descuento) {
		this.descuento = descuento;
	}
	
}
