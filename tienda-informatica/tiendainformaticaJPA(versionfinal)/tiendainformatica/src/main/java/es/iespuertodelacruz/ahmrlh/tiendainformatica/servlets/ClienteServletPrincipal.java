package es.iespuertodelacruz.ahmrlh.tiendainformatica.servlets;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.iespuertodelacruz.ahmrlh.tiendainformatica.repositories.FacturasRepository;
import es.iespuertodelacruz.ahmrlh.tiendainformatica.repositories.PedidoRepository;
import es.iespuertodelacruz.ahmrlh.tiendainformatica.repositories.ProductoRepository;
import es.iespuertodelacruz.ahmrlh.tiendainformatica.entitites.*;

/**
 * Servlet implementation class ClienteServletPrincipal
 */
public class ClienteServletPrincipal extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClienteServletPrincipal() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
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
		
		System.out.println(opcion + " esta es la opcion");

		if(opcion.equals("1")) {
			if(categoria != null) {
				for (Producto productoGen : productosGen) {
					if(productoGen.getCategoria().getNombre().equals(categoria)) {
						productos.add(productoGen);
					}
				}
			}else {
				for (Producto productoGen : productosGen) {
					productos.add(productoGen);
				}
				
				for (Producto producto : productos) {
					if(!categorias.contains(producto.getCategoria())) {
						categorias.add(producto.getCategoria());
					}
				}
				request.setAttribute("categorias", categorias);
			}
			
			request.setAttribute("productos", productos);
			ruta = "menuUser.jsp";
		}
		
		if(opcion.equals("detallePedido")) {
			
			facturas = (ArrayList<Factura>) facturasRepository.findbyPedido(Integer.parseInt(idpedido));
			request.setAttribute("facturas", facturas);
			BigDecimal precioTotal = new BigDecimal(0.00);
			
			for(Factura f : facturas) {
				
				int cantidad2 = f.getCantidad();
				BigDecimal cantidad = BigDecimal.valueOf(cantidad2);
				BigDecimal precio = f.getPrecio();
				BigDecimal sumatotal = precio.multiply(cantidad);
				precioTotal = precioTotal.add(sumatotal);
			}
			
			request.setAttribute("total", precioTotal);
			
			ruta = "productospedido.jsp";
		}
		
		if(opcion.equals("eliminarProductoCarrito")){
			
			if(idProductoCarrito!=null) {
				Factura facturadelete = facturasRepository.findById(Integer.parseInt(idProductoCarrito));
				
				if(facturadelete!=null) {
					if(facturadelete.getCantidad()>1) {
						int cantidad = facturadelete.getCantidad();
						facturadelete.setCantidad(cantidad - 1);
						facturasRepository.update(facturadelete);
						mensaje = "se ha borrado correctamente el producto de su carrito";
						
					} else if (facturadelete.getCantidad()<=1) {
						boolean deleteOk = facturasRepository.delete(Integer.parseInt(idProductoCarrito));
						
						if(deleteOk) {
							mensaje = "se ha borrado correctamente el producto de su carrito";
						} else {
							mensaje = "no ha sido posible borrar el producto del carrito";
						}
					}
				} else {
					mensaje = "ha ocurrido un error";
				}
				
				
				request.setAttribute("rutavuelta", "ClienteServletPrincipal?opcion=1");
				request.setAttribute("mensaje", mensaje);
				ruta = "mensajes.jsp";
			}
			
		}
		
		//no funciona bien
		if(opcion.equals("cancelarPedido")) {
			System.out.println("gato");
			Pedido pedido = null;
			mensaje = "ha ocurrido un error";
			System.out.println("entro aqui cancelar");
			
			if(idpedidocancelar!=null) {
				pedido = pedidoRepository.findById(idpedidocancelar);
				System.out.println(pedido.getIdPedido() + " " + pedido.getEstado().getIdEstado());
				if(pedido!=null) {
					Estado estado = em.find(Estado.class, 5);
					
					if(estado!=null) {
						pedido.setEstado(estado);
						Pedido update = pedidoRepository.update(pedido);
						System.out.println(update.getIdPedido() + " " + update.getEstado().getNombre());
						
						if(update!=null) {
							mensaje = "se ha devuelto su pedido";
						} else {
							mensaje = "no ha podido cancelarse su pedido";
						}
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
							mensaje = "Se ha realizado una compra";
						} else {
							mensaje = "No se ha podido completar la compra";
						}
						
					}else {
						//no ha sido posible realizar el pedido por falta de stock en alguno de los productos
						mensaje = "no ha sido posible realizar el pedido por falta de stock en alguno de los productos";
					}
				} else {
					//no ha sido posible realizar el pedido por falta de stock en alguno de los productos
					mensaje = "no ha sido posible realizar el pedido por falta de stock en alguno de los productos";
				}
			}
			
			request.setAttribute("rutavuelta", "ClienteServletPrincipal?opcion=1");
			request.setAttribute("mensaje", mensaje);
			ruta = "mensajes.jsp";
		}
		
		if(opcion.equals("AñadirCarrito")) {
			
			Producto productoFound = productoRepository.findById(idproducto);
			Pedido pedido = null;
			Pedido pedidoCarrito = null;
			int idpedidocarrito = 0;
			boolean haycarrito = false;
			boolean exists = false;
			mensaje = "error al añadir a carrito";
			
			if(productoFound!=null) {
				Usuario usuarioSesion = (Usuario) request.getSession().getAttribute("user");
				
				if(usuarioSesion!=null) {
					List<Pedido> pedidosCliente = pedidoRepository.findByCliente(usuarioSesion.getIdUsuario());
					
					//se comprueba si tiene pedidos en estado carrito, sino se crea uno
					if(pedidosCliente.size()>0 || pedidosCliente!=null) {
						
						for(Pedido p : pedidosCliente) {
							
							//comprueba si ya existe un pedido en carrito, sino crea un pedido, si existe añade una nueva factura
							if(p.getEstado().getIdEstado()==7) {
								haycarrito = true;
								pedidoCarrito = p;
								
							}
						}
						
						if(haycarrito==true) {
							for(Factura factura : facturasRepository.findbyPedido(pedidoCarrito.getIdPedido())) {
				
									//comprueba si ya existe una factura que contenga ese producto y añade una, sino lo crea
									if(productoFound.getIdProducto() == factura.getProducto().getIdProducto()) {
										exists = true;
										factura.setCantidad(factura.getCantidad()+1);
										
										facturasRepository.update(factura);
										mensaje = "se ha añadido al carrito " + productoFound.getNombre();
									}
										
								}
							
							if(exists==false) {
								Factura facturaNueva = new Factura();
								
								facturaNueva.setCantidad(1);
								facturaNueva.setProducto(productoFound);
								BigDecimal precio = new BigDecimal(productoFound.getPrecio());
								facturaNueva.setPrecio(precio);
								facturaNueva.setPedido(pedidoCarrito);
								
								facturasRepository.save(facturaNueva);
								
								mensaje = "se ha añadido al carrito " + productoFound.getNombre();
							} 
											
						
						} else {
						
						Timestamp fechaactual=new Timestamp(System.currentTimeMillis()); 
						Estado estado = new Estado();
						estado.setIdEstado(7);
						
						Pedido pedidoNuevo = new Pedido();
						
						pedidoNuevo.setUsuario(usuarioSesion);
						pedidoNuevo.setEstado(estado);
						pedidoNuevo.setFecha(fechaactual);
						
						pedidoRepository.save(pedidoNuevo);
						
						Factura facturaNueva = new Factura();
						
						facturaNueva.setCantidad(1);
						facturaNueva.setProducto(productoFound);
						BigDecimal precio = new BigDecimal(productoFound.getPrecio());
						facturaNueva.setPrecio(precio);
						facturaNueva.setPedido(pedidoNuevo);
						
						facturasRepository.save(facturaNueva);
						
						mensaje = "se ha añadido al carrito " + productoFound.getNombre();
					}
					
				}
			}
			//debería llevar al carrito, hacer cuando se haya solucionado
			request.setAttribute("rutavuelta", "ClienteServletPrincipal?opcion=1");
			request.setAttribute("mensaje", mensaje);
			ruta = "mensajes.jsp";
			}
		}
		RequestDispatcher rd = request.getRequestDispatcher(ruta);
		rd.forward(request, response);

		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("tiendainformatica");
		EntityManager em = emf.createEntityManager();
		ProductoRepository productorepository = new ProductoRepository(emf);
		PedidoRepository pedidorepository = new PedidoRepository(emf);
		FacturasRepository facturasrepository = new FacturasRepository(emf);
		ArrayList<Producto>productos;
		ArrayList<Factura>carrito = new ArrayList<>();
		ArrayList<Categoria>categorias = new ArrayList<>();
		ArrayList<Pedido>pedidos;
		ArrayList<Pedido>pedidosGen;
		ArrayList<Factura>facturas;
		String ruta = "";
		
		if(request.getParameter("operaciones") != null) {
			String opc = request.getParameter("operaciones");
			switch (opc) {
				case "Ver todo":
					productos = (ArrayList<Producto>) productorepository.findAll();
					
					
					if(productos!=null) {
						request.setAttribute("productos", productos);
					}
					
					for (Producto producto : productos) {
						if(!categorias.contains(producto.getCategoria())) {
							categorias.add(producto.getCategoria());
						}
					}
					request.setAttribute("categorias", categorias);
					ruta = "menuUser.jsp";
					break;
					
				case "Carrito":
					pedidosGen = (ArrayList<Pedido>) pedidorepository.findAll();
					Usuario userCarrito = (Usuario) request.getSession().getAttribute("user");
					Pedido pedido = null;
					BigDecimal precioTotal = new BigDecimal(0.00);
					
					if(pedidosGen!=null) {
						pedidos = new ArrayList<>();
						
						for(Pedido p : pedidosGen) {
							if((p.getUsuario().getIdUsuario()).equals(userCarrito.getIdUsuario()) && p.getEstado().getIdEstado()==7) {
								pedido = p;
							} 
						}
						
						
						if(pedido!=null) {
							carrito = (ArrayList<Factura>) facturasrepository.findbyPedido(pedido.getIdPedido());
							
						}
						
						for(Factura f : carrito) {
							
							int cantidad2 = f.getCantidad();
							BigDecimal cantidad = BigDecimal.valueOf(cantidad2);
							BigDecimal precio = f.getPrecio();
							BigDecimal sumatotal = precio.multiply(cantidad);
							precioTotal = precioTotal.add(sumatotal);
						}
						
					}
					System.out.println("precio total = " + precioTotal);
					request.setAttribute("carrito", carrito);
					request.setAttribute("total", precioTotal);
					ruta = "carrito.jsp";
					
					break;
				
				case "Ver mis pedidos":
					pedidosGen = (ArrayList<Pedido>) pedidorepository.findAll();
					Usuario userPedido = (Usuario) request.getSession().getAttribute("user");
										
					if(pedidosGen!=null) {
						pedidos = new ArrayList<>();
						
						for(Pedido p : pedidosGen) {
							if((p.getUsuario().getIdUsuario()).equals(userPedido.getIdUsuario()) && p.getEstado().getIdEstado()!=7) {
								pedidos.add(p);
							}
						}

						request.setAttribute("pedidos", pedidos);
					}
					
					ruta = "pedidosUser.jsp";
					
					break;
					/*
				case "Añadir a carrito":
					String idpro = request.getParameter("id");
					System.out.println("entro aqui " + idpro);
					
					ruta = "menuUser.jsp";
					
					break;*/
			}
			
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(ruta);
		rd.forward(request, response);
		
	}
}