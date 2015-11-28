package com.group7.dao;

import java.util.List;

import com.group7.entity.OrdenPedido;

public class OrdenPedidoDAO extends AbstractDAO<OrdenPedido> implements DaoInterface<OrdenPedido, Integer> {

	public void cambiarEstado(OrdenPedido ordenPedido) {
		String senten = "UPDATE OrdenPedido SET estado = ? WHERE idOrdenPedido = ?";
		getCurrentSession().createQuery(senten).setBoolean(0, true).setInteger(1, ordenPedido.getIdOrdenPedido()).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<OrdenPedido> getOrdenesPedidoARemitir() {
		List<OrdenPedido> lista = getCurrentSession().createQuery("from OrdenPedido Where estado = 0").list();
		return lista;
	}

	@Override
	public OrdenPedido buscarPorId(Integer id) {
		return (OrdenPedido) getCurrentSession().get(OrdenPedido.class, id);
	}
	
	@Override
	protected String getClassName() {
		return OrdenPedido.class.getSimpleName();
	}
	
}
