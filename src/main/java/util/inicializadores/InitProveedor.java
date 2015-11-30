/**
 * 
 */
package util.inicializadores;

import java.util.List;

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
public class InitProveedor implements Inicializador<List<Proveedor>> {

	@Override
	public List<Proveedor> init() throws Exception {
		AbstractCasaCentralFactory abstractCasaCentralFactory = new CasaCentralFactory();
		CasaCentral cc = abstractCasaCentralFactory.crearUnicaCasaCentral();
		
		AbstractProveedorFactory factory = new ProveedorFactory();
		List<Proveedor> listadoProovedores = factory.crearMuchosProveedores();
		
		ProveedorServicio.getInstancia().persistirTodos(listadoProovedores);
		cc.setProveedores(listadoProovedores);
		
		CasaCentralServicio.getInstancia().actualizar(cc);
		return listadoProovedores;
	}

}
