package util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import com.group7.entity.Rodamiento;
import com.group7.entity.enbeddable.RodamientoId;

public class HibernateUtil{
	
    private static final SessionFactory sessionFactory;
    
	    static
	    {
	        try
	        {
	        	 	AnnotationConfiguration config = new AnnotationConfiguration();
	        	 	config.addAnnotatedClass(Rodamiento.class);
	        	 	config.addAnnotatedClass(RodamientoId.class);
	           	   sessionFactory = config.buildSessionFactory();
	        }
	        catch (Throwable ex)
	        {
	            System.err.println("Initial SessionFactory creation failed." + ex);
	            throw new ExceptionInInitializerError(ex);
	        }
    }
 
    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }
    
}
