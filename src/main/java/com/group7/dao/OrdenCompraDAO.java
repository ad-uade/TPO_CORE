package com.group7.dao;

import com.group7.entity.OrdenCompra;
import com.group7.entity.Proveedor;

public class OrdenCompraDAO extends AbstractDAO<OrdenCompra> implements DaoInterface<OrdenCompra, Integer>{

	public void actualizarProveedor(Proveedor proveedor) {
		String senten = "UPDATE OrdenCompra SET proveedor.CUILProveedor = ?";
		getCurrentSession().createQuery(senten).setLong(0, proveedor.getCUILProveedor()).executeUpdate();
	}

	public OrdenCompra dameOrdenCompra(int nroOrdenCompra) {
		String senten = " FROM OrdenCompra WHERE nroOrdenCompra = ?";
		OrdenCompra orden = (OrdenCompra) getCurrentSession().createQuery(senten).setInteger(0, nroOrdenCompra).uniqueResult();
		return orden;
	}

	@Override
	public OrdenCompra buscarPorId(Integer id) {
		return (OrdenCompra) getCurrentSession().get(OrdenCompra.class, id);
	}
	
	@Override
	protected String getClassName() {
		return OrdenCompra.class.getSimpleName();
	}
	
}
