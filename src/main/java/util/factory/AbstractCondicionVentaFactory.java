/**
 * 
 */
package util.factory;

import java.util.List;

import com.group7.entity.CondicionVenta;

/**
 * @author huicha
 *
 */
public interface AbstractCondicionVentaFactory {

	public List<CondicionVenta> crearMuchasCondicionesDeVenta() throws Exception;
	
}
