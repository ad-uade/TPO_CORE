package com.group7.entity.enbeddable;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

import com.group7.entity.Remito;
import com.group7.entity.Rodamiento;

@Embeddable
public class ItemRemitoId implements Serializable{

	private static final long serialVersionUID = -4549632290304868262L;

	@ManyToOne (optional = false)
	@JoinColumn(name="nroRemito", referencedColumnName="nroRemito")
	private Remito nroRemito;
	@ManyToOne (optional = false)
	@JoinColumns ({@JoinColumn (name = "codigoSFK", referencedColumnName="codigoSFK"), @JoinColumn (name = "codigoPieza", referencedColumnName="codigoPieza")})
	private Rodamiento rodamiento;
	
	public ItemRemitoId(){
		
	}

	public Remito getNroRemito() {
		return nroRemito;
	}

	public void setNroRemito(Remito nroRemito) {
		this.nroRemito = nroRemito;
	}

	public Rodamiento getRodamiento() {
		return rodamiento;
	}

	public void setRodamiento(Rodamiento rodamiento) {
		this.rodamiento = rodamiento;
	}
	
}
