package com.group7.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.group7.business.RemitoExteriorVO;
import com.group7.entity.enbeddable.ItemRemitoId;

@Entity
@DiscriminatorValue("RE")
public class RemitoExterior extends Remito {
	
	private static final long serialVersionUID = -2529719019032328609L;
	
	@Column(name = "conformado")
	private Boolean conformeCliente = Boolean.FALSE;

	public RemitoExterior(){
		
	}

	public void setConformeCliente(Boolean conformeCliente) {
		this.conformeCliente = conformeCliente;
	}

	public Boolean isConformeCliente() {
		return conformeCliente;
	}

	public void add(Rodamiento rodamiento, int cantidad) {
		ItemRemito itemRemito= new ItemRemito();
		itemRemito.setCantidad(cantidad);
		ItemRemitoId id = new ItemRemitoId();
		id.setRodamiento(rodamiento);
		id.setNroRemito(this);
		itemRemito.setId(id);
		rodamiento.movimientoEgreso(cantidad);
		this.getItems().add(itemRemito);
	}

	public RemitoExteriorVO getView() {
		
		return null;
	}
	
}