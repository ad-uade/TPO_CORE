package com.group7.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.group7.business.ItemsComparativaPreciosVO;
import com.group7.entity.enbeddable.ItemComparativaPrecioId;

@Entity
@Table (name = "itemsComparativaPrecios")
public class ItemComparativaPrecio implements Serializable{ 

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
	
	public ItemComparativaPrecio(){
		
	}

	public Proveedor getProveedor() {
		return proveedorListaPrecios;
	}

	public void setProveedor(Proveedor proveedorListaPrecios) {
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

	public ItemsComparativaPreciosVO getView() {
		ItemsComparativaPreciosVO itemsComparativaPreciosVO = new ItemsComparativaPreciosVO();
		itemsComparativaPreciosVO.setMejorPrecio(this.getMejorPrecio());
		itemsComparativaPreciosVO.setRodamiento(this.getId().getRodamiento().getView());
		itemsComparativaPreciosVO.setNumListaPrecios(this.getNumeroListaPrecios());
		itemsComparativaPreciosVO.setProveedor(this.getProveedor().getView());
		itemsComparativaPreciosVO.setComparativa(this.getId().getComparativaPrecios().getView());
		return itemsComparativaPreciosVO;
	}

}
