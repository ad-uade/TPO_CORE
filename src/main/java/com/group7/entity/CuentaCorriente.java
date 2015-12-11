package com.group7.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.group7.business.CuentaCorrienteVO;

@Entity
@DiscriminatorValue("CC")
public class CuentaCorriente extends FormaPago {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4490615941660207028L;
	@Column (name = "recargo")
	private Float recargo;
	@Column (name = "dias")
	private Integer dias;
	
	public CuentaCorriente(){
		
	}
	
	public CuentaCorriente(CuentaCorrienteVO cuentaCorrienteVO){
		super(cuentaCorrienteVO);
		this.setDias(cuentaCorrienteVO.getDias());
		this.setRecargo(cuentaCorrienteVO.getRecargo());
	}

	/**
	 * @return the recargo
	 */
	public Float getRecargo() {
		return recargo;
	}

	/**
	 * @param recargo the recargo to set
	 */
	public void setRecargo(Float recargo) {
		this.recargo = recargo;
	}

	/**
	 * @return the dias
	 */
	public Integer getDias() {
		return dias;
	}

	/**
	 * @param dias the dias to set
	 */
	public void setDias(Integer dias) {
		this.dias = dias;
	}

	public CuentaCorrienteVO getView(){
		CuentaCorrienteVO cuentaCorrienteVO = (CuentaCorrienteVO) super.getView();
		cuentaCorrienteVO.setDias(dias);
		cuentaCorrienteVO.setRecargo(recargo);
		return cuentaCorrienteVO;
	}
	
}
