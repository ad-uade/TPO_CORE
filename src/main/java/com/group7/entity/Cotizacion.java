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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.group7.business.CotizacionVO;
import com.group7.business.ItemCotizacionVO;
import com.group7.entity.enbeddable.ItemCotizacionId;

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
	@OneToMany (cascade = CascadeType.ALL)
	@JoinColumn (name = "nroCotizacion")
	private List<ItemCotizacion>items;
	
	public Cotizacion(){
		items = new ArrayList<ItemCotizacion>();
	}
	
	public Cotizacion(CotizacionVO cotizacion){
		Cliente cliente = new Cliente(cotizacion.getCliente());
		this.setCliente(cliente);
		this.setDiasValidez(cotizacion.getDiasValidez());
		this.setFecha(cotizacion.getFecha());
		SolicitudCotizacion solicitudCotizacion = new SolicitudCotizacion();
		this.setSolicitudCotizacion(solicitudCotizacion);
		List<ItemCotizacion> itemCotizacion = new ArrayList<ItemCotizacion>();
		for (ItemCotizacionVO itemsVo : cotizacion.getItems()) {
			ItemCotizacion nuevoItem = new ItemCotizacion(itemsVo);
			itemCotizacion.add(nuevoItem);
		}
		this.setItems(itemCotizacion);
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

	public void add(Rodamiento rodamiento, Integer cantidad, Proveedor proveedor, Float precioUnitario){
		ItemCotizacion itemCotizacion = new ItemCotizacion();
		itemCotizacion.setCantidad(cantidad);
		itemCotizacion.setProveedor(proveedor);
		itemCotizacion.setPrecioUnitario(precioUnitario);
		ItemCotizacionId id = new ItemCotizacionId();
		id.setIdCotizacion(this);
		id.setRodamiento(rodamiento);
		itemCotizacion.setId(id);
		items.add(itemCotizacion);
	}
	
	
	public void add(Rodamiento rodamiento, Integer cantidad, Proveedor proveedor, Float precioUnitario, EstadoCotizacion estadoCotizacion){
		ItemCotizacion itemCotizacion = new ItemCotizacion();
		itemCotizacion.setCantidad(cantidad);
		itemCotizacion.setProveedor(proveedor);
		itemCotizacion.setPrecioUnitario(precioUnitario);
		ItemCotizacionId id = new ItemCotizacionId();
		id.setIdCotizacion(this);
		id.setRodamiento(rodamiento);
		itemCotizacion.setId(id);
		itemCotizacion.setEstadoCotizacion(estadoCotizacion);
		items.add(itemCotizacion);
	}
	
	public CotizacionVO getView(){
		CotizacionVO cotizacionVO = new CotizacionVO();
		cotizacionVO.setNroCotizacion(this.getId());
		cotizacionVO.setDiasValidez(this.getDiasValidez());
		cotizacionVO.setFecha(this.getFecha());
		cotizacionVO.setCliente(this.getCliente().getView());
		cotizacionVO.setNroSolicitudCotizacion(this.getSolicitudCotizacion().getNroSolicitudCotizacion().toString());
		List<ItemCotizacionVO> listaItems = new ArrayList<ItemCotizacionVO>();
		for (ItemCotizacion item : this.getItems()) {
			listaItems.add(item.getView());
		}
		cotizacionVO.setItems(listaItems);
		return cotizacionVO;
	}
	
}
