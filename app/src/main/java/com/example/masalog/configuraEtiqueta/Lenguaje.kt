package com.example.masalog.configuraEtiqueta

import com.example.masalog.BTHandler

abstract class Lenguaje() {

    abstract val multiplicador : Int
    abstract val ejemploPack : String
    abstract val ejemploIOMA : String
    abstract val ejemploRefrigerados : String
    abstract val ejemploFarmabox : String

    abstract fun cargarFormatoDespacho(vertical: Int, horizontal: Int, sentidoNormal: Boolean, barras: Boolean)
    abstract fun cargarFormatoPredespacho(vertical: Int, horizontal: Int,margen:Int, barras :Boolean)

    fun imprimirEjemploPack(){
        BTHandler.imprimir(ejemploPack)
    }
    fun imprimirEjemploIOMA(){
        BTHandler.imprimir(ejemploIOMA)
    }
    fun imprimirEjemploRefrigerado(){
        BTHandler.imprimir(ejemploRefrigerados)
    }
    fun imprimirEjemploFarmabox(){
        BTHandler.imprimir(ejemploFarmabox)
    }

    fun imprimirRefrigerados(modificaX : Int, modificaY: Int, cantidad: Int, mediana : Boolean){
        if(mediana){
            imprimirRefrigerados50mm(modificaX,modificaY, cantidad)
        }else{
            imprimirRefrigerados65mm(modificaX,modificaY, cantidad)
        }
    }

    open fun imprimirRefrigerados50mm(modificaX : Int, modificaY: Int, cantidad: Int){
        val etiqueta = etiquetaHeladera50mm(modificaX,modificaY,cantidad)
        BTHandler.imprimir(etiqueta)
    }

    open fun imprimirRefrigerados65mm(modificaX : Int, modificaY: Int, cantidad: Int){
        val etiqueta = etiquetaHeladera65mm(modificaX,modificaY,cantidad)
        BTHandler.imprimir(etiqueta)
    }

    abstract fun etiquetaHeladera50mm(modificaX : Int, modificaY: Int, cantidad: Int) : String
    abstract fun etiquetaHeladera65mm(modificaX : Int, modificaY: Int, cantidad: Int) : String

    abstract fun admiteGiro():Boolean
    abstract fun admiteBarrasPack():Boolean
    abstract fun admiteBarrasTapadora():Boolean
    abstract fun nombreLenguaje(): String
}