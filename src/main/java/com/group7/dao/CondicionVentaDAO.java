package com.group7.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.group7.entity.CondicionVenta;

import util.HibernateUtil;

public class CondicionVentaDAO extends AbstractDAO<CondicionVenta> implements DaoInterface<CondicionVenta, Integer> {

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
	public List<CondicionVenta> dameCondiciones() {
		String senten = " FROM CondicionVenta";
		List<CondicionVenta> condiciones = getCurrentSession().createQuery(senten).list();
		return condiciones;
	}

	public CondicionVenta dameCondicion(int nroCondicion) {
		String senten = " FROM CondicionVenta WHERE nroCondicion = ?";
		CondicionVenta con = (CondicionVenta) getCurrentSession().createQuery(senten).setInteger(0, nroCondicion).uniqueResult();
		return con;
	}

	@Override
	public CondicionVenta buscarPorId(Integer id) {
		return (CondicionVenta) getCurrentSession().get(CondicionVenta.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CondicionVenta> buscarTodos() {
		return (List<CondicionVenta>)getCurrentSession().createQuery("from CondicionVenta ").list();
	}

	@Override
	public void borrarTodos() {
		List<CondicionVenta> entityList = buscarTodos();
		for (CondicionVenta entity : entityList) {
			this.borrar(entity);
		}
	}

}
