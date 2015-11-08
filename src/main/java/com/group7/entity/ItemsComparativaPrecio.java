package com.group7.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.group7.entity.enbeddable.ItemComparativaPrecioId;

@Entity
@Table (name = "itemsComparativaPrecios")
public class ItemsComparativaPrecio implements Serializable{ //Agregar esta clase al DC

	private static final long serialVersionUID = 2089142792136800938L;

	@EmbeddedId
	private ItemComparativaPrecioId id;
	@Column (name = "mejorPrecio")
	private Float mejorPrecio;
	@Column (name = "numListaPrecios")
	private Integer numeroListaPrecios;
	@ManyToOne
	@JoinColumn (name = "CUILProveedor")
	private Proveedor proveedorListaPrecios;
	
	public ItemsComparativaPrecio(){
		
	}

	public Proveedor getProveedorListaPrecios() {
		return proveedorListaPrecios;
	}

	public void setProveedorListaPrecios(Proveedor proveedorListaPrecios) {
		this.proveedorListaPrecios = proveedorListaPrecios;
	}

	public float getMejorPrecio() {
		return mejorPrecio;
	}

	public void setMejorPrecio(float mejorPrecio) {
		this.mejorPrecio = mejorPrecio;
	}

	public int getNumeroListaPrecios() {
		return numeroListaPrecios;
	}

	public void setNumeroListaPrecios(int numeroListaPrecios) {
		this.numeroListaPrecios = numeroListaPrecios;
	}

	public ItemComparativaPrecioId getId() {
		return id;
	}

	public void setId(ItemComparativaPrecioId id) {
		this.id = id;
	}

}
