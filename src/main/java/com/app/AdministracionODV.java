package com.app;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import com.group7.business.ClienteVO;
import com.group7.business.CondicionVentaVO;
import com.group7.business.CotizacionVO;
import com.group7.business.OficinaVentasVO;
import com.group7.business.RemitoExteriorVO;
import com.group7.business.RemitoVO;
import com.group7.business.RodamientoVO;
import com.group7.business.SolicitudCotizacionVO;
import com.group7.entity.Cliente;
import com.group7.entity.Cotizacion;
import com.group7.entity.OficinaVentas;
import com.group7.entity.Remito;
import com.group7.entity.SolicitudCotizacion;
import com.group7.remote.InterfazRemotaODV;
import com.group7.service.ClienteServicio;
import com.group7.service.CondicionVentaServicio;
import com.group7.service.CotizacionServicio;
import com.group7.service.FacturaServicio;
import com.group7.service.OficinaVentasServicio;
import com.group7.service.OrdenPedidoServicio;
import com.group7.service.RemitoServicio;
import com.group7.service.RodamientoServicio;
import com.group7.service.SolicitudCotizacionServicio;

public class AdministracionODV extends UnicastRemoteObject implements InterfazRemotaODV{

	private static final long serialVersionUID = -3832169933384285597L;
	private static AdministracionODV instancia;

	protected AdministracionODV() throws RemoteException {
		super();
	}
	
	public static AdministracionODV getInstancia() throws RemoteException{
		if(instancia == null)
			instancia = new AdministracionODV();
		return instancia;
	}

	@Override
	public boolean altaCliente(String razonSocial, int CUIL, String direccion, String telefono, OficinaVentasVO of) throws RemoteException {
		Cliente cliente = new Cliente();
		cliente.setRazonSocial(razonSocial);
		cliente.setCuilCliente(CUIL);
		cliente.setDireccion(direccion);
		cliente.setTelefono(telefono);
		OficinaVentas oficinaVentas = new OficinaVentas();
		cliente.setOficinaVentas(oficinaVentas);
		ClienteServicio.getInstancia().persist(cliente);
		return true;
	}

	@Override
	public boolean modificarCliente(ClienteVO clientevo) throws RemoteException {
		Cliente cliente = new Cliente();
		cliente.setCuilCliente(clientevo.getCUILCliente());
		cliente.setDireccion(clientevo.getDireccion());
		cliente.setRazonSocial(clientevo.getRazonSocial());
		cliente.setTelefono(clientevo.getTelefono());
		ClienteServicio.getInstancia().update(cliente);
		return true;
	}

	@Override
	public boolean bajaCliente(int CUIL) throws RemoteException {
		ClienteServicio.getInstancia().baja(CUIL);
		return true;
	}

	@Override
	public boolean generarCotizacion(SolicitudCotizacionVO SC, int diasValidez) throws RemoteException {
		SolicitudCotizacion solicitud = SolicitudCotizacionServicio.getInstancia().VoAHibernate(SC);
		CotizacionServicio.getInstancia().altaCotizacion(solicitud, diasValidez);	
		return true;
	}

	 @Override
	 public void generarOrdenPedido(CotizacionVO cotizacion) throws RemoteException {
		  Cotizacion cotizacionH = CotizacionServicio.getInstancia().VoAHibernate(cotizacion);
		  OrdenPedidoServicio.getInstancia().guardarOrdenPedido(cotizacionH);
	 }

	@Override
	public void registrarConformidadCliente(int nroRemito) throws RemoteException {
		RemitoServicio.getInstancia().conformarRemito(nroRemito);
	}

	@Override
	public void guardarSolicitudCotizacion(ClienteVO cliente, List<RodamientoVO> rodamientos, List<Integer> cantidades, List<CondicionVentaVO> condiciones) throws RemoteException {
		SolicitudCotizacionServicio.getInstancia().generarSolicitudCotizacion(cliente, rodamientos, cantidades, condiciones);
	}

