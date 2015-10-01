package com.group7.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.group7.entity.enbeddable.ItemRemitoId;

@Entity
@Table (name = "itemsRemito")
public class ItemRemito implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6107651135974772189L;

	@EmbeddedId
	private ItemRemitoId id;
	
	@Column (name = "cantidad")
	private int cantidad;
	
	public ItemRemito(){
		
	}
	
	public ItemRemitoId getId() {
		return id;
	}

	public void setId(ItemRemitoId id) {
		this.id = id;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

}