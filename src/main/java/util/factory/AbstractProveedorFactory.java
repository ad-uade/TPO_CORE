/**
 * 
 */
package util.factory;

import java.util.List;

import com.group7.entity.Proveedor;

/**
 * @author huicha
 *
 */
public interface AbstractProveedorFactory {

	public List<Proveedor> crearMuchosProveedores() throws Exception;
	
}
