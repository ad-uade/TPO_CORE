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

import com.group7.entity.OficinaVentas;

/**
 * @author huicha
 *
 */
public class OficinaVentaFactory implements AbstractOficinaVentaFactory{

	@Override
	public List<OficinaVentas> crearMuchasOficinasDeVenta() throws Exception {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse("src/main/resources/OficinaVenta.xml");

		doc.getDocumentElement().normalize();
		System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

		NodeList nList = doc.getElementsByTagName("OficinaVenta");
		System.out.println("----------------------------");
		List<OficinaVentas> listadoOficinaVenta = new ArrayList<OficinaVentas>(); 
		for (int temp = 0; temp < nList.getLength(); temp++) {

			Node nNode = nList.item(temp);
			System.out.println("\nCurrent Element :" + nNode.getNodeName());

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				OficinaVentas odv = new OficinaVentas();
				System.out.println("Sucursal: " + eElement.getAttribute("sucursal"));
				odv.setSucursal(String.valueOf(eElement.getAttribute("sucursal")));
				listadoOficinaVenta.add(odv);
			}
			
		}
		return listadoOficinaVenta;
	}

}
