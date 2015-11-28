package com.group7.dao;

import com.group7.entity.ItemsListaPrecios;
import com.group7.entity.enbeddable.ItemListaPreciosId;

public class ItemsListaPreciosDAO extends AbstractDAO<ItemsListaPrecios> implements DaoInterface<ItemsListaPrecios, ItemListaPreciosId> {

	@Override
	public ItemsListaPrecios buscarPorId(ItemListaPreciosId id) {
		return (ItemsListaPrecios) getCurrentSession().get(ItemsListaPrecios.class, id);
	}

	@Override
	protected String getClassName() {
		return ItemsListaPrecios.class.getSimpleName();
	}
	
}
