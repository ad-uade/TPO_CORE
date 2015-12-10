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

import com.group7.business.ItemSolicitudCotizacionVO;
import com.group7.business.SolicitudCotizacionVO;
import com.group7.service.ClienteServicio;

@Entity
@Table (name = "solicitudesCotizacion")
public class SolicitudCotizacion implements Serializable{
	
	private static final long serialVersionUID = -1899638291364688150L;

	@Id
	@GeneratedValue
	@Column (name = "nroSolicitudCotizacion")
	private Integer nroSolicitudCotizacion;
	@Column (name = "fecha")
	private Date fecha;
	@ManyToOne
	@JoinColumn (name = "CUILCliente")
	private Cliente cliente;
	@OneToMany (cascade = CascadeType.ALL, fetch=FetchType.EAGER) 
	@JoinColumn (name = "nroSolicitudCotizacion")
	private List<ItemSolicitudCotizacion>items;
	@ManyToOne
	@JoinColumn (name = "idOficina")
	private OficinaVenta oficinaVenta;
	
	public SolicitudCotizacion(){
		
	}
	
	public SolicitudCotizacion(SolicitudCotizacionVO solicitudCotizacionVO){
		SolicitudCotizacion solicitud = new SolicitudCotizacion();
		solicitud.setNroSolicitudCotizacion(solicitudCotizacionVO.getNroSolicitudCotizacion());
		solicitud.setCliente(ClienteServicio.getInstancia().convertir(solicitudCotizacionVO.getCliente()));
		solicitud.setFecha(solicitudCotizacionVO.getFecha());
		solicitud.setVOItems(solicitudCotizacionVO.getItems());
		OficinaVenta oficinaVenta = new OficinaVenta(solicitudCotizacionVO.getOficinaVentasVO());
		solicitud.setOficinaVenta(oficinaVenta);
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public int getNroSolicitudCotizacion() {
		return nroSolicitudCotizacion;
	}

	public void setNroSolicitudCotizacion(int nroSolicitudCotizacion) {
		this.nroSolicitudCotizacion = nroSolicitudCotizacion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public List<ItemSolicitudCotizacion> getItems() {
		return items;
	}

	public void setItems(List<ItemSolicitudCotizacion> items) {
		this.items = items;
	}
	
	private void setVOItems(List<ItemSolicitudCotizacionVO> items) {
		
	}

	/**
	 * @return the oficinaVenta
	 */
	public OficinaVenta getOficinaVenta() {
		return oficinaVenta;
	}

	/**
	 * @param oficinaVenta the oficinaVenta to set
	 */
	public void setOficinaVenta(OficinaVenta oficinaVenta) {
		this.oficinaVenta = oficinaVenta;
	}
	
}
