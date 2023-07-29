package es.iespuertodelacruz.ahmrlh.tiendainformatica.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.RollbackException;

import es.iespuertodelacruz.ahmrlh.tiendainformatica.entitites.Pedido;
import es.iespuertodelacruz.ahmrlh.tiendainformatica.entitites.Usuario;

public class PedidoRepository implements JPACRUD<Pedido, String>{

	private EntityManagerFactory emf;

	public PedidoRepository(EntityManagerFactory emf) {
		this.emf = emf;
	}
	
	@Override
	public List<Pedido> findAll() {
		List<Pedido> pedidos = null;
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		pedidos = (List<Pedido>) em.createNamedQuery("Pedido.findAll", Pedido.class).getResultList();
		em.getTransaction().commit();
		
		em.close();	
		return pedidos;
	}

	@Override
	public Pedido findById(String id) {
		Pedido producto = null;
		EntityManager em = emf.createEntityManager();
		
		if(id != null) {
			try {
				em.getTransaction().begin();
				producto = em.find(Pedido.class, Integer.parseInt(id));
				
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
	
	public List<Pedido> findByCliente(String id){
		EntityManager em = emf.createEntityManager();
		Usuario usuario = null;
		
		if(id!=null) {
			usuario = em.find(Usuario.class, id);
		}
		
		List<Pedido>pedidos = null;
		
		if(usuario!=null) {
			pedidos = em.createNamedQuery("Pedido.findbyUser", Pedido.class).setParameter("usuario", usuario).getResultList();
		}
			
		return pedidos;
	}

	@Override
	public Pedido save(Pedido pedido) {
		Pedido pedidoSaved = null;
		EntityManager em = emf.createEntityManager();
		
		if(pedido != null) {
			if (!(pedido.getIdPedido() < 0 || pedido.getUsuario() == null || pedido.getFecha() == null || pedido.getEstado() == null)) {
				try {
					em.getTransaction().begin();
					em.persist(pedido);
					em.getTransaction().commit();
					
					pedidoSaved = new Pedido();
					pedidoSaved.setIdPedido(pedido.getIdPedido());
					pedidoSaved.setUsuario(pedido.getUsuario());
					pedidoSaved.setFecha(pedido.getFecha());
					pedidoSaved.setEstado(pedido.getEstado());
					
				}catch(RollbackException ex) {
					ex.printStackTrace();
					pedidoSaved = null;
				}
			}
		}
		
		em.close();
		return pedidoSaved;
	}

	@Override
	public Pedido update(Pedido pedido) {
		EntityManager em = emf.createEntityManager();
		Pedido pedidoUpdate = null;
		
		if(pedido != null) {
			if (!(pedido.getIdPedido() < 0 || pedido.getUsuario() == null || pedido.getFecha() == null || pedido.getEstado() == null)) { //getFacturas()?
				try {
					em.getTransaction().begin();
					pedidoUpdate = em.merge(pedido); 
					em.getTransaction().commit();
					
					pedidoUpdate.setIdPedido(pedido.getIdPedido());
					pedidoUpdate.setUsuario(pedido.getUsuario());
					pedidoUpdate.setFecha(pedido.getFecha());
					pedidoUpdate.setEstado(pedido.getEstado());
					
				}catch(RollbackException ex) {
					ex.printStackTrace();
					pedidoUpdate = null;
				}
			}
		}
		
		em.close();
		return pedidoUpdate;
	}

	@Override
	public boolean delete(String id) {
		EntityManager em = emf.createEntityManager();
		Pedido pedido = null;
		boolean delete = false;
		
		if(id != null) {
			
			try {
				em.getTransaction().begin();
				pedido = em.find(Pedido.class, Integer.parseInt(id));
				
				if(pedido != null) {
					em.remove(pedido);
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
