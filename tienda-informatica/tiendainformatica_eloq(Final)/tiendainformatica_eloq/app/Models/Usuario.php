<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

/**
 * @property string $id_usuario
 * @property integer $rol
 * @property string $nombre
 * @property string $password
 * @property string $direccion
 * @property integer $telefono
 * @property Role $role
 * @property Pedido[] $pedidos
 */
class Usuario extends Model
{
    public $timestamps = false;

    /**
     * The primary key for the model.
     *
     * @var string
     */
    protected $primaryKey = 'id_usuario';

    /**
     * The "type" of the auto-incrementing ID.
     *
     * @var string
     */
    protected $keyType = 'string';

    /**
     * Indicates if the IDs are auto-incrementing.
     *
     * @var bool
     */
    public $incrementing = false;

    /**
     * @var array
     */
    protected $fillable = ['rol', 'nombre', 'password', 'direccion', 'telefono'];

    /**
     * @return \Illuminate\Database\Eloquent\Relations\BelongsTo
     */
    public function role()
    {
        return $this->belongsTo('App\Models\Role', 'rol', 'id_rol');
    }

    /**
     * @return \Illuminate\Database\Eloquent\Relations\HasMany
     */
    public function pedidos()
    {
        return $this->hasMany('App\Models\Pedido', 'id_usuario', 'id_usuario');
    }
}
