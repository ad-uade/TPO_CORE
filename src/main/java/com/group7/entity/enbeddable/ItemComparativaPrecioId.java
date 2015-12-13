package com.group7.entity.enbeddable;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

import com.group7.entity.ComparativaPrecios;
import com.group7.entity.Rodamiento;

@Embeddable
public class ItemComparativaPrecioId implements Serializable{

	private static final long serialVersionUID = -5565720726640565509L;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="idComparativa", referencedColumnName="idComparativa")
	private ComparativaPrecios idComparativa;
	@ManyToOne (optional = false, targetEntity=Rodamiento.class)
	@JoinColumns ({@JoinColumn (name = "codigoSFK", referencedColumnName="codigoSFK"), @JoinColumn (name = "codigoPieza", referencedColumnName="codigoPieza")})
	private Rodamiento rodamiento;
	
	public ItemComparativaPrecioId(){
		
	}

	public ComparativaPrecios getComparativaPrecios() {
		return idComparativa;
	}

	public void setComparativaPrecios(ComparativaPrecios idComparativa) {
		this.idComparativa = idComparativa;
	}

	public Rodamiento getRodamiento() {
		return rodamiento;
	}

	public void setRodamiento(Rodamiento rodamiento) {
		this.rodamiento = rodamiento;
	}
	
}
