package test;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Random;

import org.junit.Test;

import com.group7.entity.CasaCentral;
import com.group7.entity.Cliente;
import com.group7.entity.OficinaVenta;
import com.group7.service.ClienteServicio;
import com.group7.service.OficinaVentasServicio;

import util.factory.AbstractCasaCentralFactory;
import util.factory.AbstractClienteFactory;
import util.factory.AbstractOficinaVentaFactory;
import util.factory.CasaCentralFactory;
import util.factory.ClienteFactory;
import util.factory.OficinaVentaFactory;

public class ClienteTest {

	@Test
	public void crearNuevoCliente() throws Exception {
		
		AbstractCasaCentralFactory abstractCasaCentralFactory = new CasaCentralFactory();
		CasaCentral cc = abstractCasaCentralFactory.crearUnicaCasaCentral();
		AbstractOficinaVentaFactory oficinaVentaFactory = new OficinaVentaFactory();
		List<OficinaVenta> listOficinaVentas = oficinaVentaFactory.crearMuchasOficinasDeVenta();
		for (OficinaVenta oficinaVentas : listOficinaVentas){
			oficinaVentas.setCasa(cc);
		}
		OficinaVentasServicio.getInstancia().persistirTodasLasOficinas(listOficinaVentas);
		
		AbstractClienteFactory factory = new ClienteFactory();
		List<Cliente> listadoClientes = factory.crearMuchosCliente();
		for (Cliente cliente : listadoClientes){
			Random r = new Random();
			int valorDado = r.nextInt(listOficinaVentas.size());
			OficinaVenta oficinaVentas = listOficinaVentas.get(valorDado);
			cliente.setOficinaVentas(oficinaVentas);
		}
		ClienteServicio.getInstancia().persistirTodos(listadoClientes);
		for (Cliente cliente : listadoClientes){
			Cliente clientePersistido = ClienteServicio.getInstancia().buscarClientePorCuil(cliente.getCuilCliente());
			assertTrue(clientePersistido.getCuilCliente().equals((cliente.getCuilCliente())));
		}
		
	}

}
