/**
 * 
 */
package util.factory;

import java.util.List;

import com.group7.entity.OficinaVenta;

/**
 * @author huicha
 *
 */
public interface AbstractOficinaVentaFactory {

	public List<OficinaVenta> crearMuchasOficinasDeVenta() throws Exception;
	
}
