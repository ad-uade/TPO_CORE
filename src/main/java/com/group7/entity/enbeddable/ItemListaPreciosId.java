package com.group7.entity.enbeddable;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

import com.group7.entity.ListaPrecios;
import com.group7.entity.Rodamiento;

@Embeddable
public class ItemListaPreciosId implements Serializable{

	private static final long serialVersionUID = 4654951809278890041L;
	@ManyToOne (optional = false)
	@JoinColumn(name="nroLista", referencedColumnName="nroLista")
	private ListaPrecios listaPrecios;
	@ManyToOne (optional = false, targetEntity=Rodamiento.class)
	@JoinColumns ({@JoinColumn (name = "codigoSFK", referencedColumnName="codigoSFK"), @JoinColumn (name = "codigoPieza", referencedColumnName="codigoPieza")})
	private Rodamiento rodamiento;
	
	public ItemListaPreciosId(){
		
	}
	
	public ListaPrecios getNroLista() {
		return listaPrecios;
	}

	public void setNroLista(ListaPrecios nroLista) {
		this.listaPrecios = nroLista;
	}

	public Rodamiento getRodamiento() {
		return rodamiento;
	}

	public void setRodamiento(Rodamiento rodamiento) {
		this.rodamiento = rodamiento;
	}
	
}