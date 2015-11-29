package com.group7.dao;

import java.util.List;

import com.group7.entity.Proveedor;

public class ProveedorDAO extends AbstractDAO<Proveedor> implements DaoInterface<Proveedor, Integer> {
	
	public void bajaProveedor(Long CUIL){
		String sql1 = " From Proveedor c WHERE c.cuilProveedor = ?";
		Proveedor p1 = (Proveedor) getCurrentSession().createQuery(sql1).setLong(0, CUIL).uniqueResult();
		p1.setEstado(false);
		this.actualizar(p1);
	}

	@SuppressWarnings("unchecked")
	public List<Proveedor> getProveedores() {
		List<Proveedor> lista = getCurrentSession().createQuery("FROM Proveedor WHERE estado=1").list();
		return lista;
	}

	public Proveedor getProveedor(Long cuil) {
		return (Proveedor) getCurrentSession().createQuery(" FROM Proveedor WHERE cuilProveedor = :cuil").setLong("cuil", cuil).uniqueResult();
	}

	@Override
	public Proveedor buscarPorId(Integer id) {
		return (Proveedor) getCurrentSession().get(Proveedor.class, id);
	}

	@Override
	public List<Proveedor> buscarTodos() {
		return getProveedores();
	}

	@Override
	public void borrarTodos() {
		List<Proveedor> entityList = buscarTodos();
		for (Proveedor entity : entityList) {
			this.borrar(entity);
		}
	}
	
	@Override
	protected String getClassName() {
		return Proveedor.class.getSimpleName();
	}

}
