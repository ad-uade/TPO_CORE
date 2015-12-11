package com.app;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.group7.business.ClienteVO;
import com.group7.business.ComparativaPreciosVO;
import com.group7.business.CondicionVentaVO;
import com.group7.business.CotizacionVO;
import com.group7.business.ItemsComparativaPreciosVO;
import com.group7.business.OficinaVentasVO;
import com.group7.business.RemitoExteriorVO;
import com.group7.business.RemitoVO;
import com.group7.business.RodamientoVO;
import com.group7.business.SolicitudCotizacionVO;
import com.group7.dao.ClienteDAO;
import com.group7.dao.CotizacionDAO;
import com.group7.dao.OrdenPedidoDAO;
import com.group7.dao.SolicitudCotizacionDAO;
import com.group7.entity.Cliente;
import com.group7.entity.ComparativaPrecios;
import com.group7.entity.Cotizacion;
import com.group7.entity.ItemComparativaPrecio;
import com.group7.entity.OrdenPedido;
import com.group7.entity.Remito;
import com.group7.entity.SolicitudCotizacion;
import com.group7.remote.InterfazRemotaODV;
import com.group7.service.ComparativaPreciosServicio;
import com.group7.service.CondicionVentaServicio;
import com.group7.service.CotizacionServicio;
import com.group7.service.FacturaServicio;
import com.group7.service.ItemComparativaPreciosServicio;
import com.group7.service.ItemCotizacionServicio;
import com.group7.service.ItemOrdenPedidoServicio;
import com.group7.service.OficinaVentasServicio;
import com.group7.service.OrdenPedidoServicio;
import com.group7.service.RemitoServicio;
import com.group7.service.RodamientoServicio;

public class AdministracionODV extends UnicastRemoteObject implements InterfazRemotaODV {

	private static final long serialVersionUID = -3832169933384285597L;

	private static ClienteDAO clienteDao;
	private static CotizacionDAO cotizacionDAO;
	private static OrdenPedidoDAO ordenPedidoDAO;
	private static SolicitudCotizacionDAO solicitudCotizacionDAO;

	private static AdministracionODV instancia;

