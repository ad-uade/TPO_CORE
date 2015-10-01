package com.group7.entity.enbeddable;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

import com.group7.entity.Rodamiento;

@Embeddable
public class ItemOrdenCompraId implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6966201907803271140L;

	private int nroOrdenCompra;
	private Rodamiento rodamiento;
	
	public ItemOrdenCompraId(){
		
	}

	@Column (name = "nroOrdenCompra")
	public int getNroOrdenCompra() {
		return nroOrdenCompra;
	}

	public void setNroOrdenCompra(int nroOrdenCompra) {
		this.nroOrdenCompra = nroOrdenCompra;
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
