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

import org.mindrot.jbcrypt.BCrypt;

import es.iespuertodelacruz.ahmrlh.tiendainformatica.entitites.Categoria;
import es.iespuertodelacruz.ahmrlh.tiendainformatica.entitites.Producto;
import es.iespuertodelacruz.ahmrlh.tiendainformatica.entitites.Rol;
import es.iespuertodelacruz.ahmrlh.tiendainformatica.entitites.Usuario;
import es.iespuertodelacruz.ahmrlh.tiendainformatica.repositories.ProductoRepository;
import es.iespuertodelacruz.ahmrlh.tiendainformatica.repositories.UsuarioRepository;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("tiendainformatica");
		EntityManager em = emf.createEntityManager();
		UsuarioRepository ur = new UsuarioRepository(emf);
		ProductoRepository pr = new ProductoRepository(emf);
		Usuario userfound = null;
		String ruta = "error.jsp";
		String mensaje = "";
		
		if(request.getParameter("Ingresar") != null) {
			String dni = request.getParameter("dniuser");
			String password = request.getParameter("passwordUser");
			
			if(!dni.isEmpty() && !password.isEmpty()) {
				userfound = ur.findById(dni);
				
				if(userfound!=null) {
					boolean okLogin = BCrypt.checkpw(password, userfound.getPassword());
					
					if(okLogin && userfound.getIdUsuario().equalsIgnoreCase(dni)) {
						request.getSession().setAttribute("user", userfound);
						Rol rol = userfound.getRol();
						
						if(rol.getNombre().equals("admin")) {
							ruta = "menuAdmin.jsp";
						}
						
						if(rol.getNombre().equals("user")) {
							ArrayList<Producto>productos;
							ArrayList<Categoria>categorias = new ArrayList<>();
							productos = (ArrayList<Producto>) pr.findAll();
							
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
							//como mandar la ruta y no el jsp??
						}
						
					} else {
						mensaje = "No coincide dni o contraseña";
						request.setAttribute("mensaje", mensaje);
						ruta = "error.jsp";
					}
				} else {
					//el usuario no se encuentra registrado
					mensaje = "El usuario no se encuentra registrado";
					request.setAttribute("mensaje", mensaje);
					ruta = "error.jsp";
				}
				
			} else {
				//faltan campos por completar
				mensaje = "Faltan campos por completar";
				request.setAttribute("mensaje", mensaje);
				ruta = "error.jsp";
			}
			
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(ruta);
		rd.forward(request, response);
		
		
		//doGet(request, response);
	}

}