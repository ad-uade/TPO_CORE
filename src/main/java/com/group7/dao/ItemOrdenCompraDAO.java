package com.group7.dao;

import com.group7.entity.ItemOrdenCompra;
import com.group7.entity.enbeddable.ItemOrdenCompraId;

public class ItemOrdenCompraDAO extends AbstractDAO<ItemOrdenCompra> implements DaoInterface<ItemOrdenCompra, ItemOrdenCompraId> {

	@Override
	public ItemOrdenCompra buscarPorId(ItemOrdenCompraId id) {
		return (ItemOrdenCompra) getCurrentSession().get(ItemOrdenCompra.class, id);
	}
	
}
