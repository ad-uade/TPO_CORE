package com.app;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

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
			Naming.rebind("TPO_Rodamientos2", adminCPR);
			Naming.rebind("TPO_Rodamientos", adminODV);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

}