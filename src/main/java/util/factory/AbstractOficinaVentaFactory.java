/**
 * 
 */
package util.factory;

import java.util.List;

import com.group7.entity.OficinaVentas;

/**
 * @author huicha
 *
 */
public interface AbstractOficinaVentaFactory {

	public List<OficinaVentas> crearMuchasOficinasDeVenta() throws Exception;
	
}
