package com.group7.entity.enbeddable;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

import com.group7.entity.Rodamiento;

@Embeddable
public class ItemRemitoId implements Serializable{

	private static final long serialVersionUID = -4549632290304868262L;

	@Column (name = "nroRemito")
	private Integer nroRemito;
	@ManyToOne (optional = false)
	@JoinColumns ({@JoinColumn (name = "codigoSFK"), @JoinColumn (name = "codigoPieza")})
	private Rodamiento rodamiento;
	
	public ItemRemitoId(){
		
	}

	public Integer getNroRemito() {
		return nroRemito;
	}

	public void setNroRemito(Integer nroRemito) {
		this.nroRemito = nroRemito;
	}

	public Rodamiento getRodamiento() {
		return rodamiento;
	}

	public void setRodamiento(Rodamiento rodamiento) {
		this.rodamiento = rodamiento;
	}
	
}
