package com.group7.dao;

import com.group7.entity.SolicitudCotizacion;

public class SolicitudCotizacionDAO extends AbstractDAO<SolicitudCotizacion> implements DaoInterface<SolicitudCotizacion, Integer> {

	@Override
	public SolicitudCotizacion buscarPorId(Integer id) {
		return (SolicitudCotizacion) getCurrentSession().get(SolicitudCotizacion.class, id);
	}

}
