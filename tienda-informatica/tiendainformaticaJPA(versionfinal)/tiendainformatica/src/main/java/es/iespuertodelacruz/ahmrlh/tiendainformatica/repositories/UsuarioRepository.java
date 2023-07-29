package es.iespuertodelacruz.ahmrlh.tiendainformatica.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.RollbackException;

import org.mindrot.jbcrypt.BCrypt;

import es.iespuertodelacruz.ahmrlh.tiendainformatica.entitites.Pedido;
import es.iespuertodelacruz.ahmrlh.tiendainformatica.entitites.Usuario;

public class UsuarioRepository implements JPACRUD<Usuario, String>{
	
	private EntityManagerFactory emf;

	public UsuarioRepository(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public List<Usuario> findAll() {
		List<Usuario>usuarios = null;
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		usuarios = (List<Usuario>) em.createNamedQuery("Usuario.findAll", Usuario.class).getResultList();
		em.getTransaction().commit();
		em.close();
		
		return usuarios;
	}

	@Override
	public Usuario findById(String id) {
		Usuario usuario = null;
		EntityManager em = emf.createEntityManager();
		
		if(id!=null) {
			try {
				em.getTransaction().begin();
				usuario = em.find(Usuario.class, id);
				
				if(usuario != null) {
					em.persist(usuario);
					em.getTransaction().commit();
				}
				
			} catch (RollbackException e) {
				e.printStackTrace();
				usuario = null;
			}
		}
		
		em.close();
		return usuario;
	}

	@Override
	public Usuario save(Usuario usuario) {
		Usuario usuarioSaved = null;
		EntityManager em = emf.createEntityManager();
		
		if(usuario!=null) {
			if (usuario.getIdUsuario()!=null || usuario.getNombre()!=null || usuario.getPassword()!=null || usuario.getDireccion()!=null || usuario.getRol()!=null) {
				System.out.println("entro en saveee");
				String passwordHash = BCrypt.hashpw(usuario.getPassword(), BCrypt.gensalt(10));
				usuario.setPassword(passwordHash);
				em.getTransaction().begin();
				em.persist(usuario);
				em.getTransaction().commit();
				
				usuarioSaved = new Usuario();
				usuarioSaved.setIdUsuario(usuario.getIdUsuario());
				usuarioSaved.setNombre(usuario.getNombre());
				usuarioSaved.setPassword(passwordHash);
				usuarioSaved.setDireccion(usuario.getDireccion());
				if(usuario.getTelefono()>0) {
					usuarioSaved.setTelefono(usuario.getTelefono());
				}
				if(usuario.getPedidos()!=null) {
					usuarioSaved.setPedidos(usuario.getPedidos());
				}
				usuarioSaved.setRol(usuario.getRol());
				
			}
			
		}
		
		em.close();
		
		return usuarioSaved;
	}

	@Override
	public Usuario update(Usuario usuario) {
		EntityManager em = emf.createEntityManager();
		Usuario usuarioUpdate = null;
		
		if(usuario!=null) {
			if (!(usuario.getIdUsuario()==null || usuario.getNombre()==null || usuario.getPassword()==null || usuario.getDireccion()==null || usuario.getRol()==null)) {
				
				String passwordHash = BCrypt.hashpw(usuario.getPassword(), BCrypt.gensalt(10));
				
				em.getTransaction().begin();
				usuarioUpdate = em.merge(usuario); 
				
				usuarioUpdate.setIdUsuario(usuario.getIdUsuario());
				usuarioUpdate.setNombre(usuario.getNombre());
				usuarioUpdate.setPassword(passwordHash);
				usuarioUpdate.setDireccion(usuario.getDireccion());
				if(usuario.getTelefono()>0) {
					usuarioUpdate.setTelefono(usuario.getTelefono());
				}
				if(usuario.getPedidos()!=null) {
					usuarioUpdate.setPedidos(usuario.getPedidos());
				}
				usuarioUpdate.setRol(usuario.getRol());
				
				em.getTransaction().commit();
			}
			
			em.close();
			
		}
		return usuarioUpdate;
	}

	@Override
	public boolean delete(String id) {
		Usuario usuario = null;
		boolean delete = false;
		EntityManager em = emf.createEntityManager();
		
		if(id!=null) {
			
			try {
				em.getTransaction().begin();
				usuario = em.find(Usuario.class, id);
				
				if(usuario!=null) {
					em.remove(usuario);
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
