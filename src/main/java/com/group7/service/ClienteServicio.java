package com.group7.service;

import java.util.ArrayList;
import java.util.List;

import com.group7.business.ClienteVO;
import com.group7.business.OficinaVentasVO;
import com.group7.dao.ClienteDAO;
import com.group7.entity.Cliente;

public class ClienteServicio {

	private static ClienteDAO clienteDao;
	private static ClienteServicio instancia;

	public static ClienteServicio getInstancia() {
		if (instancia == null)
			instancia = new ClienteServicio();
		return instancia;
	}
	
	private ClienteServicio() {
		clienteDao = new ClienteDAO();
	}

	public void guardarCliente(String razonSocial, Long cuil, String direccion, String telefono, OficinaVentasVO of) {
		Cliente cliente = new Cliente();
		cliente.setRazonSocial(razonSocial);
		cliente.setCuilCliente(cuil);
		cliente.setDireccion(direccion);
		cliente.setTelefono(telefono);
		cliente.setEstado(true);
		cliente.setOficinaVentas(OficinaVentasServicio.getInstancia().convertir(of));
		clienteDao.persistir(cliente);
	}

	public void modificarCliente(ClienteVO cliente) {
		clienteDao.openCurrentSessionwithTransaction();
		clienteDao.actualizar(this.convertir(cliente));
		clienteDao.closeCurrentSessionwithTransaction();
	}

	public void baja(Long CUIL) {
		clienteDao.openCurrentSessionwithTransaction();
		clienteDao.bajaCliente(CUIL);
		clienteDao.closeCurrentSessionwithTransaction();
	}

	public List<ClienteVO> dameClientes() {
		clienteDao.openCurrentSessionwithTransaction();
		List<Cliente> clientes = clienteDao.buscarTodos();
		List<ClienteVO> clientesVO = new ArrayList<ClienteVO>();
		for (Cliente cliente : clientes){
			ClienteVO clienteVO = this.clienteToVO(cliente);
			clientesVO.add(clienteVO);
		}
		clienteDao.closeCurrentSessionwithTransaction();
		return clientesVO;
	}

	public ClienteVO obtenerCliente(Long CUIL) {
		Cliente ch = this.buscarClientePorCuil(CUIL);
		return this.clienteToVO(ch);
	}
	
	public ClienteVO clienteToVO(Cliente cliente) {
		ClienteVO clienteVO = new ClienteVO();
		clienteVO.setTelefono(cliente.getTelefono());
		clienteVO.setEstado(cliente.getEstado());
		clienteVO.setCuilCliente(cliente.getCuilCliente());
		clienteVO.setRazonSocial(cliente.getRazonSocial());
		clienteVO.setDireccion(cliente.getDireccion());
		clienteVO.setODV(OficinaVentasServicio.getInstancia().popular(cliente.getOficinaVentas()));
		return clienteVO;
	}
	
	public Cliente convertir(ClienteVO clienteVO){
		Cliente cliente = new Cliente();
		cliente.setCuilCliente(clienteVO.getCuilCliente());
		cliente.setRazonSocial(clienteVO.getRazonSocial());
		cliente.setDireccion(clienteVO.getDireccion());
		cliente.setTelefono(clienteVO.getTelefono());
		cliente.setEstado(clienteVO.isEstado());
		cliente.setOficinaVentas(OficinaVentasServicio.getInstancia().convertir(clienteVO.getODV()));
		return cliente;
	}
	
	public void persistirTodos(List<Cliente> listado) {
		clienteDao.openCurrentSessionwithTransaction();
		for (Cliente cliente : listado){
			clienteDao.persistir(cliente);
		}
		clienteDao.closeCurrentSessionwithTransaction();
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

	public Cliente buscarClientePorCuil(Long CUIL) {
		clienteDao.openCurrentSession();
		Cliente cliente = clienteDao.buscarPorId(CUIL);
		clienteDao.closeCurrentSession();
		return cliente;
	}
	
	public Cliente buscarPorId(String id) {
		clienteDao.openCurrentSession();
		Cliente cliente = clienteDao.buscarPorId(Long.valueOf(id));
		clienteDao.closeCurrentSession();
		return cliente;
	}

	public List<Cliente> buscarTodos() {
		clienteDao.openCurrentSession();
		List<Cliente> clientes = clienteDao.buscarTodos();
		clienteDao.closeCurrentSession();
		return clientes;
	}

}
