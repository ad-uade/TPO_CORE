package com.group7.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
	@ManyToOne
	@JoinColumn (name = "CUILProveedor")
	private Proveedor itemProveedor; 
	@Enumerated(EnumType.STRING)
	private EstadoCotizacion estadoCotizacion = EstadoCotizacion.PENDIENTE;
	@ManyToOne
	@JoinColumn (name = "idEstrategiaCliente")
	private EstrategiaDescuentoCliente estrategiaDescuentoCliente;
	
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

	public Proveedor getItemProveedor() {
		return itemProveedor;
	}

	public void setProveedor(Proveedor itemProveedor) {
		this.itemProveedor = itemProveedor;
	}

	public ItemCotizacionVO getView(){
		ItemCotizacionVO itemCotizacionVO = new ItemCotizacionVO();
		itemCotizacionVO.setCantidad(this.getCantidad());
		itemCotizacionVO.setEstado(this.getEstadoCotizacion().toString());
		itemCotizacionVO.setPrecio(this.getPrecioUnitario());
		itemCotizacionVO.setRodamiento(this.getId().getRodamiento().getView());
		itemCotizacionVO.setProveedor(this.getItemProveedor().getView());
		itemCotizacionVO.setNroCotizacion(this.getId().getIdCotizacion().getId());
		return itemCotizacionVO;
	}
	
	/**
	 * @return the estrategiaDescuentoCliente
	 */
	public EstrategiaDescuentoCliente getEstrategiaDescuentoCliente() {
		return estrategiaDescuentoCliente;
	}

	/**
	 * @param estrategiaDescuentoCliente the estrategiaDescuentoCliente to set
	 */
	public void setEstrategiaDescuentoCliente(EstrategiaDescuentoCliente estrategiaDescuentoCliente) {
		this.estrategiaDescuentoCliente = estrategiaDescuentoCliente;
	}

	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * @param precioUnitario the precioUnitario to set
	 */
	public void setPrecioUnitario(Float precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	/**
	 * @param itemProveedor the itemProveedor to set
	 */
	public void setItemProveedor(Proveedor itemProveedor) {
		this.itemProveedor = itemProveedor;
	}
	
	/**
	 * @return the estadoCotizacion
	 */
	public EstadoCotizacion getEstadoCotizacion() {
		return estadoCotizacion;
	}

	/**
	 * @param estadoCotizacion the estadoCotizacion to set
	 */
	public void setEstadoCotizacion(EstadoCotizacion estadoCotizacion) {
		this.estadoCotizacion = estadoCotizacion;
	}
}