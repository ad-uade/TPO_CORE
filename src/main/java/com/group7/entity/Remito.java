package com.group7.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="Tipo", discriminatorType=DiscriminatorType.STRING)
@Table (name = "remitos")
public class Remito implements Serializable{
	
	private static final long serialVersionUID = 5623968621293885643L;
	@Id
	@GeneratedValue
	@Column(name = "nroRemito")
	private Integer nroRemito;
	@Column (name = "fecha")
	private Date fecha;
	@OneToMany (cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn (name = "nroRemito")
	private List<ItemRemito> items;

	@ManyToOne
	@JoinColumn(name = "CUILCliente")
	private Cliente cliente;

	@ManyToOne
	@JoinColumn(name = "nroOrdenPedido")
	private OrdenPedido ordenPedido;

	@ManyToOne
	@JoinColumn(name = "idOficina")
	private OficinaVentas ODV;

	public Remito(){
		
	}

	public int getNroRemito() {
		return nroRemito;
	}

	public void setNroRemito(int nroRemito) {
		this.nroRemito = nroRemito;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public List<ItemRemito> getItems() {
		return items;
	}

	public void setItems(List<ItemRemito> items) {
		this.items = items;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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