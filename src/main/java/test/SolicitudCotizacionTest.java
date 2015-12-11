/**
 * 
 */
package test;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import com.group7.entity.CasaCentral;
import com.group7.entity.Cliente;
import com.group7.entity.CondicionVenta;
import com.group7.entity.Rodamiento;
import com.group7.entity.SolicitudCotizacion;
import com.group7.service.CasaCentralServicio;
import com.group7.service.ClienteServicio;
import com.group7.service.CondicionVentaServicio;
import com.group7.service.RodamientoServicio;
import com.group7.service.SolicitudCotizacionServicio;

import util.factory.AbstractCasaCentralFactory;
import util.factory.CasaCentralFactory;
import util.inicializadores.InitCliente;
import util.inicializadores.InitCondicionVenta;
import util.inicializadores.InitProveedor;
import util.inicializadores.InitRodamiento;

/**
 * @author huicha
 *
 */
public class SolicitudCotizacionTest {

	public void init() throws Exception{
		// Inicializo todo lo que me hace falta para el test
		CasaCentral casaCentral = CasaCentralServicio.getInstancia().obtenerCasaCentral();
		if (casaCentral == null){
			AbstractCasaCentralFactory casaCentralFactoria = new CasaCentralFactory();
			CasaCentral unica = casaCentralFactoria.crearUnicaCasaCentral();
			CasaCentralServicio.getInstancia().fabricar(unica);
			casaCentral = unica;
			
			InitCliente initCliente = new InitCliente();
			initCliente.init();
			
			InitProveedor initProveedor = new InitProveedor();
			initProveedor.init();
			
			InitRodamiento initRodamiento = new InitRodamiento();
			initRodamiento.init();
			
			InitCondicionVenta initCondicionVenta = new InitCondicionVenta();
			initCondicionVenta.init();
		}
	}
	
	@Test
	public void test() throws Exception {
		this.init();
		// Busco todos los clientes
		List<Cliente> listadoClientes = ClienteServicio.getInstancia().buscarTodos();
		// Me quedo con el primero
		Cliente cliente = listadoClientes.get(0);
		// Genero una Solicitud de Cotizacion para el cliente
		SolicitudCotizacion solicitudCotizacion = new SolicitudCotizacion();
		solicitudCotizacion.setFecha(Calendar.getInstance().getTime());
		solicitudCotizacion.setCliente(cliente);
		solicitudCotizacion.setOficinaVenta(cliente.getOficinaVentas());
		// Busco todos los rodamientos
		List<Rodamiento> listadoRodamiento = RodamientoServicio.getInstancia().buscarTodos();
		Random r = new Random();
		int valorDado = r.nextInt(listadoRodamiento.size());
		// Recorto una lista para no procesar todos
		List<Rodamiento> subListadoRodamiento = listadoRodamiento.subList(0, valorDado);
		
		// Busco todas las condiciones de Venta
		List<CondicionVenta> listadoCondicionVenta = CondicionVentaServicio.getInstancia().buscarTodas();
		int randomCondicion = r.nextInt(listadoCondicionVenta.size());
		CondicionVenta condicion = listadoCondicionVenta.get(randomCondicion);
		
		// Hago una cantidad random entre 10
		int cantidadRandom = r.nextInt(10);
		while (cantidadRandom == 0){
			cantidadRandom = r.nextInt(10);
		}
		
		for (Rodamiento rodamiento : subListadoRodamiento){
			solicitudCotizacion.add(rodamiento, condicion, cantidadRandom);	
		}
		
		SolicitudCotizacionServicio.getInstancia().generarSolicitud(solicitudCotizacion);
		
	}

}
