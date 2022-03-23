package com.example.masalog

class ProductoComplejo(
    initCodigoBarras: Long,
    initLocalizador:String,
    initLote:String,
    initVencimiento:String,
    initCantidad:Int,
    initNombre:String){

    val codigoBarras : Long = initCodigoBarras
    val localizador : String = initLocalizador
    val lote: String = initLote
    val vencimiento: String = initVencimiento
    val nombre : String = initNombre
    val cantidad : Int = initCantidad
    private var contado : Int = 0

    fun agregar(cantidad: Int){
        contado = contado + cantidad
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
}