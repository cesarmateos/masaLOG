package com.example.masalog.configuraEtiqueta

interface Lenguaje {
    fun cargarFormatoDespacho(vertical: Int, horizontal: Int, sentidoNormal: Boolean, barras: Boolean)
    fun cargarFormatoPredespacho(vertical: Int, horizontal: Int,margen:Int, barras :Boolean)
    fun imprimirDespachoPack()
    fun imprimirDespachoIOMA()
    fun imprimirDespachoRefrigerado()
    fun imprimirPredespacho()
    fun admiteGiro():Boolean
    fun admiteBarrasPack():Boolean
    fun admiteBarrasTapadora():Boolean
    fun nombreLenguaje(): String
}