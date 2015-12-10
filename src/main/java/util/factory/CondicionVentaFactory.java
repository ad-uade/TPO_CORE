/**
 * 
 */
package util.factory;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.group7.entity.CondicionVenta;
import com.group7.entity.Contado;
import com.group7.entity.CuentaCorriente;
import com.group7.entity.FormaPago;

/**
 * @author huicha
 *
 */
public class CondicionVentaFactory implements AbstractCondicionVentaFactory {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * util.factory.AbstractCondicionVentaFactory#crearMuchasCondicionesDeVenta(
	 * )
	 */
	@Override
	public List<CondicionVenta> crearMuchasCondicionesDeVenta() throws Exception {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse("src/main/resources/CondicionVenta.xml");

		doc.getDocumentElement().normalize();
		System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

		List<CondicionVenta> listadoCondicionVenta = new ArrayList<CondicionVenta>();

		NodeList labTestList = doc.getElementsByTagName("CondicionVenta");
		for (int i = 0; i < labTestList.getLength(); ++i) {
			Element labTest = (Element) labTestList.item(i);
			CondicionVenta condicionVenta = new CondicionVenta();
			System.out.println("Fecha Desde: " + labTest.getElementsByTagName("FechaDesde").item(0).getTextContent());

			condicionVenta
					.setFechaDesde(Date.valueOf(labTest.getElementsByTagName("FechaDesde").item(0).getTextContent()));

			System.out.println("Fecha Hasta:" + labTest.getElementsByTagName("FechaHasta").item(0).getTextContent());
			condicionVenta
					.setFechaHasta(Date.valueOf(labTest.getElementsByTagName("FechaHasta").item(0).getTextContent()));

			System.out.println("IVA:" + labTest.getElementsByTagName("IVA").item(0).getTextContent());
			condicionVenta.setIva(Float.valueOf(labTest.getElementsByTagName("IVA").item(0).getTextContent()));

			NodeList valueList = labTest.getElementsByTagName("FormaPago");
			for (int j = 0; j < valueList.getLength(); ++j) {
				Element value = (Element) valueList.item(j);
				String valueType = value.getAttribute("tipo");
				System.out.println("FormaDePago Tipo: " + valueType);
				FormaPago formaPago = null;
				switch (valueType) {
				case "CO":
					String descuento = labTest.getElementsByTagName("Descuento").item(0).getTextContent();
					System.out.println("Descuento:" + descuento);
					formaPago = new Contado();
					((Contado)formaPago).setDescuento(Float.parseFloat(descuento));
					break;
				case "CC":
					String recargo = labTest.getElementsByTagName("Recargo").item(0).getTextContent();
					System.out.println("Recargo: " + recargo);
					formaPago = new CuentaCorriente();
					((CuentaCorriente)formaPago).setRecargo(Float.parseFloat(recargo));
					String dias = labTest.getElementsByTagName("Dias").item(0).getTextContent();
					System.out.println("Dias: " + dias);
					((CuentaCorriente)formaPago).setRecargo(Float.parseFloat(dias));
					break;
				default:
					formaPago = null;
					break;
				}
				condicionVenta.setFormaPago(formaPago);
			}
			listadoCondicionVenta.add(condicionVenta);
		}
		return listadoCondicionVenta;
	}

}
