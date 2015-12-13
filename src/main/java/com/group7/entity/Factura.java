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

import com.group7.business.FacturaVO;
import com.group7.business.ItemFacturaVO;
import com.group7.entity.enbeddable.ItemFacturaId;

@Entity
@Table (name = "facturas")
public class Factura implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3495558280429829262L;

	@Id
	@Column (name = "nroFactura")
	@GeneratedValue
	private Integer nroFactura;
	@Column (name = "fecha")
	private Date fecha;
	@Column (name = "descuento")
	private Float descuento;
	@ManyToOne
	@JoinColumn (name = "CUILCliente")
	private Cliente cliente;
	@ManyToOne
	@JoinColumn (name = "nroRemito")
	private Remito remito;
	@OneToMany (cascade = CascadeType.ALL)
	@JoinColumn (name = "nroFactura")
	private List<ItemFactura>items;
	@OneToOne
	@JoinColumn (name = "nroCondicion")
	private CondicionVenta condVenta;
	
	public Factura(){
		
	}
	
	public int getNroFactura() {
		return nroFactura;
	}

	public void setNroFactura(int nroFactura) {
		this.nroFactura = nroFactura;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Float getDescuento() {
		return descuento;
	}

	public void setDescuento(float descuento) {
		this.descuento = descuento;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<ItemFactura> getItems() {
		return items;
	}

	public void setItems(List<ItemFactura> items) {
		this.items = items;
	}

	public Remito getRemito() {
		return remito;
	}

	public void setRemito(Remito remito) {
		this.remito = remito;
	}
	
	public Float calcularTotal(){
		float total = 0;
		for (ItemFactura item : this.getItems()){
			total = total + item.subtotal();
		}
		return total;
	}
	
	public void add(Rodamiento rodamiento, Integer cantidad, Float precioUnitario){
		ItemFactura itemFactura = new ItemFactura();
		itemFactura.setCantidad(cantidad);
		itemFactura.setPrecioUnitario(precioUnitario);
		ItemFacturaId itemFacturaId = new ItemFacturaId();
		itemFacturaId.setFactura(this);
		itemFacturaId.setRodamiento(rodamiento);
		itemFactura.setId(itemFacturaId);
		items.add(itemFactura);
	}
	
	public FacturaVO getView(){
		FacturaVO facturaVO = new FacturaVO();
		facturaVO.setCliente(this.getCliente().getView());
		facturaVO.setFecha(this.getFecha());
		facturaVO.setPrecio(this.calcularTotal());
		facturaVO.setNroFactura(this.getNroFactura());
		List<ItemFacturaVO> itemsFactura = new ArrayList<ItemFacturaVO>();
		for (ItemFactura item : this.getItems()){
			itemsFactura.add(item.getView());
		}
		facturaVO.setItems(itemsFactura);
		return facturaVO;
	}
	
}