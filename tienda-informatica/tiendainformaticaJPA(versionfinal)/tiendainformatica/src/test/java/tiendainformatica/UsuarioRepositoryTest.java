package tiendainformatica;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import es.iespuertodelacruz.ahmrlh.tiendainformatica.entitites.Usuario;
import es.iespuertodelacruz.ahmrlh.tiendainformatica.repositories.UsuarioRepository;
import es.iespuertodelacruz.ahmrlh.tiendainformatica.entitites.Pedido;
import es.iespuertodelacruz.ahmrlh.tiendainformatica.entitites.Rol;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UsuarioRepositoryTest {
	
	static UsuarioRepository ur;
	static EntityManagerFactory emf = null;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		EMFhelper emfhelper = EMFhelper.getSingleton();
		emf = emfhelper.getEmf();
		ur = new UsuarioRepository(emf);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Test
	@Order(3)
	void testFindAll() {
		ArrayList<Usuario>usuarios = (ArrayList<Usuario>) ur.findAll();
	}

	@Test
	@Order(2)
	void testFindById() {
		Usuario userfound = ur.findById("12345678B");
	}

	@Test
	@Order(1)
	void testSave() {
		EntityManager em = emf.createEntityManager();
		Usuario usuario = new Usuario();
		ArrayList<Pedido>pedidos = new ArrayList<>();
		Rol rol = em.find(Rol.class, 2);
		usuario.setIdUsuario("74185236T");
		usuario.setNombre("Tania");
		usuario.setPassword("tania");
		usuario.setDireccion("Avenida Amanecer 3");
		usuario.setTelefono(654987321);
		//usuario.setPedidos(pedidos);
		usuario.setRol(rol);
		
		Usuario saved = ur.save(usuario);
	}

	@Test
	@Order(4)
	void testUpdate() {
		EntityManager em = emf.createEntityManager();
		Usuario u = new Usuario();
		ArrayList<Pedido>pedidos = new ArrayList<>();
		Rol rol = em.find(Rol.class, 2);
		u.setIdUsuario("12345678B");
		u.setNombre("Patricia");
		u.setPassword("1234");
		u.setDireccion("Calle");
		u.setTelefono(693582147);
		u.setPedidos(pedidos);
		u.setRol(rol);
		
		Usuario usuarioUpdated = ur.update(u);
	}

	@Test
	@Order(5)
	void testDelete() {
		boolean delete = ur.delete("12345678B");
	}

}
