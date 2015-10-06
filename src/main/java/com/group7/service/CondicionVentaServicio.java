package com.group7.service;

import com.group7.entity.CondicionVenta;
import com.group7.entity.Rodamiento;
import com.group7.entity.SolicitudCotizacion;

public class CondicionVentaServicio {

	private static CondicionVentaServicio instancia;

	public static CondicionVentaServicio getInstancia() {
		if (instancia == null)
			instancia = new CondicionVentaServicio();
		return instancia;
	}

	public CondicionVenta dameCondicionVenta(SolicitudCotizacion sc, Rodamiento rodamiento) {
		CondicionVenta condicion = new CondicionVenta();
		for (int i = 0; sc.getItems().size() - 1 >= i; i++) {
			if (sc.getItems().get(i).getId().getRodamiento().getRodamientoId().getCodigoSFK()
					.equalsIgnoreCase(rodamiento.getRodamientoId().getCodigoSFK())
					&& sc.getItems().get(i).getId().getRodamiento().getRodamientoId().getCodigoPieza()
							.equalsIgnoreCase(rodamiento.getRodamientoId().getCodigoPieza())) {
				condicion.setNroCondicion(sc.getItems().get(i).getCondicion().getNroCondicion());
				condicion.setFechaDesde(sc.getItems().get(i).getCondicion().getFechaDesde());
				condicion.setFechaHasta(sc.getItems().get(i).getCondicion().getFechaHasta());
				condicion.setIva(sc.getItems().get(i).getCondicion().getIva());
				condicion.setFormaPago(sc.getItems().get(i).getCondicion().getFormaPago());
			}
		}
		return condicion;
	}

}
