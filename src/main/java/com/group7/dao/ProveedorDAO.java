package com.group7.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.group7.entity.Proveedor;

import util.HibernateUtil;

public class ProveedorDAO extends AbstractDAO<Proveedor> implements DaoInterface<Proveedor, Integer> {

	private Session currentSession;
	private Transaction currentTransaction;

	public Session openCurrentSession() {
		currentSession = getSessionFactory().openSession();
		return currentSession;
	}

	public Session openCurrentSessionwithTransaction() {
		currentSession = getSessionFactory().openSession();
		currentTransaction = currentSession.beginTransaction();
		return currentSession;
	}

	public void closeCurrentSession() {
		currentSession.close();
	}
	
	public void bajaProveedor(Integer CUIL){
		String sql1 = " From ProveedorHibernate c where c.CUILProveedor = ?";
		Proveedor p1 = (Proveedor) getCurrentSession().createQuery(sql1).setInteger(0, CUIL).uniqueResult();
		p1.setEstado(false);
		this.actualizar(p1);
	}

	public void closeCurrentSessionwithTransaction() {
		currentTransaction.commit();
		currentSession.close();
	}

	private static SessionFactory getSessionFactory() {
		return HibernateUtil.getSessionFactory();
	}

	public Session getCurrentSession() {
		return currentSession;
	}

	public void setCurrentSession(Session currentSession) {
		this.currentSession = currentSession;
	}

	public Transaction getCurrentTransaction() {
		return currentTransaction;
	}

	public void setCurrentTransaction(Transaction currentTransaction) {
		this.currentTransaction = currentTransaction;
	}

	@SuppressWarnings("unchecked")
	public List<Proveedor> getProveedores() {
		List<Proveedor> lista = getCurrentSession().createQuery("FROM Proveedor WHERE estado=1").list();
		return lista;
	}

	public Proveedor getProveedor(Integer cuil) {
		return (Proveedor) getCurrentSession().createQuery(" FROM Proveedor WHERE CUILProveedor = :cuil").setInteger("cuil", cuil).uniqueResult();
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

}
