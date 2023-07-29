package es.iespuertodelacruz.ahmrlh.tiendainformatica.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.management.relation.Role;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.iespuertodelacruz.ahmrlh.tiendainformatica.entitites.Pedido;
import es.iespuertodelacruz.ahmrlh.tiendainformatica.entitites.Rol;
import es.iespuertodelacruz.ahmrlh.tiendainformatica.entitites.Usuario;
import es.iespuertodelacruz.ahmrlh.tiendainformatica.repositories.UsuarioRepository;

/**
 * Servlet implementation class RegistrarServlet
 */
public class RegistrarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrarServlet() {
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
		
		System.out.println("estas aqui vale");
		
		if(request.getParameter("Registrar") != null) {
			//no está recogiendo el dni???
			String dni = request.getParameter("dniUser");
			String nombre = request.getParameter("nombreUser");
			String password = request.getParameter("userPassword");
			String telefono = request.getParameter("telefonoUser");
			String direccion = request.getParameter("direccionUser");
			
			System.out.println(dni + nombre + password);
			
			if(dni!=null || nombre!=null || password!=null || direccion!=null) {
				System.out.println("estas siendo registrado");
				Usuario toSave = new Usuario();
				toSave.setIdUsuario(dni);
				toSave.setNombre(nombre);
				toSave.setPassword(password);
				toSave.setDireccion(direccion);
				if(telefono!="") {
					toSave.setTelefono(Integer.parseInt(telefono));
				}
				Rol roluser = em.find(Rol.class, 2);
				toSave.setRol(roluser);
				ur.save(toSave);
				System.out.println(toSave.getIdUsuario() + toSave.getNombre() + toSave.getRol().getNombre() + toSave.getDireccion());
				//System.out.println("guardado: " + saved.getIdUsuario() + saved.getNombre());
				
			}
			
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);
		}
		
		//doGet(request, response);
	}

}