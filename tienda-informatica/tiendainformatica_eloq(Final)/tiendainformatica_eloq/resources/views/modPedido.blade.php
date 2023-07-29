<!DOCTYPE html>
<html lang="{{ str_replace('_', '-', app()->getLocale()) }}">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Admin - Editar Pedido</title>

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
                                            <h1 class="h4 text-gray-900 mb-4">Editar Pedido</h1>
                                        </div>
                                        <form action="/modPedido" class="user" method="POST">
                                            @csrf
                                            <div class="form-group">
                                                <input type="text" class="form-control form-control-user"
                                                    name="id" aria-describedby="emailHelp"
                                                    placeholder="Id*" value="{{$pedido->id_pedido}}" hidden="true">
                                            </div>
                                            <div class="form-group">
                                                <input type="text" class="form-control form-control-user"
                                                    name="usuario" aria-describedby="emailHelp"
                                                    placeholder="Usuario*" value="{{$pedido->usuario->nombre}}" hidden="true">
                                            </div>
                                            <div class="form-group">
                                                <input type="text" class="form-control form-control-user"
                                                    name="" aria-describedby="emailHelp"
                                                    placeholder="Usuario*" value="{{$pedido->usuario->nombre}}" disabled>
                                            </div>
                                            <div class="form-group">
                                                <input type="text" class="form-control form-control-user"
                                                    name="fecha" placeholder="Fecha*" value="{{$pedido->fecha}}" hidden="true">
                                            </div>
                                            <div class="form-group">
                                                <input type="text" class="form-control form-control-user"
                                                    name="" placeholder="Fecha*" value="{{$pedido->fecha}}" disabled>
                                            </div>
                                            <div class="form-group">
                                                <select class="form-control custom-select custom-select-lg mb-3" name="estado">
                                                    @if($estados != null)
                                                        @foreach ($estados as $estado)
                                                            <option value="{{$estado->id_estado}}">{{$estado->nombre}}</option>
                                                        @endforeach
                                                    @endif
                                                </select>
                                            </div>
                                            <div class="form-group">
                                                <div class="custom-control custom-checkbox small">
                                                </div>
                                            </div>
                                            <div class="text-center">
                                            <input type="submit" name="editar" value="Editar" class="btn btn-primary btn-user btn-block">
                                            </div>
                                        </form>
                                        <br>
                                        <div class="text-center">
                                            <a class="small" href="pedidos">Volver</a>
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
