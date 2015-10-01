package com.group7.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.group7.entity.enbeddable.ItemOrdenCompraId;

@Entity
@Table (name = "itemsOrdenCompra")
public class ItemOrdenCompra implements Serializable{

	private static final long serialVersionUID = -9183440690926998533L;

	@EmbeddedId
	private ItemOrdenCompraId id;
	
	@Column (name = "cantidad")
	private int cantidad;
	
	public ItemOrdenCompra(){
		
	}
	
	public ItemOrdenCompraId getId() {
		return id;
	}

	public void setId(ItemOrdenCompraId id) {
		this.id = id;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

}
