<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

/**
 * @property integer $id_pedido
 * @property string $fecha
 * @property Factura[] $facturas
 * @property Estado $estado
 * @property Usuario $id_usuario
 */
class Pedido extends Model
{
    public $timestamps = false;

    /**
     * The primary key for the model.
     *
     * @var string
     */
    protected $primaryKey = 'id_pedido';

    /**
     * @var array
     */
    protected $fillable = ['id_usuario', 'estado', 'fecha'];

    /**
     * @return \Illuminate\Database\Eloquent\Relations\HasMany
     */
    public function facturas()
    {
        return $this->hasMany('App\Models\Factura', 'id_pedido', 'id_pedido');
    }

    /**
     * @return \Illuminate\Database\Eloquent\Relations\BelongsTo
     */
    public function estado()
    {
        return $this->belongsTo('App\Models\Estado', 'estado', 'id_estado');
    }

    /**
     * @return \Illuminate\Database\Eloquent\Relations\BelongsTo
     */
    public function usuario()
    {
        return $this->belongsTo('App\Models\Usuario', 'id_usuario', 'id_usuario');
    }
}
