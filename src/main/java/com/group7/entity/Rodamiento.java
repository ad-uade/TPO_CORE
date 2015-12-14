package com.group7.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.group7.business.RodamientoVO;
import com.group7.entity.enbeddable.RodamientoId;

@Entity
@Table (name = "rodamientos")
public class Rodamiento implements Serializable{

	private static final long serialVersionUID = -9130251938584651859L;

	@EmbeddedId
	private RodamientoId rodamientoId;
	@Column (name = "descripcion")
	private String descripcion;
	@Column (name = "marca")
	private String marca;
	@Column (name = "paisOrigen")
	private String paisOrigen;
	@Column (name = "estado")
	private Boolean estado = Boolean.TRUE;
	@OneToMany (cascade = CascadeType.ALL, targetEntity=Stock.class)
	@JoinColumns (
		{@JoinColumn (name = "codigoSFK", referencedColumnName="codigoSFK"), 
		@JoinColumn (name = "codigoPieza", referencedColumnName="codigoPieza")
	})
	private List<Stock> listaStock;
	@Column(name="stock")
	private Integer stock;
	
	public Rodamiento(){
		listaStock = new ArrayList<Stock>();
	}
	
	public Rodamiento(RodamientoVO rodamientoVO){
		this.setDescripcion(rodamientoVO.getDescripcion());
		this.setEstado(rodamientoVO.isEstado());
		this.setMarca(rodamientoVO.getMarca());
		this.setPaisOrigen(rodamientoVO.getPaisOrigen());
		RodamientoId rodamientoId = new RodamientoId();
		rodamientoId.setCodigoPieza(rodamientoVO.getCodigoPieza());
		rodamientoId.setCodigoSFK(rodamientoVO.getCodigoSFK());
		this.setRodamientoId(rodamientoId);
	}

	public RodamientoId getRodamientoId() {
		return rodamientoId;
	}

	public void setRodamientoId(RodamientoId rodamientoId) {
		this.rodamientoId = rodamientoId;
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getPaisOrigen() {
		return paisOrigen;
	}

	public void setPaisOrigen(String paisOrigen) {
		this.paisOrigen = paisOrigen;
	}
	
	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	
	@Override
	public String toString() {
		return "Rodamiento [descripcion=" + descripcion + ", marca=" + marca + ", paisOrigen=" + paisOrigen + ", estado=" + estado + "]";
	}
	
	/**
	 * @return the listaStock
	 */
	public List<Stock> getListaStock() {
		return listaStock;
	}

	/**
	 * @param listaStock the listaStock to set
	 */
	public void setListaStock(List<Stock> listaStock) {
		this.listaStock = listaStock;
	}

	/**
	 * @return the stock
	 */
	public Integer getStock() {
		return stock;
	}

	/**
	 * @param stock the stock to set
	 */
	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public void movimientoIngreso(Integer cantidad){
		if (this.getStock() == null){
			// primera vez que genero el stock
			this.setStock(cantidad);
		}else{
			this.setStock(this.getStock() + cantidad);
		}
		Stock stock = new Stock();
		stock.registrarIngreso(cantidad);
		stock.setRodamiento(this);
		this.getListaStock().add(stock);
	}
	
	public void movimientoEgreso(Integer cantidad){
		cantidad = cantidad * -1;
		if (this.getStock() == null){
			// primera vez que genero el stock
			this.setStock(cantidad);
		}else{
			this.setStock(this.getStock() + cantidad);
		}
		Stock stock = new Stock();
		stock.registrarEgreso(cantidad);
		stock.setRodamiento(this);
		this.getListaStock().add(stock);
	}
	
	public void movimientoSolicitud(Integer cantidad){
		if (this.getStock() == null){
			// primera vez que genero el stock
			this.setStock(cantidad);
		}else{
			this.setStock(this.getStock() + cantidad);
		}
		Stock stock = new Stock();
		stock.registrarSolicitud(cantidad);
		stock.setRodamiento(this);
		this.getListaStock().add(stock);
	}
	
	public RodamientoVO getView(){
		RodamientoVO rodamientoVO = new RodamientoVO();
		rodamientoVO.setCodigoSFK(this.getRodamientoId().getCodigoSFK());
		rodamientoVO.setCodigoPieza(this.getRodamientoId().getCodigoPieza());
		rodamientoVO.setDescripcion(this.getDescripcion());
		rodamientoVO.setMarca(this.getMarca());
		rodamientoVO.setPaisOrigen(this.getPaisOrigen());
		return rodamientoVO;
	}
	
	public boolean equals(Rodamiento obj) {
		return this.getRodamientoId().getCodigoPieza().equals(obj.getRodamientoId().getCodigoPieza()) && this.getRodamientoId().getCodigoSFK().equals(obj.getRodamientoId().getCodigoSFK());
	}
	
}
