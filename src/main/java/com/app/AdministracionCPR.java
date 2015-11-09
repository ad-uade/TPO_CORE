package com.app;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import com.group7.business.ListaPreciosVO;
import com.group7.business.OrdenCompraVO;
import com.group7.business.OrdenPedidoVO;
import com.group7.business.ProveedorVO;
import com.group7.business.RodamientoVO;
import com.group7.entity.ListaPrecios;
import com.group7.remote.InterfazRemotaCPR;
import com.group7.service.ComparativaPreciosServicio;
import com.group7.service.ListaPreciosServicio;
import com.group7.service.OrdenCompraServicio;
import com.group7.service.OrdenPedidoServicio;
import com.group7.service.ProveedorServicio;
import com.group7.service.RemitoServicio;
import com.group7.service.RodamientoServicio;

public class AdministracionCPR extends UnicastRemoteObject implements InterfazRemotaCPR {

	private static final long serialVersionUID = 1327387606735665644L;

	private static AdministracionCPR instancia;

	protected AdministracionCPR() throws RemoteException {
		super();
	}

	public static AdministracionCPR getInstancia() throws RemoteException {
		if (instancia == null)
			instancia = new AdministracionCPR();
		return instancia;
	}

	@Override
	public boolean altaProveedor(String razonSocial, int CUIL, String direccion, String telefono) throws RemoteException {
		ProveedorServicio.getInstancia().altaProveedor(razonSocial, CUIL, direccion, telefono);
		return true;
	}

	@Override
	public boolean modificarProveedor(ProveedorVO proveedor) throws RemoteException {
		ProveedorServicio.getInstancia().modificarProveedor(proveedor);
		return true;
	}

	@Override
	public boolean bajaProveedor(int CUIL) throws RemoteException {
		ProveedorServicio.getInstancia().bajaProveedor(CUIL);
		return true;
	}

	@Override
	public void actualizarComparativaPrecios(ListaPreciosVO listaVO) throws RemoteException {
		ListaPrecios lista = ListaPreciosServicio.getInstancia().VoAHibernate(listaVO);
		ComparativaPreciosServicio.getInstancia().actualizarComparativa(lista);
	}

	@Override
	public List<ProveedorVO> listarProveedores() throws RemoteException {
		List<ProveedorVO> lista = ProveedorServicio.getInstancia().obtenerListadoProveedores();
		return lista;
	}

	@Override
	public ProveedorVO traerProveedor(Integer cuil) throws RemoteException {
		ProveedorVO proveedor = ProveedorServicio.getInstancia().obtenerProveedor(cuil);
		return proveedor;
	}

	@Override
	public ListaPreciosVO armarListaDePrecios(ProveedorVO proveedor, List<RodamientoVO> rodamientos, List<Float> precios, String tipo, String vigencia, float descuento) throws RemoteException {
		ListaPreciosVO lista = ListaPreciosServicio.getInstancia().generarLista(proveedor, rodamientos, precios, descuento, tipo, vigencia);
		this.actualizarComparativaPrecios(lista);
		return lista;
	}

	@Override
	public boolean generarOrdenDeCompra(OrdenPedidoVO ordenDePedido) throws RemoteException {
		OrdenCompraServicio.getInstancia().generarOrden(ordenDePedido);
		return true;
	}

	@Override
	public boolean revisarOrdenDePedido(OrdenPedidoVO ordenPedido) throws RemoteException {
		RemitoServicio.getInstancia().generarRemitoExterior(ordenPedido);
		return true;
	}

	@Override
	public OrdenPedidoVO dameOrdenVO(int nroOrdenPedido) throws RemoteException {
		OrdenPedidoVO ordenVO = OrdenPedidoServicio.getInstancia().obtenerOrdenVO(nroOrdenPedido);
		return ordenVO;
	}

	@Override
	public ListaPreciosVO obtenerLista(Integer nro) throws RemoteException {
		ListaPreciosVO lista = ListaPreciosServicio.getInstancia().obtenerLista(nro);
		return lista;
	}

	@Override
	public List<ListaPreciosVO> traerListas() throws RemoteException {
		List<ListaPreciosVO> listas = ListaPreciosServicio.getInstancia().obtenerListas();
		return listas;
	}

	@Override
	public List<OrdenPedidoVO> listarOrdenesPedido() throws RemoteException {
		List<OrdenPedidoVO> ordenes = OrdenPedidoServicio.getInstancia().obtenerOrdenes();
		return ordenes;
	}

	@Override
	public List<OrdenPedidoVO> listarOrdenesPedidoARemitir() throws RemoteException {
		List<OrdenPedidoVO> ordenes = OrdenPedidoServicio.getInstancia().buscarOrdenesARemitir();
		return ordenes;
	}

	@Override
	public void generarOrdenDeCompraManual(List<RodamientoVO> rodamientos, List<Integer> cantidades)
			throws RemoteException {
		OrdenCompraServicio.getInstancia().altaOrdenCompra(rodamientos, cantidades);
	}

	@Override
	public OrdenCompraVO dameOrdenCompraVO(int nroOrdenCompra) throws RemoteException {
		OrdenCompraVO ordenVO = OrdenCompraServicio.getInstancia().dameOrden(nroOrdenCompra);
		return ordenVO;
	}

	@Override
	public void recepcionDeMercaderia(OrdenCompraVO ordenVO) throws RemoteException {
		RemitoServicio.getInstancia().recibirMercaderia(ordenVO);
	}

	@Override
	public List<OrdenCompraVO> dameOrdenesComprasVO() throws RemoteException {
		List<OrdenCompraVO> ordenes = OrdenCompraServicio.getInstancia().buscarOrdenes();
		return ordenes;
	}

	@Override
	public void altaRodamiento(String codigoSFK, String codigoPieza, String descripcion, String paisOrigen, String marca, boolean estado) throws RemoteException {
		RodamientoServicio.getInstancia().guardarRodamiento(codigoSFK, codigoPieza, descripcion, paisOrigen, marca,
				estado);
	}
}
