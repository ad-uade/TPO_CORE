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
	
	private ProveedorServicio(){
		proveedorDAO = new ProveedorDAO();
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

	public Proveedor viewToModel(ProveedorVO proveedor) {
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
		Proveedor proveedorHibernate = this.viewToModel(proveedor);
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
			proveedoresVO.add(proveedor.getView());
		}
		proveedorDAO.closeCurrentSessionwithTransaction();
		return proveedoresVO;
	}
	
	/**
	 * 
	 * @param cuil
	 * @return
	 */
	public Proveedor buscarProveedor(Long cuil) {
		proveedorDAO.openCurrentSessionwithTransaction();
		Proveedor proveedor = proveedorDAO.buscarPorId(cuil);
		proveedorDAO.closeCurrentSessionwithTransaction();
		return proveedor;
	}

	public void persistirTodos(List<Proveedor> listadoProovedores) {
		proveedorDAO.openCurrentSessionwithTransaction();
		for (Proveedor proveedor : listadoProovedores){
			proveedorDAO.persistir(proveedor);
		}
		proveedorDAO.closeCurrentSessionwithTransaction();		
	}

}
