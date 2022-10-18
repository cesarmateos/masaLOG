package com.example.masalog.configuraEtiqueta

interface Lenguaje {
    fun cargarFormatoDespacho(vertical: Int, horizontal: Int, sentidoNormal: Boolean, barras: Boolean)
    fun cargarFormatoPredespacho(vertical: Int, horizontal: Int,margen:Int)
    fun imprimirDespachoPack()
    fun imprimirDespachoIOMA()
    fun imprimirDespachoRefrigerado()
    fun imprimirPredespacho()
    fun adminteGiro():Boolean
    fun nombreLenguaje(): String
}