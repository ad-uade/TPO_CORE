/**
 * 
 */
package test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.group7.entity.CasaCentral;
import com.group7.entity.Proveedor;
import com.group7.service.CasaCentralServicio;
import com.group7.service.ProveedorServicio;

import util.factory.AbstractCasaCentralFactory;
import util.factory.AbstractProveedorFactory;
import util.factory.CasaCentralFactory;
import util.factory.ProveedorFactory;

/**
 * @author huicha
 *
 */
public class ProveedorTest {

	@Test
	public void crearNuevoProveedor() throws Exception {
		
		AbstractCasaCentralFactory abstractCasaCentralFactory = new CasaCentralFactory();
		CasaCentral cc = abstractCasaCentralFactory.crearUnicaCasaCentral();
		
		AbstractProveedorFactory factory = new ProveedorFactory();
		List<Proveedor> listadoProovedores = factory.crearMuchosProveedores();
		
		ProveedorServicio.getInstancia().persistirTodos(listadoProovedores);
		cc.setProveedores(listadoProovedores);
		
		CasaCentralServicio.getInstancia().actualizar(cc);
		
		for (Proveedor proveedor : listadoProovedores){
			Proveedor clientePersistido = ProveedorServicio.getInstancia().buscarProveedor(proveedor.getCuilProveedor());
			assertTrue(clientePersistido.getCuilProveedor().equals((proveedor.getCuilProveedor())));
		}
		
	}
	
}
