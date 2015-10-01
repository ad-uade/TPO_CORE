package com.group7.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.group7.entity.enbeddable.ItemOrdenPedidoId;

@Entity
@Table (name = "itemsOrdenPedido")
public class ItemOrdenPedido implements Serializable{
	
	private static final long serialVersionUID = 4487207071184151254L;

	@EmbeddedId
	private ItemOrdenPedidoId id;
	
	@Column (name = "cantidad")
	private int cantidad;
	
	@ManyToOne
	@JoinColumn (name = "CUILProveedor")
	private Proveedor proveedor;
	
	@Column (name = "estado")
	private boolean estado;
	
	public ItemOrdenPedido(){
		
	}

	public ItemOrdenPedidoId getId() {
		return id;
	}

	public void setId(ItemOrdenPedidoId id) {
		this.id = id;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	
}
