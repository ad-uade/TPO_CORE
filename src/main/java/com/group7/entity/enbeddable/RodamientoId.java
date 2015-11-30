package com.group7.entity.enbeddable;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RodamientoId implements Serializable{

	private static final long serialVersionUID = -7384775515165642178L;

	@Column (name = "codigoSFK")
	private String codigoSFK;
	@Column (name = "codigoPieza")
	private String codigoPieza;
	
	public RodamientoId(){
		
	}

	public String getCodigoSFK() {
		return codigoSFK;
	}

	public void setCodigoSFK(String codigoSFK) {
		this.codigoSFK = codigoSFK;
	}

	public String getCodigoPieza() {
		return codigoPieza;
	}

	public void setCodigoPieza(String codigoPieza) {
		this.codigoPieza = codigoPieza;
	}
	
}