package es.iespuertodelacruz.ahmrlh.tiendainformatica.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.RollbackException;

import es.iespuertodelacruz.ahmrlh.tiendainformatica.entitites.*;

public class CategoriaRepository implements JPACRUD<Categoria, Integer> {
	
	private EntityManagerFactory emf;

	public CategoriaRepository(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public List<Categoria> findAll() {
		List<Categoria> categorias = null;
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		categorias = (List<Categoria>) em.createNamedQuery("Categoria.findAll", Categoria.class).getResultList();
		em.getTransaction().commit();
		
		em.close();	
		return categorias;
	}

	@Override
	public Categoria findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Categoria save(Categoria categoria) {
		Categoria categoriaSaved = null;
		EntityManager em = emf.createEntityManager();
		
		if(categoria!=null) {
			em.getTransaction().begin();
			em.persist(categoria);
			em.getTransaction().commit();
			
			categoriaSaved = new Categoria();
			categoriaSaved.setIdCategoria(categoria.getIdCategoria());
			categoriaSaved.setNombre(categoria.getNombre());
			
		}
		
		em.close();
		
		return categoriaSaved;
	}

	@Override
	public Categoria update(Categoria categoria) {
		EntityManager em = emf.createEntityManager();
		Categoria categoriaUpdate = null;
		
		if(categoria != null) {
			if (!(categoria.getIdCategoria() < 0 || categoria.getNombre() == null)) {
				try {
					em.getTransaction().begin();
					categoriaUpdate = em.merge(categoria); 
					em.getTransaction().commit();
					
					categoriaUpdate.setIdCategoria(categoria.getIdCategoria());
					categoriaUpdate.setNombre(categoria.getNombre());
					
				}catch(RollbackException ex) {
					ex.printStackTrace();
					categoriaUpdate = null;
				}
			}
		}
		
		em.close();
		return categoriaUpdate;
	}

	@Override
	public boolean delete(Integer id) {
		EntityManager em = emf.createEntityManager();
		Categoria categoria = null;
		boolean delete = false;
		
		if(id != null) {
			
			try {
				categoria = em.find(Categoria.class,id);
				em.getTransaction().begin();
				em.remove(categoria);
				em.getTransaction().commit();
				
				delete = true;
				
			} catch (RollbackException e) {
				e.printStackTrace();
				delete = false;
			}
		}
	
		em.close();
		return delete;
	}

}
