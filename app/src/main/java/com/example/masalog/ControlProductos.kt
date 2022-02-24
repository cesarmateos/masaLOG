package com.example.masalog

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import java.util.*

object ControlProductos {
    var productoIngresado: Producto? = null
    val productos = mutableListOf<Producto>()
    var productosMatchCodigoBarras = mutableListOf<Producto>()
    var etiqueta = true


    fun porControlar(): List<Producto>{
        return productos.filter{item -> item.faltaControlar() >0}
    }

    fun controlados() : List<Producto>{
        return productos.filter{item -> item.contado() >0}
    }

    fun lectura(codigoBarras: String) {
        val barras = codigoBarras.toLong()
        productosMatchCodigoBarras = devolverListaConMatchCodigoBarras(barras, productos)

        if (productosMatchCodigoBarras.isEmpty()) {
            val productoDesconocido = Producto(barras, "****", "****","****",0,"Producto Desconocido")
            productos.add(productoDesconocido)
            productosMatchCodigoBarras.add(productoDesconocido)
        }
    }

    fun cargarCantidad(cantidad:String){
        productoIngresado?.agregar(cantidad.toInt())
        if(etiqueta){
            imprimirEtiquetaRecepcion(cantidad)
        }
    }

    fun limpiar() {
        productos.clear()
        productosMatchCodigoBarras.clear()
        productoIngresado = null
    }

    fun imprimirEtiquetaRecepcion(cantidad:String){

        val date = getCurrentDateTime()
        val dateInString = date.toString("dd/MM/yyyy HH:mm:ss")

        val etiqueta = "<STX><ESC>P;E32;F32;<ETX>\n" +
                "<STX>H0;o360,55;f3;c26;d0,30;k20;<ETX>\n" +
                "<STX>H1;o260,55;f3;c26;d0,30;k17;<ETX>\n" +
                "<STX>H2;o210,55;f3;c26;d0,30;k14;<ETX>\n" +
                "<STX>H3;o140,55;f3;c26;d0,20;k26;<ETX>\n" +
                "<STX>H4;o165,522;f3;c21;d0,30;k10;<ETX>\n" +
                "<STX>H5;o135,505;f3;c21;d0,30;k10;<ETX>\n" +
                "<STX>H6;o80,610;f3;c26;d0,10;k22;<ETX>\n" +
                "<STX>H7;o035,55;f3;c21;d0,20;k10;<ETX>\n" +
                "<STX>R<ESC>E32<CAN><ETX>\n" +
                "<STX>"+
                productoIngresado?.codigoBarras.toString() + "<CR>\n" + //H0
                productoIngresado?.nombre?.take(26)+ "<CR>\n" + //H1
                productoIngresado?.nombre?.drop(25)+ "<CR>\n" + // H2
                productoIngresado?.localizador+ "<CR>\n" +  //H3
                "Lote: "+ productoIngresado?.lote+ "<CR>\n" +  //H4
                "Venc.: "+ productoIngresado?.vencimiento+ "<CR>\n" + //H5
                cantidad+ "<CR>\n" +//H6
                dateInString+ //H7
                "<ETX>\n" +
                "<STX><ETB><ETX>"

        BTHandler.imprimir(etiqueta)
    }
}


class Producto(
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

fun buscarProductoEnLista(codigo: Long, lote: String, lista: MutableList<Producto>): Producto? {
    val iterador = lista.listIterator()
    var match = false
    while (iterador.hasNext() && !match) {
        val producto = iterador.next()
        if (producto.codigoBarras == codigo && producto.lote== lote) {
            match = true
            return producto
        }
    }
    return null
}

fun devolverListaConMatchCodigoBarras(codigo: Long, lista: MutableList<Producto>): MutableList<Producto> {
    val productosEncontrados = mutableListOf<Producto>()
    val iterador = lista.listIterator()
    while (iterador.hasNext()) {
        val producto = iterador.next()
        if (producto.codigoBarras == codigo) {
            productosEncontrados.add(producto)
        }
    }
    return productosEncontrados
}

fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}

fun getCurrentDateTime(): Date {
    return Calendar.getInstance().time
}