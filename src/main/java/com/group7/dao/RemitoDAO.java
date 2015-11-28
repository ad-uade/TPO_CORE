package com.group7.dao;

import java.util.List;

import com.group7.entity.ItemRemito;
import com.group7.entity.Remito;

public class RemitoDAO extends AbstractDAO<Remito> implements DaoInterface<Remito, Integer> {

	@Override
	public Remito buscarPorId(Integer id) {
		return (Remito) getCurrentSession().get(Remito.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Remito> buscarTodos() {
		return getCurrentSession().createQuery("from Remito ").list();
	}

	@Override
	public void borrarTodos() {
		List<Remito> entityList = buscarTodos();
		for (Remito entity : entityList) {
			this.borrar(entity);
		}
	}

	@SuppressWarnings("unchecked")
	public List<ItemRemito> dameItemsRemito(int nroRemito) {
		String senten = " FROM ItemRemito WHERE id.nroRemito = ?";
		List<ItemRemito> items = getCurrentSession().createQuery(senten).setInteger(0, nroRemito).list();
		return items;
	}
	
	@Override
	protected String getClassName() {
		return ItemRemito.class.getSimpleName();
	}

}