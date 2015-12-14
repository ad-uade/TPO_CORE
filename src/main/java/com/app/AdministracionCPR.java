package com.app;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import com.group7.business.ProveedorVO;
import com.group7.dao.ProveedorDAO;
import com.group7.dao.RodamientoDAO;
import com.group7.entity.Proveedor;
import com.group7.entity.Rodamiento;
import com.group7.entity.enbeddable.RodamientoId;
import com.group7.remote.InterfazRemotaCPR;

public class AdministracionCPR extends UnicastRemoteObject implements InterfazRemotaCPR {

	private static final long serialVersionUID = 1327387606735665644L;

	private static AdministracionCPR instancia;
	private static RodamientoDAO rodamientoDAO;
	private static ProveedorDAO proveedorDAO;

	protected AdministracionCPR() throws RemoteException {
		super();
		rodamientoDAO = new RodamientoDAO();
		proveedorDAO = new ProveedorDAO();
	}

	public static AdministracionCPR getInstancia() throws RemoteException {
		if (instancia == null)
			instancia = new AdministracionCPR();
		return instancia;
	}

	@Override
	public void altaProveedor(String razonSocial, Long CUIL, String direccion, String telefono) throws RemoteException {
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

	@Override
	public void modificarProveedor(ProveedorVO proveedorVO) throws RemoteException {
		proveedorDAO.openCurrentSessionwithTransaction();
		Proveedor proveedor = new Proveedor(proveedorVO);
		proveedorDAO.actualizar(proveedor);
		proveedorDAO.closeCurrentSessionwithTransaction();
	}

	@Override
	public void bajaProveedor(Long CUIL) throws RemoteException {
		proveedorDAO.openCurrentSessionwithTransaction();
		proveedorDAO.bajaProveedor(CUIL);
		proveedorDAO.closeCurrentSessionwithTransaction();
	}

	@Override
	public List<ProveedorVO> listarProveedores() throws RemoteException {
		proveedorDAO.openCurrentSessionwithTransaction();
		List<Proveedor> proveedores = proveedorDAO.getProveedores();
		List<ProveedorVO> proveedoresVO = new ArrayList<ProveedorVO>();
		for (Proveedor proveedor : proveedores){
			proveedoresVO.add(proveedor.getView());
		}
		proveedorDAO.closeCurrentSessionwithTransaction();
		return proveedoresVO;
	}

	@Override
	public ProveedorVO traerProveedor(Long cuil) throws RemoteException {
		proveedorDAO.openCurrentSessionwithTransaction();
		Proveedor proveedor = proveedorDAO.buscarPorId(cuil);
		proveedorDAO.closeCurrentSessionwithTransaction();
		return proveedor.getView();
	}

	@Override
	public void altaRodamiento(String codigoSFK, String codigoPieza, String descripcion, String paisOrigen, String marca, boolean estado) throws RemoteException {
		rodamientoDAO.openCurrentSessionwithTransaction();
		Rodamiento r = new Rodamiento();
		RodamientoId rId = new RodamientoId();
		rId.setCodigoPieza(codigoPieza);
		rId.setCodigoSFK(codigoSFK);
		r.setRodamientoId(rId);
		r.setDescripcion(descripcion);
		r.setMarca(marca);
		r.setPaisOrigen(paisOrigen);
		r.setEstado(estado);
		rodamientoDAO.persistir(r);
		rodamientoDAO.closeCurrentSessionwithTransaction();
	}
}
