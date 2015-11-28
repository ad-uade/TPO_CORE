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

import com.group7.entity.Cliente;

/**
 * @author huicha
 *
 */
public class ClienteFactory implements AbstractClienteFactory {

	@Override
	public List<Cliente> crearMuchosCliente() throws Exception {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse("src/main/resources/Clientes.xml");

		doc.getDocumentElement().normalize();
		System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

		NodeList nList = doc.getElementsByTagName("Cliente");
		System.out.println("----------------------------");
		List<Cliente> listadoClientes = new ArrayList<Cliente>(); 
		for (int temp = 0; temp < nList.getLength(); temp++) {

			Node nNode = nList.item(temp);
			System.out.println("\nCurrent Element :" + nNode.getNodeName());

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				Cliente cliente = new Cliente();
				System.out.println("Cuil: " + eElement.getAttribute("cuil"));
				cliente.setCuilCliente(Long.valueOf(eElement.getAttribute("cuil")));
				
				System.out.println("Razon Social:" + eElement.getElementsByTagName("RazonSocial").item(0).getTextContent());
				cliente.setRazonSocial(eElement.getElementsByTagName("RazonSocial").item(0).getTextContent());
				
				System.out.println("Direccion:" + eElement.getElementsByTagName("Direccion").item(0).getTextContent());
				cliente.setDireccion(eElement.getElementsByTagName("Direccion").item(0).getTextContent());
				
				System.out.println("Telefono:" + eElement.getElementsByTagName("Telefono").item(0).getTextContent());
				cliente.setTelefono(eElement.getElementsByTagName("Telefono").item(0).getTextContent());
				
				listadoClientes.add(cliente);
			}
			
		}
		return listadoClientes;
	}

}
