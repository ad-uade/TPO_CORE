package com.group7.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.group7.entity.ComparativaPrecios;

import util.HibernateUtil;

public class ComparativaPreciosDAO implements DaoInterface<ComparativaPrecios, Integer> {

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
	
	public ComparativaPrecios getComparativa() {
		Transaction t = getCurrentSession().beginTransaction();
		String senten = " FROM ComparativaPrecios";
		ComparativaPrecios comparativa = (ComparativaPrecios) getCurrentSession().createQuery(senten).uniqueResult();
		t.commit();
		return comparativa;
	}

	public void actualizarFecha() {
		Transaction t = getCurrentSession().beginTransaction();
		Calendar fechaActual = Calendar.getInstance();
		Date fechaPublicacion = fechaActual.getTime();
		String senten = "UPDATE ComparativaPrecios SET fechaActualizacion = ?";
		getCurrentSession().createQuery(senten).setDate(0, fechaPublicacion).executeUpdate();
		t.commit();
	}

	@Override
	public void persistir(ComparativaPrecios entity) {
		getCurrentSession().save(entity);
	}

	@Override
	public void actualizar(ComparativaPrecios entity) {
		getCurrentSession().update(entity);
	}

	@Override
	public ComparativaPrecios buscarPorId(Integer id) {
		return (ComparativaPrecios) getCurrentSession().get(ComparativaPrecios.class, id);
	}

	@Override
	public void borrar(ComparativaPrecios entity) {
		getCurrentSession().delete(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComparativaPrecios> buscarTodos() {
		return (List<ComparativaPrecios>)getCurrentSession().createQuery("from ComparativaPrecios Where estado = 1").list();
	}

	@Override
	public void borrarTodos() {
		List<ComparativaPrecios> entityList = buscarTodos();
		for (ComparativaPrecios entity : entityList) {
			this.borrar(entity);
		}
	}

}
