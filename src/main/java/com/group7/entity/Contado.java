package com.group7.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CO")
public class Contado extends FormaPago {
	
	private static final long serialVersionUID = -7698294557498962843L;
	
	@Column (name = "descuento")
	private Float descuento;
	
	public Contado(){
		
	}
	
	public float getDescuento() {
		return descuento;
	}

	public void setDescuento(float descuento) {
		this.descuento = descuento;
	}

}
