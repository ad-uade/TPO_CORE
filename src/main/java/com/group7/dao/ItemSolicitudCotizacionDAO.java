package com.group7.dao;

import com.group7.entity.ItemSolicitudCotizacion;

public class ItemSolicitudCotizacionDAO extends AbstractDAO<ItemSolicitudCotizacion> implements DaoInterface<ItemSolicitudCotizacion, Integer> {

	@Override
	public ItemSolicitudCotizacion buscarPorId(Integer id) {
		return (ItemSolicitudCotizacion) getCurrentSession().get(ItemSolicitudCotizacion.class, id);
	}
	
}
