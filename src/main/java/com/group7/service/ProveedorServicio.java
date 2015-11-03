package com.group7.service;

import java.util.ArrayList;
import java.util.List;

import com.group7.business.ProveedorVO;
import com.group7.dao.ProveedorDAO;
import com.group7.entity.Proveedor;

public class ProveedorServicio {

	private static ProveedorServicio instancia;
	private static ProveedorDAO proveedorDAO;

	public static ProveedorServicio getInstancia() {
		if (instancia == null)
			instancia = new ProveedorServicio();
		return instancia;
	}

	public void guardarProveedor(String razonSocial, int CUIL, String direccion, String telefono) {
		proveedorDAO.openCurrentSessionwithTransaction();
		Proveedor proveedor = new Proveedor();
		proveedor.setRazonSocial(razonSocial);
		proveedor.setCUILProveedor(CUIL);
		proveedor.setDireccion(direccion);
		proveedor.setTelefono(telefono);
		proveedor.setCasaCentral(CasaCentralServicio.getInstancia().obtenerCasaCentral());
		proveedor.setEstado(true);
		proveedorDAO.persistir(proveedor);
		proveedorDAO.actualizarCasa(proveedor);
		proveedorDAO.closeCurrentSessionwithTransaction();
	}

	public ProveedorVO HibernateAVo(Proveedor proveedor) {
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
		proveedorDAO.openCurrentSessionwithTransaction();
		Proveedor proveedorHibernate = this.VoAHibernate(proveedor);
		proveedorDAO.actualizar(proveedorHibernate);
		proveedorDAO.closeCurrentSessionwithTransaction();
	}

	public void baja(int CUIL) {
		proveedorDAO.openCurrentSessionwithTransaction();
		proveedorDAO.bajaProveedor(CUIL);
		proveedorDAO.closeCurrentSessionwithTransaction();
	}

	public List<ProveedorVO> dameProveedores() {
		List<Proveedor> proveedores = proveedorDAO.getProveedores();
		List<ProveedorVO> proveedorVO = new ArrayList<ProveedorVO>();
		for (int i = 0; i < proveedores.size(); i++) {
			ProveedorVO p = this.HibernateAVo(proveedores.get(i));
			proveedorVO.add(p);
		}
		return proveedorVO;
	}

	public ProveedorVO traemeProveedor(Integer cuil) {
		Proveedor ph = proveedorDAO.getProveedor(cuil);
		if (ph == null)
			return null;
		ProveedorVO p = this.HibernateAVo(ph);
		return p;
	}

}
