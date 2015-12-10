/**
 * 
 */
package util.inicializadores;

import java.util.List;

import com.group7.entity.CondicionVenta;
import com.group7.service.CondicionVentaServicio;

import util.factory.AbstractCondicionVentaFactory;
import util.factory.CondicionVentaFactory;

/**
 * @author huicha
 *
 */
public class InitCondicionVenta implements Inicializador<List<CondicionVenta>>{

	@Override
	public List<CondicionVenta> init() throws Exception {
		AbstractCondicionVentaFactory factory = new CondicionVentaFactory();
		List<CondicionVenta> listadoOficinaVentas = factory.crearMuchasCondicionesDeVenta();
		CondicionVentaServicio.getInstancia().persistirTodos(listadoOficinaVentas);
		return listadoOficinaVentas;
	}

}
