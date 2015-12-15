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
public class ProveedorTask extends TimerTask{

	public ProveedorTask() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void run() {
		System.out.println(Calendar.getInstance().getTime() + " : Hi I'm ProveedorTask see you after 12 seconds");
	}

}
