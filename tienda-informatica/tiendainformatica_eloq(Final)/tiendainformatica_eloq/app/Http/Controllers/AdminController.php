<?php

namespace App\Http\Controllers;

use App\Models\Categoria;
use App\Models\Factura;
use App\Models\Pedido;
use App\Models\Producto;
use Exception;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use App\Models\Estado;
use SebastianBergmann\Type\NullType;

class AdminController extends Controller
{
    public function findProductos(Request $request){
        $id = $request->get("producto");
        $productosGen = null;
        $productos = null;
        $categorias[] = null;
        $cat = $request->get("categoria");

        DB::beginTransaction();
        if($id != null){
            $producto = Producto::find($id);
            $categorias = Categoria::all();

            return view('modProducto', compact('producto'), compact("categorias"));

        }else{
            try{
                $productosGen = Producto::all();
                //$productosGen = Producto::with('Categoria')->get();
                DB::commit();

                if($cat != null){
                    if($productosGen != null){
                        $i = 0;
                        foreach ($productosGen as $productoGen) {
                            $categoria = $productoGen->categoria()->find($productoGen->categoria);

                            if($categoria->nombre == $cat){
                                $productos[$i] = $productoGen;
                            }

                            if(!in_array($categoria, $categorias) && $categoria != null){
                                $categorias[$i] = $categoria;
                            }

                            $i++;
                        }
                    }

                }else{
                    if($productosGen != null){
                        $i = 0;
                        foreach ($productosGen as $productoGen) {
                            $productos[$i] = $productoGen;
                            $categoria = $productoGen->categoria()->find($productoGen->categoria);

                            if(!in_array($categoria, $categorias) && $categoria != null){
                                $categorias[$i] = $categoria;
                            }

                            $i++;
                        }
                    }
                }
                //$categorias = Categoria::all();

            }catch(Exception $e){
                DB::rollBack();
                $productos = null;
            }

            return view('adminProductos', compact('productos'), compact('categorias'));
        }
    }

    public function saveProductos(Request $request){
        $nombre = $request->get("nombre");
        $descripcion = $request->get("descripcion");
        $categoria = $request->get("categoria");
        $precio = $request->get("precio");
        $stock = $request->get("stock");

        if($nombre != null && $descripcion != null && $categoria != null && $precio != null && $stock != null){
            try{
                DB::beginTransaction();
                $producto = new Producto();
                $producto->nombre = $nombre;
                $producto->descripcion = $descripcion;
                $producto->categoria = $categoria;
                $producto->precio = $precio;
                $producto->stock = $stock;
                $producto->save();
                DB::commit();

            } catch(Exception $e){
                DB::rollBack();
                $producto = null;
            }

            $mensaje = "Producto añadido correctamente";

        }else{
            $mensaje = "Error al añadir el producto";
        }
        return view('resProducto', compact("mensaje"));
    }

    public function modProducto(Request $request){
        $id = $request->get("id");
        $descripcion = $request->get("descripcion");
        $nombre = $request->get("nombre");
        $id_categoria = $request->get("categoria");
        $precio = $request->get("precio");
        $stock = $request->get("stock");

        if($id != null && $nombre != null && $descripcion != null && $id_categoria != null && $precio != null && $stock != null){
            $producto = Producto::find($id);

            if($producto != null){
                try{
                    DB::beginTransaction();
                    $producto->nombre = $nombre;
                    $producto->descripcion = $descripcion;
                    $producto->categoria = $id_categoria;
                    $producto->precio = $precio;
                    $producto->stock = $stock;

                    $producto->save();

                    DB::commit();

                } catch(Exception $e){
                    DB::rollBack();
                    $producto = null;
                }

                $mensaje = "Producto editado correctamente";

            }else{
                $mensaje = "Error al añadir el producto";
            }

        }else{
            $mensaje = "Error al añadir el producto";
        }

        return view('resProducto', compact("mensaje"));
    }

    public function formProductos(){
        $categorias = Categoria::all();

        return view('addProducto', compact("categorias"));
    }

    public function delProducto(Request $request){
        $id_delete = $request->get("producto_del");

        if($id_delete != null){
            $producto = Producto::find($id_delete);

            if($producto != null){
                try{
                    //DB::beginTransaction();
                    $producto->delete();
                    //DB::commit();

                } catch(Exception $e){
                    DB::rollBack();
                    $producto = null;
                }
            }
        }

        return $this->findProductos($request);
    }

