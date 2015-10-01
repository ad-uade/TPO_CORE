package com.group7.service;

import java.util.ArrayList;
import java.util.List;

import com.group7.business.ProveedorVO;
import com.group7.dao.ProveedorDAO;
import com.group7.entity.Proveedor;

public class ProveedorImpl {

	private static ProveedorImpl instancia;
	
	public static ProveedorImpl getInstancia(){
		if(instancia == null)
			instancia = new ProveedorImpl();
		return instancia;
	}

	public ProveedorVO proveedorToVo(Proveedor proveedor) {
		ProveedorVO proveedorVO = new ProveedorVO();
		proveedorVO.setCUILProveedor(proveedor.getCUILProveedor());
		proveedorVO.setDireccion(proveedor.getDireccion());
		proveedorVO.setEstado(proveedor.isEstado());
		proveedorVO.setRazonSocial(proveedor.getRazonSocial());
		proveedorVO.setTelefono(proveedor.getTelefono());
		return proveedorVO;
	}

	public Proveedor VoAHibernate(ProveedorVO proveedor) {
		Proveedor proveedorH = new Proveedor();
		proveedorH.setCUILProveedor(proveedor.getCUILProveedor());
		proveedorH.setRazonSocial(proveedor.getRazonSocial());
		proveedorH.setDireccion(proveedor.getDireccion());
		proveedorH.setTelefono(proveedor.getTelefono());
		proveedorH.setEstado(proveedor.isEstado());
		return proveedorH;
	}

	public void modificarProveedor(ProveedorVO proveedor) {
		Proveedor proveedorHibernate = this.VoAHibernate(proveedor);
		ProveedorDAO miProveedorDAO = new ProveedorDAO();
		miProveedorDAO.actualizarProveedor(proveedorHibernate);
	}

	public void guardarProveedor(String razonSocial, int CUIL, String direccion, String telefono) {
		Proveedor proveedorHibernate = new Proveedor();
		ProveedorDAO miProveedorDAO = new ProveedorDAO();
		
		proveedorHibernate.setRazonSocial(razonSocial);
		proveedorHibernate.setCUILProveedor(CUIL);
		proveedorHibernate.setDireccion(direccion);
		proveedorHibernate.setTelefono(telefono);
		proveedorHibernate.setCasaCentral(CasaCentralImpl.getInstancia().obtenerCasaCentral());
		proveedorHibernate.setEstado(true);
		
		miProveedorDAO.altaProveedor(proveedorHibernate);
		miProveedorDAO.actualizarCasa(proveedorHibernate);
	}

	public void baja(int CUIL) {
		ProveedorDAO miProveedorDAO = new ProveedorDAO();
		miProveedorDAO.bajaProveedor(CUIL);
	}

	public List<ProveedorVO> dameProveedores() {
		try {
			ProveedorDAO miProveedor = new ProveedorDAO();
			List<Proveedor> proveedores = miProveedor.getProveedores();
			List<ProveedorVO> proveedorVO = new ArrayList<ProveedorVO>();
			for (int i=0;i<proveedores.size();i++){
				ProveedorVO p = this.proveedorToVo(proveedores.get(i));
				proveedorVO.add(p);
			}
			return proveedorVO;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ProveedorVO traemeProveedor(Integer cuil) {
		try {
			ProveedorDAO miProveedor = new ProveedorDAO();
			Proveedor ph = miProveedor.getProveedor(cuil);
			if (ph==null)
				return null;
			ProveedorVO p = this.proveedorToVo(ph);
			return p;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
