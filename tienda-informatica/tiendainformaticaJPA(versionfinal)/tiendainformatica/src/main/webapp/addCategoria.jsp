<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Inventario</title>
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
					<p>&nbsp;&nbsp;&nbsp;</p>
				</li>
				
				<li class="nav-item">
					<a href="AdminServletPrincipal?opcion=1" class="btn btn-primary">Volver</a>
				</li>
				
			</ul>
		</div>

		<br> <br>
		<h1 class="h4 text-gray-900 mb-4">Categorías existentes</h1>
		
		
	
						<br> <br>
       
		<div class="row">
			<div class="col-lg-4">
				<form class="form-inline" action="AdminServletPrincipal" method="POST">
                 
                    <input type="text" class="form-control mb-2 mr-sm-2" name="categorianame" placeholder="Nueva categoria">
                  
                    <button type="submit" class="btn btn-primary mb-2" name="operaciones" value="Guardar">Guardar</button>
                  </form>
			</div>
			<div class="col-lg-4">

				<table class="table table-hover">

					<thead>
						<tr>
							<th>#</th>
							<th>Categoria</th>
                            <th></th>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${categorias}" var="categoria">
							<tr>
								<th scope="row">${categoria.getIdCategoria()}</th>
								<td>${categoria.getNombre()}</td>
								<td scope="row">
									<a href="AdminServletPrincipal?opcion=editarcategoria&ideditarcat=${categoria.getIdCategoria()}" class="btn btn-primary">Editar</a>
								</td>
								<td scope="row">
									<a href="AdminServletPrincipal?opcion=eliminarcategoria&ideliminarcat=${categoria.getIdCategoria()}" class="btn btn-primary">Eliminar</a>
								</td>
								
							</tr>
						</c:forEach>
					</tbody>
				</table>
				
			</div>
		</div>
	</div>

</body>
</html>