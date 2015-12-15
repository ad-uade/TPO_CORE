package com.app;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.group7.XML.CotizacionXML;
import com.group7.business.ClienteVO;
import com.group7.business.CotizacionVO;
import com.group7.business.ItemCotizacionVO;
import com.group7.business.ItemSolicitudCotizacionVO;
import com.group7.business.OficinaVentasVO;
import com.group7.business.RodamientoVO;
import com.group7.business.SolicitudCotizacionVO;
import com.group7.dao.ClienteDAO;
import com.group7.dao.ComparativaPreciosDAO;
import com.group7.dao.CotizacionDAO;
import com.group7.dao.FacturaDAO;
import com.group7.dao.OficinaVentasDAO;
import com.group7.dao.OrdenPedidoDAO;
import com.group7.dao.RodamientoDAO;
import com.group7.dao.SolicitudCotizacionDAO;
import com.group7.entity.Cliente;
import com.group7.entity.ComparativaPrecios;
import com.group7.entity.Cotizacion;
import com.group7.entity.EstadoCotizacion;
import com.group7.entity.Factura;
import com.group7.entity.ItemComparativaPrecio;
import com.group7.entity.ItemCotizacion;
import com.group7.entity.ItemSolicitudCotizacion;
import com.group7.entity.OficinaVenta;
import com.group7.entity.OrdenPedido;
import com.group7.entity.RemitoExterior;
import com.group7.entity.Rodamiento;
import com.group7.entity.SolicitudCotizacion;
import com.group7.entity.enbeddable.RodamientoId;
import com.group7.remote.InterfazRemotaODV;

public class AdministracionODV extends UnicastRemoteObject implements InterfazRemotaODV {

	private static final long serialVersionUID = -3832169933384285597L;

	private static ClienteDAO clienteDao;
	private static CotizacionDAO cotizacionDAO;
	private static FacturaDAO facturaDAO;
	private static OficinaVentasDAO oficinaVentasDAO;
	private static SolicitudCotizacionDAO solicitudCotizacionDAO;
	private static RodamientoDAO rodamientoDAO;
	private static OrdenPedidoDAO ordenPedidoDAO;

	private static AdministracionODV instancia;

	protected AdministracionODV() throws RemoteException {
		super();
		clienteDao = new ClienteDAO();
		cotizacionDAO = new CotizacionDAO();
		solicitudCotizacionDAO = new SolicitudCotizacionDAO();
		facturaDAO = new FacturaDAO();
		oficinaVentasDAO = new OficinaVentasDAO();
		rodamientoDAO = new RodamientoDAO();
		ordenPedidoDAO = new OrdenPedidoDAO();
	}

	public static AdministracionODV getInstancia() throws RemoteException {
		if (instancia == null)
			instancia = new AdministracionODV();
		return instancia;
	}

	@Override
	public void altaCliente(ClienteVO clientevo) throws RemoteException {
		Cliente cliente = new Cliente(clientevo);
		clienteDao.openCurrentSessionwithTransaction();
		clienteDao.persistir(cliente);
		clienteDao.closeCurrentSessionwithTransaction();
	}

	@Override
	public void modificarCliente(ClienteVO clientevo) throws RemoteException {
		clienteDao.openCurrentSessionwithTransaction();
		Cliente cliente = clienteDao.buscarPorId(clientevo.getCuilCliente());
		cliente.setDireccion(clientevo.getDireccion());
		cliente.setRazonSocial(clientevo.getRazonSocial());
		cliente.setTelefono(clientevo.getTelefono());
		clienteDao.actualizar(cliente);
		clienteDao.closeCurrentSessionwithTransaction();
	}

	@Override
	public void bajaCliente(Long CUIL) throws RemoteException {
		clienteDao.openCurrentSessionwithTransaction();
		clienteDao.bajaCliente(CUIL);
		clienteDao.closeCurrentSessionwithTransaction();
	}
	
