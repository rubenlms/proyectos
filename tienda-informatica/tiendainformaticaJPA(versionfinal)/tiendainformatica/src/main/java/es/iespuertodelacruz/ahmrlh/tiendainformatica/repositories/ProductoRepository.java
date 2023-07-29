package es.iespuertodelacruz.ahmrlh.tiendainformatica.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.RollbackException;

import es.iespuertodelacruz.ahmrlh.tiendainformatica.entitites.Producto;

public class ProductoRepository implements JPACRUD<Producto, String>{

	private EntityManagerFactory emf;

	public ProductoRepository(EntityManagerFactory emf) {
		this.emf = emf;
	}
	
	@Override
	public List<Producto> findAll() {
		List<Producto> productos = null;
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		productos = (List<Producto>) em.createNamedQuery("Producto.findAll", Producto.class).getResultList();
		em.getTransaction().commit();
		
		em.close();
		return productos;
	}
	
	@Override
	public Producto findById(String id) {
		Producto producto = null;
		EntityManager em = emf.createEntityManager();
		
		if(id != null) {
			try {
				em.getTransaction().begin();
				producto = em.find(Producto.class, Integer.parseInt(id));
				
				if(producto != null) {
					em.persist(producto);
					em.getTransaction().commit();
				}
				
			} catch (RollbackException e) {
				e.printStackTrace();
				producto = null;
			}
		}
		
		em.close();
		return producto;
	}

	@Override
	public Producto save(Producto producto) {
		Producto productoSaved = null;
		EntityManager em = emf.createEntityManager();
		
		if(producto != null) {
			if (!(producto.getIdProducto() < 0 || producto.getNombre() == null || producto.getCategoria() == null || producto.getPrecio() < 0 || producto.getStock() < 0)) {
				try {
					em.getTransaction().begin();
					em.persist(producto);
					em.getTransaction().commit();
					
					productoSaved = new Producto();
					
					productoSaved.setIdProducto(producto.getIdProducto());
					productoSaved.setNombre(producto.getNombre());
					
					if(producto.getDescripcion() != null) {
						productoSaved.setDescripcion(producto.getDescripcion());
					}
					
					productoSaved.setCategoria(producto.getCategoria());
					productoSaved.setPrecio(producto.getPrecio());
					productoSaved.setStock(producto.getStock());
					
				}catch (RollbackException e) {
					e.printStackTrace();
					productoSaved = null;
				}				
			}
			
		}
		
		em.close();
		return productoSaved;
	}

	@Override
	public Producto update(Producto producto) {
		EntityManager em = emf.createEntityManager();
		Producto productoUpdate = null;
		
		if(producto != null) {
			if (!(producto.getIdProducto() < 0 || producto.getNombre() == null || producto.getCategoria() == null || producto.getPrecio() < 0 || producto.getStock() < 0)) {
				try {
					em.getTransaction().begin();
					productoUpdate = em.merge(producto); 
					em.getTransaction().commit();
					
					productoUpdate.setIdProducto(producto.getIdProducto());
					productoUpdate.setNombre(producto.getNombre());
					
					if(producto.getDescripcion() != null) {
						productoUpdate.setDescripcion(producto.getDescripcion());
					}
					
					productoUpdate.setCategoria(producto.getCategoria());
					productoUpdate.setPrecio(producto.getPrecio());
					productoUpdate.setStock(producto.getStock());
					
				}catch (RollbackException e) {
					e.printStackTrace();
					productoUpdate = null;
				}
			}
		}
		
		em.close();
		return productoUpdate;
	}

	@Override
	public boolean delete(String id) {
		EntityManager em = emf.createEntityManager();
		Producto producto = null;
		boolean delete = false;
		
		if(id!=null) {
			
			try {
				em.getTransaction().begin();
				producto = em.find(Producto.class, Integer.parseInt(id));
				
				if(producto != null) {
					em.remove(producto);
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
