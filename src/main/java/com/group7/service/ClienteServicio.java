package com.group7.service;

import java.util.List;

import com.group7.dao.ClienteDAO;
import com.group7.entity.Cliente;

public class ClienteServicio {

	private static ClienteDAO clienteDao;
	private static ClienteServicio instancia;

	public static ClienteServicio getInstancia(){
		if(instancia == null)
			instancia = new ClienteServicio();
		return instancia;
	}
	
	public ClienteServicio() {
		clienteDao = new ClienteDAO();
	}

	public void persist(Cliente entity) {
		clienteDao.openCurrentSessionwithTransaction();
		clienteDao.persistir(entity);
		clienteDao.closeCurrentSessionwithTransaction();
	}

	public void update(Cliente entity) {
		clienteDao.openCurrentSessionwithTransaction();
		clienteDao.actualizar(entity);
		clienteDao.closeCurrentSessionwithTransaction();
	}

	public Cliente buscarClientePorCuil(Integer CUIL) {
		clienteDao.openCurrentSession();
		Cliente cliente = clienteDao.buscarPorId(CUIL);
		clienteDao.closeCurrentSession();
		return cliente;
	}
	
	public Cliente buscarPorId(String id) {
		clienteDao.openCurrentSession();
		Cliente cliente = clienteDao.buscarPorId(Integer.valueOf(id));
		clienteDao.closeCurrentSession();
		return cliente;
	}

	public void borrar(String id) {
		clienteDao.openCurrentSessionwithTransaction();
		Cliente book = clienteDao.buscarPorId(Integer.valueOf(id));
		clienteDao.borrar(book);
		clienteDao.closeCurrentSessionwithTransaction();
	}

	public List<Cliente> findAll() {
		clienteDao.openCurrentSession();
		List<Cliente> clientes = clienteDao.buscarTodos();
		clienteDao.closeCurrentSession();
		return clientes;
	}

	public void deleteAll() {
		clienteDao.openCurrentSessionwithTransaction();
		clienteDao.borrarTodos();
		clienteDao.closeCurrentSessionwithTransaction();
	}

}
