<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

/**
 * @property integer $id_factura
 * @property integer $cantidad
 * @property float $precio
 * @property Pedido $id_pedido
 * @property Producto $id_producto
 */
class Factura extends Model
{
    public $timestamps = false;

    /**
     * The primary key for the model.
     *
     * @var string
     */
    protected $primaryKey = 'id_factura';

    /**
     * @var array
     */
    protected $fillable = ['id_pedido', 'id_producto', 'cantidad', 'precio'];

    /**
     * @return \Illuminate\Database\Eloquent\Relations\BelongsTo
     */
    public function pedido()
    {
        return $this->belongsTo('App\Models\Pedido', 'id_pedido', 'id_pedido');
    }

    /**
     * @return \Illuminate\Database\Eloquent\Relations\BelongsTo
     */
    public function producto()
    {
        return $this->belongsTo('App\Models\Producto', 'id_producto', 'id_producto');
    }
}