	@Override
	public List<ClienteVO> listarClientes() throws RemoteException {
		clienteDao.openCurrentSessionwithTransaction();
		List<Cliente> clientes = clienteDao.buscarTodos();
		List<ClienteVO> clientesVO = new ArrayList<ClienteVO>();
		for (Cliente cliente : clientes){
			ClienteVO clienteVO = cliente.getView();
			clientesVO.add(clienteVO);
		}
		clienteDao.closeCurrentSessionwithTransaction();
		return clientesVO;
	}
	
	@Override
	public ClienteVO buscarCliente(Long id) throws RemoteException {
		clienteDao.openCurrentSessionwithTransaction();
		Cliente cliente = clienteDao.buscarPorId(Long.valueOf(id));
		clienteDao.closeCurrentSessionwithTransaction();
		return cliente.getView();
	}
	
	public Cliente buscarCliente2(Long id) throws RemoteException {
		clienteDao.openCurrentSessionwithTransaction();
		Cliente cliente = clienteDao.buscarPorId(Long.valueOf(id));
		clienteDao.closeCurrentSessionwithTransaction();
		return cliente;
	}

	public void generarCotizacion(SolicitudCotizacion solicitudCotizacion, int diasValidez) throws RemoteException {
		Cotizacion cotizacion = new Cotizacion();
		cotizacion.setCliente(solicitudCotizacion.getCliente());
		cotizacion.setDiasValidez(diasValidez);
		cotizacion.setFecha(Calendar.getInstance().getTime());
		cotizacion.setSolicitudCotizacion(solicitudCotizacion);
		ComparativaPreciosDAO comparativaPreciosDAO = new ComparativaPreciosDAO();
		comparativaPreciosDAO.openCurrentSession();
		for (ItemSolicitudCotizacion itemSolicitud : solicitudCotizacion.getItems()){
			ComparativaPrecios comparativaPrecios = comparativaPreciosDAO.getComparativa();
			try{
				ItemComparativaPrecio itemComparativaPrecio = comparativaPrecios.getMejorPrecio(itemSolicitud);
				if (itemComparativaPrecio != null)
					cotizacion.add(itemSolicitud.getId().getRodamiento(), itemSolicitud.getCantidad(), itemComparativaPrecio.getProveedor(), itemComparativaPrecio.getMejorPrecio());
			}catch (Exception e){
				System.out.println(e);
			}
			
		}
		comparativaPreciosDAO.closeCurrentSession();
		oficinaVentasDAO.openCurrentSessionwithTransaction();
		OficinaVenta oficinaVenta = oficinaVentasDAO.buscarPorId(solicitudCotizacion.getOficinaVenta().getIdOficinaVenta());
		oficinaVenta.add(cotizacion);
		oficinaVentasDAO.persistir(oficinaVenta);
		oficinaVentasDAO.closeCurrentSessionwithTransaction();
		System.out.println(CotizacionXML.cotizacionXML(cotizacion));
		CotizacionXML cotizacionXML = new CotizacionXML();
		cotizacionXML.cotizacionXML(cotizacion);
	}

	@Override
	public void guardarSolicitudCotizacion(SolicitudCotizacionVO solicitudCotizacionVO) throws RemoteException {
		clienteDao.openCurrentSession();
		Cliente cliente = clienteDao.buscarPorId(solicitudCotizacionVO.getCliente().getCuilCliente());
		clienteDao.closeCurrentSession();
		oficinaVentasDAO.openCurrentSession();
		OficinaVenta oficinaVenta = oficinaVentasDAO.buscarPorId(solicitudCotizacionVO.getOficinaVentasVO().getIdOficina());
		oficinaVentasDAO.closeCurrentSession();
		SolicitudCotizacion solicitudCotizacion = new SolicitudCotizacion(cliente, oficinaVenta);
		solicitudCotizacion.setCliente(cliente);
		solicitudCotizacion.setFecha(solicitudCotizacionVO.getFecha());
		rodamientoDAO.openCurrentSession();
		for (ItemSolicitudCotizacionVO item : solicitudCotizacionVO.getItems()){
			RodamientoId id = new RodamientoId(item.getRodamiento().getCodigoSFK() ,item.getRodamiento().getCodigoPieza()); 
			Rodamiento rodamiento = rodamientoDAO.buscarPorId(id);
			solicitudCotizacion.add(rodamiento, item.getCantidad());
		}
		rodamientoDAO.closeCurrentSession();
		solicitudCotizacionDAO.openCurrentSessionwithTransaction();
		solicitudCotizacionDAO.persistir(solicitudCotizacion);
		solicitudCotizacionDAO.closeCurrentSessionwithTransaction();		
		CotizacionXML cotizacionXML = new CotizacionXML();
		cotizacionXML.solicitudCotizacionXML(solicitudCotizacion);
	}

