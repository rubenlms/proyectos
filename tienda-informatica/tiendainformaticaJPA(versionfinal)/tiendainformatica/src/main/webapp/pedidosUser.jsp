<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>  
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
	<title>Pedidos</title>
    <!-- Custom fonts for this template-->
    <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link
        href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
        rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="css/sb-admin-2.min.css" rel="stylesheet">
</head>
<body>
	 <div class="container-fluid">
        <br>
        <br>
       
	        <div>
	            <ul class="nav">
	            	<li class="nav-item">
	                  <p class="font-weight-bolder">${user.getNombre()}</p>
	                </li>
	                <li class="nav-item">
						<p>&nbsp;&nbsp;&nbsp;</p>
					</li>
	                <li class="nav-item">
	                  <a class="btn btn-primary" href="ClienteServletPrincipal?opcion=1">Volver</a>
	                </li>
	              </ul>
	        </div>
	    
        <br>
        <br>
        <h2 class="h4 text-gray-900 mb-4">Tus pedidos</h2>
        <br>
        <br>
         <div class="row">
         <div class="col-lg-2">

	   		 </div>
        <div class="col-lg-8">
        	
            <table class="table table-hover">
            
                <thead>
                  <tr>
                    <th>#ID</th>
                    <th>Fecha</th>
                    <th>Estado</th>
                    <th></th>
                    <th></th>
                  </tr>
                </thead>
                
                <tbody>
                
                <c:forEach items="${pedidos}" var="pedido">
                	<tr>
                    <th scope="row">${pedido.getIdPedido()}</th>
                    <td>${pedido.getFecha()}</td>
                    <td>${pedido.getEstado().getNombre()}</td>
                    <td><a href="ClienteServletPrincipal?opcion=detallePedido&idpedido=${pedido.getIdPedido()}" class="btn btn-primary">Ver productos</a></td>
                    <c:if test = "${pedido.getEstado().getIdEstado()} == 1}">
							<td>No hay productos registrados</td>
						</c:if>
                    <td><a href="CarritoServlet?opcion=cancelarPedido&idpedidocancelar=${pedido.getIdPedido()}" class="btn btn-primary">Devolver</a></td>
                  
                  </tr>
                  
				</c:forEach>
			
                 <br>
                 <br>
				 </tbody>
              </table>
        </div>
        </div>
    </div>
</body>
</html>