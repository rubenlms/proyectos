<?php

namespace App\Http\Controllers;

use App\Models\Producto;
use App\Models\Pedido;
use App\Models\Estado;
use App\Models\Factura;
use Illuminate\Http\Request;
use Exception;
use Illuminate\Support\Facades\DB;

class ClienteController extends Controller
{
    /**
     * Recoge todos los productos y sus categorías de la DDBB
     */
    public function findProductos(){
            $productos = null;
            $categorias[] = null;

            $productosGen = Producto::all();
            $i = 0;
            foreach($productosGen as $producto){

                //Se muestran los pedidos con stock 0 o no?
                //if($producto->stock > 0){
                    $productos[$i] = $producto;
                    $categoria = $producto->categoria()->find($producto->categoria);

                    if (!(in_array($categoria, $categorias))){
                        $categorias[$i] = $categoria;
                    }
                    $i++;
                //}
            }

        return view('menuUser', compact('productos'), compact('categorias'));

    }

    public function buscarCategoria(Request $request){
        $cat = $request->get("categoria");
        //$productosGen = null;
        $productosGen = Producto::all();
        $categorias[]=null;
        //$productos[] = null;

        if($cat!=null){
            if($productosGen != null){
                $i = 0;
                foreach ($productosGen as $productoGen) {
                    $categoria = $productoGen->categoria()->find($productoGen->categoria);

                    if($categoria->nombre == $cat && $productoGen->stock > 0){
                        $productos[$i] = $productoGen;
                    }

                    if(!in_array($categoria, $categorias) && $categoria != null){
                        $categorias[$i] = $categoria;
                    }

                    $i++;
                }
            }
        }

        return view('menuUser', compact('productos'), compact('categorias'));
    }

    public function verCarrito(Request $request){
        $usuariosesion = $request->session()->get('usuario');
        $dniuser = $usuariosesion->id_usuario;
        $pedidosUser = Pedido::with('Estado')->where('id_usuario', "=",$dniuser)->get();
        $total = 0;

        $facturasGen = Factura::all();
        $pedido = null;
        $facturas = null;

        foreach ($pedidosUser as $pedidoPos) {
            if($pedidoPos->estado == 7){
                $pedido = $pedidoPos;
            }
        }

        if($pedido != null){
            $facturas = Factura::where('id_pedido', "=", $pedido->id_pedido)->get();

            if($facturas != null){
                foreach ($facturas as $factura) {
                    $producto = Producto::find($factura->id_producto);
                    $factura->id_producto = $producto;
                    $cant = $factura->cantidad * $factura->precio;
                    $total += $cant;
                }
            }

        }else{
            DB::beginTransaction();

            try{
                $pedido = new Pedido();
                $pedido->id_usuario = $usuariosesion->id_usuario;
                $pedido->fecha = date("Y-m-d");
                $pedido->estado = 7;

                $pedido->save();

                DB::commit();

            } catch(Exception $e){
                DB::rollBack();
            }
        }

        $id_pedido = $pedido->id_pedido;

        return view('carrito', compact('facturas'), compact("total", "id_pedido"));
    }

    /**
     * Busca todos los pedidos de un usuario
     * (funciona pero el array facturas sobreescribe y solo llega a mostrar el último producto)
     */
    public function verPedidos(Request $request){
        $usuariosesion = $request->session()->get('usuario');
        $dniuser = $usuariosesion->id_usuario;
        $pedidosUser = Pedido::with('Estado')->where('id_usuario', "=",$dniuser)->get();
        $pedidos = null;

        $i = 0;
        foreach ($pedidosUser as $pedido) {
            $estado = Estado::find($pedido->estado);
            $pedido->estado = $estado;

            if($pedido->estado->id_estado != 7){
                $pedidos[$i] = $pedido;
                $i++;
            }
        }

        return view('pedidos', compact('pedidos'));
    }