	@Override
	public SolicitudCotizacionVO dameSolicitud(int nroSolicitud) throws RemoteException {
		solicitudCotizacionDAO.openCurrentSessionwithTransaction();
		SolicitudCotizacion solicitud = solicitudCotizacionDAO.buscarPorId(nroSolicitud);
		solicitudCotizacionDAO.closeCurrentSessionwithTransaction();
		return solicitud.getView();
	}

	@Override
	public RodamientoVO obtenerRodamiento(String SFK, String codigo) throws RemoteException {
		rodamientoDAO.openCurrentSessionwithTransaction();
		Rodamiento rodamiento = rodamientoDAO.getRodamiento(SFK, codigo);
		rodamientoDAO.closeCurrentSessionwithTransaction();
		return rodamiento.getView();
	}

	@Override
	public List<RodamientoVO> traerRodamientos() throws RemoteException {
		rodamientoDAO.openCurrentSessionwithTransaction();
		List<Rodamiento> rodamientos = rodamientoDAO.buscarTodos();
		List<RodamientoVO> rodamientosVO = new ArrayList<RodamientoVO>();
		for (Rodamiento rodamiento : rodamientos) {
			rodamientosVO.add(rodamiento.getView());
		}
		rodamientoDAO.closeCurrentSessionwithTransaction();
		return rodamientosVO;
	}

	@Override
	public CotizacionVO dameCotizacion(int nroCotizacion) throws RemoteException {
		cotizacionDAO.openCurrentSessionwithTransaction();
		Cotizacion cotizacionHibernate = cotizacionDAO.buscarPorId(nroCotizacion);
		CotizacionVO cotizacionVO = null;
		if (cotizacionHibernate != null){
			cotizacionVO = cotizacionHibernate.getView();
		}
		cotizacionDAO.closeCurrentSessionwithTransaction();
		return cotizacionVO;
	}

	@Override
	public List<SolicitudCotizacionVO> listarSolicitudesCotizacion() throws RemoteException {
		solicitudCotizacionDAO.openCurrentSessionwithTransaction();
		List<SolicitudCotizacion> solicitudes = solicitudCotizacionDAO.buscarTodos();
		List<SolicitudCotizacionVO> solicitudesVO = new ArrayList<SolicitudCotizacionVO>();
		for (SolicitudCotizacion solicitudCotizacionVO : solicitudes){
			solicitudesVO.add(solicitudCotizacionVO.getView());
		}
		solicitudCotizacionDAO.closeCurrentSessionwithTransaction();
		return solicitudesVO;
	}

