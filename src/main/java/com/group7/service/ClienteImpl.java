package com.group7.service;

import java.util.ArrayList;
import java.util.List;

import com.group7.business.ClienteVO;
import com.group7.business.OficinaVentasVO;
import com.group7.dao.ClienteDAO;
import com.group7.entity.Cliente;

public class ClienteImpl {

	private static ClienteImpl instancia;
	
	public static ClienteImpl getInstancia(){
		if(instancia == null)
			instancia = new ClienteImpl();
		return instancia;
	}
	
	public ClienteVO clienteToVO(Cliente cliente) {
		ClienteVO clienteVO = new ClienteVO();
		clienteVO.setCUILCliente(cliente.getcUILCliente());
		clienteVO.setRazonSocial(cliente.getRazonSocial());
		clienteVO.setDireccion(cliente.getDireccion());
		clienteVO.setTelefono(cliente.getTelefono());
		clienteVO.setEstado(cliente.getEstado());
		clienteVO.setODV(OficinaVentasImpl.getInstancia().HibernateAVo(cliente.getOficinaVentas()));
		return clienteVO;
	}
	
	public Cliente clienteVOtoCliente(ClienteVO cliente){
		Cliente clienteH = new Cliente();
		clienteH.setcUILCliente(cliente.getCUILCliente());
		clienteH.setRazonSocial(cliente.getRazonSocial());
		clienteH.setDireccion(cliente.getDireccion());
		clienteH.setTelefono(cliente.getTelefono());
		clienteH.setEstado(cliente.isEstado());
		clienteH.setOficinaVentas(OficinaVentasImpl.getInstancia().oficinaVOaH(cliente.getODV()));
		return clienteH;
	}

	public void guardarCliente(String razonSocial, int CUIL, String direccion, String telefono, OficinaVentasVO of) {
		Cliente cliente = new Cliente();
		ClienteDAO miClienteDAO = new ClienteDAO();
		
		cliente.setRazonSocial(razonSocial);
		cliente.setcUILCliente(CUIL);
		cliente.setDireccion(direccion);
		cliente.setTelefono(telefono);
		cliente.setEstado(true);
		cliente.setOficinaVentas(OficinaVentasImpl.getInstancia().oficinaVOaH(of));
		miClienteDAO.altaCliente(cliente);
		miClienteDAO.agregarOficina(cliente);
	}
	
	public void modificarCliente(ClienteVO cliente) {
		Cliente miCliente = this.clienteVOtoCliente(cliente);
		ClienteDAO miClienteDAO = new ClienteDAO();
		miClienteDAO.actualizarCliente(miCliente);
	}

	public void baja(int CUIL) {
		ClienteDAO miClienteDAO = new ClienteDAO();
		miClienteDAO.bajaCliente(CUIL);
	}

	public List<ClienteVO> dameClientes() {
		try {
			ClienteDAO miCliente = new ClienteDAO();
			List<Cliente> clientes = miCliente.getClientes();
			List<ClienteVO> clientesVO = new ArrayList<ClienteVO>();
			
			for (Cliente c : clientes){
				ClienteVO cvo = this.clienteToVO(c);
				clientesVO.add(cvo);
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
			Cliente ch = miCliente.getCliente(CUIL);
			if (ch==null)
				return null;
			ClienteVO c = this.clienteToVO(ch);
			return c;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
