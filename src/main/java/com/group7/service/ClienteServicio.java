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

	public void guardarCliente(String razonSocial, int CUIL, String direccion, String telefono, OficinaVentasVO of) {
		Cliente clienteHibernate = new Cliente();
		ClienteDAO miClienteDAO = new ClienteDAO();
		
		clienteHibernate.setRazonSocial(razonSocial);
		clienteHibernate.setcUILCliente(CUIL);
		clienteHibernate.setDireccion(direccion);
		clienteHibernate.setTelefono(telefono);
		clienteHibernate.setEstado(true);
		clienteHibernate.setOficinaVentas(OficinaVentasServicio.getInstancia().convertir(of));
		miClienteDAO.persistir(clienteHibernate);
		miClienteDAO.agregarOficina(clienteHibernate);
	}

	public void modificarCliente(ClienteVO cliente) {
		Cliente miCliente = this.popular(cliente);
		ClienteDAO miClienteDAO = new ClienteDAO();
		miClienteDAO.actualizar(miCliente);
	}

	public void baja(int CUIL) {
		ClienteDAO miClienteDAO = new ClienteDAO();
		miClienteDAO.bajaCliente(CUIL);
	}

	public List<ClienteVO> dameClientes() {
		try {
			ClienteDAO miCliente = new ClienteDAO();
			List<Cliente> clientes = miCliente.buscarTodos();
			List<ClienteVO> clientesVO = new ArrayList<ClienteVO>();
			for (int i=0;i<clientes.size();i++)
			{
				ClienteVO c = this.clienteToVO(clientes.get(i));
				clientesVO.add(c);
			}
			return clientesVO;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ClienteVO dameCliente(int CUIL) {
		try {
			ClienteDAO miCliente = new ClienteDAO();
			Cliente ch = miCliente.buscarPorId(CUIL);
			if (ch==null)
				return null;
			ClienteVO c = this.clienteToVO(ch);
			return c;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ClienteVO clienteToVO(Cliente cliente) {
		ClienteVO clienteVO = new ClienteVO();
		clienteVO.setCUILCliente(cliente.getcUILCliente());
		clienteVO.setRazonSocial(cliente.getRazonSocial());
		clienteVO.setDireccion(cliente.getDireccion());
		clienteVO.setTelefono(cliente.getTelefono());
		clienteVO.setEstado(cliente.getEstado());
		clienteVO.setODV(OficinaVentasServicio.getInstancia().popular(cliente.getOficinaVentas()));
		return clienteVO;
	}
	
	public Cliente popular(ClienteVO clienteVO){
		Cliente cliente = new Cliente();
		cliente.setcUILCliente(clienteVO.getCUILCliente());
		cliente.setRazonSocial(clienteVO.getRazonSocial());
		cliente.setDireccion(clienteVO.getDireccion());
		cliente.setTelefono(clienteVO.getTelefono());
		cliente.setEstado(clienteVO.isEstado());
		cliente.setOficinaVentas(OficinaVentasServicio.getInstancia().convertir(clienteVO.getODV()));
		return cliente;
	}
	
	public void persist(Cliente entity) {
		clienteDao.openCurrentSessionwithTransaction();
		clienteDao.persistir(entity);
		clienteDao.agregarOficina(entity);
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

	public List<Cliente> buscarTodos() {
		clienteDao.openCurrentSession();
		List<Cliente> clientes = clienteDao.buscarTodos();
		clienteDao.closeCurrentSession();
		return clientes;
	}

	public void borrarTodos() {
		clienteDao.openCurrentSessionwithTransaction();
		clienteDao.borrarTodos();
		clienteDao.closeCurrentSessionwithTransaction();
	}

}
