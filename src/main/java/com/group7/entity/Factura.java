package com.group7.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table (name = "facturas")
public class Factura implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3495558280429829262L;

	@Id
	@Column (name = "nroFactura")
	@GeneratedValue
	private Integer nroFactura;
	
	@Column (name = "fecha")
	private Date fecha;
	
	@Column (name = "descuento")
	private float descuento;
	
	@ManyToOne
	@JoinColumn (name = "CUILCliente")
	private Cliente cliente;
	
	@Column (name = "precioTotal")
	private float precioTotal;
	
	@ManyToOne
	@JoinColumn (name = "nroRemito")
	private RemitoExterior remito;
	
	@OneToMany (cascade = CascadeType.ALL)
	@JoinColumn (name = "nroFactura")
	private List<ItemFactura>items;
	
	@ManyToOne
	@JoinColumn (name = "idOficina")
	private OficinaVentas ODV;
	
	public Factura(){
		
	}
	
	public int getNroFactura() {
		return nroFactura;
	}

	public void setNroFactura(int nroFactura) {
		this.nroFactura = nroFactura;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public float getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(float precioTotal) {
		this.precioTotal = precioTotal;
	}

	public float getDescuento() {
		return descuento;
	}

	public void setDescuento(float descuento) {
		this.descuento = descuento;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<ItemFactura> getItems() {
		return items;
	}

	public void setItems(List<ItemFactura> items) {
		this.items = items;
	}

	public RemitoExterior getRemito() {
		return remito;
	}

	public void setRemito(RemitoExterior remito) {
		this.remito = remito;
	}

	public OficinaVentas getODV() {
		return ODV;
	}

	public void setODV(OficinaVentas oDV) {
		ODV = oDV;
	}
	
}