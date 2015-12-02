/**
 * 
 */
package util.inicializadores;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.group7.business.ProveedorVO;
import com.group7.entity.ListaPrecios;
import com.group7.service.ListaPreciosServicio;
import com.group7.service.ProveedorServicio;

import util.XMLUtil;

/**
 * @author huicha
 *
 */
public class InitListaPrecio implements Inicializador<List<ListaPrecios>> {

	@Override
	public List<ListaPrecios> init() throws Exception {
		File rodamientos = new File("src/main/resources/ListaPrecio.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(rodamientos);
		doc.getDocumentElement().normalize();
		List<ListaPrecios> lista = new ArrayList<ListaPrecios>();
		NodeList nodes = doc.getElementsByTagName("ListaPrecio");
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				String cuilProveedor = XMLUtil.getValue("CUILProveedor", element);
				ProveedorVO proveedor = ProveedorServicio.getInstancia().obtenerProveedor(Long.getLong(cuilProveedor));
				String tipo = XMLUtil.getValue("Tipo", element);
				String fechaPublicacion = XMLUtil.getValue("FechaPublicacion", element);
				String vigencia = XMLUtil.getValue("Vigencia", element);
				Float descuento = Float.parseFloat((String) XMLUtil.getValue("Descuento", element));
				//ListaPreciosServicio.getInstancia().armarListaDePrecios(proveedor, items, precios, tipo, vigencia, descuento);
			}
		}
		return lista;
	}
	
}
