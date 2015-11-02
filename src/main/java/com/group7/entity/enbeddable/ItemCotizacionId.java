package com.group7.entity.enbeddable;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumns;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.group7.entity.Rodamiento;

@Embeddable
public class ItemCotizacionId implements Serializable{

	private static final long serialVersionUID = -7494563893808763545L;
	
	private int idCotizacion;
	private Rodamiento rodamiento;
	
	public ItemCotizacionId(){
		
	}

	@Column (name = "nroCotizacion")
	public int getIdCotizacion() {
		return idCotizacion;
	}

	public void setIdCotizacion(int idCotizacion) {
		this.idCotizacion = idCotizacion;
	}

	@ManyToOne (optional = false)
	@JoinColumns ({@JoinColumn (name = "codigoSFK"), @JoinColumn (name = "codigoPieza")})
	public Rodamiento getRodamiento() {
		return rodamiento;
	}

	public void setRodamiento(Rodamiento rodamiento) {
		this.rodamiento = rodamiento;
	}
	
}