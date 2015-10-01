package com.group7.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CC")
public class CuentaCorriente extends FormaPago {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4490615941660207028L;
	
	private float recargo;
	private int dias;
	
	public CuentaCorriente(){
		
	}

	@Column (name = "recargo")
	public float getRecargo() {
		return recargo;
	}

	public void setRecargo(float recargo) {
		this.recargo = recargo;
	}

	@Column (name = "dias")
	public int getDias() {
		return dias;
	}

	public void setDias(int dias) {
		this.dias = dias;
	}
	
}