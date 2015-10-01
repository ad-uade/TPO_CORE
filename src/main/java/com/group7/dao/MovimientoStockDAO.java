package com.group7.dao;

import java.util.List;

import com.group7.entity.MovimientoStock;

public class MovimientoStockDAO extends AbstractDAO<MovimientoStock> implements DaoInterface<MovimientoStock, Integer> {

	@SuppressWarnings("unchecked")
	public List<MovimientoStock> verificarStockIngreso(String codigoSFK, String codigoPieza) {
		String senten = " FROM MovimientoStock m WHERE m.rodamiento.rodamientoId.codigoSFK = ? "
				+ "AND m.rodamiento.rodamientoId.codigoPieza = ? AND m.tipo = ?";
		List<MovimientoStock> lista = getCurrentSession().createQuery(senten).setString(0, codigoSFK).setString(1, codigoPieza).setString(2, "ingreso").list();
		return lista;
	}

	@SuppressWarnings("unchecked")
	public List<MovimientoStock> verificarStockEgreso(String codigoSFK, String codigoPieza) {
		String senten = " FROM MovimientoStock m WHERE m.rodamiento.rodamientoId.codigoSFK = ? "
				+ "AND m.rodamiento.rodamientoId.codigoPieza = ? AND m.tipo = ?";
		List<MovimientoStock> lista = getCurrentSession().createQuery(senten).setString(0, codigoSFK).setString(1, codigoPieza).setString(2, "egreso").list();
		return lista;
	}

	@Override
	public MovimientoStock buscarPorId(Integer id) {
		return (MovimientoStock) getCurrentSession().get(MovimientoStock.class, id);
	}

}