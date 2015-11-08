package com.group7.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("RE")
public class RemitoExterior extends Remito {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2529719019032328609L;
	
	@Column(name = "conformado")
	private Boolean conformeCliente;

	public RemitoExterior(){
		
	}

	public void setConformeCliente(Boolean conformeCliente) {
		this.conformeCliente = conformeCliente;
	}

	public Boolean isConformeCliente() {
		return conformeCliente;
	}
	
}