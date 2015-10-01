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
	private float porcentajeGanancia;
	
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
	private List<RemitoInterior> remitos;
	
	@OneToMany
	@JoinColumn (name = "idCasaCentral")
	private List<OficinaVentas> oficinas;
	
	public CasaCentral(){
		
	}

	public int getIdCasaCentral() {
		return idCasaCentral;
	}

	public void setIdCasaCentral(int idCasaCentral) {
		this.idCasaCentral = idCasaCentral;
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

	public List<RemitoInterior> getRemitos() {
		return remitos;
	}

	public void setRemitos(List<RemitoInterior> remitos) {
		this.remitos = remitos;
	}

	public List<OficinaVentas> getOficinas() {
		return oficinas;
	}

	public void setOficinas(List<OficinaVentas> oficinas) {
		this.oficinas = oficinas;
	}

}
