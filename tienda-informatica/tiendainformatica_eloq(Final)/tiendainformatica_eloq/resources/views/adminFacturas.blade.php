<!DOCTYPE html>
<html lang="{{ str_replace('_', '-', app()->getLocale()) }}">
    <head>
        <title>Menu Admin - Facturas</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
        <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
    </head>
    <body class="antialiased">

        @php
            $usuario = session('usuario');
        @endphp
        <div class="container-fluid">
            <br>
            <br>

                <div class="bg-gradient-primary">
                    <ul class="nav" >
                        <li class="nav-item">
                          <p class="font-weight-bolder">{{$usuario->nombre}}</p>
                        </li>
                        <li class="nav-item">
                            <p>&nbsp;&nbsp;&nbsp;</p>
                        </li>
                        <li class="nav-item">
                          <form action="/facturas" method="POST">
                                @csrf
                                <input type="submit" name="operaciones" value="Ver todo" class="btn btn-primary">
                            </form>
                        </li>
                        <li class="nav-item">
                            <p>&nbsp;&nbsp;&nbsp;</p>
                        </li>
                        <li class="nav-item">
                            <a href="menuAdmin" class="btn btn-primary">Inicio</a>
                        </li>
                      </ul>
                </div>

            <br>
            <br>
            <h1 class="h4 text-gray-900 mb-4">Consulta las facturas</h1>
            <br>
            <br>
            <div class="row">
                <div class="col-lg-2">
                    <div class="dropdown">
                        <button class="btn btn-secondary dropdown-toggle"
                                type="button" id="dropdownMenu1" data-toggle="dropdown"
                                aria-haspopup="true" aria-expanded="false">
                            Pedido
                        </button>
                        <div class="dropdown-menu" aria-labelledby="dropdownMenu1">
                        @if ($pedidos!=null)
                            @foreach ($pedidos as $pedido)
                                <a class="dropdown-item" href="facturas?pedido={{$pedido->id_pedido}}">{{$pedido->id_pedido}}</a>
                            @endforeach
                        @endif
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
                        @if ($facturas != null)
                            @foreach ($facturas as $factura)
                            <tr>
                                <th scope="row">{{$factura->id_factura}}</th>
                                <td>{{$factura->id_pedido}}</td>
                                <td>{{$factura->id_producto->nombre}}</td>
                                <td>{{$factura->cantidad}}</td>
                                <td>{{$factura->precio}}€</td>
                            </tr>
                            @endforeach
                        @else
                            <p>No hay productos registrados</p>
                        @endif
                    </tbody>
                </table>
            </div>
            </div>
        </div>
    </body>
</html>
