package com.group7.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Inheritance (strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn (name = "tipo", discriminatorType = DiscriminatorType.STRING)
@Table (name = "estrategiasCliente")
public class EstrategiaDescuentoCliente implements Serializable{

	private static final long serialVersionUID = -3637122412740180242L;

	@Id
	@Column (name = "idEstrategiaCliente")
	private Integer idEstrategiaCliente;
	@Column (name = "descuento")
	private Float descuento;
	
	public EstrategiaDescuentoCliente(){
		
	}

	public Integer getIdEstrategiaCliente() {
		return idEstrategiaCliente;
	}

	public void setIdEstrategiaCliente(Integer idEstrategiaCliente) {
		this.idEstrategiaCliente = idEstrategiaCliente;
	}

	public Float getDescuento() {
		return descuento;
	}

	public void setDescuento(Float descuento) {
		this.descuento = descuento;
	}
	
}