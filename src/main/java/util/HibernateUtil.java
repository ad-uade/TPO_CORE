package util;

import java.lang.reflect.Field;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.impl.SessionFactoryImpl;

import com.group7.entity.CasaCentral;
import com.group7.entity.Cliente;
import com.group7.entity.ComparativaPrecios;
import com.group7.entity.CondicionCompra;
import com.group7.entity.CondicionVenta;
import com.group7.entity.Contado;
import com.group7.entity.Cotizacion;
import com.group7.entity.CuentaCorriente;
import com.group7.entity.EstrategiaDescuentoCliente;
import com.group7.entity.Factura;
import com.group7.entity.FormaPago;
import com.group7.entity.ItemComparativaPrecio;
import com.group7.entity.ItemCotizacion;
import com.group7.entity.ItemFactura;
import com.group7.entity.ItemListaPrecios;
import com.group7.entity.ItemOrdenCompra;
import com.group7.entity.ItemRemito;
import com.group7.entity.ItemSolicitudCotizacion;
import com.group7.entity.ListaPrecios;
import com.group7.entity.Stock;
import com.group7.entity.OficinaVenta;
import com.group7.entity.OrdenCompra;
import com.group7.entity.PorMonto;
import com.group7.entity.PorVolumen;
import com.group7.entity.Proveedor;
import com.group7.entity.Remito;
import com.group7.entity.RemitoExterior;
import com.group7.entity.Rodamiento;
import com.group7.entity.SolicitudCotizacion;
import com.group7.entity.enbeddable.ItemComparativaPrecioId;
import com.group7.entity.enbeddable.ItemCotizacionId;
import com.group7.entity.enbeddable.ItemFacturaId;
import com.group7.entity.enbeddable.ItemListaPreciosId;
import com.group7.entity.enbeddable.ItemOrdenCompraId;
import com.group7.entity.enbeddable.ItemRemitoId;
import com.group7.entity.enbeddable.ItemSolicitudCotizacionId;
import com.group7.entity.enbeddable.RodamientoId;

public class HibernateUtil{
	
    private static final SessionFactory sessionFactory;
    
	    static
	    {
	        try
	        {
	        	 	AnnotationConfiguration config = new AnnotationConfiguration();
	        		config.addAnnotatedClass(Cliente.class);
	        	 	config.addAnnotatedClass(ComparativaPrecios.class);
	        	 	config.addAnnotatedClass(CondicionCompra.class);
	        	 	config.addAnnotatedClass(CondicionVenta.class);
	        	 	config.addAnnotatedClass(Contado.class);
	        	 	config.addAnnotatedClass(Cotizacion.class);
	        	 	config.addAnnotatedClass(CuentaCorriente.class);
	        	 	config.addAnnotatedClass(EstrategiaDescuentoCliente.class);
	        	 	config.addAnnotatedClass(Factura.class);
	        	 	config.addAnnotatedClass(FormaPago.class);
	        	 	config.addAnnotatedClass(ItemComparativaPrecioId.class);
	        	 	config.addAnnotatedClass(ItemCotizacion.class);
	        	 	config.addAnnotatedClass(ItemCotizacionId.class);
	        	 	config.addAnnotatedClass(ItemFactura.class);
	        	 	config.addAnnotatedClass(ItemFacturaId.class);
	        	 	config.addAnnotatedClass(ItemListaPreciosId.class);
	        	 	config.addAnnotatedClass(ItemOrdenCompra.class);
	        	 	config.addAnnotatedClass(ItemOrdenCompraId.class);
	        	 	config.addAnnotatedClass(ItemRemito.class);
	        	 	config.addAnnotatedClass(ItemRemitoId.class);
	        	 	config.addAnnotatedClass(ItemComparativaPrecio.class);
	        	 	config.addAnnotatedClass(ItemListaPrecios.class);
	        	 	config.addAnnotatedClass(ItemSolicitudCotizacion.class);
	        	 	config.addAnnotatedClass(ItemSolicitudCotizacionId.class);
	        	 	config.addAnnotatedClass(ListaPrecios.class);
	        	 	config.addAnnotatedClass(Stock.class);
	        	 	config.addAnnotatedClass(OficinaVenta.class);
	        	 	config.addAnnotatedClass(OrdenCompra.class);
	        	 	config.addAnnotatedClass(PorMonto.class);
	        	 	config.addAnnotatedClass(PorVolumen.class);
	        	 	config.addAnnotatedClass(Proveedor.class);
	        	 	config.addAnnotatedClass(Remito.class);
	        	 	config.addAnnotatedClass(RemitoExterior.class);
	        	 	config.addAnnotatedClass(Rodamiento.class);
	        	 	config.addAnnotatedClass(RodamientoId.class);
	        	 	config.addAnnotatedClass(SolicitudCotizacion.class);
	        	 	config.addAnnotatedClass(CasaCentral.class);
	           	   sessionFactory = config.buildSessionFactory();
	        }catch (Throwable ex){
	            System.err.println("Initial SessionFactory creation failed." + ex);
	            throw new ExceptionInInitializerError(ex);
	        }
    }
 
	public static Properties hibernateProperties() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field f = SessionFactoryImpl.class.getDeclaredField("hibernate.properties");
		f.setAccessible(true);
		return (Properties)f.get(sessionFactory);
    }
	    
    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }
    
}
