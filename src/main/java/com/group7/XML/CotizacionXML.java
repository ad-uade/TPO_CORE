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

import com.group7.dao.*;
import com.group7.entity.*;

public class CotizacionXML
{
	private static String root = "RepoXML\\";
	private static String pendientes = "\\solicitudes";
	private static String armadas = "\\cotizaciones";
	private static String aceptadas = "\\cotizacionesaceptadas";
	/*
	public static void generarXMLArmarCotizacion(Cotizacion cotizacion)
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
			
			attribute = xmlDoc.createAttribute("estado");
			attribute.setValue(cotizacion.getEstado());
			raiz.setAttributeNode(attribute);
			
			attribute = xmlDoc.createAttribute("fecha");
			attribute.setValue(DateConverter.convertirFechaString(cotizacion.getFecha()));
			raiz.setAttributeNode(attribute);
			
			attribute = xmlDoc.createAttribute("codigoCliente");
			attribute.setValue(Integer.toString(cotizacion.getCliente().getId()));
			raiz.setAttributeNode(attribute);
			
			for (ItemCotizacion itemCotizacion : cotizacion.getItems())
			{
				Element item = xmlDoc.createElement("item");
				
				attribute = xmlDoc.createAttribute("cantidad");
				attribute.setValue(Integer.toString(itemCotizacion.getCantidad()));
				item.setAttributeNode(attribute);
				
				attribute = xmlDoc.createAttribute("CodigoSKF");
				attribute.setValue(itemCotizacion.getId().getRodamiento().getRodamientoId().getCodigoSFK());
				item.setAttributeNode(attribute);
				
				attribute = xmlDoc.createAttribute("CodigoPieza");
				attribute.setValue(itemCotizacion.getId().getRodamiento().getRodamientoId().getCodigoPieza());
				item.setAttributeNode(attribute);
				
				attribute = xmlDoc.createAttribute("precio");
				if (itemCotizacion.getItProveedor() != null)
				{
					attribute.setValue(Float.toString(itemCotizacion.getItProveedor().getPrecio()));
				}
				else
				{
					attribute.setValue("0");
				}
				item.setAttributeNode(attribute);
				
				attribute = xmlDoc.createAttribute("cotizado");
				attribute.setValue(Boolean.toString(itemCotizacion.isCotizado()));
				item.setAttributeNode(attribute);
				
				raiz.appendChild(item);
			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(xmlDoc);
			long timestamp = Calendar.getInstance().getTimeInMillis();
			StreamResult result = new StreamResult(new File(root + Integer.toString(cotizacion.getCliente().getOVenta().getId()) + armadas, Long.toString(timestamp) + ".xml"));
			transformer.transform(source, result);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	*/
	
	public static Cotizacion leerXMLCotizacionParaArmar(File file)
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
				int id = Integer.parseInt(attribute.getNodeValue());
				attribute = attrs.getNamedItem("estado");
				String estado = attribute.getNodeValue();
				attribute = attrs.getNamedItem("codigoCliente");
				int idCliente = Integer.valueOf(attribute.getNodeValue());
				attribute = attrs.getNamedItem("fecha");
				Date fecha = DateConverter.parsearFecha(attribute.getNodeValue());
				
