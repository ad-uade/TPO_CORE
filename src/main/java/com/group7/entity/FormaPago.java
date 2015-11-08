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
@Table(name="formasPago")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="tipo", discriminatorType=DiscriminatorType.STRING)
public class FormaPago implements Serializable{

	private static final long serialVersionUID = -6157761859880488469L;

	@Id
	@Column(name="idFormaPago")
	private Integer idFormaPago;
	@Column (name = "descripcion")
	private String descripcion;

	public FormaPago (){
		
	}

	/**
	 * @return the idFormaPago
	 */
	public Integer getIdFormaPago() {
		return idFormaPago;
	}

	/**
	 * @param idFormaPago the idFormaPago to set
	 */
	public void setIdFormaPago(Integer idFormaPago) {
		this.idFormaPago = idFormaPago;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
