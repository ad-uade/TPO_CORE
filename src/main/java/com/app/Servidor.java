package com.app;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.Timer;

import com.app.task.CotizacionesAprobadasTask;
import com.app.task.SolicitudCotizacionTask;
import com.group7.XML.CotizacionXML;
import com.group7.dao.SolicitudCotizacionDAO;
import com.group7.entity.CasaCentral;
import com.group7.entity.Cliente;
import com.group7.entity.Rodamiento;
import com.group7.entity.SolicitudCotizacion;
import com.group7.service.CasaCentralServicio;
import com.group7.service.ClienteServicio;
import com.group7.service.RodamientoServicio;

import util.factory.AbstractCasaCentralFactory;
import util.factory.CasaCentralFactory;
import util.inicializadores.InitCliente;
import util.inicializadores.InitCondicionVenta;
import util.inicializadores.InitListaPrecio;
import util.inicializadores.InitProveedor;
import util.inicializadores.InitRodamiento;
import util.inicializadores.InitSQL;

/**
 * 
 * @author huicha
 *
 */
public class Servidor {

	public static void crearSolicitudes(){
		// Busco todos los clientes
				List<Cliente> listadoClientes = ClienteServicio.getInstancia().buscarTodos();
				// Me quedo con el primero
			for (Cliente cliente : listadoClientes){
				
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
				
				// Hago una cantidad random entre 10
				int cantidadRandom = r.nextInt(10);
				while (cantidadRandom == 0){
					cantidadRandom = r.nextInt(10);
				}
				
				for (Rodamiento rodamiento : subListadoRodamiento){
					solicitudCotizacion.add(rodamiento, cantidadRandom);	
				}
				SolicitudCotizacionDAO solicitudCotizacionDAO = new SolicitudCotizacionDAO();
				solicitudCotizacionDAO.openCurrentSessionwithTransaction();
				solicitudCotizacionDAO.persistir(solicitudCotizacion);
				solicitudCotizacionDAO.closeCurrentSessionwithTransaction();	
				
				System.out.println(Integer.toString(solicitudCotizacion.getNroSolicitudCotizacion()));
				CotizacionXML cotizacionXML = new CotizacionXML();
				cotizacionXML.solicitudCotizacionXML(solicitudCotizacion);
			}
	}
	
	public static void cron(){
		Timer t = new Timer();
		SolicitudCotizacionTask solicitudCotizacionTask = new SolicitudCotizacionTask();
	    // This task is scheduled to run every 20 seconds
	    t.scheduleAtFixedRate(solicitudCotizacionTask, 0, 20000);
	    
	    CotizacionesAprobadasTask cotizacionesAprobadasTask = new CotizacionesAprobadasTask();
	    // This task is scheduled to run every 15 seconds
	    t.scheduleAtFixedRate(cotizacionesAprobadasTask, 0, 15000);
	}
	
	public static void main(String[] args) throws Exception {
		AdministracionCPR adminCPR = new AdministracionCPR();
		AdministracionODV adminODV = new AdministracionODV();
		LocateRegistry.createRegistry(1099);
		try {
			System.out.println("Servicio: AdministracionCPR");
			Naming.rebind("AdministracionCPR", adminCPR);
			System.out.println("Servicio: AdministracionODV");
			Naming.rebind("AdministracionODV", adminODV);
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
				
				InitListaPrecio initListaPrecio = new InitListaPrecio();
				initListaPrecio.init();
				
				InitSQL init = new InitSQL();
				init.execute();
				// Correr las solicitudes de Cotizacion 
				crearSolicitudes();
			}
			System.out.println("Se levanto la CC");
			System.out.println("CASA CENTRAL ID: " + casaCentral.getIdCasaCentral() + " PORCENTAJE DE GANANCIA: %" + casaCentral.getPorcentajeGanancia());
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		// up cron
		cron();
	}
}
