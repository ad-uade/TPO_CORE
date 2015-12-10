package com.app;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import com.group7.entity.CasaCentral;
import com.group7.service.CasaCentralServicio;

import util.factory.AbstractCasaCentralFactory;
import util.factory.CasaCentralFactory;
import util.inicializadores.InitCliente;
import util.inicializadores.InitCondicionVenta;
import util.inicializadores.InitProveedor;
import util.inicializadores.InitRodamiento;

/**
 * 
 * @author huicha
 *
 */
public class Servidor {

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
				
			}
			System.out.println("Se levanto la CC");
			System.out.println("CASA CENTRAL ID: " + casaCentral.getIdCasaCentral() + " PORCENTAJE DE GANANCIA: %" + casaCentral.getPorcentajeGanancia());
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
}
