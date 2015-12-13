package com.group7.dao;

import java.util.List;

import com.group7.entity.Stock;

public class MovimientoStockDAO extends AbstractDAO<Stock> implements DaoInterface<Stock, Integer> {

	@SuppressWarnings("unchecked")
	public List<Stock> verificarStockIngreso(String codigoSFK, String codigoPieza) {
		String senten = " FROM MovimientoStock m WHERE m.rodamiento.rodamientoId.codigoSFK = ? "
				+ "AND m.rodamiento.rodamientoId.codigoPieza = ? AND m.tipo = ?";
		List<Stock> lista = getCurrentSession().createQuery(senten).setString(0, codigoSFK).setString(1, codigoPieza).setString(2, "ingreso").list();
		return lista;
	}

	@SuppressWarnings("unchecked")
	public List<Stock> verificarStockEgreso(String codigoSFK, String codigoPieza) {
		String senten = " FROM MovimientoStock m WHERE m.rodamiento.rodamientoId.codigoSFK = ? "
				+ "AND m.rodamiento.rodamientoId.codigoPieza = ? AND m.tipo = ?";
		List<Stock> lista = getCurrentSession().createQuery(senten).setString(0, codigoSFK).setString(1, codigoPieza).setString(2, "egreso").list();
		return lista;
	}

	@Override
	public Stock buscarPorId(Integer id) {
		return (Stock) getCurrentSession().get(Stock.class, id);
	}

	@Override
	protected String getClassName() {
		return Stock.class.getSimpleName();
	}
	
}