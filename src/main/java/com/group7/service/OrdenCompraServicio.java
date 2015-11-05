package com.group7.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.group7.business.OrdenCompraVO;
import com.group7.business.OrdenPedidoVO;
import com.group7.business.RodamientoVO;
import com.group7.dao.OrdenCompraDAO;
import com.group7.entity.ItemOrdenPedido;
import com.group7.entity.ItemsComparativaPrecio;
import com.group7.entity.OrdenCompra;
import com.group7.entity.OrdenPedido;

public class OrdenCompraServicio {

	private static OrdenCompraDAO ordenCompraDAO;
	private static OrdenCompraServicio instancia;

	public static OrdenCompraServicio getInstancia() {
		if (instancia == null)
			instancia = new OrdenCompraServicio();
		return instancia;
	}

	private OrdenCompraServicio() {
		ordenCompraDAO = new OrdenCompraDAO();
	}

	public OrdenCompra VoAHibernate(OrdenCompraVO ordenVO) {
		OrdenCompra orden = new OrdenCompra();
		orden.setNroOrdenCompra(ordenVO.getNroOrdenCompra());
		orden.setFecha(ordenVO.getFecha());
		orden.setProveedor(ProveedorServicio.getInstancia().VoAHibernate(ordenVO.getProveedor()));
		orden.setItems(ItemOrdenCompraServicio.getInstancia().VoAHibernate(ordenVO.getItems()));
		return orden;
	}

	public OrdenCompraVO ordenCompra2Vo(OrdenCompra orden) {
		OrdenCompraVO ordenVO = new OrdenCompraVO();
		ordenVO.setNroOrdenCompra(orden.getNroOrdenCompra());
		ordenVO.setFecha(orden.getFecha());
		ordenVO.setProveedor(ProveedorServicio.getInstancia().HibernateAVo(orden.getProveedor()));
		ordenVO.setItems(ItemOrdenCompraServicio.getInstancia().HibernateAVo(orden.getItems()));
		return ordenVO;
	}

	public List<OrdenCompraVO> dameOrdenes() {
		ordenCompraDAO.openCurrentSessionwithTransaction();
		List<OrdenCompra> ordenes = ordenCompraDAO.buscarTodos();
		List<OrdenCompraVO> ordenesVO = new ArrayList<OrdenCompraVO>();
		for (int i = 0; ordenes.size() - 1 >= i; i++) {
			ordenesVO.add(this.ordenCompra2Vo(ordenes.get(i)));
		}
		ordenCompraDAO.closeCurrentSession();
		return ordenesVO;
	}

	public void altaOrdenCompraManual(List<RodamientoVO> rodamientos, List<Integer> cantidades) {
		ordenCompraDAO.openCurrentSessionwithTransaction();
		Calendar fechaActual = Calendar.getInstance();
		Date fecha = fechaActual.getTime();
		int i = 0;
		while (rodamientos.size() - 1 >= i) {
			for (int j = 0; cantidades.size() - 1 >= j; j++) {
				ItemsComparativaPrecio item = ItemComparativaPreciosServicio.getInstancia()
						.dameItemsProveedor(RodamientoServicio.getInstancia().VoAHibernate(rodamientos.get(i)));
				OrdenCompra orden = new OrdenCompra();
				orden.setFecha(fecha);
				orden.setProveedor(item.getProveedorListaPrecios());
				ordenCompraDAO.persistir(orden);
				ItemOrdenCompraServicio.getInstancia().guardarItem(orden.getNroOrdenCompra(), item, cantidades.get(j));
				i++;
			}
		}
		ordenCompraDAO.closeCurrentSession();
	}

	public OrdenCompraVO dameOrden(int nroOrdenCompra) {
		ordenCompraDAO.openCurrentSessionwithTransaction();
		OrdenCompra orden = ordenCompraDAO.dameOrdenCompra(nroOrdenCompra);
		ordenCompraDAO.closeCurrentSession();
		return this.ordenCompra2Vo(orden);
	}

	public void generarOrden(OrdenPedidoVO ordenDePedido) {
		ordenCompraDAO.openCurrentSessionwithTransaction();
		Calendar fechaActual = Calendar.getInstance();
		Date fecha = fechaActual.getTime();
		OrdenCompra ordenDeCompra = new OrdenCompra();
		OrdenPedido ordenPedido = OrdenPedidoServicio.getInstancia().dameOrden(ordenDePedido.getNroOrdenPedido());

		for (ItemOrdenPedido item : ordenPedido.getItems()){
			List<ItemOrdenPedido> itemsTemp = ItemOrdenPedidoServicio.getInstancia().dameTemporales(ordenDePedido.getNroOrdenPedido(), item.getProveedor());
			if (itemsTemp != null) {
				ordenDeCompra.setFecha(fecha);
				ordenCompraDAO.persistir(ordenDeCompra);
				for (int j = 0; itemsTemp.size() - 1 >= j; j++) {
					ItemOrdenCompraServicio.getInstancia().guardarlos(ordenDeCompra.getNroOrdenCompra(), itemsTemp.get(j));
					ordenCompraDAO.actualizarProveedor(itemsTemp.get(j).getProveedor());
				}
			}
		}
		ordenCompraDAO.closeCurrentSession();
	}

}