	protected AdministracionODV() throws RemoteException {
		super();
		clienteDao = new ClienteDAO();
		cotizacionDAO = new CotizacionDAO();
		ordenPedidoDAO = new OrdenPedidoDAO();
		solicitudCotizacionDAO = new SolicitudCotizacionDAO();
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
		Cliente cliente = new Cliente(clientevo);
		clienteDao.openCurrentSessionwithTransaction();
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

	@Override
	public void generarCotizacion(SolicitudCotizacionVO solicitudCotizacionVO, int diasValidez) throws RemoteException {
		SolicitudCotizacion solicitud = new SolicitudCotizacion(solicitudCotizacionVO);

		Calendar fechaActual = Calendar.getInstance();
		Date fecha = fechaActual.getTime();

		Cotizacion cotizacion = new Cotizacion();
		cotizacion.setDiasValidez(diasValidez);
		cotizacion.setFecha(fecha);
		cotizacion.setCliente(solicitud.getCliente());
		cotizacion.setSolicitudCotizacion(solicitud);
		cotizacion.setOficinaVentas(solicitud.getCliente().getOficinaVentas());
		cotizacionDAO.openCurrentSessionwithTransaction();
		cotizacionDAO.persistir(cotizacion);
		cotizacionDAO.closeCurrentSessionwithTransaction();

		ComparativaPrecios comparativa = ComparativaPreciosServicio.getInstancia().dameComparativa();
		List<ItemComparativaPrecio> itemsComparativa = ItemComparativaPreciosServicio.getInstancia().dameItems();

		ComparativaPreciosVO comparativaVO = ComparativaPreciosServicio.getInstancia().modelToView(comparativa);
		List<ItemsComparativaPreciosVO> itemsVO = ItemComparativaPreciosServicio.getInstancia()
				.itemsComparativaHAVO(itemsComparativa);
		comparativaVO.setItems(itemsVO);

		for (int i = 0; comparativaVO.getItems().size() - 1 >= i; i++) {
			for (int j = 0; solicitud.getItems().size() - 1 >= j; j++) {
				if (comparativaVO.getItems().get(i).getRodamiento().getCodigoSFK().equalsIgnoreCase(
						solicitud.getItems().get(j).getId().getRodamiento().getRodamientoId().getCodigoSFK())) {
					ItemCotizacionServicio.getInstancia().guardarItem(cotizacion, comparativaVO.getItems().get(i),
							solicitud.getItems().get(j));
				}
			}
		}
	}

	@Override
	public void generarOrdenPedido(CotizacionVO cotizacion) throws RemoteException {
		Cotizacion cotizacionH = new Cotizacion(cotizacion);
		OrdenPedidoServicio.getInstancia().guardarOrdenPedido(cotizacionH);
		ordenPedidoDAO.openCurrentSessionwithTransaction();
		Calendar fechaActual = Calendar.getInstance();
		Date fecha = fechaActual.getTime();
		OrdenPedido ordenDePedido = new OrdenPedido();
		ordenDePedido.setCliente(cotizacionH.getCliente());
		ordenDePedido.setEstado(false);
		ordenDePedido.setFecha(fecha);
		ordenPedidoDAO.persistir(ordenDePedido);
		ordenPedidoDAO.closeCurrentSessionwithTransaction();
		for (int i = 0; cotizacionH.getItems().size() - 1 >= i; i++) {
			if (cotizacionH.getItems().get(i).getEstado().equalsIgnoreCase("APROBADO"))
				ItemOrdenPedidoServicio.getInstancia().guardarItems(cotizacionH.getItems().get(i), ordenDePedido);
		}
	}

	@Override
	public void registrarConformidadCliente(int nroRemito) throws RemoteException {
		RemitoServicio.getInstancia().conformarRemito(nroRemito);
	}

	@Override
	public void guardarSolicitudCotizacion(SolicitudCotizacionVO solicitudCotizacionVO) throws RemoteException {
		solicitudCotizacionDAO.openCurrentSessionwithTransaction();
		SolicitudCotizacion solicitudCotizacion = new SolicitudCotizacion(solicitudCotizacionVO);
		solicitudCotizacionDAO.persistir(solicitudCotizacion);
		solicitudCotizacionDAO.closeCurrentSessionwithTransaction();		
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
		RodamientoVO rodamiento = RodamientoServicio.getInstancia().buscarRodamiento(SFK, codigo);
		return rodamiento;
	}

	@Override
	public List<RodamientoVO> traerRodamientos() throws RemoteException {
		List<RodamientoVO> rodamientos = RodamientoServicio.getInstancia().dameRodamientos();
		return rodamientos;
	}

	@Override
	public CotizacionVO dameCotizacion(int nroCotizacion) throws RemoteException {
		CotizacionVO cotizacion = CotizacionServicio.getInstancia().buscarCotizacion(nroCotizacion);
		return cotizacion;
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
	public void aprobarCotizacion(CotizacionVO cotizacion) throws RemoteException {
		CotizacionServicio.getInstancia().actualizarCotizacion(cotizacion);
	}

	@Override
	public List<CotizacionVO> dameCotizaciones() throws RemoteException {
		List<CotizacionVO> cotizaciones = CotizacionServicio.getInstancia().buscarCotizaciones();
		return cotizaciones;
	}

	@Override
	public RemitoVO dameRemito(int nroRemito) throws RemoteException {
		RemitoVO remito = RemitoServicio.getInstancia().obtenerRemitoExteriorVO(nroRemito);
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
		return OficinaVentasServicio.getInstancia().buscarOficinas();
	}

	@Override
	public OficinaVentasVO dameOficina(int idOficina) throws RemoteException {
		OficinaVentasVO oficinaVO = OficinaVentasServicio.getInstancia().buscarOficina(idOficina);
		return oficinaVO;
	}

	@Override
	public List<RemitoExteriorVO> traerRemitosNoConformados() throws RemoteException {
		List<RemitoExteriorVO> remitos = RemitoServicio.getInstancia().buscarRemitosNoConformados();
		return remitos;
	}

	@Override
	public List<CondicionVentaVO> buscarCondiciones() throws RemoteException {
		List<CondicionVentaVO> condiciones = CondicionVentaServicio.getInstancia().buscarCondiciones();
		return condiciones;
	}

	@Override
	public CondicionVentaVO dameCondicion(int nroCondicion) throws RemoteException {
		CondicionVentaVO condicion = CondicionVentaServicio.getInstancia().buscarCondicion(nroCondicion);
		return condicion;
	}
}
