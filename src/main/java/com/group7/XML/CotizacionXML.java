package com.group7.XML;

import java.io.File;
import java.util.Calendar;
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

import com.group7.business.ProveedorVO;
import com.group7.dao.*;
import com.group7.entity.*;

public class CotizacionXML
{
	private static String root = "RepoXML\\";
	private static String solicitudes = "\\solicitudes";
	private static String cotizaciones = "\\cotizaciones";
	private static String cotizacionesAceptadas = "\\cotizacionesaceptadas";
	
	
	
	
	public static SolicitudCotizacion leerSolicitudCotizacionXML(File file)// Obtengo la solicitud de cotizacion a partir de un archivo 
	{
		try
		{
			final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(false);
			dbf.setValidating(false);
			dbf.setIgnoringComments(true);
			final DocumentBuilder db = dbf.newDocumentBuilder();
			Document xmlDoc = db.parse(file);
			Node n = xmlDoc.getFirstChild();
			if ("cotizacion".equalsIgnoreCase(n.getNodeName()))
			{
				NamedNodeMap attrs = n.getAttributes();
				Node attribute = attrs.getNamedItem("nroCotizacion");
				int nroCotizacion = Integer.parseInt(attribute.getNodeValue());
				attribute = attrs.getNamedItem("codigoCliente");
				int idCliente = Integer.valueOf(attribute.getNodeValue());
				attribute = attrs.getNamedItem("fecha");
				Date fecha = DateConverter.parsearFecha(attribute.getNodeValue());
				
				SolicitudCotizacion solicitudCotizacion = new SolicitudCotizacion();
				solicitudCotizacion.setNroSolicitudCotizacion(nroCotizacion);
				solicitudCotizacion.setCliente(new ClienteDAO().buscarPorId(Long.valueOf(idCliente)));
				solicitudCotizacion.setFecha(fecha);
				for (Node d = n.getFirstChild(); d != null; d = d.getNextSibling())
				{
					if ("itemCotizacion".equalsIgnoreCase(d.getNodeName()))
					{
						attrs = d.getAttributes();
						int cantidad = Integer.valueOf(attrs.getNamedItem("cantidad").getNodeValue());
						solicitudCotizacion.add(new RodamientoDAO().getRodamiento(attrs.getNamedItem("CodigoSKF").getNodeValue(), attrs.getNamedItem("CodigoPieza").getNodeValue() ), cantidad);
					}
				}
				return solicitudCotizacion;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
		



	public static Cotizacion leerCotizacionXML(File file) // Obtengo la cotizacion a partir de un archivo 
	{
		try
		{
			final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(false);
			dbf.setValidating(false);
			dbf.setIgnoringComments(true);
			final DocumentBuilder db = dbf.newDocumentBuilder();
			Document xmlDoc = db.parse(file);
			Node n = xmlDoc.getFirstChild();
			if ("Cotizacion".equalsIgnoreCase(n.getNodeName()))
			{
				NamedNodeMap attrs = n.getAttributes();
				Node attribute = attrs.getNamedItem("nroCotizacion");
				int idCotizacion = Integer.parseInt(attribute.getNodeValue());
				attribute = attrs.getNamedItem("fecha");
				Date fecha = DateConverter.parsearFecha(attribute.getNodeValue());
				attribute = attrs.getNamedItem("codigoCliente");
				int idCliente = Integer.valueOf(attribute.getNodeValue());
				
				Cotizacion cotizacion = new Cotizacion();
				cotizacion.setFecha(fecha);
				cotizacion.setId(idCotizacion);
				cotizacion.setCliente(new ClienteDAO().buscarPorId(Long.valueOf(idCliente)));
				for (Node d = n.getFirstChild(); d != null; d = d.getNextSibling())
				{
					if ("itemCotizacion".equalsIgnoreCase(d.getNodeName()))
					{
						attrs = d.getAttributes();
						int cantidad = Integer.valueOf(attrs.getNamedItem("cantidad").getNodeValue());
						Rodamiento rodamiento = new RodamientoDAO().getRodamiento(attrs.getNamedItem("CodigoSKF").getNodeValue(), attrs.getNamedItem("CodigoPieza").getNodeValue() );
						Proveedor proveedor = new ProveedorDAO().buscarPorId(Long.valueOf(attrs.getNamedItem("CUILproveedor").getNodeValue()));
						Float precioUnitario = Float.valueOf(attrs.getNamedItem("precioUnitario").getNodeValue());
						cotizacion.add(rodamiento, cantidad, proveedor, precioUnitario, EstadoCotizacion.valueOf(attrs.getNamedItem("estado").getNodeValue())); 
					}
				}
				return cotizacion;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	public static void cotizacionXML(Cotizacion cotizacion) //genera archivo xml de cotizacion
	{
		try
		{
			final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(false);
			dbf.setValidating(false);
			dbf.setIgnoringComments(true);
			final DocumentBuilder db = dbf.newDocumentBuilder();
			Document xmlDoc = db.newDocument();
			
			Element raiz = xmlDoc.createElement("Cotizacion");
			xmlDoc.appendChild(raiz);
			
			Attr attribute = xmlDoc.createAttribute("nroCotizacion");
			attribute.setValue(Integer.toString(cotizacion.getId()));
			raiz.setAttributeNode(attribute);
			

			
			attribute = xmlDoc.createAttribute("fecha");
			attribute.setValue(DateConverter.convertirFechaString(cotizacion.getFecha()));
			raiz.setAttributeNode(attribute);
			
			attribute = xmlDoc.createAttribute("codigoCliente");
			attribute.setValue(Long.toString(cotizacion.getCliente().getCuilCliente()));
			raiz.setAttributeNode(attribute);
			
			for (ItemCotizacion itemCotizacion : cotizacion.getItems())			{
				
				Element item = xmlDoc.createElement("itemCotizacion");
				
				
				attribute = xmlDoc.createAttribute("CodigoSKF");
				attribute.setValue(itemCotizacion.getId().getRodamiento().getRodamientoId().getCodigoSFK());
				item.setAttributeNode(attribute);
				
				attribute = xmlDoc.createAttribute("CodigoPieza");
				attribute.setValue(itemCotizacion.getId().getRodamiento().getRodamientoId().getCodigoPieza());
				item.setAttributeNode(attribute);
				
				attribute = xmlDoc.createAttribute("cantidad");
				attribute.setValue(Integer.toString(itemCotizacion.getCantidad()));
				item.setAttributeNode(attribute);
				
				attribute = xmlDoc.createAttribute("estado");
				attribute.setValue(itemCotizacion.getEstadoCotizacion().toString());
				item.setAttributeNode(attribute);
				
				attribute = xmlDoc.createAttribute("precioUnitario");
				if (itemCotizacion.getItemProveedor() != null)
				{
					attribute.setValue(Float.toString(itemCotizacion.getPrecioUnitario()));
				}
				else
				{
					attribute.setValue("0");
				}
				item.setAttributeNode(attribute);
				
				
				raiz.appendChild(item);
			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(xmlDoc);
			long timestamp = Calendar.getInstance().getTimeInMillis();
			StreamResult result = new StreamResult(new File(root + Integer.toString(cotizacion.getCliente().getOficinaVentas().getIdOficinaVenta()) + cotizaciones, Long.toString(timestamp) + ".xml"));
			transformer.transform(source, result);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	
	
	public static boolean SolicitudCotizacionXML(SolicitudCotizacion solicitudCotizacion)  //genera archivo xml de solicitud de cotizacion
	{
		try
		{
			final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(false);
			dbf.setValidating(false);
			dbf.setIgnoringComments(true);
			final DocumentBuilder db = dbf.newDocumentBuilder();
			Document xmlDoc = db.newDocument();
			
			Element raiz = xmlDoc.createElement("Cotizacion");
			xmlDoc.appendChild(raiz);
			
			Attr attribute = xmlDoc.createAttribute("nroCotizacion");
			attribute.setValue(Integer.toString(solicitudCotizacion.getNroSolicitudCotizacion()));
			raiz.setAttributeNode(attribute);
			
			attribute = xmlDoc.createAttribute("estado");
			attribute.setValue("pendiente");
			raiz.setAttributeNode(attribute);
			
			attribute = xmlDoc.createAttribute("fecha");
			attribute.setValue(DateConverter.convertirFechaString(solicitudCotizacion.getFecha()));
			raiz.setAttributeNode(attribute);
			
			attribute = xmlDoc.createAttribute("codigoCliente");
			attribute.setValue(Integer.toString(solicitudCotizacion.getCliente().getOficinaVentas().getIdOficinaVenta()));
			raiz.setAttributeNode(attribute);
			
			for (ItemSolicitudCotizacion itemSolicitudCotizacion : solicitudCotizacion.getItems())
			{
				Element item = xmlDoc.createElement("itemCotizacion");
		
				
				attribute = xmlDoc.createAttribute("CodigoSKF");
				attribute.setValue(itemSolicitudCotizacion.getId().getRodamiento().getRodamientoId().getCodigoSFK());
				item.setAttributeNode(attribute);
				
				attribute = xmlDoc.createAttribute("CodigoPieza");
				attribute.setValue(itemSolicitudCotizacion.getId().getRodamiento().getRodamientoId().getCodigoPieza());
				item.setAttributeNode(attribute);
				
				attribute = xmlDoc.createAttribute("cantidad");
				attribute.setValue(Integer.toString(itemSolicitudCotizacion.getCantidad()));
				item.setAttributeNode(attribute);
				
				attribute = xmlDoc.createAttribute("precioUnitario");
				attribute.setValue("0");
				item.setAttributeNode(attribute);
				
				attribute = xmlDoc.createAttribute("estado");
				attribute.setValue("PENDIENTE");
				item.setAttributeNode(attribute);
				
				raiz.appendChild(item);
			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(xmlDoc);
			long timestamp = Calendar.getInstance().getTimeInMillis();
			StreamResult result = new StreamResult(new File(root + Integer.toString(solicitudCotizacion.getCliente().getOficinaVentas().getIdOficinaVenta()) + solicitudes, Float.toString(timestamp) + ".xml"));
			transformer.transform(source, result);
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	
	
	
	
	
	
	public static File[] filesCotizacionAceptadasXML(OficinaVenta oficinaVenta) //Levanta todos los archivos de cotizaciones aceptadas
	{
		try
		{
			File dir = new File(root + Integer.toString(oficinaVenta.getIdOficinaVenta()), cotizacionesAceptadas);
			return dir.listFiles(new XMLFilter());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	

	
	
	public static File[] filesCotizacionesXML(OficinaVenta oficinaVenta)   // Trae todos los archivos de cotizaciones
	{
		try
		{
			File dir = new File(root + Integer.toString(oficinaVenta.getIdOficinaVenta()), cotizaciones);
			return dir.listFiles(new XMLFilter());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static File[] filesSolicitudCotizacionXML(OficinaVenta oficinaVenta)  //Levanta todos los archivos de solicitudes
	{
		try
		{
			File dir = new File(root + Integer.toString(oficinaVenta.getIdOficinaVenta()), solicitudes);
			return dir.listFiles(new XMLFilter());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	public static void borrarCotizacionXML(Cotizacion cotizacion)
	{
		File[] files = filesCotizacionesXML(cotizacion.getCliente().getOficinaVentas());
		
		if (files != null)
			for (File file : files)
				if (leerCotizacionXML(file).getId() == cotizacion.getId())
					file.delete();
	}

	
	
	
	public static void borrarCotizacionAceptadaXML(Cotizacion cotizacion)
	{
		File[] files = filesCotizacionAceptadasXML(cotizacion.getCliente().getOficinaVentas());
		
		if (files != null)
			for (File file : files)
				if (leerCotizacionXML(file).getId() == cotizacion.getId())
					file.delete();
	}
	
	
	
	
	public static void borrarSolicitudCotizacionXML(SolicitudCotizacion solicitudCotizacion)
	{
		File[] files = filesSolicitudCotizacionXML(solicitudCotizacion.getCliente().getOficinaVentas());
		
		if (files != null)
			for (File file : files)
				if (leerSolicitudCotizacionXML(file).getNroSolicitudCotizacion() == solicitudCotizacion.getNroSolicitudCotizacion())
					file.delete();
	}
	
	
	
	
	
	
	
	
}