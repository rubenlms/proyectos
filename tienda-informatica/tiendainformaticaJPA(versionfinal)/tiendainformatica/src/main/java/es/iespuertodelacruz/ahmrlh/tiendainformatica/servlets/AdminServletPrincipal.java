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

import es.iespuertodelacruz.ahmrlh.tiendainformatica.repositories.CategoriaRepository;
import es.iespuertodelacruz.ahmrlh.tiendainformatica.repositories.FacturasRepository;
import es.iespuertodelacruz.ahmrlh.tiendainformatica.repositories.PedidoRepository;
import es.iespuertodelacruz.ahmrlh.tiendainformatica.repositories.ProductoRepository;
import es.iespuertodelacruz.ahmrlh.tiendainformatica.entitites.*;

/**
 * Servlet implementation class ClienteServletPrincipal
 */
public class AdminServletPrincipal extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminServletPrincipal() {
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
		ProductoRepository productoRep = new ProductoRepository(emf);
		FacturasRepository facturaRep = new FacturasRepository(emf);
		PedidoRepository pedidoRep = new PedidoRepository(emf);
		CategoriaRepository categoriaRepository = new CategoriaRepository(emf);
		
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		String opcion = request.getParameter("opcion");
		String categoria = request.getParameter("categoria");
		String pedido = request.getParameter("pedido");
		String usuario = request.getParameter("usuario");
		String ruta = "";
		String mensaje = "";
		
		String idproductodelete = request.getParameter("ideliminarprod");
		String idcategoriadelete = request.getParameter("ideliminarcat");
		String idproductoupdate = request.getParameter("idproductoedit");
		String idpedidoeditar = request.getParameter("idpedidoeditar");
		
		ArrayList<Categoria> categorias = new ArrayList<>();
		ArrayList<Producto> productos = new ArrayList<>();
		ArrayList<Factura> facturas = new ArrayList<>();
		ArrayList<Usuario> usuarios = new ArrayList<>();
		ArrayList<Pedido> pedidos = new ArrayList<>();
		
		ArrayList<Producto> productosGen = (ArrayList<Producto>) productoRep.findAll();
		ArrayList<Factura> facturasGen = (ArrayList<Factura>) facturaRep.findAll();
		ArrayList<Pedido> pedidosGen = (ArrayList<Pedido>) pedidoRep.findAll();
		
