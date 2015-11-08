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
	@Column (name = "recargo")
	private Float recargo;
	@Column (name = "dias")
	private Integer dias;
	
	public CuentaCorriente(){
		
	}

	/**
	 * @return the recargo
	 */
	public Float getRecargo() {
		return recargo;
	}

	/**
	 * @param recargo the recargo to set
	 */
	public void setRecargo(Float recargo) {
		this.recargo = recargo;
	}

	/**
	 * @return the dias
	 */
	public Integer getDias() {
		return dias;
	}

	/**
	 * @param dias the dias to set
	 */
	public void setDias(Integer dias) {
		this.dias = dias;
	}

}
