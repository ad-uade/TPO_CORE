package com.group7.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table (name = "clientes")
public class Cliente implements Serializable{
	
	private static final long serialVersionUID = 509077828567396943L;

	@Id
	@Column (name = "CUILCliente")
	private Integer cuilCliente;
	private String razonSocial;
	private String direccion;
	private String telefono;
	private boolean estado;
	private OficinaVentas oficinaVentas;
	
	public Cliente(){
		
	}
	
	@Column (name = "razonSocial")
 	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public Integer getcUILCliente() {
		return cuilCliente;
	}

	public void setCuilCliente(Integer cUILCliente) {
		cuilCliente = cUILCliente;
	}

	@Column (name = "direccion")
	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Column (name = "telefono")
	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@Column (name = "estado")
	public boolean getEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	@ManyToOne (cascade = CascadeType.ALL)
	@JoinColumn (name = "idOficina")
	public OficinaVentas getOficinaVentas() {
		return oficinaVentas;
	}

	public void setOficinaVentas(OficinaVentas oDV) {
		oficinaVentas = oDV;
	}
	
}
