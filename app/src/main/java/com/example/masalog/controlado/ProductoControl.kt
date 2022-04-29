package com.example.masalog.controlado

class ProductoControl(
    initCodigoBarras: String,
    initLocalizador: String,
    initNombre:String,
    initCantidad:Int){

    val codigoBarras : String = initCodigoBarras
    val cantidad : Int = initCantidad
    val nombre : String = initNombre
    val localizador : String = initLocalizador
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
}