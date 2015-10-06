package com.group7.service;

import com.group7.dao.ProveedorDAO;
import com.group7.entity.Proveedor;

public class ProveedorServicio {

	private static ProveedorServicio instancia;
	
	public static ProveedorServicio getInstancia(){
		if(instancia == null)
			instancia = new ProveedorServicio();
		return instancia;
	}

	public void guardarProveedor(String razonSocial, int CUIL, String direccion, String telefono) {
		Proveedor proveedorHibernate = new Proveedor();
		ProveedorDAO miProveedorDAO = new ProveedorDAO();
		
		proveedorHibernate.setRazonSocial(razonSocial);
		proveedorHibernate.setCUILProveedor(CUIL);
		proveedorHibernate.setDireccion(direccion);
		proveedorHibernate.setTelefono(telefono);
		proveedorHibernate.setCasaCentral(CasaCentralServicio.getInstancia().obtenerCasaCentral());
		proveedorHibernate.setEstado(true);
		
		miProveedorDAO.persistir(proveedorHibernate);
		miProveedorDAO.actualizarCasa(proveedorHibernate);
	}

}
