<!DOCTYPE html>
<html lang="{{ str_replace('_', '-', app()->getLocale()) }}">

<head>
    <title>Menu Cliente - Tienda</title>
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
            <ul class="nav">
                <li class="nav-item">
                    <p class="font-weight-bolder">{{ $usuario->nombre }}</p>
                </li>
                <li class="nav-item">
                    <p>&nbsp;&nbsp;&nbsp;</p>
                </li>
                <li class="nav-item">
                    <a class="btn btn-primary" href="/vertodo">Volver</a>
                </li>
            </ul>
        </div>

        <br>
        <br>
        <h2 class="h4 text-gray-900 mb-4">Tienes estos productos en tu carrito</h2>
        <br>
        <br>
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
                        </tr>
                    </thead>

                    <tbody>

                        @if ($facturas != null)
                            @foreach ($facturas as $factura)
                                <tr>
                                    <th scope="row">{{ $factura->id_factura }}</th>
                                    <td>{{ $factura->id_producto->nombre }}</td>
                                    <td>{{ $factura->precio }}€</td>
                                    <td>{{ $factura->cantidad }}</td>
                                    <td scope="row">
                                        <a href="/delFactura?factura={{ $factura->id_factura }}" class="btn btn-primary">Eliminar</a>
                                    </td>
                                </tr>
                            @endforeach
                        @endif

                        <div>
                            <h4>Total {{$total}}€</h4>
                        </div>

                        <div>
                            <a href="/pagar?pedido={{$id_pedido}}" class="btn btn-primary">Pagar</a>
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