	@Override
	public SolicitudCotizacionVO dameSolicitud(int nroSolicitud) throws RemoteException {
		SolicitudCotizacionVO solicitudVO = SolicitudCotizacionServicio.getInstancia().dameSolicitud(nroSolicitud);
		return solicitudVO;
	}

	
	@Override
	public List<ClienteVO> listarClientes() throws RemoteException {
		List<ClienteVO> clientes = ClienteServicio.getInstancia().dameClientes();
		return clientes;
	}

	@Override
	public ClienteVO traerCliente(int CUIL) throws RemoteException {
		ClienteVO cliente = ClienteServicio.getInstancia().obtenerCliente(CUIL);
		return cliente;
	}

	@Override
	public RodamientoVO obtenerRodamiento(String SFK, String codigo) throws RemoteException {
		RodamientoVO rodamiento = RodamientoServicio.getInstancia().obtenerRodamiento(SFK, codigo);
		return rodamiento;
	}

	@Override
	public List<RodamientoVO> traerRodamientos() throws RemoteException {
		List<RodamientoVO> rodamientos = RodamientoServicio.getInstancia().dameRodamientos();
		return rodamientos;
	}

	@Override
	public CotizacionVO dameCotizacion(int nroCotizacion) throws RemoteException {
		CotizacionVO cotizacion = CotizacionServicio.getInstancia().dameCotizacion(nroCotizacion);
		return cotizacion;
	}
	
	public List<SolicitudCotizacionVO> listarSolicitudesCotizacion() throws RemoteException {
		List<SolicitudCotizacionVO> solicitudes = SolicitudCotizacionServicio.getInstancia().dameSolicitudes();
		return solicitudes;
	}

	@Override
	 public void aprobarCotizacion(CotizacionVO cotizacion) throws RemoteException {
		CotizacionServicio.getInstancia().revisarCotizacion(cotizacion);
	 }

	@Override
	public List<CotizacionVO> dameCotizaciones() throws RemoteException {
		List<CotizacionVO> cotizaciones = CotizacionServicio.getInstancia().cotizaciones();
		return cotizaciones;
	}

	@Override
	public RemitoVO dameRemito(int nroRemito) throws RemoteException {
		RemitoVO remito = RemitoServicio.getInstancia().dameRemitoVO(nroRemito);
		return remito;
	}

	@Override
	public List<RemitoExteriorVO> traerRemitos() throws RemoteException {
		List<RemitoExteriorVO> remitos = RemitoServicio.getInstancia().dameRemitos();
		return remitos;
	}

	
	@Override
	public void generarFactura(RemitoVO remito) throws RemoteException {
		Remito remExterior = RemitoServicio.getInstancia().VoAHibernate(remito);
		FacturaServicio.getInstancia().guardarFactura(remExterior);
	}

	@Override
	public List<OficinaVentasVO> obtenerOficinas() throws RemoteException {
		List<OficinaVentasVO> oficinas = OficinaVentasServicio.getInstancia().dameOficinas();
		return oficinas;
	}

	@Override
	public OficinaVentasVO dameOficina(int idOficina) throws RemoteException {
		OficinaVentasVO oficinaVO = OficinaVentasServicio.getInstancia().buscaOficina(idOficina);
		return oficinaVO;
	}
	
	@Override
	public List<RemitoExteriorVO> traerRemitosNoConformados() throws RemoteException {
		List<RemitoExteriorVO> remitos = RemitoServicio.getInstancia().dameRemitosNoConformados();
		return remitos;
	}
	
	@Override
	public List<CondicionVentaVO> condiciones() throws RemoteException {
		List<CondicionVentaVO> condiciones = CondicionVentaServicio.getInstancia().dameCondiciones();
		return condiciones;
	}

	@Override
	public CondicionVentaVO dameCondicion(int nroCondicion) throws RemoteException {
		  CondicionVentaVO condicion = CondicionVentaServicio.getInstancia().dameCondicion(nroCondicion);
		  return condicion;
	}
}
