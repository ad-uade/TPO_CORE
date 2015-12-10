package com.group7.entity;


import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.group7.business.OficinaVentasVO;

@Entity
@Table (name = "oficinasVentas")
public class OficinaVenta implements Serializable{
	
	private static final long serialVersionUID = 3654098214098260773L;

	@Id
	@Column (name = "idOficina")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idOficinaVenta;
	@Column (name = "sucursal")
	private String sucursal;
	@OneToMany (cascade = CascadeType.ALL)
	@JoinColumn (name = "idOficina")
	private List<Cliente> clientes;
	@OneToMany (cascade = CascadeType.ALL)
	@JoinColumn (name = "idOficina")
	private List<Cotizacion> cotizaciones;
	@OneToMany (cascade = CascadeType.ALL)
	@JoinColumn (name = "idOficina")
	private List<SolicitudCotizacion> solicitudesCotizacion;
	@OneToMany (cascade = CascadeType.ALL)
	@JoinColumn (name = "idOficina")
	private List<Factura> facturas;
	@OneToMany (cascade = CascadeType.ALL)
	@JoinColumn (name = "idOficina")
	private List<RemitoExterior> remitos;
	@ManyToOne (cascade = CascadeType.ALL)
	@JoinColumn (name = "idCasaCentral")
	private CasaCentral casa;

	public OficinaVenta(){
		
	}
	
	public OficinaVenta(OficinaVentasVO oficinaVentaVO){
		this.setSucursal(oficinaVentaVO.getSucursal());
	}

	public Integer getIdOficinaVenta() {
		return idOficinaVenta;
	}

	public void setIdOficinaVenta(Integer idOficinaVenta) {
		this.idOficinaVenta = idOficinaVenta;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public List<Cotizacion> getCotizaciones() {
		return cotizaciones;
	}

	public void setCotizaciones(List<Cotizacion> cotizaciones) {
		this.cotizaciones = cotizaciones;
	}

	public List<SolicitudCotizacion> getSolicitudesCotizacion() {
		return solicitudesCotizacion;
	}

	public void setSolicitudesCotizacion(List<SolicitudCotizacion> solicitudesCotizacion) {
		this.solicitudesCotizacion = solicitudesCotizacion;
	}

	public String getSucursal() {
		return sucursal;
	}

	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}

	public List<Factura> getFacturas() {
		return facturas;
	}

	public void setFacturas(List<Factura> facturas) {
		this.facturas = facturas;
	}

	public List<RemitoExterior> getRemitos() {
		return remitos;
	}

	public void setRemitos(List<RemitoExterior> remitos) {
		this.remitos = remitos;
	}

	public CasaCentral getCasa() {
		return casa;
	}

	public void setCasa(CasaCentral casa) {
		this.casa = casa;
	}

	public OficinaVentasVO getView() {
		OficinaVentasVO oficinaVentasVO = new OficinaVentasVO();
		oficinaVentasVO.setSucursal(this.getSucursal());
		oficinaVentasVO.setIdOficina(this.getIdOficinaVenta());
		return oficinaVentasVO;
	}
	
}
