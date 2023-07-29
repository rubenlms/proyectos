package es.iespuertodelacruz.ahmrlh.tiendainformatica.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;

import es.iespuertodelacruz.ahmrlh.tiendainformatica.entitites.Factura;
import es.iespuertodelacruz.ahmrlh.tiendainformatica.entitites.Pedido;
import es.iespuertodelacruz.ahmrlh.tiendainformatica.entitites.Usuario;

public class FacturasRepository implements JPACRUD<Factura, Integer> {
	
	private EntityManagerFactory emf;
	
	public FacturasRepository(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public List<Factura> findAll() {
		List<Factura>facturas = null;
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		facturas = (List<Factura>) em.createNamedQuery("Factura.findAll", Factura.class).getResultList();
		em.getTransaction().commit();
		em.close();
		
		return facturas;
	}

	@Override
	public Factura findById(Integer id) {
		Factura factura = null;
		EntityManager em = emf.createEntityManager();
		
		if(id!=null) {
			try {
				em.getTransaction().begin();
				factura = em.find(Factura.class, id);
				
				if(factura != null) {
					em.persist(factura);
					em.getTransaction().commit();
				}
			} catch (RollbackException e) {
				e.printStackTrace();
				factura = null;
			}
		}
		
		em.close();
		return factura;
	}
	
	public List<Factura> findbyPedido(Integer id) {
		//String query="select facturas.* from facturas inner join pedidos on pedidos.id_pedido = facturas.id_pedido";
		EntityManager em = emf.createEntityManager();
		List<Factura>facturasporpedido = null;
		Pedido pedido = em.find(Pedido.class, id);
		
		if(id>0) {
			facturasporpedido = em.createNamedQuery("Factura.findbyPedido", Factura.class).setParameter("pedido", pedido).getResultList();
		}
		
		em.close();
		return facturasporpedido;
	}
	
	/**
	 * Recupera el producto que se ha asociado en factura a un pedido con estado carrito
	 * @param id
	 * @return
	 */
	public Factura findCarrito(Integer id) {
		EntityManager em = emf.createEntityManager();
		Factura productoCarrito = null;
		Pedido pedido = em.find(Pedido.class, id);
		
		if(pedido!=null) {
			//productoCarrito = ((TypedQuery<Factura>) em.createNamedQuery("Factura.findCarrito", Factura.class).getSingleResult()).setParameter("pedido", pedido);
		}
		
		em.close();
		return productoCarrito;
	}
	
	@Override
	public Factura save(Factura factura) {
		EntityManager em = emf.createEntityManager();
		Factura facturaSaved = null;
		
		if(factura!=null) {
			
			if(!(factura.getIdFactura()<0 || factura.getPedido()==null || factura.getProducto()==null || factura.getCantidad()<0 || factura.getPrecio()==null)) {
				try {
					em.getTransaction().begin();
					em.persist(factura);
					em.getTransaction().commit();
					
					facturaSaved = new Factura();
					facturaSaved.setIdFactura(factura.getIdFactura());
					facturaSaved.setProducto(factura.getProducto());
					facturaSaved.setPedido(factura.getPedido());
					facturaSaved.setCantidad(factura.getCantidad());
					facturaSaved.setPrecio(factura.getPrecio());
					
				}catch(RollbackException ex) {
					ex.printStackTrace();
					facturaSaved = null;
				}
			}
		}
		
		em.close();
		return facturaSaved;
	}

	@Override
	public Factura update(Factura factura) {
		EntityManager em = emf.createEntityManager();
		Factura facturaUpdate = null;
		
		if (factura!=null) {
			
			if(!(factura.getIdFactura()<0 || factura.getPedido()==null || factura.getProducto()==null || factura.getCantidad()<0 || factura.getPrecio()==null)) {
				try {
					em.getTransaction().begin();
					facturaUpdate = em.merge(factura);
					em.getTransaction().commit();
					
					facturaUpdate.setIdFactura(factura.getIdFactura());
					facturaUpdate.setPedido(factura.getPedido());
					facturaUpdate.setProducto(factura.getProducto());
					facturaUpdate.setPrecio(factura.getPrecio());
					facturaUpdate.setCantidad(factura.getCantidad());
					
				}catch(RollbackException ex) {
					ex.printStackTrace();
					facturaUpdate = null;
				}
			}
		}
		
		em.close();
		return facturaUpdate;
	}

	@Override
	public boolean delete(Integer id) {
		EntityManager em = emf.createEntityManager();
		boolean delete = false;
		Factura factura = null;
		
		if(id>0) {
			
			try {
				
				em.getTransaction().begin();
				factura = em.find(Factura.class, id);
				
				if(factura!=null) {
					em.remove(factura);
					em.getTransaction().commit();
					delete = true;
				}
				
			} catch (RollbackException e) {
				e.printStackTrace();
				delete = false;
			}
		}
		
		em.close();
		return delete;
	}

}
