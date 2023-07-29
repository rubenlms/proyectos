<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>  
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Bootstrap 4 Example</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>

    <div class="container-fluid">
        <br>
        <br>
       
	        <div class="bg-gradient-primary">
	            <ul class="nav" >
	            	<li class="nav-item">
	                  <h4 class="font-weight-bolder">${user.getNombre()}</h4>
	                 
	                </li>
	                <li class="nav-item">
						<p>&nbsp;&nbsp;&nbsp;</p>
					</li>
	                <li class="nav-item">
	                  <form action="ClienteServletPrincipal" method="POST">
							<input type="submit" name="operaciones" value="Ver todo" class="btn btn-primary">
						</form>
	                </li>
	                <li class="nav-item">
						<p>&nbsp;&nbsp;&nbsp;</p>
					</li>
	            	<li class="nav-item">
	                  <form action="ClienteServletPrincipal" method="POST">
							<input type="submit" name="operaciones" value="Carrito" class="btn btn-primary">
						</form>
	                </li>
	                <li class="nav-item">
						<p>&nbsp;&nbsp;&nbsp;</p>
					</li>
	                <li class="nav-item">
	                  <form action="ClienteServletPrincipal" method="POST">
							<input type="submit" name="operaciones" value="Ver mis pedidos" class="btn btn-primary">
						</form>
	                </li>
	                <li class="nav-item">
						<p>&nbsp;&nbsp;&nbsp;</p>
					</li>
	                <li class="nav-item">
	                   <a class="btn btn-primary" href="login.jsp">Cerrar Sesión</a>
	                </li>
	              </ul>
	        </div>
	    
        <br>
        <br>
        <h1 class="h4 text-gray-900 mb-4">Consulta los productos disponibles!</h1>
        <br>
        <br>
         <div class="row">
	         <div class="col-lg-2">
                <div class="dropdown">
                    <button class="btn btn-secondary dropdown-toggle"
                            type="button" id="dropdownMenu1" data-toggle="dropdown"
                            aria-haspopup="true" aria-expanded="false">
                      Categoría
                    </button>
                    
                    <div class="dropdown-menu" aria-labelledby="dropdownMenu1">
                     <c:forEach items="${categorias}" var="categoria">
                      <a class="dropdown-item" href="ClienteServletPrincipal?opcion=1&categoria=${categoria.getNombre()}">${categoria.getNombre()}</a>
                    
                    </c:forEach>
                   </div>
                  </div>
	   		 </div>
        <div class="col-lg-8">
        	
            <table class="table table-hover">
            
                <thead>
                  <tr>
                    <th>#</th>
                    <th>Producto</th>
                    <th>Precio</th>
                    <th>Stock</th>
                  </tr>
                </thead>
                
                <tbody>
                
                <c:forEach items="${productos}" var="producto">
                	<tr>
                    <th scope="row">${producto.getIdProducto()}</th>
                    <td>${producto.getNombre()}</td>
                    <td>${producto.getPrecio()} €</td>
                    <td>${producto.getStock()}</td>
                    <td scope="row">
                    	<a class="btn btn-primary" href="ClienteServletPrincipal?opcion=AñadirCarrito&id=${producto.getIdProducto()}">Añadir a carrito</a>
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