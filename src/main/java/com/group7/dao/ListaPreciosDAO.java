package com.group7.dao;

import com.group7.entity.ListaPrecios;

public class ListaPreciosDAO extends AbstractDAO<ListaPrecios> implements DaoInterface<ListaPrecios, Integer> {

	public ListaPrecios existeListaConIgualTipo(Long cuilProveedor, String tipo) {
		String senten = " FROM ListaPrecios WHERE CUILProveedor = ? AND tipo = ? AND estado = true";
		return (ListaPrecios) getCurrentSession().createQuery(senten).setLong(0, cuilProveedor).setString(1, tipo).uniqueResult();
	}

	public void bajaListaPrecios(ListaPrecios lista1) {
		String senten = "UPDATE ListaPrecios SET estado = ? WHERE nroLista = ? AND CUILProveedor = ?";
		getCurrentSession().createQuery(senten).setBoolean(0, false).setInteger(1, lista1.getNroLista()).setLong(2, lista1.getProveedor().getCuilProveedor()).executeUpdate();
	}

	public ListaPrecios getListaDePrecios(Integer nro) {
		return (ListaPrecios) getCurrentSession().createQuery(" FROM ListaPrecios WHERE nroLista = :nro").setInteger("nro", nro).uniqueResult();
	}

	public void actualizarProveedor(ListaPrecios lista) {
		String senten = "UPDATE ListaPrecios SET CUILProveedor = ? WHERE nroLista = ?";
		getCurrentSession().createQuery(senten).setLong(0, lista.getProveedor().getCuilProveedor()).setInteger(1, lista.getNroLista()).executeUpdate();
	}

	@Override
	public ListaPrecios buscarPorId(Integer id) {
		return (ListaPrecios) getCurrentSession().get(ListaPrecios.class, id);
	}
	
	@Override
	protected String getClassName() {
		return ListaPrecios.class.getSimpleName();
	}
	
}
