package com.group7.dao;

import java.util.List;

import com.group7.entity.ItemRemito;

public class ItemRemitoDAO extends AbstractDAO<ItemRemito> implements DaoInterface<ItemRemito, Integer>  {

	@Override
	public ItemRemito buscarPorId(Integer id) {
		return (ItemRemito) getCurrentSession().get(ItemRemito.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<ItemRemito> dameItemsRemito(int nroRemito) {
		String senten = " FROM ItemRemito WHERE id.nroRemito = ?";
		List<ItemRemito> items = getCurrentSession().createQuery(senten).setInteger(0, nroRemito).list();
		return items;
	}
	
}