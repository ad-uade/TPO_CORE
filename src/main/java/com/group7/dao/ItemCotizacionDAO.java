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
	
	public void actualizarEstado(ItemCotizacion itemCotizacion) {
		  String senten = "UPDATE ItemCotizacion SET estado = ? "
		  		+ "WHERE id.idCotizacion = ? AND id.rodamiento.rodamientoId.codigoSFK = ? " +
		    "AND id.rodamiento.rodamientoId.codigoPieza = ?";
		  getCurrentSession().createQuery(senten).setString(0, itemCotizacion.getEstado()).setInteger(1, itemCotizacion.getId().getIdCotizacion()).setString(2, itemCotizacion.getId().getRodamiento().getRodamientoId().getCodigoSFK()).setString(3, itemCotizacion.getId().getRodamiento().getRodamientoId().getCodigoPieza()).executeUpdate();
	}

	@Override
	public ItemCotizacion buscarPorId(ItemCotizacionId id) {
		return (ItemCotizacion) getCurrentSession().get(ItemCotizacion.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ItemCotizacion> buscarTodos() {
		return (List<ItemCotizacion>)getCurrentSession().createQuery("from ItemCotizacion ").list();
	}

	@Override
	public void borrarTodos() {
		List<ItemCotizacion> entityList = buscarTodos();
		for (ItemCotizacion entity : entityList) {
			this.borrar(entity);
		}
	}

}
