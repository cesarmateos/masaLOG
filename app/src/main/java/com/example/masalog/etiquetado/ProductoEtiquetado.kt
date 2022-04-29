package com.example.masalog.etiquetado

class ProductoEtiquetado(initCodigoBarras: String,
                         initLocalizador:String,
                         initNombre:String){
    val codigoBarras : String = initCodigoBarras
    val localizador : String = initLocalizador
    val nombre : String = initNombre

    fun matchCodigoBarras(codigoBarrasBuscado:String):Boolean{
        return codigoBarrasBuscado == codigoBarras
    }
}






