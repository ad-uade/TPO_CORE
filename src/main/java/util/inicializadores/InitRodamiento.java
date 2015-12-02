/**
 * 
 */
package util.inicializadores;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.group7.entity.Rodamiento;
import com.group7.service.RodamientoServicio;

/**
 * @author huicha
 *
 */
public class InitRodamiento implements Inicializador<List<Rodamiento>> {

	@Override
	public List<Rodamiento> init() throws Exception {
		String codigosfk = "";
		String codigopieza = "";
		String descripcion = "";
		String paisorigen = "";
		String marca = "";
		boolean estado = true;
		
		File rodamientos = new File("src/main/resources/Rodamiento.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(rodamientos);
		doc.getDocumentElement().normalize();

		NodeList nodes = doc.getElementsByTagName("Rodamiento");
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				codigosfk = getValue("CodigoPais", element);
				codigopieza = getValue("CodigoPieza", element);
				descripcion = getValue("Descripcion", element);
				paisorigen = getValue("PaisOrigen", element);
				marca = getValue("Marca", element);
				RodamientoServicio.getInstancia().guardarRodamiento(codigosfk, codigopieza, descripcion, paisorigen, marca, estado);
			}
		}
		
		return RodamientoServicio.getInstancia().buscarTodos();
	}
	
	private static String getValue(String tag, Element element) {
		NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodes.item(0);
		return node.getNodeValue();
	}

}
