package com.group7.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("RE")
public class RemitoExterior extends Remito {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2529719019032328609L;

	@ManyToOne
	@JoinColumn (name = "CUILCliente")
	private Cliente cliente;
	@ManyToOne
	@JoinColumn (name = "nroOrdenPedido")
	private OrdenPedido ordenPedido;
	@Column (name = "conformado")
	private Boolean conformeCliente; 
	@ManyToOne
	@JoinColumn (name = "idOficina")
	private OficinaVentas ODV;
	
	public RemitoExterior(){
		
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public void setConformeCliente(boolean conformeCliente) {
		this.conformeCliente = conformeCliente;
	}

	public boolean isConformeCliente() {
		return conformeCliente;
	}

	public OrdenPedido getOP() {
		return ordenPedido;
	}

	public void setOP(OrdenPedido oP) {
		ordenPedido = oP;
	}

	public OficinaVentas getODV() {
		return ODV;
	}

	public void setODV(OficinaVentas oDV) {
		ODV = oDV;
	}
	
}