package tiendainformatica;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import es.iespuertodelacruz.ahmrlh.tiendainformatica.entitites.Pedido;
import es.iespuertodelacruz.ahmrlh.tiendainformatica.entitites.Usuario;
import es.iespuertodelacruz.ahmrlh.tiendainformatica.repositories.PedidoRepository;
import es.iespuertodelacruz.ahmrlh.tiendainformatica.entitites.Categoria;
import es.iespuertodelacruz.ahmrlh.tiendainformatica.entitites.Estado;
import es.iespuertodelacruz.ahmrlh.tiendainformatica.entitites.Factura;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PedidoRepositoryTest {
	
	static PedidoRepository pr;
	static EntityManagerFactory emf = null;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		EMFhelper emfhelper = EMFhelper.getSingleton();
		emf = emfhelper.getEmf();
		pr = new PedidoRepository(emf);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Test
	@Order(3)
	void testFindAll() {
		ArrayList<Pedido> productos = (ArrayList<Pedido>) pr.findAll();
	}

	@Test
	@Order(2)
	void testFindById() {
		Pedido pedidofound = pr.findById("1");
	}

	@Test
	@Order(1)
	void testSave() {
		EntityManager em = emf.createEntityManager();
		
		Pedido pedido = new Pedido();
		ArrayList<Factura> facturas = new ArrayList<>();
		
		Usuario usuario = em.find(Usuario.class, "43491608B");
		Estado estado = em.find(Estado.class, 1);
		
		pedido.setUsuario(usuario);
		pedido.setFecha(new Timestamp(new Date().getTime()));
		pedido.setEstado(estado);
		
		Pedido saved = pr.save(pedido);
	}

	@Test
	@Order(4)
	void testUpdate() {
		EntityManager em = emf.createEntityManager();
		
		Pedido pedido = em.find(Pedido.class, 1);
		ArrayList<Factura> facturas = new ArrayList<>();
		
		Usuario usuario = em.find(Usuario.class, "12345678B");
		Estado estado = em.find(Estado.class, 5);
		
		//pedido.setUsuario(usuario);
		//pedido.setFecha(new Timestamp(new Date().getTime()));
		pedido.setEstado(estado);
		
		Pedido pedidoUpdated = pr.update(pedido);
		
		if(pedidoUpdated!=null) {
			System.out.println("works");
		}
	}

	@Test
	@Order(5)
	void testDelete() {
		boolean delete = pr.delete("1");
	}

}