				Cotizacion cotizacionDAO = new Cotizacion();
				cotizacionDAO.setId(id);
				cotizacionDAO.setEstado(estado);
				cotizacionDAO.setCliente(ClienteDAO.getCliente(idCliente).getDTO());
				cotizacionDAO.setFecha(fecha);
				for (Node d = n.getFirstChild(); d != null; d = d.getNextSibling())
				{
					if ("item".equalsIgnoreCase(d.getNodeName()))
					{
						attrs = d.getAttributes();
						int cantidad = Integer.valueOf(attrs.getNamedItem("cantidad").getNodeValue());
						RodamientoDAO rodamientoDAO = RodamientoDAO.getRodamiento(attrs.getNamedItem("CodigoSKF").getNodeValue()).getDTO();
						cotizacionDAO.agregarItem(cantidad, rodamientoDAO);
					}
				}
				return cotizacionDAO;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static File[] obtenerXMLCotizacionParaArmar(OficinaVenta oficinaVenta)
	{
		try
		{
			File dir = new File(root + Integer.toString(oficinaVenta.getIdOficinaVenta()), pendientes);
			return dir.listFiles(new XMLFilter());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static File[] obtenerXMLCotizacionArmadas(OficinaVenta oficinaVenta)
	{
		try
		{
			File dir = new File(root + Integer.toString(oficinaVenta.getIdOficinaVenta()), armadas);
			return dir.listFiles(new XMLFilter());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean SolicitudCotizacionXML(SolicitudCotizacion solicitudCotizacion)
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
				Element item = xmlDoc.createElement("item");
				
				attribute = xmlDoc.createAttribute("cantidad");
				attribute.setValue(Integer.toString(itemSolicitudCotizacion.getCantidad()));
				item.setAttributeNode(attribute);
				
				attribute = xmlDoc.createAttribute("CodigoSKF");
				attribute.setValue(itemSolicitudCotizacion.getId().getRodamiento().getRodamientoId().getCodigoSFK());
				item.setAttributeNode(attribute);
				
				attribute = xmlDoc.createAttribute("CodigoPieza");
				attribute.setValue(itemSolicitudCotizacion.getId().getRodamiento().getRodamientoId().getCodigoPieza());
				item.setAttributeNode(attribute);
				
				attribute = xmlDoc.createAttribute("precio");
				attribute.setValue("0");
				item.setAttributeNode(attribute);
				
				attribute = xmlDoc.createAttribute("cotizado");
				attribute.setValue(Boolean.toString(false));
				item.setAttributeNode(attribute);
				
				raiz.appendChild(item);
			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(xmlDoc);
			long timestamp = Calendar.getInstance().getTimeInMillis();
			StreamResult result = new StreamResult(new File(root + Integer.toString(solicitudCotizacion.getCliente().getOficinaVentas().getIdOficinaVenta()) + pendientes, Float.toString(timestamp) + ".xml"));
			transformer.transform(source, result);
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public static File[] obtenerXMLCotizacionesAceptadas(OficinaVenta oficinaVenta)
	{
		try
		{
			File dir = new File(root + Integer.toString(oficinaVenta.getIdOficinaVenta()), aceptadas);
			return dir.listFiles(new XMLFilter());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	
	/*
	public static cotizacion leerXMLCotizacionAceptada(File file)
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
				int idCotizacion = Integer.parseInt(attribute.getNodeValue());
				attribute = attrs.getNamedItem("estado");
				String estado = attribute.getNodeValue();
				attribute = attrs.getNamedItem("fecha");
				Date fecha = DateConverter.parsearFecha(attribute.getNodeValue());
				attribute = attrs.getNamedItem("codigoCliente");
				int idCliente = Integer.valueOf(attribute.getNodeValue());
				
				CotizacionDAO cotizacionDAO = new CotizacionDAO();
				cotizacionDAO.setEstado(estado);
				cotizacionDAO.setFecha(fecha);
				cotizacionDAO.setId(idCotizacion);
				cotizacionDAO.setCliente(ClienteDAO.getCliente(idCliente).getDTO());
				for (Node d = n.getFirstChild(); d != null; d = d.getNextSibling())
				{
					if ("item".equalsIgnoreCase(d.getNodeName()))
					{
						attrs = d.getAttributes();
						int cantidad = Integer.valueOf(attrs.getNamedItem("cantidad").getNodeValue());
						RodamientoDAO rodamientoDAO = RodamientoDAO.getRodamiento(attrs.getNamedItem("CodigoSKF").getNodeValue()).getDTO();
						cotizacionDAO.agregarItem(cantidad, rodamientoDAO);
					}
				}
				return cotizacionDAO;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	*/
	
	/*
	public static boolean generarXMLAceptarCotizacion(Cotizacion cotizacion)
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
			
			attribute = xmlDoc.createAttribute("estado");
			attribute.setValue(cotizacion.getEstado());
			raiz.setAttributeNode(attribute);
			
			attribute = xmlDoc.createAttribute("codigoCliente");
			attribute.setValue(Long.toString(cotizacion.getCliente().getCuilCliente()));
			raiz.setAttributeNode(attribute);
			
			for (ItemCotizacion itemCotizacion : cotizacion.getItems())
			{
				Element item = xmlDoc.createElement("item");
				
				attribute = xmlDoc.createAttribute("cantidad");
				attribute.setValue(Integer.toString(itemCotizacion.getCantidad()));
				item.setAttributeNode(attribute);
				
				attribute = xmlDoc.createAttribute("CodigoSKF");
				attribute.setValue(itemCotizacion.getRod().getCodigoSKF());
				item.setAttributeNode(attribute);
				
				attribute = xmlDoc.createAttribute("CodigoPieza");
				attribute.setValue(itemCotizacion.getId().getRodamiento().getRodamientoId().getCodigoPieza());
				item.setAttributeNode(attribute);
				
				attribute = xmlDoc.createAttribute("precio");
				attribute.setValue(Float.toString(itemCotizacion.getItProveedor().getPrecio()));
				item.setAttributeNode(attribute);
				
				attribute = xmlDoc.createAttribute("cotizado");
				attribute.setValue(Boolean.toString(itemCotizacion.isCotizado()));
				item.setAttributeNode(attribute);
				
				raiz.appendChild(item);
			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(xmlDoc);
			long timestamp = Calendar.getInstance().getTimeInMillis();
			StreamResult result = new StreamResult(new File(root + Integer.toString(cotizacion.getCliente().getOVenta().getId()) + aceptadas, Long.toString(timestamp) + ".xml"));
			transformer.transform(source, result);
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	*/
	
	
	/*
	public static void borrarXMLCotizacionAceptada(Cotizacion cotizacion)
	{
		File[] files = obtenerXMLCotizacionArmadas(cotizacion.getCliente().getOficinaVentas());
		
		if (files != null)
		{
			for (File file : files)
			{
				if (leerXMLCotizacionArmada(file).getId() == cotizacion.getId())
				{
					file.delete();
				}
			}
		}
	}
	*/
	
	
	/*
	public static CotizacionDAO leerXMLCotizacionArmada(File file)
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
				attribute = attrs.getNamedItem("estado");
				String estado = attribute.getNodeValue();
				attribute = attrs.getNamedItem("fecha");
				Date fecha = DateConverter.parsearFecha(attribute.getNodeValue());
				attribute = attrs.getNamedItem("codigoCliente");
				int idCliente = Integer.valueOf(attribute.getNodeValue());
				
				CotizacionDAO cotizacionDAO = new CotizacionDAO();
				cotizacionDAO.getsetEstado(estado);
				cotizacionDAO.setFecha(fecha);
				cotizacionDAO.setId(idCotizacion);
				cotizacionDAO.setCliente(ClienteDAO.getCliente(idCliente).getDTO());
				for (Node d = n.getFirstChild(); d != null; d = d.getNextSibling())
				{
					if ("item".equalsIgnoreCase(d.getNodeName()))
					{
						attrs = d.getAttributes();
						int cantidad = Integer.valueOf(attrs.getNamedItem("cantidad").getNodeValue());
						RodamientoDAO rodamientoDAO = RodamientoDAO.getRodamiento(attrs.getNamedItem("CodigoSKF").getNodeValue()).getDTO();
						cotizacionDAO.agregarItem(cantidad, rodamientoDAO);
					}
				}
				return cotizacionDAO;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	*/
}