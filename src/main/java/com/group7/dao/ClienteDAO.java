package com.group7.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.group7.entity.Cliente;

import util.HibernateUtil;

public class ClienteDAO extends AbstractDAO<Cliente> implements DaoInterface<Cliente, Integer> {

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

	public void agregarOficina(Cliente c) {
		Transaction t = getCurrentSession().beginTransaction();
		String sql1 = " UPDATE Cliente SET ODV = ? Where CUILCliente = ?";
		getCurrentSession().createQuery(sql1).setInteger(0, c.getOficinaVentas().getIdOficinaVenta()).setInteger(1, c.getcUILCliente()).executeUpdate();
		t.commit();
	}

	@Override
	public void persistir(Cliente entity) {
		getCurrentSession().save(entity);
	}

	@Override
	public void actualizar(Cliente entity) {
		getCurrentSession().update(entity);
	}

	@Override
	public Cliente buscarPorId(Integer id) {
		return (Cliente) getCurrentSession().get(Cliente.class, id);
	}

	@Override
	public void borrar(Cliente entity) {
		getCurrentSession().delete(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Cliente> buscarTodos() {
		return (List<Cliente>)getCurrentSession().createQuery("from Cliente Where estado = 1").list();
	}

	@Override
	public void borrarTodos() {
		List<Cliente> entityList = buscarTodos();
		for (Cliente entity : entityList) {
			this.borrar(entity);
		}
	}

}