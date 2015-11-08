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
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table (name = "cotizaciones")
public class Cotizacion implements Serializable{
	
	private static final long serialVersionUID = 9064892059887261666L;

	@Id
	@Column (name = "nroCotizacion")
	@GeneratedValue
	private Integer id;
	@Column (name = "fecha")
	private Date fecha;
	@Column (name = "diasValidez")
	private Integer diasValidez;
	@ManyToOne
	@JoinColumn (name = "CUILCliente")
	private Cliente cliente;
	@OneToOne
	@JoinColumn (name = "nroSolicitudCotizacion")
	private SolicitudCotizacion solicitudCotizacion;
	@OneToMany (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn (name = "nroCotizacion")
	private List<ItemCotizacion>items;
	@ManyToOne
	@JoinColumn (name = "idOficina")
	private OficinaVentas oficinaVentas;
	@OneToMany
	@JoinColumn (name = "idFormaPago")
	private List<FormaPago> formaPago;
	
	public Cotizacion(){
			
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the diasValidez
	 */
	public Integer getDiasValidez() {
		return diasValidez;
	}

	/**
	 * @param diasValidez the diasValidez to set
	 */
	public void setDiasValidez(Integer diasValidez) {
		this.diasValidez = diasValidez;
	}

	/**
	 * @return the cliente
	 */
	public Cliente getCliente() {
		return cliente;
	}

	/**
	 * @param cliente the cliente to set
	 */
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	/**
	 * @return the solicitudCotizacion
	 */
	public SolicitudCotizacion getSolicitudCotizacion() {
		return solicitudCotizacion;
	}

	/**
	 * @param solicitudCotizacion the solicitudCotizacion to set
	 */
	public void setSolicitudCotizacion(SolicitudCotizacion solicitudCotizacion) {
		this.solicitudCotizacion = solicitudCotizacion;
	}

	/**
	 * @return the items
	 */
	public List<ItemCotizacion> getItems() {
		return items;
	}

	/**
	 * @param items the items to set
	 */
	public void setItems(List<ItemCotizacion> items) {
		this.items = items;
	}

	/**
	 * @return the oficinaVentas
	 */
	public OficinaVentas getOficinaVentas() {
		return oficinaVentas;
	}

	/**
	 * @param oficinaVentas the oficinaVentas to set
	 */
	public void setOficinaVentas(OficinaVentas oficinaVentas) {
		this.oficinaVentas = oficinaVentas;
	}

	/**
	 * @return the formaPago
	 */
	public List<FormaPago> getFormaPago() {
		return formaPago;
	}

	/**
	 * @param formaPago the formaPago to set
	 */
	public void setFormaPago(List<FormaPago> formaPago) {
		this.formaPago = formaPago;
	}

}
