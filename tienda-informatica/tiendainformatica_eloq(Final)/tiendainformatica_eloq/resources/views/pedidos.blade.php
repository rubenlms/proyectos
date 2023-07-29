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
                        </tr>
                    </thead>

                    <tbody>

                        @if ($pedidos != null)
                            @foreach ($pedidos as $pedido)
                                <tr>
                                    <th scope="row">{{ $pedido->id_pedido }}</th>
                                    <td>{{ $pedido->fecha }}</td>
                                    <td>{{ $pedido->estado->nombre }}</td>
                                    <td scope="row">
                                        <a href="verMas?pedido={{ $pedido->id_pedido }}" class="btn btn-primary">Ver
                                            productos</a>
                                    </td>
                                    @if($pedido->estado->id_estado == 1 || $pedido->estado->id_estado == 2)
                                        <td scope="row">
                                            <a href="errPedido?estado=cancelar&pedido={{$pedido->id_pedido}}" class="btn btn-primary">Cancelar</a>
                                        </td>
                                    @endif
                                    @if($pedido->estado->id_estado == 3 || $pedido->estado->id_estado == 4)
                                        <td scope="row">
                                            <a href="errPedido?estado=devolver&pedido={{$pedido->id_pedido}}" class="btn btn-primary">Devolver</a>
                                        </td>
                                    @endif
                                </tr>
                            @endforeach
                        @else
                            <p>No hay registros</p>
                        @endif
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>

</html>
