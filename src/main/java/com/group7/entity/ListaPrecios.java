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
@Table (name = "listaPrecios")
public class ListaPrecios implements Serializable{

	private static final long serialVersionUID = 5302393786280525811L;

	@Id
	@Column (name = "nroLista")
	@GeneratedValue
	private Integer nroLista;
	
	@Column (name = "tipo")
	private String tipo;
	
	@Column (name = "fechaPublicacion")
	private Date fechaPublicacion;
	
	@Column (name = "vigencia")
	private String vigencia;
	
	@Column (name = "estado")
	private boolean estado;
	
	@ManyToOne (cascade = CascadeType.ALL)
	@JoinColumn (name = "CUILProveedor")
	private Proveedor proveedor;
	
	@OneToMany (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn (name = "nroLista")
	private List<ItemsListaPrecios>items;
	
	public ListaPrecios(){
		
	}

	public int getNroLista() {
		return nroLista;
	}

	public void setNroLista(int nroLista) {
		this.nroLista = nroLista;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
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

	public List<ItemsListaPrecios> getItems() {
		return items;
	}

	public void setItems(List<ItemsListaPrecios> items) {
		this.items = items;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public String getVigencia() {
		return vigencia;
	}

	public void setVigencia(String vigencia) {
		this.vigencia = vigencia;
	}
	
}
