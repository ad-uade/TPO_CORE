package com.group7.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.group7.business.ItemCotizacionVO;
import com.group7.entity.enbeddable.ItemCotizacionId;

@Entity
@Table (name = "itemsCotizacion")
public class ItemCotizacion implements Serializable{

	private static final long serialVersionUID = 8548755993081305471L;

	@EmbeddedId
	private ItemCotizacionId id;
	@Column (name = "cantidad")
	private Integer cantidad;
	@Column (name = "precioUnitario")
	private Float precioUnitario;
	@Column (name = "estado")
	private String estado; 
	@ManyToOne
	@JoinColumn (name = "CUILProveedor")
	private Proveedor itemProveedor; 
	@ManyToOne
	@JoinColumn (name = "idEstrategiaClienteMonto")
	private PorMonto estrategyPorMonto;
	@ManyToOne
	@JoinColumn (name = "idEstrategiaClienteVolumen")
	private PorVolumen estrategyPorVolumen;
	
	public ItemCotizacion(){
		
	}
	
	public ItemCotizacion(ItemCotizacionVO itemsVo){
		
	}

	public ItemCotizacionId getId() {
		return id;
	}

	public void setId(ItemCotizacionId id) {
		this.id = id;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public float getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(float precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Proveedor getItemProveedor() {
		return itemProveedor;
	}

	public void setItemProveedor(Proveedor itemProveedor) {
		this.itemProveedor = itemProveedor;
	}

	public PorMonto getEstrategyPorMonto() {
		return estrategyPorMonto;
	}

	public void setEstrategyPorMonto(PorMonto estrategyPorMonto) {
		this.estrategyPorMonto = estrategyPorMonto;
	}

	public PorVolumen getEstrategyPorVolumen() {
		return estrategyPorVolumen;
	}

	public void setEstrategyPorVolumen(PorVolumen estrategyPorVolumen) {
		this.estrategyPorVolumen = estrategyPorVolumen;
	}
	
	public ItemCotizacionVO getView(){
		ItemCotizacionVO itemCotizacionVO = new ItemCotizacionVO();
		return itemCotizacionVO;
	}
	
}