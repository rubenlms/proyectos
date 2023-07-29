<!DOCTYPE html>
<html lang="{{ str_replace('_', '-', app()->getLocale()) }}">

<head>
    <title>Inventario</title>
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
                    <p class="font-weight-bolder">{{$usuario->nombre}}</p>
                </li>
                <li class="nav-item">
                    <p>&nbsp;&nbsp;&nbsp;</p>
                </li>
                <li class="nav-item">
                    <a href="productos" class="btn btn-primary">Volver</a>
                </li>

            </ul>
        </div>

        <br> <br>
        <h1 class="h4 text-gray-900 mb-4">Categorías existentes</h1>



        <br> <br>

        <div class="row">
            <div class="col-lg-4">
                <form action="/addCategoria" class="form-inline">
                    <input type="text" class="form-control mb-2 mr-sm-2" id="inlineFormInputName2" placeholder="Nueva categoria" name="nombre">
                    <button type="submit" class="btn btn-primary mb-2">Guardar</button>
                </form>
            </div>
            <div class="col-lg-4">

                <table class="table table-hover">

                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Categoria</th>
                        </tr>
                    </thead>

                    <tbody>
                        @if ($categorias != null)
                            @foreach ($categorias as $categoria)
                                <tr>
                                    <th scope="row">{{ $categoria->id_categoria }}</th>
                                    <td>{{ $categoria->nombre }}</td>
                                    <td scope="row">
                                        <a href="/modCategoria?categoria={{ $categoria->id_categoria }}" class="btn btn-primary">Editar</a>
                                    </td>
                                    <td scope="row">
                                        <a href="/delCategoria?categoria={{ $categoria->id_categoria }}" class="btn btn-primary">Eliminar</a>
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
