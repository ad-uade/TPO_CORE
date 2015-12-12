package com.app;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.Timer;

import com.app.task.CotizacionTask;
import com.app.task.CotizacionesAprobadasTask;
import com.app.task.SolicitudCotizacionTask;
import com.group7.entity.CasaCentral;
import com.group7.service.CasaCentralServicio;

import util.factory.AbstractCasaCentralFactory;
import util.factory.CasaCentralFactory;
import util.inicializadores.InitCliente;
import util.inicializadores.InitCondicionVenta;
import util.inicializadores.InitListaPrecio;
import util.inicializadores.InitProveedor;
import util.inicializadores.InitRodamiento;

/**
 * 
 * @author huicha
 *
 */
public class Servidor {

	public static void cron(){
		Timer t = new Timer();
		SolicitudCotizacionTask solicitudCotizacionTask = new SolicitudCotizacionTask();
	    // This task is scheduled to run every 10 seconds
	    t.scheduleAtFixedRate(solicitudCotizacionTask, 0, 10000);
	    
	    CotizacionTask cotizacionTask = new CotizacionTask();
	    // This task is scheduled to run every 12 seconds
	    t.scheduleAtFixedRate(cotizacionTask, 0, 12000);
	    
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
