package tiendainformatica;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import es.iespuertodelacruz.ahmrlh.tiendainformatica.entitites.Producto;
import es.iespuertodelacruz.ahmrlh.tiendainformatica.repositories.ProductoRepository;
import es.iespuertodelacruz.ahmrlh.tiendainformatica.entitites.Categoria;
import es.iespuertodelacruz.ahmrlh.tiendainformatica.entitites.Factura;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductoRepositoryTest {
	
	static ProductoRepository pr;
	static EntityManagerFactory emf = null;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		EMFhelper emfhelper = EMFhelper.getSingleton();
		emf = emfhelper.getEmf();
		pr = new ProductoRepository(emf);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Test
	@Order(3)
	void testFindAll() {
		ArrayList<Producto> productos = (ArrayList<Producto>) pr.findAll();
	}

	@Test
	@Order(2)
	void testFindById() {
		Producto productfound = pr.findById("1");
	}

	@Test
	@Order(1)
	void testSave() {
		EntityManager em = emf.createEntityManager();
		
		Producto producto = new Producto();
		ArrayList<Factura> facturas = new ArrayList<>();
		
		Categoria categoria = em.find(Categoria.class, 2);
		
		producto.setNombre("Producto 1");
		producto.setDescripcion("Descripcion para el producto1");
		producto.setCategoria(categoria);
		producto.setPrecio(19.95);
		producto.setStock(20);
		
		Producto saved = pr.save(producto);
	}

	@Test
	@Order(4)
	void testUpdate() {
		EntityManager em = emf.createEntityManager();
		
		Producto producto = new Producto();
		ArrayList<Factura> facturas = new ArrayList<>();
		
		Categoria categoria = em.find(Categoria.class, 2);
		
		producto.setIdProducto(23);
		producto.setNombre("Producto 1");
		producto.setDescripcion("Descripcion para el producto1");
		producto.setCategoria(categoria);
		producto.setPrecio(19.95);
		producto.setStock(20);
		
		Producto productoUpdated = pr.update(producto);
	}

	@Test
	@Order(5)
	void testDelete() {
		boolean delete = pr.delete("1");
	}

}
