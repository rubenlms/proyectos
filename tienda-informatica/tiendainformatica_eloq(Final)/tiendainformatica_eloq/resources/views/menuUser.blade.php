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
                    <form action="/vertodo" method="POST">
                        @csrf
                        <input type="submit" name="operaciones" value="Ver todo" class="btn btn-primary">
                    </form>
                </li>
                <li class="nav-item">
                    <p>&nbsp;&nbsp;&nbsp;</p>
                </li>
                <li class="nav-item">
                    <form action="/carrito" method="POST">
                        @csrf
                        <input type="submit" name="operaciones" value="Carrito" class="btn btn-primary">
                    </form>
                </li>
                <li class="nav-item">
                    <p>&nbsp;&nbsp;&nbsp;</p>
                </li>
                <li class="nav-item">
                    <form action="/mispedidos" method="POST">
                        @csrf
                        <input type="submit" name="operaciones" value="Ver mis pedidos" class="btn btn-primary">
                    </form>
                </li>
                <li class="nav-item">
                    <p>&nbsp;&nbsp;&nbsp;</p>
                </li>
                <li>
                    <a class="btn btn-primary" href="/login">Cerrar Sesión</a>
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
                    <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenu1"
                        data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Categoría
                    </button>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenu1">
                        @if ($categorias != null)
                            @foreach ($categorias as $categoria)
                                <a class="dropdown-item"
                                    href="buscarCategoria?categoria={{ $categoria->nombre }}">{{ $categoria->nombre }}</a>
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
                            <th>Producto</th>
                            <th>Precio</th>
                            <th>Stock</th>
                        </tr>
                    </thead>

                    <tbody>
                        @if ($productos != null)
                            @foreach ($productos as $producto)
                                <tr>
                                    <th scope="row">{{ $producto->id_producto }}</th>
                                    <td>{{ $producto->nombre }}</td>
                                    <td>{{ $producto->precio }} €</td>
                                    <td>{{$producto->stock}}</td>
                                    <td scope="row">
                                        <a class="btn btn-primary" href="/addCarrito?producto={{$producto->id_producto}}">Añadir al carrito</a>
                                    </td>
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
