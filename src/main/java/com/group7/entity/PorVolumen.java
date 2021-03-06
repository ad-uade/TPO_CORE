package com.group7.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("PorVolumen")
public class PorVolumen extends EstrategiaDescuentoCliente{

	private static final long serialVersionUID = 7105484000569289780L;
	
	@Column (name = "volumen")
	private Integer volumen;
	
	public PorVolumen(){
		
	}

	public int getVolumen() {
		return volumen;
	}

	public void setVolumen(int volumen) {
		this.volumen = volumen;
	}

}