	@Override
	public void aprobarCotizacion(CotizacionVO cotizacionVO) throws RemoteException {
		cotizacionDAO.openCurrentSessionwithTransaction();
		Cotizacion cotizacion = cotizacionDAO.buscarPorId(cotizacionVO.getNroCotizacion());
		cotizacion.setFecha(Calendar.getInstance().getTime());
		OrdenPedido ordenDePedido = new OrdenPedido();
		ordenDePedido.setCliente(cotizacion.getCliente());
		ordenDePedido.setEstado(false);
		ordenDePedido.setFecha(cotizacion.getFecha());
		for (ItemCotizacionVO item: cotizacionVO.getItems()){
			if (item.getEstado().equals(EstadoCotizacion.APROBADA.toString())){
				for (ItemCotizacion itemSolicitud : cotizacion.getItems()){
					if (itemSolicitud.getId().getRodamiento().getRodamientoId().getCodigoPieza().equals(item.getRodamiento().getCodigoPieza()) && itemSolicitud.getId().getRodamiento().getRodamientoId().getCodigoSFK().equals(item.getRodamiento().getCodigoSFK())){
						itemSolicitud.setEstadoCotizacion(EstadoCotizacion.APROBADA);
					}
				}
			}
		}
		cotizacionDAO.actualizar(cotizacion);
		cotizacionDAO.closeCurrentSessionwithTransaction();
		CotizacionXML cotizacionXML = new CotizacionXML();
		cotizacionXML.cotizacionAceptadasXML(cotizacion);
		cotizacionXML.borrarCotizacionXML(cotizacion);
	}

	@Override
	public List<CotizacionVO> dameCotizaciones() throws RemoteException {
		cotizacionDAO.openCurrentSessionwithTransaction();
		List<CotizacionVO> cotizacionesVO = new ArrayList<CotizacionVO>();
		List<Cotizacion> cotizaciones = cotizacionDAO.buscarTodos();
		for(Cotizacion cotizacion :cotizaciones){ 
			CotizacionVO coti = cotizacion.getView();
			for (ItemCotizacion items : cotizacion.getItems()){
				List<ItemCotizacionVO> colleccion = new ArrayList<ItemCotizacionVO>();
				colleccion.add(items.getView());
			}
			cotizacionesVO.add(coti);
		}
		cotizacionDAO.closeCurrentSessionwithTransaction();
		return cotizacionesVO;
	}

	@Override
	public void generarFactura(CotizacionVO cotizacionVO) throws RemoteException {
		facturaDAO.openCurrentSessionwithTransaction();
		Cotizacion cotizacion = cotizacionDAO.buscarPorId(cotizacionVO.getNroCotizacion());
		facturaDAO.closeCurrentSessionwithTransaction();
		Calendar fechaActual = Calendar.getInstance();
		Date fecha = fechaActual.getTime();
		Factura factura = new Factura();
		factura.setFecha(fecha);
		factura.setCliente(cotizacion.getCliente());
		RemitoExterior remito = new RemitoExterior();
		for (ItemCotizacion itemCotizacion : cotizacion.getItems()){
			if (itemCotizacion.getEstadoCotizacion().equals(EstadoCotizacion.APROBADA)){
				factura.add(itemCotizacion.getId().getRodamiento(), itemCotizacion.getCantidad(), itemCotizacion.getPrecioUnitario());
				remito.add(itemCotizacion.getId().getRodamiento(), itemCotizacion.getCantidad());
			}
		}
		factura.setRemito(remito);
		facturaDAO.openCurrentSessionwithTransaction();
		facturaDAO.persistir(factura);
		facturaDAO.closeCurrentSessionwithTransaction();
	}

	@Override
	public List<OficinaVentasVO> obtenerOficinas() throws RemoteException {
		oficinaVentasDAO.openCurrentSessionwithTransaction();
		List<OficinaVenta> oficinas = oficinaVentasDAO.buscarTodos();
		List<OficinaVentasVO> oficinasVO = new ArrayList<OficinaVentasVO>();
		for (OficinaVenta oficinaVentas : oficinas){
			oficinasVO.add(oficinaVentas.getView());
		}
		oficinaVentasDAO.closeCurrentSessionwithTransaction();
		return oficinasVO;
	}

	@Override
	public OficinaVentasVO dameOficina(int idOficina) throws RemoteException {
		oficinaVentasDAO.openCurrentSessionwithTransaction();
		OficinaVenta oficina = oficinaVentasDAO.buscarPorId(idOficina);
		oficinaVentasDAO.closeCurrentSessionwithTransaction();
		return oficina.getView();
	}

}
