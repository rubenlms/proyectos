<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Exception;
use Illuminate\Support\Facades\DB;
use App\Models\Producto;
use App\Models\Categoria;
use App\Models\Usuario;
use App\Models\Rol;

class LoginController extends Controller
{
    /**
     * Registra a un nuevo usuario
     * No consigue recoger los campos del formulario?
     */
    public function registrar(Request $request){
        //$repeticiones = ['repeticion'=>6];

        $dni = $request->get("dnireg");
        $nombre = $request->get("nombreg");
        $password = $request->get("passwordreg");
        $telefono = $request->get("telefonoreg");
        $direccion = $request->get("direccionreg");

        if($dni!=null && $nombre!=null && $password!=null && $direccion!=null){

            try{
                DB::beginTransaction();
                $rol = Rol::find(2);
                $passwordhash = password_hash($password, PASSWORD_DEFAULT);
                $usuario = new Usuario();
                $usuario->id_usuario = $dni;
                $usuario->nombre = $nombre;
                $usuario->password = $passwordhash;

                if($telefono!=null){
                    $usuario->telefono = $telefono;
                }
                $usuario->direccion = $direccion;
                $usuario->rol = 2;

                $usuario->save();
                DB::commit();

                $mensaje = "Registro de $usuario->nombre completo";

            }catch(Exception $e){
                DB::rollBack();
                $usuario = null;
            }

            return view('error', compact('mensaje'));

        } else {
            //faltan campos obligatorios
            $mensaje = "¡Faltan campos obligatorios!";
            return view('error', compact('mensaje'));
        }
    }

    public function validar(Request $request){
        session()->flush();
        $request->session()->regenerate();
        $usuario = null;

        //poner aqui el código del login para validar usuario
        $dni = $request->dniuser;
        $password = $request->passwordUser;

        if($dni!=null && $password!=null){
            $usuario = Usuario::find($dni);

            if($usuario!=null){

                if(password_verify($password, $usuario->password) && $dni==$usuario->id_usuario){

                    session()->put('usuario', $usuario);
                    $rol = Rol::find($usuario->rol);

                    if($rol->nombre == "admin"){
                        return view('menuAdmin');
                    }

                    if($rol->nombre == "user"){
                        $productos = null;
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


                }else{
                    //el dni o contraseña no es correcto
                    $mensaje = "El dni o contraseña no es correcto";
                    return view ('error', compact('mensaje'));
                }
            }else{
                //no se ha encontrado el usuario-no registrado
                $mensaje = "No se ha encontrado el usuario, no registrado";
                return view ('error', compact('mensaje'));
            }

        } else {
            //faltan campos por completar
            $mensaje = "Faltan campos por completar";
            return view ('error', compact('mensaje'));
        }

    }
}
