package com.group7.dao;

import com.group7.entity.Rodamiento;
import com.group7.entity.enbeddable.RodamientoId;

public class RodamientoDAO extends AbstractDAO<Rodamiento> implements DaoInterface<Rodamiento, RodamientoId>{

	public Rodamiento getRodamiento(String SFK, String codigo) {
		return (Rodamiento) getCurrentSession().createQuery(" FROM Rodamiento r WHERE r.rodamientoId.codigoSFK = :sfk AND r.rodamientoId.codigoPieza = :codigo").setString("sfk", SFK).setString("codigo", codigo).uniqueResult();
	}

	@Override
	public Rodamiento buscarPorId(RodamientoId id) {
		return (Rodamiento) getCurrentSession().get(Rodamiento.class, id);
	}

}
