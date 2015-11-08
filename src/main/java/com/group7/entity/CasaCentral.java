package com.group7.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table (name = "casaCentral")
public class CasaCentral implements Serializable{

	private static final long serialVersionUID = -5839644292251482348L;

	@Id
	@Column (name = "idCasaCentral")
	private Integer idCasaCentral;
	
	@Column (name = "porcentajeGanancia")
	private Float porcentajeGanancia;
	
	@OneToOne (cascade = CascadeType.ALL)
	@JoinColumn (name = "idComparativa")
	private ComparativaPrecios comparativa;
	
	@OneToMany (cascade = CascadeType.ALL)
	@JoinColumn (name = "idCasaCentral")
	private List<MovimientoStock> movimientos;
	
	@OneToMany (cascade = CascadeType.ALL)
	@JoinColumn (name = "idCasaCentral")
	private List<Proveedor> proveedores;
	
	@OneToMany
	@JoinColumn (name = "idCasaCentral")
	private List<OrdenPedido> ordenesPedido;
	
	@OneToMany
	@JoinColumn (name = "idCasaCentral")
	private List<Remito> remitos;
	
	@OneToMany
	@JoinColumn (name = "idCasaCentral")
	private List<OficinaVentas> oficinas;

	public CasaCentral(){
		
	}
	
	/**
	 * @param idCasaCentral the idCasaCentral to set
	 */
	public void setIdCasaCentral(Integer idCasaCentral) {
		this.idCasaCentral = idCasaCentral;
	}

	/**
	 * @param porcentajeGanancia the porcentajeGanancia to set
	 */
	public void setPorcentajeGanancia(Float porcentajeGanancia) {
		this.porcentajeGanancia = porcentajeGanancia;
	}

	public float getPorcentajeGanancia() {
		return porcentajeGanancia;
	}

	public void setPorcentajeGanancia(float porcentajeGanancia) {
		this.porcentajeGanancia = porcentajeGanancia;
	}

	public ComparativaPrecios getComparativa() {
		return comparativa;
	}

	public void setComparativa(ComparativaPrecios comparativa) {
		this.comparativa = comparativa;
	}

	public List<MovimientoStock> getMovimientos() {
		return movimientos;
	}

	public void setMovimientos(List<MovimientoStock> movimientos) {
		this.movimientos = movimientos;
	}

	public List<Proveedor> getProveedores() {
		return proveedores;
	}

	public void setProveedores(List<Proveedor> proveedores) {
		this.proveedores = proveedores;
	}

	public List<OrdenPedido> getOrdenesPedido() {
		return ordenesPedido;
	}

	public void setOrdenesPedido(List<OrdenPedido> ordenesPedido) {
		this.ordenesPedido = ordenesPedido;
	}

	public List<Remito> getRemitos() {
		return remitos;
	}

	public void setRemitos(List<Remito> remitos) {
		this.remitos = remitos;
	}

	public List<OficinaVentas> getOficinas() {
		return oficinas;
	}

	public void setOficinas(List<OficinaVentas> oficinas) {
		this.oficinas = oficinas;
	}

}
