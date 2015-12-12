package com.group7.entity;

import java.io.Serializable;
import java.util.ArrayList;
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
import com.group7.entity.enbeddable.ItemSolicitudCotizacionId;

@Entity
@Table (name = "solicitudCotizacion")
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
	private List<ItemSolicitudCotizacion> itemSolicitudCotizacion;
	@ManyToOne
	@JoinColumn (name = "idOficina")
	private OficinaVenta oficinaVenta;
	
	public SolicitudCotizacion(){
		itemSolicitudCotizacion = new ArrayList<ItemSolicitudCotizacion>();
	}
	
	public SolicitudCotizacion(SolicitudCotizacionVO solicitudCotizacionVO){
		this();
		this.setNroSolicitudCotizacion(solicitudCotizacionVO.getNroSolicitudCotizacion());
		Cliente clietne = new Cliente(solicitudCotizacionVO.getCliente());
		this.setCliente(clietne);
		this.setFecha(solicitudCotizacionVO.getFecha());
		OficinaVenta oficinaVenta = new OficinaVenta(solicitudCotizacionVO.getOficinaVentasVO());
		this.setOficinaVenta(oficinaVenta);
		for (ItemSolicitudCotizacionVO item : solicitudCotizacionVO.getItems()){
			Rodamiento rodamiento = new Rodamiento(item.getRodamiento());
			this.add(rodamiento, item.getCantidad());	
		}
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
		return itemSolicitudCotizacion;
	}

	public void setItems(List<ItemSolicitudCotizacion> items) {
		this.itemSolicitudCotizacion = items;
	}
	
	public void add(Rodamiento rodamiento, Integer cantidad){
		ItemSolicitudCotizacion item = new ItemSolicitudCotizacion();
		item.setCantidad(cantidad);
		ItemSolicitudCotizacionId id = new ItemSolicitudCotizacionId();
		id.setRodamiento(rodamiento);
		id.setSolicitudCotizacion(this);
		item.setId(id);
		itemSolicitudCotizacion.add(item);
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

	public SolicitudCotizacionVO getView() {
		SolicitudCotizacionVO solicitudCotizacionVO = new SolicitudCotizacionVO();
		return solicitudCotizacionVO;
	}
	
}
