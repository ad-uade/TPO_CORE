package com.group7.dao;

import com.group7.entity.ItemComparativaPrecio;
import com.group7.entity.Proveedor;
import com.group7.entity.Rodamiento;
import com.group7.entity.enbeddable.ItemComparativaPrecioId;

public class ItemsComparativaPrecioDAO extends AbstractDAO<ItemComparativaPrecio> implements DaoInterface<ItemComparativaPrecio, ItemComparativaPrecioId> {

	public void actualizarItem(Rodamiento rodamiento, float precioVenta, Proveedor proveedorVOAHibernate, int nroLista) {
		String senten = "UPDATE ItemsComparativaPrecio SET mejorPrecio = ?, numeroListaPrecios = ?, "
+ "proveedorListaPrecios.CUILProveedor = ? WHERE id.rodamiento.rodamientoId.codigoSFK = ?";
		getCurrentSession().createQuery(senten).setFloat(0, precioVenta).setInteger(1, nroLista).setLong(2, proveedorVOAHibernate.getCuilProveedor()).setString(3, rodamiento.getRodamientoId().getCodigoSFK()).executeUpdate();
	}

	public boolean existeItemConRodamiento(Rodamiento rodamientoVOAHibernate) {
		String senten = " FROM ItemsComparativaPrecio WHERE id.rodamiento.rodamientoId.codigoSFK = ?";
		ItemComparativaPrecio item = (ItemComparativaPrecio) getCurrentSession().createQuery(senten).setString(0, rodamientoVOAHibernate.getRodamientoId().getCodigoSFK()).uniqueResult();
		if (item == null) {
			return false;
		} else {
			return true;
		}
	}

	public ItemComparativaPrecio dameItemConProveedor(Rodamiento rodamiento) {
		String senten = " FROM ItemsComparativaPrecio WHERE id.rodamiento.rodamientoId.codigoSFK = ? AND "
				+ "id.rodamiento.rodamientoId.codigoPieza = ?";
		ItemComparativaPrecio item = (ItemComparativaPrecio) getCurrentSession().createQuery(senten).setString(0, rodamiento.getRodamientoId().getCodigoSFK()).setString(1, rodamiento.getRodamientoId().getCodigoPieza()).uniqueResult();
		return item;
	}

	@Override
	public ItemComparativaPrecio buscarPorId(ItemComparativaPrecioId id) {
		return (ItemComparativaPrecio) getCurrentSession().get(ItemComparativaPrecio.class, id);
	}

	public void eliminar(int nroLista) {
		String senten = "DELETE ItemsComparativaPrecio WHERE numeroListaPrecios = ?";
		getCurrentSession().createQuery(senten).setInteger(0, nroLista).executeUpdate();
	}
	
	@Override
	protected String getClassName() {
		return ItemComparativaPrecio.class.getSimpleName();
	}
	
}
