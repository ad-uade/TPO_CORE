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

	public void altaProveedor(String razonSocial, Long CUIL, String direccion, String telefono) {
		proveedorDAO.openCurrentSessionwithTransaction();
		Proveedor proveedor = new Proveedor();
		proveedor.setRazonSocial(razonSocial);
		proveedor.setCuilProveedor(CUIL);
		proveedor.setDireccion(direccion);
		proveedor.setTelefono(telefono);
		proveedor.setEstado(true);
		proveedorDAO.persistir(proveedor);
		proveedorDAO.closeCurrentSessionwithTransaction();
	}

	public ProveedorVO HibernateAVo(Proveedor proveedor) {
		ProveedorVO proveedorVO = new ProveedorVO();
		proveedorVO.setCuilProveedor(proveedor.getCUILProveedor());
		proveedorVO.setDireccion(proveedor.getDireccion());
		proveedorVO.setEstado(proveedor.isEstado());
		proveedorVO.setRazonSocial(proveedor.getRazonSocial());
		proveedorVO.setTelefono(proveedor.getTelefono());
		return proveedorVO;
	}

	public Proveedor VoAHibernate(ProveedorVO proveedor) {
		Proveedor proveedorH = new Proveedor();
		proveedorH.setCuilProveedor(proveedor.getCuilProveedor());
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

	public void bajaProveedor(Long CUIL) {
		proveedorDAO.openCurrentSessionwithTransaction();
		proveedorDAO.bajaProveedor(CUIL);
		proveedorDAO.closeCurrentSessionwithTransaction();
	}

	public List<ProveedorVO> obtenerListadoProveedores() {
		proveedorDAO.openCurrentSessionwithTransaction();
		List<Proveedor> proveedores = proveedorDAO.getProveedores();
		List<ProveedorVO> proveedoresVO = new ArrayList<ProveedorVO>();
		for (Proveedor proveedor : proveedores){
			proveedoresVO.add(this.HibernateAVo(proveedor));
		}
		proveedorDAO.closeCurrentSessionwithTransaction();
		return proveedoresVO;
	}

	/**
	 * 
	 * @param cuil
	 * @return
	 */
	public ProveedorVO obtenerProveedor(Long cuil) {
		proveedorDAO.openCurrentSessionwithTransaction();
		ProveedorVO proveedorVO = this.HibernateAVo(proveedorDAO.getProveedor(cuil));
		proveedorDAO.closeCurrentSessionwithTransaction();
		return proveedorVO;
	}

}
