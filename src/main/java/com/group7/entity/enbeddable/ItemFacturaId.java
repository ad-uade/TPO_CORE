package com.group7.entity.enbeddable;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

import com.group7.entity.Factura;
import com.group7.entity.Rodamiento;

@Embeddable
public class ItemFacturaId implements Serializable{

	private static final long serialVersionUID = -1669769905858888339L;
	
	@ManyToOne (optional = false, targetEntity=Factura.class)
	@JoinColumn(name="nroFactura", referencedColumnName="nroFactura")
	private Factura nroFactura;
	@ManyToOne (optional = false, targetEntity=Rodamiento.class)
	@JoinColumns ({@JoinColumn (name = "codigoSFK", referencedColumnName="codigoSFK"), @JoinColumn (name = "codigoPieza", referencedColumnName="codigoPieza")})
	private Rodamiento rodamiento;
	
	public ItemFacturaId(){
		
	}

	public Factura getFactura() {
		return nroFactura;
	}

	public void setFactura(Factura nroFactura) {
		this.nroFactura = nroFactura;
	}

	public Rodamiento getRodamiento() {
		return rodamiento;
	}

	public void setRodamiento(Rodamiento rodamiento) {
		this.rodamiento = rodamiento;
	}
	
}