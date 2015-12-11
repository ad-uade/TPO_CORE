package com.group7.entity.enbeddable;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

import com.group7.entity.Rodamiento;
import com.group7.entity.SolicitudCotizacion;

@Embeddable
public class ItemSolicitudCotizacionId implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8839767171810114755L;
	@ManyToOne
	@JoinColumn (name = "nroSolicitudCotizacion", referencedColumnName="nroSolicitudCotizacion")
	private SolicitudCotizacion nroSolicitudCotizacion;
	@ManyToOne (optional = false)
	@JoinColumns ({@JoinColumn (name = "codigoSFK", referencedColumnName="codigoSFK"), @JoinColumn (name = "codigoPieza", referencedColumnName="codigoPieza")})
	private Rodamiento rodamiento;
	
	public ItemSolicitudCotizacionId(){
		
	}

	public SolicitudCotizacion getSolicitudCotizacion() {
		return nroSolicitudCotizacion;
	}

	public void setSolicitudCotizacion(SolicitudCotizacion nroSolicitudCotizacion) {
		this.nroSolicitudCotizacion = nroSolicitudCotizacion;
	}

	public Rodamiento getRodamiento() {
		return rodamiento;
	}

	public void setRodamiento(Rodamiento rodamiento) {
		this.rodamiento = rodamiento;
	}
	
}
