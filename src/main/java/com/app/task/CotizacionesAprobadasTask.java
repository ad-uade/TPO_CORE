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
public class CotizacionesAprobadasTask extends TimerTask{

	public CotizacionesAprobadasTask() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void run() {
		System.out.println(Calendar.getInstance().getTime() + " : Hi I'm CotizacionesAprobadasTask see you after 15 seconds");
	}

}
