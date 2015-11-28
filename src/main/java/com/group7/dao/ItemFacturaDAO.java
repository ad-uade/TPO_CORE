package com.group7.dao;

import com.group7.entity.ItemFactura;
import com.group7.entity.enbeddable.ItemFacturaId;

public class ItemFacturaDAO extends AbstractDAO<ItemFactura> implements DaoInterface<ItemFactura, ItemFacturaId> {

	@Override
	public ItemFactura buscarPorId(ItemFacturaId id) {
		return (ItemFactura) getCurrentSession().get(ItemFactura.class, id);
	}
	
	@Override
	protected String getClassName() {
		return ItemFactura.class.getSimpleName();
	}
}
