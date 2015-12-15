package com.group7.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.group7.business.ClienteVO;

@Entity
@Table (name = "clientes")
public class Cliente implements Serializable{
	
	private static final long serialVersionUID = 509077828567396943L;

	@Id
	@Column (name = "CUILCliente")
	private Long cuilCliente;
	@Column (name = "razonSocial")
	private String razonSocial;
	@Column (name = "direccion")
	private String direccion;
	@Column (name = "telefono")
	private String telefono;
	@Column (name = "estado")
	private Boolean estado = Boolean.TRUE;
	@ManyToOne
	@JoinColumn (name = "idOficina")
	private OficinaVenta oficinaVentas;
	
	public Cliente(){
		
	}
	
	public Cliente(ClienteVO clientevo){
		this.setCuilCliente(clientevo.getCuilCliente());
		this.setDireccion(clientevo.getDireccion());
		this.setRazonSocial(clientevo.getRazonSocial());
		this.setTelefono(clientevo.getTelefono());
	}

	/**
	 * @return the cuilCliente
	 */
	public Long getCuilCliente() {
		return cuilCliente;
	}

	/**
	 * @param cuilCliente the cuilCliente to set
	 */
	public void setCuilCliente(Long cuilCliente) {
		this.cuilCliente = cuilCliente;
	}

	/**
	 * @return the razonSocial
	 */
	public String getRazonSocial() {
		return razonSocial;
	}

	/**
	 * @param razonSocial the razonSocial to set
	 */
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	/**
	 * @return the direccion
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * @param direccion the direccion to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * @return the estado
	 */
	public Boolean getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	/**
	 * @return the oficinaVentas
	 */
	public OficinaVenta getOficinaVentas() {
		return oficinaVentas;
	}

	/**
	 * @param oficinaVentas the oficinaVentas to set
	 */
	public void setOficinaVentas(OficinaVenta oficinaVentas) {
		this.oficinaVentas = oficinaVentas;
	}

	public ClienteVO getView(){
		ClienteVO clienteVO = new ClienteVO();
		clienteVO.setTelefono(this.getTelefono());
		clienteVO.setEstado(this.getEstado());
		clienteVO.setCuilCliente(this.getCuilCliente());
		clienteVO.setRazonSocial(this.getRazonSocial());
		clienteVO.setDireccion(this.getDireccion());
		clienteVO.setODV(this.getOficinaVentas().getView());
		return clienteVO;
	}
	
}
