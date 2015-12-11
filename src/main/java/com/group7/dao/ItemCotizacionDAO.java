package com.group7.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.group7.entity.ItemCotizacion;
import com.group7.entity.enbeddable.ItemCotizacionId;

import util.HibernateUtil;

public class ItemCotizacionDAO extends AbstractDAO<ItemCotizacion> implements DaoInterface<ItemCotizacion, ItemCotizacionId> {

	private Session currentSession;
	private Transaction currentTransaction;

	@Override
	public Session openCurrentSession() {
		currentSession = getSessionFactory().openSession();
		return currentSession;
	}

	@Override
	public Session openCurrentSessionwithTransaction() {
		currentSession = getSessionFactory().openSession();
		currentTransaction = currentSession.beginTransaction();
		return currentSession;
	}

	@Override
	public void closeCurrentSession() {
		currentSession.close();
	}

	@Override
	public void closeCurrentSessionwithTransaction() {
		currentTransaction.commit();
		currentSession.close();
	}

	private static SessionFactory getSessionFactory() {
		return HibernateUtil.getSessionFactory();
	}

	@Override
	public void setCurrentSession(Session currentSession) {
		this.currentSession = currentSession;
	}

	@Override
	public Transaction getCurrentTransaction() {
		return currentTransaction;
	}

	@Override
	public void setCurrentTransaction(Transaction currentTransaction) {
		this.currentTransaction = currentTransaction;
	}
	
	@SuppressWarnings("unchecked")
	public List<ItemCotizacion> buscarItemsAprobados(int nroCotizacion) {
		String senten = " FROM ItemCotizacion items WHERE items.id.idCotizacion = ? AND items.estado = 'APROBADO'";
		List<ItemCotizacion> items = getCurrentSession().createQuery(senten).setInteger(0, nroCotizacion).list();
		return items;
	}

	@SuppressWarnings("unchecked")
	public List<ItemCotizacion> dameItemsCotizacion(int nroCotizacion) {
		String senten = " FROM ItemCotizacion WHERE id.idCotizacion = ?";
		List<ItemCotizacion> items = getCurrentSession().createQuery(senten).setInteger(0, nroCotizacion).list();
		return items;
	}

	@Override
	public ItemCotizacion buscarPorId(ItemCotizacionId id) {
		return (ItemCotizacion) getCurrentSession().get(ItemCotizacion.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ItemCotizacion> buscarTodos() {
		return getCurrentSession().createQuery("from ItemCotizacion ").list();
	}

	@Override
	public void borrarTodos() {
		List<ItemCotizacion> entityList = buscarTodos();
		for (ItemCotizacion entity : entityList) {
			this.borrar(entity);
		}
	}

	@Override
	protected String getClassName() {
		return ItemCotizacion.class.getSimpleName();
	}
}
