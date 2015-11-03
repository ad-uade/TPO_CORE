package com.group7.dao;

import java.util.List;

import com.group7.entity.ItemSolicitudCotizacion;

public class ItemSolicitudCotizacionDAO extends AbstractDAO<ItemSolicitudCotizacion> implements DaoInterface<ItemSolicitudCotizacion, Integer> {

	@Override
	public ItemSolicitudCotizacion buscarPorId(Integer id) {
		return (ItemSolicitudCotizacion) getCurrentSession().get(ItemSolicitudCotizacion.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<ItemSolicitudCotizacion> getItems(int nroSolicitudCotizacion){
		String senten = " FROM ItemSolicitudCotizacion items WHERE items.id.nroSolicitudCotizacion = ?";
		List<ItemSolicitudCotizacion> items = getCurrentSession().createQuery(senten).setInteger(0, nroSolicitudCotizacion).list();
		return items;
	}
}
