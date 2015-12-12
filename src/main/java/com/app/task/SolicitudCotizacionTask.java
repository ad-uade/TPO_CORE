/**
 * 
 */
package com.app.task;

import java.util.Calendar;
import java.util.TimerTask;

/**
 * @author huicha
 *
 */
public class SolicitudCotizacionTask extends TimerTask{

	public SolicitudCotizacionTask() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void run() {
		System.out.println(Calendar.getInstance().getTime() + " : Hi I'm SolicitudCotizacionTask see you after 10 seconds");
	}

}
