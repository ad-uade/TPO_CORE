package com.group7.entity;

import java.io.Serializable;
import java.util.ArrayList;
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

import com.group7.entity.enbeddable.ItemListaPreciosId;

@Entity
@Table (name = "listaPrecios")
public class ListaPrecios implements Serializable{

	private static final long serialVersionUID = 5302393786280525811L;

	@Id
	@Column (name = "nroLista")
	@GeneratedValue
	private Integer nroLista;
	@Column (name = "fechaPublicacion")
	private Date fechaPublicacion;
	@Column (name = "vigencia")
	private Integer vigencia;
	@Column (name = "estado")
	private Boolean estado = Boolean.TRUE;
	@ManyToOne (cascade = CascadeType.ALL)
	@JoinColumn (name = "CUILProveedor")
	private Proveedor proveedor;
	@OneToMany (cascade = CascadeType.ALL)
	@JoinColumn (name = "nroLista")
	private List<ItemListaPrecios> items;
	
	public ListaPrecios(){
		items = new ArrayList<ItemListaPrecios>();
	}

	public int getNroLista() {
		return nroLista;
	}

	public void setNroLista(int nroLista) {
		this.nroLista = nroLista;
	}

	public Date getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(Date fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public List<ItemListaPrecios> getItems() {
		return items;
	}

	public void setItems(List<ItemListaPrecios> items) {
		this.items = items;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public Integer getVigencia() {
		return vigencia;
	}

	public void setVigencia(Integer vigencia) {
		this.vigencia = vigencia;
	}

	public void agregarItem(Rodamiento rodamiento, Float precioVenta, Float descuento) {
		ItemListaPrecios itemListaPrecios = new ItemListaPrecios();		
		itemListaPrecios.setDescuento(descuento);
		itemListaPrecios.setPrecioVenta(precioVenta);
		ItemListaPreciosId itemListaPreciosId = new ItemListaPreciosId();
		itemListaPreciosId.setNroLista(this);
		itemListaPreciosId.setRodamiento(rodamiento);
		itemListaPrecios.setId(itemListaPreciosId);
		items.add(itemListaPrecios);
	}
	
}