    public function addtoCarrito(Request $request){
        $usuariosesion = $request->session()->get('usuario');
        $dniuser = $usuariosesion->id_usuario;
        $idProducto = $request->get("producto");
        $pedido = null;

        if($idProducto!=null){

            $producto = Producto::find($idProducto);

            if($producto!=null){

                $pedidosUser = Pedido::with('Estado')->where('id_usuario', "=",$dniuser)->get();

                foreach ($pedidosUser as $pedidoPos) {
                    if($pedidoPos->estado == 7){
                        $pedido = $pedidoPos;
                    }
                }

                if($pedido != null){
                    $facturas = Factura::where('id_pedido','=',$pedido->id_pedido)->get();
                    $factura = null;

                    foreach ($facturas as $facturaPos) {
                        if($facturaPos->id_producto == $producto->id_producto){
                            $factura = $facturaPos;
                        }
                    }

                    if($factura != null){

                        DB::beginTransaction();
                        try{
                            $stock = $factura->cantidad;
                            $factura->cantidad = $stock + 1;

                            $factura->save();

                            DB::commit();

                        } catch(Exception $e){
                            DB::rollBack();
                        }

                    }else{
                        DB::beginTransaction();

                        try{
                            $factura = new Factura();
                            $factura->id_pedido = $pedido->id_pedido;
                            $factura->id_producto = $producto->id_producto;
                            $factura->cantidad = 1;
                            $factura->precio = $producto->precio;

                            $factura->save();

                            DB::commit();

                        } catch(Exception $e){
                            DB::rollBack();
                        }
                    }

                }else{
                    DB::beginTransaction();

                    try{
                        $pedido = new Pedido();
                        $pedido->id_usuario = $usuariosesion->id_usuario;
                        $pedido->fecha = date("Y-m-d");
                        $pedido->estado = 7;

                        $pedido->save();

                        $factura = new Factura();
                        $factura->pedido()->associate($pedido);
                        $factura->producto()->associate($producto);
                        $factura->cantidad = 1;
                        $factura->precio = $producto->precio;

                        $factura->save();

                        DB::commit();

                    } catch(Exception $e){
                        DB::rollBack();
                    }


                }
            }
        }

        $categorias[] = null;
        $productos = Producto::all();
        $i = 0;
        foreach($productos as $producto){

            $categoria = $producto->categoria()->find($producto->categoria);

            if (!(in_array($categoria, $categorias))){
                $categorias[$i] = $categoria;
            }
            $i++;
        }

        return view('menuUser', compact('productos'), compact('categorias'));

    }

    public function facturasByPedido(Request $request){
        $id_pedido = $request->get("pedido");
        $total = 0;

        $facturas = Factura::where('id_pedido', "=", $id_pedido)->get();

        foreach ($facturas as $factura) {
            $producto = Producto::find($factura->id_producto);
            $factura->id_producto = $producto;
            $cant = $factura->cantidad * $factura->precio;
            $total += $cant;
        }

        return view('verMas', compact('facturas'), compact("total"));
    }

    public function delFactura(Request $request){
        $id = $request->get("factura");

        if($id != null){
            $factura = Factura::find($id);
            $stock = $factura->cantidad;

            if($stock > 1){
                DB::beginTransaction();
                try{
                    $factura->cantidad = $stock - 1;
                    $factura->save();

                    DB::commit();

                } catch(Exception $e){
                    DB::rollBack();
                }
            }else{
                if($factura != null){
                    try{
                        $factura->delete();

                    } catch(Exception $e){
                        DB::rollBack();
                        $factura = null;
                    }
                }
            }
        }
        return $this->verCarrito($request);
    }

    public function errPedido(Request $request){
        $estado = $request->get("estado");
        $id_pedido = $request->get("pedido");

        $pedido = Pedido::find($id_pedido);
        $facturas = Factura::where('id_pedido', "=", $id_pedido)->get();

        if($pedido != null && sizeof($facturas) != 0){
            try{
                DB::beginTransaction();
            //
                foreach ($facturas as $factura) {
                    $producto = Producto::find($factura->id_producto);

                    $producto->stock += $factura->cantidad;
                    $producto->save();
                }
            //
                if($estado == "cancelar"){
                    $pedido->estado = 5;
                    $pedido->save();

                }else if($estado == "devolver"){
                    $pedido->estado = 6;
                    $pedido->save();
                }

                DB::commit();

            } catch(Exception $e){
                DB::rollBack();
            }
        }


        return $this->verPedidos($request);
    }

    public function pagar(Request $request){
        $id_pedido = $request->get("pedido");

        $facturas = Factura::where('id_pedido', "=", $id_pedido)->get();
        $pedido = Pedido::find($id_pedido);

        if($pedido != null && sizeof($facturas) != 0){
            try{
                DB::beginTransaction();

                $stock = true;

                foreach ($facturas as $factura) {
                    $producto = Producto::find($factura->id_producto);

                    if($producto->stock >= $factura->cantidad){
                        $producto->stock -= $factura->cantidad;
                        $producto->save();
                    }else{
                        $stock = false;
                    }
                }

                $mensaje = "Error al realizar la compra";

                if($stock == true){
                    $pedido->estado = 1;
                    $pedido->save();

                    DB::commit();

                    $mensaje = "Compra realizada correctamente";
                }

            }catch(Exception $e){
                DB::rollBack();
            }

        }else{
            $mensaje = "Error al realizar la compra";
        }

        return view("resPagar", compact("mensaje"));
    }
}
