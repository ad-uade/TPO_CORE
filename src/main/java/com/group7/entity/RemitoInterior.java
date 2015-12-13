/**
 * 
 */
package com.group7.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author huicha
 *
 */
@Entity
@DiscriminatorValue("RI")
public class RemitoInterior extends Remito {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5278856824377466227L;

	public Remito getView() {
		return null;
	}

}
