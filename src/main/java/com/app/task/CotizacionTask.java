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
public class CotizacionTask extends TimerTask{

	public CotizacionTask() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void run() {
		System.out.println(Calendar.getInstance().getTime() + " : Hi I'm CotizacionTask see you after 12 seconds");
	}

}
