/**
 * 
 */
package com.app.task;

import java.io.File;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.List;
import java.util.TimerTask;

import com.app.AdministracionODV;
import com.group7.XML.CotizacionXML;
import com.group7.dao.OficinaVentasDAO;
import com.group7.entity.Cotizacion;
import com.group7.entity.OficinaVenta;
import com.group7.entity.SolicitudCotizacion;

/**
 * @author huicha
 *
 */
public class CotizacionesAprobadasTask extends TimerTask{

	public void run() {
		OficinaVentasDAO oficinaVentasDAO = new OficinaVentasDAO();
		oficinaVentasDAO.openCurrentSession();
		List<OficinaVenta> listado = oficinaVentasDAO.buscarTodos();
		oficinaVentasDAO.closeCurrentSession();
		System.out.println(Calendar.getInstance().getTime() + " : Hi I'm SolicitudCotizacionTask see you after 20 seconds");
		for (OficinaVenta oficinaVenta : listado) {
			System.out.println(Calendar.getInstance().getTime() + " : Proceso la oficina de venta ("+oficinaVenta.getSucursal()+")");
			File[] files = CotizacionXML.filesCotizacionAceptadasXML(oficinaVenta);
			if (files != null){
				System.out.println(Calendar.getInstance().getTime() + " : Voy a procesar cantidad de arvhivos ("+files.length+")");
				for (File file : files) {
					Cotizacion cotizacion = CotizacionXML.leerCotizacionXML(file);
					CotizacionXML.borrarCotizacionXML(cotizacion);
//					try {
//						Poner lo que genera la orden de compra;
//					} catch (RemoteException e) {
//						System.out.println(Calendar.getInstance().getTime() + " : ME ROMPI");
//					}
				}
			}
		}

	}


}
