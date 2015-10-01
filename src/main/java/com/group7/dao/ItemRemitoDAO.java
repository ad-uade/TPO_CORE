package com.group7.dao;

import com.group7.entity.ItemRemito;

public class ItemRemitoDAO extends AbstractDAO<ItemRemito> implements DaoInterface<ItemRemito, Integer>  {

	@Override
	public ItemRemito buscarPorId(Integer id) {
		return (ItemRemito) getCurrentSession().get(ItemRemito.class, id);
	}
	
}