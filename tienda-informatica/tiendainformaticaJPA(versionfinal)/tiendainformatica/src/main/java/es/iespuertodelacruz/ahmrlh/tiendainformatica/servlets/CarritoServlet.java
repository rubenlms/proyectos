package es.iespuertodelacruz.ahmrlh.tiendainformatica.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.iespuertodelacruz.ahmrlh.tiendainformatica.entitites.Categoria;
import es.iespuertodelacruz.ahmrlh.tiendainformatica.entitites.Estado;
import es.iespuertodelacruz.ahmrlh.tiendainformatica.entitites.Factura;
import es.iespuertodelacruz.ahmrlh.tiendainformatica.entitites.Pedido;
import es.iespuertodelacruz.ahmrlh.tiendainformatica.entitites.Producto;
import es.iespuertodelacruz.ahmrlh.tiendainformatica.entitites.Usuario;
import es.iespuertodelacruz.ahmrlh.tiendainformatica.repositories.FacturasRepository;
import es.iespuertodelacruz.ahmrlh.tiendainformatica.repositories.PedidoRepository;
import es.iespuertodelacruz.ahmrlh.tiendainformatica.repositories.ProductoRepository;

/**
 * Servlet implementation class CarritoServlet
 */
public class CarritoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CarritoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("tiendainformatica");
		EntityManager em = emf.createEntityManager();
		
		ProductoRepository productoRepository = new ProductoRepository(emf);
		PedidoRepository pedidoRepository = new PedidoRepository(emf);
		FacturasRepository facturasRepository = new FacturasRepository(emf);
		
		ArrayList<Producto>productos = new ArrayList<>();
		ArrayList<Categoria>categorias = new ArrayList<>();
		ArrayList<Factura>facturas = new ArrayList<>();
		ArrayList<Pedido>pedidosGen = new ArrayList<>();
		ArrayList<Producto>productosGen = (ArrayList<Producto>) productoRepository.findAll();
		ArrayList<Factura>facturasGen = (ArrayList<Factura>) facturasRepository.findAll();
		
		String ruta = "";
		String mensaje = "";
		String categoria = request.getParameter("categoria");
		String idproducto = request.getParameter("id");
		String opcion = request.getParameter("opcion");
		String idpedido = request.getParameter("idpedido");
		String idProductoCarrito = request.getParameter("ideliminarprod");
		String idpedidocancelar = request.getParameter("idpedidocancelar");
		
		if(opcion.equals("cancelarPedido")) {
			System.out.println("gato miau");
			Pedido pedido = null;
			mensaje = "ha ocurrido un error";
			
			
			if(idpedidocancelar!=null) {
				pedido = pedidoRepository.findById(idpedidocancelar);
				
				System.out.println(pedido.getIdPedido() + " " + pedido.getEstado().getIdEstado());
				if(pedido!=null) {
					
					facturas = (ArrayList<Factura>) facturasRepository.findbyPedido(pedido.getIdPedido());
					
					if(pedido.getEstado().getIdEstado()==1 || pedido.getEstado().getIdEstado()==2) {
						
						Estado estado = em.find(Estado.class, 5);
						if(estado!=null) {
							pedido.setEstado(estado);
							Pedido update = pedidoRepository.update(pedido);
							System.out.println(update.getIdPedido() + " " + update.getEstado().getNombre());
							
							if(update!=null) {
								mensaje = "se ha cancelado su pedido";
								
								for (Factura f : facturas) {
									Producto prod = f.getProducto();
									int stockprod = prod.getStock();
									prod.setStock(stockprod + f.getCantidad());
									productoRepository.update(prod);
								}
							} else {
								mensaje = "no ha podido cancelarse su pedido";
							}
						}
					} else if (pedido.getEstado().getIdEstado()==3 || pedido.getEstado().getIdEstado()==4) {
						Estado estado = em.find(Estado.class, 6);
						if(estado!=null) {
							pedido.setEstado(estado);
							Pedido update = pedidoRepository.update(pedido);
							System.out.println(update.getIdPedido() + " " + update.getEstado().getNombre());
							
							if(update!=null) {
								mensaje = "se ha devuelto su pedido";
								
								for (Factura f : facturas) {
									Producto prod = f.getProducto();
									int stockprod = prod.getStock();
									prod.setStock(stockprod + f.getCantidad());
									productoRepository.update(prod);
								}
							} else {
								mensaje = "no ha podido devolverse su pedido";
							}
						}
					} else {
						mensaje = "pedido no apto para devolución o cancelación";
					}
					
				}
			}
			
			request.setAttribute("rutavuelta", "ClienteServletPrincipal?opcion=1");
			request.setAttribute("mensaje", mensaje);
			ruta = "mensajes.jsp";
		}
		
		if(opcion.equals("pagarCarrito")) {
			System.out.println("vas a pagar el crrito");
			pedidosGen = (ArrayList<Pedido>) pedidoRepository.findAll();
			Usuario userCarrito = (Usuario) request.getSession().getAttribute("user");
			Pedido pedido = null;
			boolean stockSuficiente = true;
			
			//va a buscar el pedido que guarda todo el carrito y guarda en una variable
			if(pedidosGen!=null) {
				
				for(Pedido p : pedidosGen) {
					if((p.getUsuario().getIdUsuario()).equals(userCarrito.getIdUsuario()) && p.getEstado().getIdEstado()==7) {
						pedido = p;
					} 
				}
				
				//aqui recoge todos los productos que están en el carrito
				if(pedido!=null) {
					facturas = (ArrayList<Factura>) facturasRepository.findbyPedido(pedido.getIdPedido());
					
					for(Factura f : facturas) {
						
						int idproductocarrito = f.getProducto().getIdProducto();
						Producto productoCarrito = productoRepository.findById(Integer.toString(idproductocarrito));
						
						if(f.getCantidad() > productoCarrito.getStock()) {
							stockSuficiente = false;
						}
					}
					
					if(stockSuficiente==true) {
						Estado estado = em.find(Estado.class, 1);
						pedido.setEstado(estado);
						System.out.println(pedido.getEstado() + " " + pedido.getIdPedido());
						Pedido update = pedidoRepository.update(pedido);
						System.out.println(update.getIdPedido() + " " + update.getEstado().getNombre());
						if(update!=null) {
							
							for(Factura f : facturas) {
								int idproductocarrito = f.getProducto().getIdProducto();
								Producto productoCarrito = productoRepository.findById(Integer.toString(idproductocarrito));
								int stockproducto = productoCarrito.getStock();
								
								productoCarrito.setStock(stockproducto - f.getCantidad());
								Producto update2 = productoRepository.update(productoCarrito);
							}
							mensaje = "Se ha realizado una compra";
						} else {
							mensaje = "No se ha podido completar la compra";
						}
						
					}else {
						//no ha sido posible realizar el pedido por falta de stock en alguno de los productos
						mensaje = "no ha sido posible realizar el pedido por falta de stock en alguno de los productos";
					}
				} else {
					mensaje = "no tienes productos en tu carrito";
				}
			}
			
			request.setAttribute("rutavuelta", "ClienteServletPrincipal?opcion=1");
			request.setAttribute("mensaje", mensaje);
			ruta = "mensajes.jsp";
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(ruta);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
