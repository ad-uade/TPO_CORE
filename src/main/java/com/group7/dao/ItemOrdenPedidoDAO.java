package com.group7.dao;

import java.util.List;

import com.group7.entity.ItemOrdenPedido;
import com.group7.entity.Proveedor;
import com.group7.entity.Rodamiento;
import com.group7.entity.enbeddable.ItemOrdenPedidoId;

public class ItemOrdenPedidoDAO extends AbstractDAO<ItemOrdenPedido> implements DaoInterface<ItemOrdenPedido, ItemOrdenPedidoId> {

	@SuppressWarnings("unchecked")
	public List<ItemOrdenPedido> dameItems(int nroOrdenPedido) {
		String senten = " FROM ItemOrdenPedido WHERE id.nroOrdenPedido = ?";
		List<ItemOrdenPedido> items = getCurrentSession().createQuery(senten).setInteger(0, nroOrdenPedido).list();
		return items;
	}

	@SuppressWarnings("unchecked")
	public List<ItemOrdenPedido> dameItemsTemporales(int nroOrdenPedido, Proveedor proveedor) {
		String senten = " FROM ItemOrdenPedido WHERE id.nroOrdenPedido = ? AND proveedor.CUILProveedor = ? AND estado = false";
		List<ItemOrdenPedido> itemsT = getCurrentSession().createQuery(senten).setInteger(0, nroOrdenPedido).setLong(1, proveedor.getCuilProveedor()).list();
		return itemsT;
	}

	public void bajaDeEstados(List<ItemOrdenPedido> itemsTemp) { 
		for (int i = 0; itemsTemp.size() - 1 >= i; i++) {
			String senten = "UPDATE ItemOrdenPedido SET estado = ? WHERE id.nroOrdenPedido = ? AND proveedor.CUILProveedor = ?";
			getCurrentSession().createQuery(senten).setBoolean(0, true).setInteger(1, itemsTemp.get(i).getId().getNroOrdenPedido()).setLong(2, itemsTemp.get(i).getProveedor().getCuilProveedor()).executeUpdate();
		}
	}

	public void actualizarEstado(int nroOrdenPedido, Rodamiento rodamiento) {
		String senten = "UPDATE ItemOrdenPedido SET estado = ? WHERE id.nroOrdenPedido = ? AND "
				+ "id.rodamiento.rodamientoId.codigoSFK = ? AND id.rodamiento.rodamientoId.codigoPieza = ?";
		getCurrentSession().createQuery(senten).setBoolean(0, true).setInteger(1, nroOrdenPedido)
				.setString(2, rodamiento.getRodamientoId().getCodigoSFK())
				.setString(3, rodamiento.getRodamientoId().getCodigoPieza()).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<ItemOrdenPedido> dameItemsConEstadoFalse(int idOrdenPedido) {
		String senten = " FROM ItemOrdenPedido WHERE estado = ? AND id.nroOrdenPedido = ?";
		List<ItemOrdenPedido> items = getCurrentSession().createQuery(senten).setBoolean(0, false).setInteger(1, idOrdenPedido).list();
		return items;
	}

	@Override
	public ItemOrdenPedido buscarPorId(ItemOrdenPedidoId id) {
		return (ItemOrdenPedido) getCurrentSession().get(ItemOrdenPedido.class, id);
	}
	
	@Override
	protected String getClassName() {
		return ItemOrdenPedido.class.getSimpleName();
	}
	
}
