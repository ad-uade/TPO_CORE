/**
 * 
 */
package test;

import java.util.List;

import org.junit.Test;

import com.group7.entity.CasaCentral;
import com.group7.entity.OficinaVenta;
import com.group7.service.OficinaVentasServicio;

import util.factory.AbstractCasaCentralFactory;
import util.factory.AbstractOficinaVentaFactory;
import util.factory.CasaCentralFactory;
import util.factory.OficinaVentaFactory;

/**
 * @author huicha
 *
 */
public class OficinaVentaTest {

	@Test
	public void test() throws Exception {
		AbstractCasaCentralFactory abstractCasaCentralFactory = new CasaCentralFactory();
		CasaCentral cc = abstractCasaCentralFactory.crearUnicaCasaCentral();
		AbstractOficinaVentaFactory factory = new OficinaVentaFactory();
		List<OficinaVenta> listOficinaVentas = factory.crearMuchasOficinasDeVenta();
		for (OficinaVenta oficinaVentas : listOficinaVentas){
			oficinaVentas.setCasa(cc);
		}
		OficinaVentasServicio.getInstancia().persistirTodasLasOficinas(listOficinaVentas);
	}

}
