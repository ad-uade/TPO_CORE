package com.group7.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("PorMonto")
public class PorMonto extends EstrategiaDescuentoCliente{

	private static final long serialVersionUID = 2968788854329307190L;
	
	@Column (name = "monto")
	private Float monto;
	
	public PorMonto(){
		
	}

	public float getMonto() {
		return monto;
	}

	public void setMonto(float monto) {
		this.monto = monto;
	}

}
