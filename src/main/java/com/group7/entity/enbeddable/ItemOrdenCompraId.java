package com.group7.entity.enbeddable;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

import com.group7.entity.OrdenCompra;
import com.group7.entity.Rodamiento;

@Embeddable
public class ItemOrdenCompraId implements Serializable{

	private static final long serialVersionUID = -6966201907803271140L;

	@ManyToOne (optional = false)
	@JoinColumn(name="nroOrdenCompra", referencedColumnName="nroOrdenCompra")
	private OrdenCompra nroOrdenCompra;
	@ManyToOne (optional = false, targetEntity=Rodamiento.class)
	@JoinColumns ({@JoinColumn (name = "codigoSFK", referencedColumnName="codigoSFK"), @JoinColumn (name = "codigoPieza", referencedColumnName="codigoPieza")})
	private Rodamiento rodamiento;
	
	public ItemOrdenCompraId(){
		
	}

	public OrdenCompra getNroOrdenCompra() {
		return nroOrdenCompra;
	}

	public void setNroOrdenCompra(OrdenCompra nroOrdenCompra) {
		this.nroOrdenCompra = nroOrdenCompra;
	}

	public Rodamiento getRodamiento() {
		return rodamiento;
	}

	public void setRodamiento(Rodamiento rodamiento) {
		this.rodamiento = rodamiento;
	}
	
}
