<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Facturas</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
<script
	src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.slim.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>

	<div class="container-fluid">
		<br> <br>

		<div class="bg-gradient-primary">
			<ul class="nav">
				<li class="nav-item">
					<p class="font-weight-bolder">${user.getNombre()}</p>
				</li>
				<li class="nav-item">
					<p>&nbsp;&nbsp;&nbsp;</p>
				</li>
				<li class="nav-item">
					<form action="AdminServletPrincipal" method="POST">
						<input type="submit" name="operaciones" value="Ver Facturas"
							class="btn btn-primary">
					</form>
				</li>
				<li class="nav-item">
					<p>&nbsp;&nbsp;&nbsp;</p>
				</li>
				<li class="nav-item">
					<a href="menuAdmin.jsp" class="btn btn-primary">Inicio</a>
				</li>
			</ul>
		</div>

		<br> <br>
		<h1 class="h4 text-gray-900 mb-4">Consulta las facturas</h1>
		<br> <br>
		<div class="row">
			<div class="col-lg-2">
				<div class="dropdown">
					<button class="btn btn-secondary dropdown-toggle" type="button"
						id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true"
						aria-expanded="false">Pedidos</button>
					<div class="dropdown-menu" aria-labelledby="dropdownMenu1">
						<c:forEach items="${pedidos}" var="pedido">
							<a class="dropdown-item" href="AdminServletPrincipal?opcion=2&pedido=${pedido.getIdPedido()}">${pedido.getIdPedido()}</a>
						</c:forEach>
					</div>
				</div>
			</div>
			<div class="col-lg-8">

				<table class="table table-hover">

					<thead>
						<tr>
							<th>#</th>
							<th>Pedido</th>
							<th>Producto</th>
							<th>Cantidad</th>
							<th>Precio</th>
						</tr>
					</thead>

					<tbody>

						<c:forEach items="${facturas}" var="factura">
							<tr>
								<th scope="row">${factura.getIdFactura()}</th>
								<td>${factura.getPedido().getIdPedido()}</td>
								<td>${factura.getProducto().getNombre()}</td>
								<td>${factura.getCantidad()}</td>
								<td>${factura.getPrecio()}€</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>

</body>
</html>