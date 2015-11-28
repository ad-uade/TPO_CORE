/**
 * 
 */
package util.factory;

import java.util.List;

import com.group7.entity.Cliente;

/**
 * @author huicha
 *
 */
public interface AbstractClienteFactory {

	public List<Cliente> crearMuchosCliente() throws Exception;
	
}