		if(user != null) {
			Rol rol = user.getRol();
			if(rol.getNombre().equals("admin")) {
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
					}
					
					if(productos != null) {
						request.setAttribute("productos", productos);
						ruta = "adminProductos.jsp";
						
						for (Producto producto : productosGen) {
							if(!categorias.contains(producto.getCategoria())) {
								categorias.add(producto.getCategoria());
							}
						}
						
						request.setAttribute("categorias", categorias);
					}
				}else if(opcion.equals("2")) {
					if(pedido != null) {
						for (Factura facturaGen : facturasGen) {
							if(facturaGen.getPedido().getIdPedido() == Integer.parseInt(pedido)) {
								facturas.add(facturaGen);
							}
						}
					}else {
						for (Factura facturaGen : facturasGen) {
							facturas.add(facturaGen);
						}
					}
					
					if(facturas != null) {
						request.setAttribute("facturas", facturas);
						ruta = "adminFacturas.jsp";
						
						for (Factura factura : facturasGen) {
							if(!pedidos.contains(factura.getPedido())) {
								pedidos.add(factura.getPedido());
							}
						}
						
						request.setAttribute("pedidos", pedidos);
					}
				}else if(opcion.equals("3")) {
					if(usuario != null) {
						for (Pedido pedidoGen : pedidosGen) {
							if(pedidoGen.getUsuario().getNombre().equals(usuario) && pedidoGen.getEstado().getIdEstado()!=7) {
								pedidos.add(pedidoGen);
							}
						}
					}else {
						
						for (Pedido pedidoGen : pedidosGen) {
							if(pedidoGen.getEstado().getIdEstado()!=7) {
								pedidos.add(pedidoGen);
							}
							
						}
					}
					
					if(pedidos != null) {
						request.setAttribute("pedidos", pedidos);
						ruta = "adminPedidos.jsp";
						
						for (Pedido pedidoU : pedidosGen) {
							if(!usuarios.contains(pedidoU.getUsuario())) {
								usuarios.add(pedidoU.getUsuario());
							}
						}
						
						request.setAttribute("usuarios", usuarios);
					}
					
				}else {
					request.setAttribute("mensaje", "Error al realizar la operacion");
					ruta = "error.jsp";
				}
			}else {
				request.setAttribute("mensaje", "No puedes pasar!");
				ruta = "error.jsp";
			}
		}else {
			request.setAttribute("mensaje", "No puedes pasar!");
			ruta = "error.jsp";
		}
		
		if(opcion.equals("4")) {
			categorias = (ArrayList<Categoria>) categoriaRepository.findAll();
			request.setAttribute("categorias", categorias);
			request.setAttribute("rutavuelta", "AdminServletPrincipal?opcion=1");
			ruta = "addProducto.jsp";
		}
		
		if(opcion.equals("eliminarproducto")) {
			
			boolean delete = productoRep.delete(idproductodelete);
			
			if(delete) {
				mensaje = "Se ha eliminado correctamente";
			}else {
				mensaje = "Hay un error al borrar el producto " + idproductodelete;
			}
			
			request.setAttribute("mensaje", mensaje);
			request.setAttribute("rutavuelta", "AdminServletPrincipal?opcion=1");
			ruta = "mensajes.jsp";
			
		}
		
		if(opcion.equals("vercategorias")) {
			System.out.println("estoy en categorias");
			categorias = (ArrayList<Categoria>) categoriaRepository.findAll();
			request.setAttribute("categorias", categorias);
			ruta = "addCategoria.jsp";
		}
		
		if(opcion.equals("eliminarcategoria")) {
			System.out.println(idcategoriadelete);
			boolean deleteOk = categoriaRepository.delete(Integer.parseInt(idcategoriadelete));
			
			if(deleteOk) {
				mensaje = "Se ha borrado correctamente la categoría";
			} else {
				mensaje = "No se pudo borrar la categoría";
			}
			
			request.setAttribute("mensaje", mensaje);
			request.setAttribute("rutavuelta", "AdminServletPrincipal?opcion=vercategorias");
			ruta = "mensajes.jsp";
			
		}
		
		if(opcion.equals("editarcategoria")) {
			String ideditarcat = request.getParameter("ideditarcat");
			Categoria categoriaedit = em.find(Categoria.class, Integer.parseInt(ideditarcat));
			request.setAttribute("categoria", categoriaedit);
			request.setAttribute("rutavuelta", "AdminServletPrincipal?opcion=1");
			ruta = "editarCategoria.jsp";
		}
		
		if(opcion.equals("editarproducto")) {
			System.out.println(idproductoupdate + "holaaaaaaaaaa");
			Producto producto = productoRep.findById(idproductoupdate);
			
			if(producto!=null) {
				request.setAttribute("producto", producto);
			}
			categorias = (ArrayList<Categoria>) categoriaRepository.findAll();
			request.setAttribute("categorias", categorias);
			request.setAttribute("rutavuelta", "AdminServletPrincipal?opcion=1");
			ruta = "editarproducto.jsp";
		}
		
		if(opcion.equals("editarpedido")) {
			if(idpedidoeditar!=null || idpedidoeditar.isEmpty()) {
				Pedido pedidoedit = pedidoRep.findById(idpedidoeditar);
				
				if(pedidoedit!=null) {
					request.setAttribute("pedido", pedidoedit);
				}
			}
			request.setAttribute("rutavuelta", "AdminServletPrincipal?opcion=1");
			ruta = "editarPedido.jsp";
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
		ProductoRepository productoRep = new ProductoRepository(emf);
		PedidoRepository pedidoRep = new PedidoRepository(emf);
		FacturasRepository facturasRep = new FacturasRepository(emf);
		CategoriaRepository categoriaRepository = new CategoriaRepository(emf);
		
		ArrayList<Producto>productos;
		ArrayList<Categoria> categorias = new ArrayList<>();
		ArrayList<Usuario> usuarios = new ArrayList<>();
		ArrayList<Pedido> pedidos = new ArrayList<>();
		ArrayList<Factura>facturas;
		String ruta = "";
		String mensaje = "";
		
		if(request.getParameter("operaciones") != null) {
			String opcion = request.getParameter("operaciones");
			switch (opcion) {
				case "Ver Productos":
					productos = (ArrayList<Producto>) productoRep.findAll();
					
					if(productos != null) {
						request.setAttribute("productos", productos);
						
						for (Producto producto : productos) {
							if(!categorias.contains(producto.getCategoria())) {
								categorias.add(producto.getCategoria());
							}
						}
						
						request.setAttribute("categorias", categorias);
					}
					
					ruta = "adminProductos.jsp";
					break;
				
				case "Ver Facturas":
					facturas = (ArrayList<Factura>) facturasRep.findAll();
					
					if(facturas != null) {
						request.setAttribute("facturas", facturas);
						
						for (Factura factura : facturas) {
							if(!pedidos.contains(factura.getPedido())) {
								pedidos.add(factura.getPedido());
							}
						}
						
						request.setAttribute("pedidos", pedidos);
					}
					
					ruta = "adminFacturas.jsp";
					break;
				
				case "Ver Pedidos":
					pedidos = (ArrayList<Pedido>) pedidoRep.findAll();
					
					if(pedidos != null) {
						request.setAttribute("pedidos", pedidos);
						
						for (Pedido pedido : pedidos) {
							if(!usuarios.contains(pedido.getUsuario())) {
								usuarios.add(pedido.getUsuario());
							}
						}
						
						request.setAttribute("usuarios", usuarios);
					}
					
					ruta = "adminPedidos.jsp";
					break;
					
				case "Agregar producto":
					String nombre = request.getParameter("nombre");
					String descripcion = request.getParameter("descripcion");
					String categoria = request.getParameter("categoria");
					String precio = request.getParameter("precio");
					String stock = request.getParameter("stock");
					
					if(!(nombre.isEmpty() || categoria.isEmpty() || precio.isEmpty() || stock.isEmpty())) {
					
						Producto producto = new Producto();
						
						producto.setNombre(nombre);
						if(descripcion!=null) {
							producto.setDescripcion(descripcion);
						}
						Categoria cat = em.find(Categoria.class, Integer.parseInt(categoria));
						producto.setCategoria(cat);
						producto.setPrecio(Double.parseDouble(precio));
						producto.setStock(Integer.parseInt(stock));
						
						Producto saved = productoRep.save(producto);
						
						System.out.println(saved.toString());
						
						ruta = "menuAdmin.jsp";
					} else {
						mensaje = "Faltan campos obligatorios";
						request.setAttribute("rutavuelta", "AdminServletPrincipal?opcion=1");
						request.setAttribute("mensaje", mensaje);
						ruta = "mensajes.jsp";
					}
					
					System.out.println("estas en agregar producto" + categoria + precio);
					
					break;
					
					//guardar nuevas categorías
				case "Guardar":
					String nombrecat = request.getParameter("categorianame");
					System.out.println("estoy en categorias POST" + nombrecat);
					
					Categoria toSave = new Categoria();
					toSave.setNombre(nombrecat);
					
					Categoria saved = categoriaRepository.save(toSave);
					
					if(saved!=null) {
						mensaje = "Se ha guardado correctamente la nueva categoría";
					} else {
						mensaje = "No se pudo guardar la categoría";
					}
					
					request.setAttribute("rutavuelta", "AdminServletPrincipal?opcion=vercategorias");
					request.setAttribute("mensaje", mensaje);
					ruta = "mensajes.jsp";
					
					break;
					
				case "Editar producto":
					String idproducto = request.getParameter("idproducto");
					String nombreEdit = request.getParameter("nombre");
					String descripcionEdit = request.getParameter("descripcion");
					String categoriaEdit = request.getParameter("categoria");
					String precioEdit = request.getParameter("precio");
					String stockEdit = request.getParameter("stock");
					
					if(!(idproducto.isEmpty() || nombreEdit.isEmpty() || categoriaEdit.isEmpty() || precioEdit.isEmpty() || stockEdit.isEmpty())) {
						
						Producto producto = new Producto();
						
						producto.setIdProducto(Integer.parseInt(idproducto));
						producto.setNombre(nombreEdit);
						if(descripcionEdit!=null) {
							producto.setDescripcion(descripcionEdit);
						}
						Categoria cat = em.find(Categoria.class, Integer.parseInt(categoriaEdit));
						producto.setCategoria(cat);
						producto.setPrecio(Double.parseDouble(precioEdit));
						producto.setStock(Integer.parseInt(stockEdit));
						
						Producto updated = productoRep.update(producto);
						
						System.out.println(updated.getIdProducto() + updated.getNombre() + updated.getStock());
						
						mensaje = "Producto editado correctamente";
						request.setAttribute("rutavuelta", "AdminServletPrincipal?opcion=1");
						request.setAttribute("mensaje", mensaje);
						ruta = "mensajes.jsp";
					} else {
						mensaje = "Faltan campos obligatorios";
						request.setAttribute("rutavuelta", "AdminServletPrincipal?opcion=1");
						request.setAttribute("mensaje", mensaje);
						ruta = "mensajes.jsp";
					}
					break;
					
				case "Editar categoria":
					String idcat = request.getParameter("idproductocat");
					String nombrecateg = request.getParameter("nombrecat");
					
					if(!(idcat.isEmpty() || nombrecateg.isEmpty())) {
						Categoria catedit = new Categoria();
						
						catedit.setIdCategoria(Integer.parseInt(idcat));
						catedit.setNombre(nombrecateg);
						
						Categoria update = categoriaRepository.update(catedit);
						
						if(update!=null) {
							mensaje = "se ha editado";
						}else {
							mensaje = "no se ha podido editar";
						}
					} else {
						mensaje = "Faltan campos obligatorios";
						
					}
					
					request.setAttribute("rutavuelta", "AdminServletPrincipal?opcion=vercategorias");
					request.setAttribute("mensaje", mensaje);
					ruta = "mensajes.jsp";
					
					break;
					
				case "Editar pedido":
					String idpedidoedit = request.getParameter("idpedidoedit");
					String estadopedidoedit = request.getParameter("estadopedido");
					
					if(idpedidoedit!=null && estadopedidoedit!=null) {
						Pedido pedidoedit = pedidoRep.findById(idpedidoedit);
						
						if(pedidoedit!=null) {
							
							Estado estado = em.find(Estado.class, Integer.parseInt(estadopedidoedit));
							
							if(estado!=null) {
								pedidoedit.setEstado(estado);
								Pedido update = pedidoRep.update(pedidoedit);
								
								if(update!=null) {
									mensaje = "se ha editado";
								}else {
									mensaje = "no se ha podido editar";
								}
							} else {
								mensaje = "no se ha podido encontrar ese estado";
							}
							
						}
					}
					
					request.setAttribute("rutavuelta", "AdminServletPrincipal?opcion=3");
					request.setAttribute("mensaje", mensaje);
					ruta = "mensajes.jsp";
					break;
					
				default:
					request.setAttribute("mensaje", "Error al realizar la operacion");
					ruta = "error.jsp";
					break;
			}
			
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(ruta);
		rd.forward(request, response);
		
	}

}