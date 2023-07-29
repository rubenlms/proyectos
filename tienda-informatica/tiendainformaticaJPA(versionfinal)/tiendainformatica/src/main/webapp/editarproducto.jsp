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

        <title>Editar producto</title>

        <!-- Custom fonts for this template-->
        <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
        <link
            href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
            rel="stylesheet">

        <!-- Custom styles for this template-->
        <link href="css/sb-admin-2.min.css" rel="stylesheet">
</head>
	<body class="bg-gradient-primary">
        <div class="container">

            <!-- Outer Row -->
            <div class="row justify-content-center">

                <div class="col-xl-10 col-lg-12 col-md-9">

                    <div class="card o-hidden border-0 shadow-lg my-5">
                        <div class="card-body p-0">
                            <!-- Nested Row within Card Body -->
                            <div class="row justify-content-center">
                               <!--<div class="col-lg-6 d-none d-lg-block bg-login-image"></div>-->
                                <div class="col-lg-6">
                                    <div class="p-5">
                                        <div class="text-center">
                                            <h1 class="h4 text-gray-900 mb-4">Editar un producto</h1>
                                        </div>
                                        <form action="AdminServletPrincipal" class="user" method="POST">
                                              <div class="form-group">
                                                <input type="text" class="form-control form-control-user"
                                                    name="idproducto" aria-describedby="emailHelp" value="${producto.getIdProducto()}"
                                                    placeholder="ID*" hidden="true">
                                            </div>
                                            <div class="form-group">
                                                <input type="text" class="form-control form-control-user"
                                                    name="nombre" aria-describedby="emailHelp" value="${producto.getNombre()}"
                                                    placeholder="Nombre*">
                                            </div>
                                            <div class="form-group">
                                                <input type="text" class="form-control form-control-user"
                                                    name="descripcion" value="${producto.getDescripcion()}" placeholder="Descripcion">
                                            </div>
                                            <div class="form-group">
                                                <select  name="categoria" class="form-control custom-select custom-select-lg mb-3">
                                                	<c:forEach items="${categorias}" var="categoria">
                      									<option value="${categoria.getIdCategoria()}">${categoria.getNombre()}</option>
                    								</c:forEach>
                                                </select>
                                            </div>
                                            <div class="form-group">
                                                <input type="number" step="any" class="form-control form-control-user"
                                                    name="precio" aria-describedby="emailHelp" value="${producto.getPrecio()}"
                                                    placeholder="Precio*">
                                            </div>
                                            <div class="form-group">
                                                <input type="number" class="form-control form-control-user"
                                                    name="stock" aria-describedby="emailHelp" value="${producto.getStock()}"
                                                    placeholder="Stock*">
                                            </div>
                                            <div class="form-group">
                                                <div class="custom-control custom-checkbox small">
                                                </div>
                                            </div>
                                            <div class="text-center">
                                            <input type="submit" id="operaciones" name="operaciones" value="Editar producto" class="btn btn-primary btn-user btn-block">
                                            </div>
                                        </form>
                                        <br>
                                        <div class="text-center">
                                            <a class="small" href="${rutavuelta}">Volver</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>

            </div>

        </div>
</body>
</html>