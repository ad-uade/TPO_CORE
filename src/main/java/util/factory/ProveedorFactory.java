/**
 * 
 */
package util.factory;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.group7.entity.Proveedor;

/**
 * @author huicha
 *
 */
public class ProveedorFactory implements AbstractProveedorFactory {

	/* (non-Javadoc)
	 * @see util.factory.AbstractProveedorFactory#crearMuchosProveedores()
	 */
	@Override
	public List<Proveedor> crearMuchosProveedores() throws Exception {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse("src/main/resources/Proveedores.xml");

		doc.getDocumentElement().normalize();
		System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

		NodeList nList = doc.getElementsByTagName("Proveedor");
		System.out.println("----------------------------");
		List<Proveedor> listadoProveedores = new ArrayList<Proveedor>(); 
		for (int temp = 0; temp < nList.getLength(); temp++) {

			Node nNode = nList.item(temp);
			System.out.println("\nCurrent Element :" + nNode.getNodeName());

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				Proveedor cliente = new Proveedor();
				System.out.println("Cuil: " + eElement.getAttribute("cuil"));
				cliente.setCuilProveedor(Long.valueOf(eElement.getAttribute("cuil")));
				
				System.out.println("Razon Social:" + eElement.getElementsByTagName("RazonSocial").item(0).getTextContent());
				cliente.setRazonSocial(eElement.getElementsByTagName("RazonSocial").item(0).getTextContent());
				
				System.out.println("Direccion:" + eElement.getElementsByTagName("Direccion").item(0).getTextContent());
				cliente.setDireccion(eElement.getElementsByTagName("Direccion").item(0).getTextContent());
				
				System.out.println("Telefono:" + eElement.getElementsByTagName("Telefono").item(0).getTextContent());
				cliente.setTelefono(eElement.getElementsByTagName("Telefono").item(0).getTextContent());
				
				listadoProveedores.add(cliente);
			}
			
		}
		return listadoProveedores;
	}

}
