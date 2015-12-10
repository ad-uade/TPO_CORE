/**
 * 
 */
package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import com.group7.entity.Cliente;
import com.group7.entity.CondicionVenta;
import com.group7.entity.ItemSolicitudCotizacion;
import com.group7.entity.Rodamiento;
import com.group7.entity.SolicitudCotizacion;
import com.group7.entity.enbeddable.ItemSolicitudCotizacionId;
import com.group7.service.ClienteServicio;
import com.group7.service.CondicionVentaServicio;
import com.group7.service.RodamientoServicio;
import com.group7.service.SolicitudCotizacionServicio;

/**
 * @author huicha
 *
 */
public class SolicitudCotizacionTest {

	public void init(){
		// Inicializo todo lo que me hace falta para el test
		
	}
	
	@Test
	public void test() {
		// Busco todos los clientes
		List<Cliente> listadoClientes = ClienteServicio.getInstancia().buscarTodos();
		// Me quedo con el primero
		Cliente cliente = listadoClientes.get(0);
		// Genero una Solicitud de Cotizacion para el cliente
		SolicitudCotizacion solicitudCotizacion = new SolicitudCotizacion();
		solicitudCotizacion.setCliente(cliente);
		solicitudCotizacion.setOficinaVenta(cliente.getOficinaVentas());
		// Preparo una cotizacion
		List<ItemSolicitudCotizacion> listadoItemSolicitudCotizacion = new ArrayList<ItemSolicitudCotizacion>();
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
			ItemSolicitudCotizacion itemSolicitudCotizacion = new ItemSolicitudCotizacion();	
			itemSolicitudCotizacion.setCantidad(cantidadRandom);
			itemSolicitudCotizacion.setCondicion(condicion);
			ItemSolicitudCotizacionId id = new ItemSolicitudCotizacionId();
			id.setRodamiento(rodamiento);
			itemSolicitudCotizacion.setId(id);
			listadoItemSolicitudCotizacion.add(itemSolicitudCotizacion);
		}
		solicitudCotizacion.setItems(listadoItemSolicitudCotizacion);
		
		SolicitudCotizacionServicio.getInstancia().generarSolicitud(solicitudCotizacion);
	}

}
