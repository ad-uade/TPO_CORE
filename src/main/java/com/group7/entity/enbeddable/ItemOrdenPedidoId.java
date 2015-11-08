package com.group7.entity.enbeddable;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

import com.group7.entity.Rodamiento;

@Embeddable
public class ItemOrdenPedidoId implements Serializable{

	private static final long serialVersionUID = 4995189118850353329L;

	private Integer nroOrdenPedido;	
	private Rodamiento rodamiento;
	
	public ItemOrdenPedidoId(){
		
	}

	@Column (name = "nroOrdenPedido")
	public Integer getNroOrdenPedido() {
		return nroOrdenPedido;
	}

	public void setNroOrdenPedido(Integer nroOrdenPedido) {
		this.nroOrdenPedido = nroOrdenPedido;
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
