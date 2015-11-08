package com.group7.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table (name = "ordenesPedido")
public class OrdenPedido implements Serializable{
	
	private static final long serialVersionUID = 1487206222517703320L;

	@Id
	@GeneratedValue
	@Column (name = "nroOrdenPedido")
	private Integer idOrdenPedido;
	@Column (name = "fecha")
	private Date fecha;
	@Column (name = "estado")
	private Boolean estado;
	@ManyToOne 
	@JoinColumn (name = "CUILCliente")
	private Cliente cliente;
	@ManyToOne
	@JoinColumn (name = "nroCotizacion")
	private Cotizacion cotizacion;
	@OneToMany (cascade = CascadeType.ALL , fetch=FetchType.EAGER)
	@JoinColumn (name = "nroOrdenPedido")
	private List<ItemOrdenPedido>items;
	@ManyToOne
	@JoinColumn (name = "idCasaCentral")
	private CasaCentral casaCentral;
	
	public OrdenPedido(){
		
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<ItemOrdenPedido> getItems() {
		return items;
	}

	public void setItems(List<ItemOrdenPedido> items) {
		this.items = items;
	}

	public int getIdOrdenPedido() {
		return idOrdenPedido;
	}

	public void setIdOrdenPedido(int idOrdenPedido) {
		this.idOrdenPedido = idOrdenPedido;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Cotizacion getCotizacion() {
		return cotizacion;
	}

	public void setCotizacion(Cotizacion cotizacion) {
		this.cotizacion = cotizacion;
	}

	public CasaCentral getCasaCentral() {
		return casaCentral;
	}

	public void setCasaCentral(CasaCentral casaCentral) {
		this.casaCentral = casaCentral;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	
}
