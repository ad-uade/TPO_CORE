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
@Table (name = "ordenesCompra")
public class OrdenCompra implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 222933907265692435L;

	@Id
	@GeneratedValue
	@Column (name = "nroOrdenCompra")
	private Integer nroOrdenCompra;
	@Column (name = "fecha")
	private Date fecha;
	@ManyToOne
	@JoinColumn (name = "CUILProveedor")
	private Proveedor proveedor;
	@OneToMany (cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn (name = "nroOrdenCompra")
	private List<ItemOrdenCompra>items;
	
	public OrdenCompra(){
		
	}

	public int getNroOrdenCompra() {
		return nroOrdenCompra;
	}

	public void setNroOrdenCompra(int nroOrdenCompra) {
		this.nroOrdenCompra = nroOrdenCompra;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public List<ItemOrdenCompra> getItems() {
		return items;
	}

	public void setItems(List<ItemOrdenCompra> items) {
		this.items = items;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}
	
}
