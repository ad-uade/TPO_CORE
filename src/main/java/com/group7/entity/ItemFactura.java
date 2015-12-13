package com.group7.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.group7.business.ItemFacturaVO;
import com.group7.entity.enbeddable.ItemFacturaId;

@Entity
@Table (name = "itemsFactura")
public class ItemFactura implements Serializable{

	private static final long serialVersionUID = -6301951054889369307L;

	@EmbeddedId
	private ItemFacturaId id;
	@Column (name = "cantidad")
	private Integer cantidad;
	@Column (name = "precioUnitario")
	private Float precioUnitario;
	
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

	public float subtotal() {
		return this.getPrecioUnitario() * this.getCantidad();
	}

	public ItemFacturaVO getView() {
		ItemFacturaVO itemFacturaVO = new ItemFacturaVO();
		itemFacturaVO.setNroFactura(this.getId().getFactura().getView());
		itemFacturaVO.setPrecioUnitario(this.getPrecioUnitario());
		itemFacturaVO.setSubtotal(this.subtotal());
		itemFacturaVO.setRodamiento(this.getId().getRodamiento().getView());
		return itemFacturaVO;
	}
	
}
