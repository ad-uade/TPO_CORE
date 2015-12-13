package com.group7.entity.enbeddable;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

import com.group7.entity.Cotizacion;
import com.group7.entity.Rodamiento;

@Embeddable
public class ItemCotizacionId implements Serializable{

	private static final long serialVersionUID = -7494563893808763545L;
	
	@ManyToOne (optional = false)
	@JoinColumn(name = "nroCotizacion", referencedColumnName="nroCotizacion")
	private Cotizacion cotizacion;
	@ManyToOne (optional = false, targetEntity=Rodamiento.class)
	@JoinColumns ({@JoinColumn (name = "codigoSFK", referencedColumnName="codigoSFK"), @JoinColumn (name = "codigoPieza", referencedColumnName="codigoPieza")})
	private Rodamiento rodamiento;
	
	public ItemCotizacionId(){
		
	}

	public Cotizacion getIdCotizacion() {
		return cotizacion;
	}

	public void setIdCotizacion(Cotizacion idCotizacion) {
		this.cotizacion = idCotizacion;
	}

	public Rodamiento getRodamiento() {
		return rodamiento;
	}

	public void setRodamiento(Rodamiento rodamiento) {
		this.rodamiento = rodamiento;
	}
	
}