package com.group7.dao;

import com.group7.entity.ItemsComparativaPrecio;
import com.group7.entity.Proveedor;
import com.group7.entity.Rodamiento;
import com.group7.entity.enbeddable.ItemComparativaPrecioId;

public class ItemsComparativaPrecioDAO extends AbstractDAO<ItemsComparativaPrecio> implements DaoInterface<ItemsComparativaPrecio, ItemComparativaPrecioId> {

	public void actualizarItem(Rodamiento rodamiento, float precioVenta, Proveedor proveedorVOAHibernate, int nroLista) {
		String senten = "UPDATE ItemsComparativaPrecio SET mejorPrecio = ?, numeroListaPrecios = ?, "
+ "proveedorListaPrecios.CUILProveedor = ? WHERE id.rodamiento.rodamientoId.codigoSFK = ?";
		getCurrentSession().createQuery(senten).setFloat(0, precioVenta).setInteger(1, nroLista).setLong(2, proveedorVOAHibernate.getCUILProveedor()).setString(3, rodamiento.getRodamientoId().getCodigoSFK()).executeUpdate();
	}

	public boolean existeItemConRodamiento(Rodamiento rodamientoVOAHibernate) {
		String senten = " FROM ItemsComparativaPrecio WHERE id.rodamiento.rodamientoId.codigoSFK = ?";
		ItemsComparativaPrecio item = (ItemsComparativaPrecio) getCurrentSession().createQuery(senten).setString(0, rodamientoVOAHibernate.getRodamientoId().getCodigoSFK()).uniqueResult();
		if (item == null) {
			return false;
		} else {
			return true;
		}
	}

	public ItemsComparativaPrecio dameItemConProveedor(Rodamiento rodamiento) {
		String senten = " FROM ItemsComparativaPrecio WHERE id.rodamiento.rodamientoId.codigoSFK = ? AND "
				+ "id.rodamiento.rodamientoId.codigoPieza = ?";
		ItemsComparativaPrecio item = (ItemsComparativaPrecio) getCurrentSession().createQuery(senten).setString(0, rodamiento.getRodamientoId().getCodigoSFK()).setString(1, rodamiento.getRodamientoId().getCodigoPieza()).uniqueResult();
		return item;
	}

	@Override
	public ItemsComparativaPrecio buscarPorId(ItemComparativaPrecioId id) {
		return (ItemsComparativaPrecio) getCurrentSession().get(ItemsComparativaPrecio.class, id);
	}

	public void eliminar(int nroLista) {
		String senten = "DELETE ItemsComparativaPrecio WHERE numeroListaPrecios = ?";
		getCurrentSession().createQuery(senten).setInteger(0, nroLista).executeUpdate();
	}
	
	@Override
	protected String getClassName() {
		return ItemsComparativaPrecio.class.getSimpleName();
	}
	
}
