package com.group7.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Entity
@DiscriminatorValue("RI")
public class RemitoInterior extends Remito {

	private static final long serialVersionUID = 3421927431308272972L;
	@ManyToOne
	@JoinColumn (name = "idCasaCentral")
	private CasaCentral casaCentral;
	
	public RemitoInterior(){
		
	}

	public CasaCentral getCasaCentral() {
		return casaCentral;
	}

	public void setCasaCentral(CasaCentral casaCentral) {
		this.casaCentral = casaCentral;
	}
	
}