package es.iespuertodelacruz.ahmrlh.tiendainformatica.entitites;

import java.io.Serializable;

import javax.management.relation.Role;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the usuarios database table.
 * 
 */
@Entity
@Table(name="usuarios")
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_usuario", unique=true, nullable=false, length=9)
	private String idUsuario;

	@Column(nullable=false, length=150)
	private String direccion;

	@Column(nullable=false, length=100)
	private String nombre;

	@Column(nullable=false, length=250)
	private String password;

	private int telefono;

	//bi-directional many-to-one association to Pedido
	@OneToMany(mappedBy="usuario")
	private List<Pedido> pedidos;

	//bi-directional many-to-one association to Role
	@ManyToOne
	@JoinColumn(name="rol", nullable=false)
	private Rol rol;

	public Usuario() {
	}

	public String getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getTelefono() {
		return this.telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public List<Pedido> getPedidos() {
		return this.pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public Pedido addPedido(Pedido pedido) {
		getPedidos().add(pedido);
		pedido.setUsuario(this);

		return pedido;
	}

	public Pedido removePedido(Pedido pedido) {
		getPedidos().remove(pedido);
		pedido.setUsuario(null);

		return pedido;
	}

	public Rol getRol() {
		return this.rol;
	}

	public void setRol(Rol roluser) {
		this.rol = roluser;
	}

}