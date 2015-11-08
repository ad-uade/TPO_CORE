package com.group7.dao;

import java.util.List;

import com.group7.entity.Remito;
import com.group7.entity.RemitoExterior;

public class RemitoExteriorDAO extends RemitoDAO {

	public void conformar(Remito remito) {
		String senten = "UPDATE RemitoExterior SET conformeCliente = ? WHERE nroRemito = ?";
		getCurrentSession().createQuery(senten).setBoolean(0, true).setInteger(1, remito.getNroRemito()).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<RemitoExterior> dameRemitosConformados() {
		String senten = " FROM RemitoExterior WHERE conformeCliente = ?";
		List<RemitoExterior> remitos = getCurrentSession().createQuery(senten).setBoolean(0, true).list();
		return remitos;
	}

	@SuppressWarnings("unchecked")
	public List<RemitoExterior> dameRemitosNoConformados() {
		String senten = " FROM RemitoExterior WHERE conformeCliente = ?";
		List<RemitoExterior> remitos = getCurrentSession().createQuery(senten).setBoolean(0, false).list();
		return remitos;
	}

	@Override
	public RemitoExterior buscarPorId(Integer id) {
		return (RemitoExterior) getCurrentSession().get(RemitoExterior.class, id);
	}
	
}