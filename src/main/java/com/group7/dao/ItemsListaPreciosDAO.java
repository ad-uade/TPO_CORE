package com.group7.dao;

import com.group7.entity.ItemListaPrecios;
import com.group7.entity.enbeddable.ItemListaPreciosId;

public class ItemsListaPreciosDAO extends AbstractDAO<ItemListaPrecios> implements DaoInterface<ItemListaPrecios, ItemListaPreciosId> {

	@Override
	public ItemListaPrecios buscarPorId(ItemListaPreciosId id) {
		return (ItemListaPrecios) getCurrentSession().get(ItemListaPrecios.class, id);
	}

	@Override
	protected String getClassName() {
		return ItemListaPrecios.class.getSimpleName();
	}
	
}
