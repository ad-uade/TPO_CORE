/**
 * 
 */
package util.inicializadores;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.group7.entity.ListaPrecios;
import com.group7.entity.Proveedor;
import com.group7.entity.Rodamiento;
import com.group7.entity.enbeddable.RodamientoId;
import com.group7.service.ListaPreciosServicio;
import com.group7.service.ProveedorServicio;
import com.group7.service.RodamientoServicio;

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

		NodeList labTestList = doc.getElementsByTagName("ListaPrecio");
		for (int i = 0; i < labTestList.getLength(); ++i) {
			Element labTest = (Element) labTestList.item(i);

			String cuilProveedor = labTest.getElementsByTagName("CUILProveedor").item(0).getTextContent();
			Long cuil = Long.valueOf(cuilProveedor);
			Proveedor proveedor = ProveedorServicio.getInstancia().buscarProveedor(cuil);
			if (proveedor == null) {
				System.out.println("Proveedor no existe");
			} else {
				ListaPrecios listaPrecio = new ListaPrecios();
				System.out.println("Fecha Publicacion: "
						+ labTest.getElementsByTagName("FechaPublicacion").item(0).getTextContent());
				listaPrecio.setFechaPublicacion(
						Date.valueOf(labTest.getElementsByTagName("FechaPublicacion").item(0).getTextContent()));

				System.out.println("Vigencia:" + labTest.getElementsByTagName("Vigencia").item(0).getTextContent());
				Integer vigencia = Integer.valueOf(labTest.getElementsByTagName("Vigencia").item(0).getTextContent());
				listaPrecio.setVigencia(vigencia);

				Float descuento = Float.valueOf(labTest.getElementsByTagName("Descuento").item(0).getTextContent());
				System.out.println("Descuento para todos los productos:" + descuento);
				listaPrecio.setProveedor(proveedor);

				NodeList valueList = labTest.getElementsByTagName("item");
				for (int j = 0; j < valueList.getLength(); ++j) {
					System.out.println("Cantidad de items: " + valueList.getLength());
					Element value = (Element) valueList.item(j);
					Float precioVenta = Float.valueOf(XMLUtil.getValue("PrecioVenta", value));
					System.out.println("PrecioVenta: " + precioVenta);
					NodeList rodamientoList = value.getElementsByTagName("Rodamiento");
					for (int k = 0; k < rodamientoList.getLength(); k++) {
						Element element = (Element) rodamientoList.item(k);
						String codigosfk = XMLUtil.getValue("CodigoPais", element);
						String codigopieza = XMLUtil.getValue("CodigoPieza", element);
						Rodamiento rodamiento = RodamientoServicio.getInstancia().buscarPorId(codigosfk, codigopieza);
						if (rodamiento == null) {
							String descripcion = XMLUtil.getValue("Descripcion", element);
							String paisorigen = XMLUtil.getValue("PaisOrigen", element);
							String marca = XMLUtil.getValue("Marca", element);
							rodamiento = new Rodamiento();
							rodamiento.setDescripcion(descripcion);
							rodamiento.setPaisOrigen(paisorigen);
							rodamiento.setMarca(marca);
							RodamientoId rodamientoId = new RodamientoId();
							rodamientoId.setCodigoPieza(codigopieza);
							rodamientoId.setCodigoSFK(codigosfk);
							rodamiento.setRodamientoId(rodamientoId);
						}
						listaPrecio.agregarItem(rodamiento, precioVenta, descuento);
					}

				}
				
				ListaPreciosServicio.getInstancia().persistir(listaPrecio);
				System.out.println("------------- ------------- ----------------");
				lista.add(listaPrecio);
			}

		}

		return lista;
	}

}
