package com.app;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import com.group7.entity.CasaCentral;
import com.group7.service.CasaCentralServicio;

/**
 * 
 * @author huicha
 *
 */
public class Servidor {

	public static void main(String[] args) throws RemoteException {
		AdministracionCPR adminCPR = new AdministracionCPR();
		AdministracionODV adminODV = new AdministracionODV();
		LocateRegistry.createRegistry(1099);
		try {
			Naming.rebind("AdministracionCPR", adminCPR);
			Naming.rebind("AdministracionODV", adminODV);
			CasaCentral casaCentral = CasaCentralServicio.getInstancia().obtenerCasaCentral();
			System.out.println("Se levanto la casa central");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
}