    public function findFacturas(Request $request){
        $facturasGen = null;
        $facturas = null;
        $pedidos[] = null;
        $ped = $request->get("pedido");

        DB::beginTransaction();

        try{
            $facturasGen = Factura::all();
            DB::commit();

            if($ped != null){
                if($facturasGen != null){
                    $i = 0;
                    foreach ($facturasGen as $facturaGen) {
                        $pedido = $facturaGen->pedido;

                        $producto = Producto::find($facturaGen->id_producto);
                        $facturaGen->id_producto = $producto;

                        if($pedido->id_pedido == $ped){
                            $facturas[$i] = $facturaGen;
                        }

                        if(!in_array($pedido, $pedidos) && $pedido != null){
                            $pedidos[$i] = $pedido;
                        }

                        $i++;
                    }
                }

            }else{
                if($facturasGen != null){
                    $i = 0;
                    foreach ($facturasGen as $facturaGen) {
                        $producto = Producto::find($facturaGen->id_producto);
                        $facturaGen->id_producto = $producto;

                        $facturas[$i] = $facturaGen;
                        $pedido = $facturaGen->pedido;

                        if(!in_array($pedido, $pedidos) && $pedido != null){
                            $pedidos[$i] = $pedido;
                        }

                        $i++;
                    }
                }
            }

        }catch(Exception $e){
            DB::rollBack();
            $productos = null;
        }

        return view('adminFacturas', compact('facturas'), compact('pedidos'));
    }

    public function findPedidos(Request $request){
        $id = $request->get("pedido");
        $pedidosGen = null;
        $pedidos = null;
        $usuarios[] = null;
        $user = $request->get("usuario");

        if($id != null){
            $pedido = Pedido::find($id);
            $estados = Estado::all();

            return view('modPedido', compact('pedido'), compact("estados"));

        }else{
            DB::beginTransaction();

            try{
                $pedidosGen = Pedido::all();
                DB::commit();

                if($user != null){
                    if($pedidosGen != null){
                        $i = 0;
                        foreach ($pedidosGen as $pedidoGen) {
                            $estado = Estado::find($pedidoGen->estado);
                            $pedidoGen->estado = $estado;

                            $usuario = $pedidoGen->usuario;

                            if($usuario->nombre == $user && $pedidoGen->estado->id_estado != 7){
                                $pedidos[$i] = $pedidoGen;
                            }

                            if(!in_array($usuario, $usuarios) && $usuario != null){
                                $usuarios[$i] = $usuario;
                            }

                            $i++;
                        }
                    }

                }else{
                    if($pedidosGen != null){
                        $i = 0;
                        foreach ($pedidosGen as $pedidoGen) {
                            $estado = Estado::find($pedidoGen->estado);
                            $pedidoGen->estado = $estado;

                            if($pedidoGen->estado->id_estado != 7){
                                $pedidos[$i] = $pedidoGen;
                                $usuario = $pedidoGen->usuario;
                            }

                            if(!in_array($usuario, $usuarios) && $usuario != null){
                                $usuarios[$i] = $usuario;
                            }

                            $i++;
                        }
                    }
                }

            }catch(Exception $e){
                DB::rollBack();
                $productos = null;
            }

            return view('adminPedidos', compact('pedidos'), compact('usuarios'));
        }
    }

    public function modPedido(Request $request){
        $id = $request->get("id");
        $usuario = $request->get("usuario");
        $fecha = $request->get("fecha");
        $estado = $request->get("estado");

        if($id != null && $usuario != null && $fecha != null && $estado != null){
            try{
                DB::beginTransaction();

                $pedido = Pedido::find($id);
                $pedido->estado = $estado;
                $pedido->save();

                DB::commit();

                $mensaje = "Pedido editado correctamente";

            } catch(Exception $e){
                DB::rollBack();
                $pedido = null;
            }
        }else{
            $mensaje = "Error al editar el pedido";
        }

        return view('resPedido', compact("mensaje"));
    }

    public function findCategorias(Request $request){
        $id = $request->get("categoria");

        if($id == null){
            $categorias = Categoria::all();
            return view('categorias', compact("categorias"));
        }else{
            $categoria = Categoria::find($id);
            return view('modCategoria', compact("categoria"));
        }
    }

    public function saveCategorias(Request $request){
        $nombre = $request->get("nombre");

        if($nombre != null){
            try{
                DB::beginTransaction();
                $categoria = new Categoria();
                $categoria->nombre = $nombre;
                $categoria->save();
                DB::commit();

            } catch(Exception $e){
                DB::rollBack();
                $categoria = null;
            }
        }

        $categorias = Categoria::all();
        return view('categorias', compact("categorias"));
    }

    public function modCategoria(Request $request){
        $nombre = $request->get("nombre");
        $id = $request->get("id");

        if($id != null && $nombre != null){
            $categoria = Categoria::find($id);

            if($categoria != null){
                try{
                    DB::beginTransaction();
                    $categoria->nombre = $nombre;
                    $categoria->save();
                    DB::commit();

                } catch(Exception $e){
                    DB::rollBack();
                    $categoria = null;
                }
            }
        }
        $categorias = Categoria::all();
        return view('categorias', compact("categorias"));
    }

    public function delCategoria(Request $request){
        $id = $request->get("categoria");

        if($id != null){
            $categoria = Categoria::find($id);

            if($categoria != null){
                try{
                    //DB::beginTransaction();
                    $categoria->delete();
                    //DB::commit();

                } catch(Exception $e){
                    DB::rollBack();
                    $categoria = null;
                }
            }
        }
        $categorias = Categoria::all();
        return view('categorias', compact("categorias"));
    }
}
