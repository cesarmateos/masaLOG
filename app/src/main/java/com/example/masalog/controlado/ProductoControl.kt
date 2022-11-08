package com.example.masalog.controlado


open class ProductoControl(
    val codigoBarras: String,
    val localizador: String,
    val nombre:String,
    val cantidad:Int){

    lateinit var lote : String
    lateinit var vencimiento : String

    constructor(codigoBarras: String,
                localizador: String,
                nombre: String,
                cantidad: Int,
                lote: String,
                vencimiento :String) : this(codigoBarras,localizador,nombre,cantidad){
        this.lote = lote
        this.vencimiento = vencimiento
    }

    private var contado : Int = 0


    fun agregar(cantidad: Int){
        contado += cantidad
    }

    fun contado(): Int{
        return contado
    }

    fun diferencia():Int{
        return contado - cantidad
    }

    fun faltaControlar():Int{
        return cantidad - contado
    }

    fun matchCodigoBarras(codigoBarrasBuscado : String) : Boolean{
        return codigoBarras == codigoBarrasBuscado
    }

    fun matchProducto(codigoBarras: Int, lote: String, vencimiento:String): Boolean{
        return true
    }
}