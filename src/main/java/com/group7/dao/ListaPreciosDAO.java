package com.group7.dao;

import com.group7.entity.ListaPrecios;

public class ListaPreciosDAO extends AbstractDAO<ListaPrecios> implements DaoInterface<ListaPrecios, Integer> {

	public ListaPrecios existeListaConIgualTipo(int cuilProveedor, String tipo) {
		String senten = " FROM ListaPrecios WHERE proveedor.CUILProveedor = ? AND tipo = ? AND estado = true";
		return (ListaPrecios) getCurrentSession().createQuery(senten).setInteger(0, cuilProveedor).setString(1, tipo).uniqueResult();
	}

	public void bajaListaPrecios(ListaPrecios lista1) {
		String senten = "UPDATE ListaPrecios SET estado = ? WHERE nroLista = ? AND proveedor.CUILProveedor = ?";
		getCurrentSession().createQuery(senten).setBoolean(0, false).setInteger(1, lista1.getNroLista()).setInteger(2, lista1.getProveedor().getCUILProveedor()).executeUpdate();
	}

	public ListaPrecios getListaDePrecios(Integer nro) {
		return (ListaPrecios) getCurrentSession().createQuery(" from ListaPrecios where nroLista = :nro").setInteger("nro", nro).uniqueResult();
	}

	public void actualizarProveedor(ListaPrecios lista) {
		String senten = "UPDATE ListaPrecios SET proveedor.CUILProveedor = ? WHERE nroLista = ?";
		getCurrentSession().createQuery(senten).setInteger(0, lista.getProveedor().getCUILProveedor()).setInteger(1, lista.getNroLista()).executeUpdate();
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
