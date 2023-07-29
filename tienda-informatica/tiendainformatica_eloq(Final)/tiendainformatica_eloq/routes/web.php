<?php

use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Route::get('/', function () {
    return view('welcome');
});

//Usuarios

Route::get('/cliente', function(){
    return view('menuUser');
});

Route::get('/login', function(){
    return view('login');
});

Route::get('/registrarUsuario', function(){
    return view('registrarUsuario');
});

Route::post('/validar', '\App\Http\Controllers\LoginController@validar');
Route::post('/nuevoUsuario', '\App\Http\Controllers\LoginController@registrar');

Route::post('/vertodo', '\App\Http\Controllers\ClienteController@findProductos');
Route::get('/vertodo', '\App\Http\Controllers\ClienteController@findProductos');

Route::get('/categorias', '\App\Http\Controllers\ClienteController@findProductos');
Route::get('/buscarCategoria', '\App\Http\Controllers\ClienteController@buscarCategoria');

Route::post('/mispedidos', '\App\Http\Controllers\ClienteController@verpedidos');
Route::get('/mispedidos', '\App\Http\Controllers\ClienteController@verpedidos');
Route::get("/verMas", "\App\Http\Controllers\ClienteController@facturasByPedido");
Route::get('/errPedido', '\App\Http\Controllers\ClienteController@errPedido');

Route::post('/carrito', '\App\Http\Controllers\ClienteController@vercarrito');
Route::get('/addCarrito', '\App\Http\Controllers\ClienteController@addtoCarrito');

Route::get('/pagar', '\App\Http\Controllers\ClienteController@pagar');

Route::get("/delFactura", "\App\Http\Controllers\ClienteController@delFactura");

//Administrador

Route::get("/menuAdmin", function(){
    return view('menuAdmin');
});

Route::post("/productos", "\App\Http\Controllers\AdminController@findProductos");
Route::get("/productos", "\App\Http\Controllers\AdminController@findProductos");
Route::post("/addProductos", "\App\Http\Controllers\AdminController@saveProductos");
Route::get("/addProductos", "\App\Http\Controllers\AdminController@formProductos");
Route::get("/delProducto", "\App\Http\Controllers\AdminController@delProducto");
Route::post("/modProducto", "\App\Http\Controllers\AdminController@modProducto");
Route::get("/modProducto", "\App\Http\Controllers\AdminController@findProductos");

Route::post("/facturas", "\App\Http\Controllers\AdminController@findFacturas");
Route::get("/facturas", "\App\Http\Controllers\AdminController@findFacturas");

Route::post("/pedidos", "\App\Http\Controllers\AdminController@findPedidos");
Route::get("/pedidos", "\App\Http\Controllers\AdminController@findPedidos");
Route::get("/modPedido", "\App\Http\Controllers\AdminController@findPedidos");
Route::post("/modPedido", "\App\Http\Controllers\AdminController@modPedido");

Route::get("/categorias", "\App\Http\Controllers\AdminController@findCategorias");
Route::get("/addCategoria", "\App\Http\Controllers\AdminController@saveCategorias");
Route::post("/modCategoria", "\App\Http\Controllers\AdminController@modCategoria");
Route::get("/modCategoria", "\App\Http\Controllers\AdminController@findCategorias");
Route::get("/delCategoria", "\App\Http\Controllers\AdminController@delCategoria");
