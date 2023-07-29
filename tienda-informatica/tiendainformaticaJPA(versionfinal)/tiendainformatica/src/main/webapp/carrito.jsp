<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<title>Carrito</title>
<!-- Custom fonts for this template-->
<link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet"
	type="text/css">
<link
	href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">

<!-- Custom styles for this template-->
<link href="css/sb-admin-2.min.css" rel="stylesheet">
</head>
<body class="antialiased">
	<div class="container-fluid">
		<br> <br>

		<div>
			<ul class="nav">
				<li class="nav-item">
					<h4 class="font-weight-bolder">${user.getNombre()}</h4>
				</li>
				<li class="nav-item">
						<p>&nbsp;&nbsp;&nbsp;</p>
				</li>
				<li class="nav-item">
					<a class="btn btn-primary" href="ClienteServletPrincipal?opcion=1">Volver</a>
				</li>
			</ul>
		</div>

		<br> <br>
		<h2 class="card-header text-justify">Tienes estos productos en tu carrito</h2>
		<br> <br>
		<div class="row">
			<div class="col-lg-2">

	   		 </div>
			<div class="col-lg-8">

				<table class="table table-hover">

					<thead>
						<tr>
							<th>#</th>
							<th>Producto</th>
							<th>Precio</th>
							<th>Cantidad</th>
							<th></th>
						</tr>
					</thead>

					<tbody>

						<c:forEach items="${carrito}" var="factura">
							<tr>
								<th scope="row">${factura.getIdFactura()}</th>
								<td>${factura.getProducto().getNombre()}</td>
								<td>${factura.getPrecio()}€</td>
								<td>${factura.getCantidad()}</td>
								<td scope="row">
									<a href="ClienteServletPrincipal?opcion=eliminarProductoCarrito&ideliminarprod=${factura.getIdFactura()}" class="btn btn-primary">Eliminar</a>
								</td>
								<!-- PONER AQUI LA CANTIDAD -->
							</tr>

						</c:forEach>
						
						<div >
							<h4 class="h4 text-gray-900 mb-4">Total ${total} €</h4>
						</div>

						<div>
							<a class="btn btn-primary" href="CarritoServlet?opcion=pagarCarrito">Pagar</a>
						</div>
						<br>
						<br>
						<br>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>