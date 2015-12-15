package com.group7.XML;

import java.io.File;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.app.AdministracionCPR;
import com.app.AdministracionODV;
import com.group7.entity.Cotizacion;
import com.group7.entity.ItemCotizacion;
import com.group7.entity.OrdenCompra;
import com.group7.entity.Proveedor;
import com.group7.entity.Rodamiento;

public class ProveedorXML {

	public ProveedorXML() {
	}

	public static void GenerarOrdenesCompraXML(Cotizacion cotizacion) {

		for (ItemCotizacion itemCotizacion : cotizacion.getItems()) {
			try {

				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.newDocument();

				Element rootElement = doc.createElement("OrdenCompra");
				doc.appendChild(rootElement);
				Element orden = doc.createElement("orden");
				rootElement.appendChild(orden);
				Attr attr = doc.createAttribute("Proveedor");
				attr.setValue(String.valueOf(itemCotizacion.getItemProveedor()));
				Attr attr1 = doc.createAttribute("fecha");
				attr1.setValue(String.valueOf(cotizacion.getFecha()));
				orden.setAttributeNode(attr);
				orden.setAttributeNode(attr1);

				Element item = doc.createElement("item");
				Attr attrCant = doc.createAttribute("cantidad");
				attrCant.setValue(String.valueOf(itemCotizacion.getCantidad()));
				item.setAttributeNode(attrCant);
				Attr attrSKFRod = doc.createAttribute("CodigoSKF");
				attrSKFRod.setValue(itemCotizacion.getId().getRodamiento().getRodamientoId().getCodigoSFK());
				item.setAttributeNode(attrSKFRod);
				Attr attrPieza = doc.createAttribute("CodigoPieza");
				attrPieza.setValue(itemCotizacion.getId().getRodamiento().getRodamientoId().getCodigoPieza());
				item.setAttributeNode(attrPieza);
				Attr attrCot = doc.createAttribute("nroCotizacion");
				attrCot.setValue(Integer.toString(cotizacion.getId()));
				item.setAttributeNode(attrCot);
				orden.appendChild(item);

				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(
						new File("src/main/java/RepoXML/ordenesCompra/" + itemCotizacion.getItemProveedor()));
				transformer.transform(source, result);

			}

			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static OrdenCompra leerOrdenCompraXML(File file) {
		try {
			final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(false);
			dbf.setValidating(false);
			dbf.setIgnoringComments(true);
			final DocumentBuilder db = dbf.newDocumentBuilder();
			Document xmlDoc = db.parse(file);
			Node n = xmlDoc.getFirstChild();
			if ("cotizacion".equalsIgnoreCase(n.getNodeName())) {
				NamedNodeMap attrs = n.getAttributes();
				Node attribute = attrs.getNamedItem("nroCotizacion");
				int nroCotizacion = Integer.parseInt(attribute.getNodeValue());
				attribute = attrs.getNamedItem("codigoCliente");
				Long idCliente = Long.valueOf(attribute.getNodeValue());

				attribute = attrs.getNamedItem("fecha");
				Date fecha = DateConverter.parsearFecha(attribute.getNodeValue());

				AdministracionODV odv = AdministracionODV.getInstancia();
				AdministracionCPR cpr = AdministracionCPR.getInstancia();
				OrdenCompra ordenCompra = new OrdenCompra();
				ordenCompra.setNroOrdenCompra(nroCotizacion);
				// ordenCompra.setCliente(odv.buscarCliente2(idCliente));
				// ordenCompra.setOficinaVenta(ordenCompra.getCliente().getOficinaVentas());
				ordenCompra.setFecha(fecha);
				for (Node d = n.getFirstChild(); d != null; d = d.getNextSibling()) {
					if ("itemCotizacion".equalsIgnoreCase(d.getNodeName())) {
						attrs = d.getAttributes();
						int cantidad = Integer.valueOf(attrs.getNamedItem("cantidad").getNodeValue());
						Rodamiento rodamiento = cpr.buscarRodamiento(attrs.getNamedItem("CodigoSKF").getNodeValue(),
								attrs.getNamedItem("CodigoPieza").getNodeValue());
						ordenCompra.add(rodamiento, cantidad);
					}
				}
				return ordenCompra;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static File obtenerFileOrdenesCompraXML(Proveedor proveedor) {
		File[] files = filesOrdenesCompraXML(proveedor);

		if (files != null)
			for (File file : files)
				if (leerOrdenCompraXML(file).getProveedor().getCuilProveedor().equals(proveedor.getCuilProveedor()))
					return file;
		return null;
	}

	public static File[] filesOrdenesCompraXML(Proveedor proveedor) // Levanta
	{
		try {
			File dir = new File("src/main/java/RepoXML/ordenesCompra/" + proveedor.getCuilProveedor());
			return dir.listFiles(new XMLFilter());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
