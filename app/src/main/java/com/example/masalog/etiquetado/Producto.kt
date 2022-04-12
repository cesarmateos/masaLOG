package com.example.masalog.etiquetado

class Producto(initCodigoBarras: Long,
               initLocalizador:String,
               initNombre:String){
    val codigoBarras : Long = initCodigoBarras
    val localizador : String = initLocalizador
    val nombre : String = initNombre

    fun matchCodigoBarras(codigoBarrasString:String):Boolean{
        val codigoBarrasBuscado = codigoBarrasString.toLong()
        return codigoBarrasBuscado == codigoBarras
    }
}






