/**
 * 
 */
package util.factory;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.group7.entity.CasaCentral;

/**
 * @author huicha
 *
 */
public class CasaCentralFactory implements AbstractCasaCentralFactory {

	/* (non-Javadoc)
	 * @see util.factory.AbstractCasaCentralFactory#crearUnicaCasaCentral()
	 */
	@Override
	public CasaCentral crearUnicaCasaCentral() throws Exception {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse("src/main/resources/CasaCentral.xml");

		doc.getDocumentElement().normalize();
		System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

		System.out.println("----------------------------");

		NodeList nList = doc.getElementsByTagName("CasaCentral");
		System.out.println("----------------------------");
		CasaCentral casaCentral = new CasaCentral(); 
		for (int temp = 0; temp < nList.getLength(); temp++) {

			Node nNode = nList.item(temp);
			System.out.println("\nCurrent Element :" + nNode.getNodeName());

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				System.out.println("CASA CENTRAL ID: " + eElement.getAttribute("idCasaCentral"));
				casaCentral.setIdCasaCentral(Integer.valueOf(eElement.getAttribute("idCasaCentral")));
				System.out.println("PorcentajeGanancia: %" + eElement.getElementsByTagName("porcentajeGanancia").item(0).getTextContent());
				casaCentral.setPorcentajeGanancia(Float.parseFloat(eElement.getElementsByTagName("porcentajeGanancia").item(0).getTextContent()));
			}
			
		}
		return casaCentral;
	}

}
