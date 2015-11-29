/**
 * 
 */
package test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.group7.business.ProveedorVO;
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

		cc.setProveedores(listadoProovedores);
		
		ProveedorServicio.getInstancia().persistirTodos(listadoProovedores);
		
		CasaCentralServicio.getInstancia().actualizar(cc);
		
		for (Proveedor proveedor : listadoProovedores){
			ProveedorVO clientePersistido = ProveedorServicio.getInstancia().obtenerProveedor(proveedor.getCuilProveedor());
			assertTrue(clientePersistido.getCuilProveedor().equals((proveedor.getCuilProveedor())));
		}
		
	}
	
}
