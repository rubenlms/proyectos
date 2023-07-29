<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

/**
 * @property integer $id_producto
 * @property integer $categoria
 * @property string $nombre
 * @property string $descripcion
 * @property float $precio
 * @property integer $stock
 * @property Factura[] $facturas
 * @property Categoria $categoria
 */
class Producto extends Model
{
    public $timestamps = false;

    /**
     * The primary key for the model.
     *
     * @var string
     */
    protected $primaryKey = 'id_producto';
    //$producto->id_categoriabase\Eloquent\Relations\HasMany

    public function facturas()
    {
        return $this->hasMany('App\Models\Factura', 'id_producto', 'id_producto');
    }

    /**
     * @return \Illuminate\Database\Eloquent\Relations\BelongsTo
     */
    public function categoria()
    {
        return $this->belongsTo('App\Models\Categoria', 'categoria', 'id_categoria');
    }
}
