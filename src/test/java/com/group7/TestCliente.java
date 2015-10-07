/**
 * 
 */
package com.group7;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.group7.entity.Cliente;
import com.group7.service.ClienteServicio;

/**
 * @author huicha
 *
 */
public class TestCliente {

	private static final String RAZON_SOCIAL = "Maguito";
	private static final String DIRECCION = "congreso tucuman 2227";
	private static final String TELEFONO = "1135800037";
	private static final int CUIL = 1179;

	@Test
	public void createClient() {
		Cliente cliente = new Cliente();
		cliente.setTelefono(TELEFONO);
		cliente.setCuilCliente(CUIL);
		cliente.setDireccion(DIRECCION);
		cliente.setEstado(true);
		cliente.setRazonSocial(RAZON_SOCIAL);
		
		ClienteServicio.getInstancia().persist(cliente);
		Cliente clientePersistido = ClienteServicio.getInstancia().buscarClientePorCuil(CUIL);
		assertEquals(cliente.getRazonSocial(), clientePersistido.getRazonSocial());
	}

}
