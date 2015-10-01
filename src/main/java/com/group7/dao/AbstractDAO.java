/**
 * 
 */
package com.group7.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import util.HibernateUtil;

/**
 * @author huicha
 *
 */
public abstract class AbstractDAO<T> {

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

	public void setCurrentSession(Session currentSession) {
		this.currentSession = currentSession;
	}

	public Transaction getCurrentTransaction() {
		return currentTransaction;
	}

	public void setCurrentTransaction(Transaction currentTransaction) {
		this.currentTransaction = currentTransaction;
	}
	
	public Session getCurrentSession() {
		return currentSession;
	}
	
	public void persistir(T entity) {
		getCurrentSession().save(entity);
	}

	public void actualizar(T entity) {
		getCurrentSession().update(entity);		
	}
	
	public void borrar(T entity) {
		getCurrentSession().delete(entity);		
	}
	
	@SuppressWarnings("unchecked")
	public List<T> buscarTodos() {
		return (List<T>)getCurrentSession().createQuery("from "+ this.getClass().getName() + "").list();
	}
	
	public void borrarTodos() {
		List<T> entityList = buscarTodos();
		for (T entity : entityList) {
			this.borrar(entity);
		}
	}
	
}
