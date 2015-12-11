package com.group7.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.group7.business.ContadoVO;

@Entity
@DiscriminatorValue("CO")
public class Contado extends FormaPago {
	
	private static final long serialVersionUID = -7698294557498962843L;
	
	@Column (name = "descuento")
	private Float descuento;
	
	public Contado(){
		
	}
	
	public Contado(ContadoVO contadoVO){
		super(contadoVO);
		this.setDescuento(contadoVO.getDescuento());
	}
	
	public float getDescuento() {
		return descuento;
	}

	public void setDescuento(float descuento) {
		this.descuento = descuento;
	}

	public ContadoVO getView(){
		ContadoVO contadoVO = (ContadoVO) super.getView();
		contadoVO.setDescuento(descuento);
		return contadoVO;
	}
	
}
