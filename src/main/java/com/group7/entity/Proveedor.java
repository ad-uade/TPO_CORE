package com.group7.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.group7.business.ProveedorVO;

@Entity
@Table (name = "proveedores")
public class Proveedor implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7480760626488153467L;

	@Id
	@Column (name = "CUILProveedor")
	private Long cuilProveedor;
	@Column (name = "razonSocial")
	private String razonSocial;
	@Column (name = "estado")
	private Boolean estado = Boolean.TRUE;
	@Column (name = "direccion")
	private String direccion;
	@Column (name = "telefono")
	private String telefono;
	@OneToMany (cascade = CascadeType.ALL)
	@JoinColumn (name = "CUILProveedor")
	private List<ListaPrecios>listaPrecios;
	
	public Proveedor(){
		
	}

	public Proveedor(ProveedorVO vo){
		this.setCuilProveedor(vo.getCuilProveedor());
		this.setDireccion(vo.getDireccion());
		this.setRazonSocial(vo.getRazonSocial());
		this.setTelefono(vo.getTelefono());
	}
	
	public Long getCuilProveedor() {
		return cuilProveedor;
	}
	
	public void setCuilProveedor(Long cUILProveedor) {
		cuilProveedor = cUILProveedor;
	}
	
	public String getRazonSocial() {
		return razonSocial;
	}
	
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	
	public boolean isEstado() {
		return estado;
	}
	
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	
	public String getDireccion() {
		return direccion;
	}
	
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	public String getTelefono() {
		return telefono;
	}
	
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public List<ListaPrecios> getListaPrecios() {
		return listaPrecios;
	}
	
	public void setListaPrecios(List<ListaPrecios> listaPrecios) {
		this.listaPrecios = listaPrecios;
	}

	public ProveedorVO getView (){
		ProveedorVO proveedorVO = new ProveedorVO();
		proveedorVO.setCuilProveedor(this.getCuilProveedor());
		proveedorVO.setDireccion(this.getDireccion());
		proveedorVO.setEstado(this.isEstado());
		proveedorVO.setRazonSocial(this.getRazonSocial());
		proveedorVO.setTelefono(this.getTelefono());
		return proveedorVO;
	}
	
}
