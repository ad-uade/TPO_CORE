package com.group7.entity.enbeddable;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

import com.group7.entity.Rodamiento;

@Embeddable
public class ItemListaPreciosId implements Serializable{

	private static final long serialVersionUID = 4654951809278890041L;

	private Integer nroLista;
	private Rodamiento rodamiento;
	
	public ItemListaPreciosId(){
		
	}
	
	@Column (name = "nroLista")
	public Integer getNroLista() {
		return nroLista;
	}

	public void setNroLista(Integer nroLista) {
		this.nroLista = nroLista;
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