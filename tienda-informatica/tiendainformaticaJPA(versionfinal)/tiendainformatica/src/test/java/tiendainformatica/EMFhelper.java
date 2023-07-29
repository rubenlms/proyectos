package tiendainformatica;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EMFhelper {
	
	private static EMFhelper emfhelper;
	private EntityManagerFactory emf;

	public EMFhelper() {
		emf = Persistence.createEntityManagerFactory("tiendainformatica");
	}
	
	public static EMFhelper getSingleton() {
		if(emfhelper == null) {
			emfhelper = new EMFhelper();
		}
		
		return emfhelper;
	}
	
	public EntityManagerFactory getEmf() {
		return emf;
	}

}
