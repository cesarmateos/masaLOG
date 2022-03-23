package com.example.masalog

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import java.util.*

object ControlProductos {
    var productoComplejoIngresado: ProductoComplejo? = null
    val productos = mutableListOf<ProductoComplejo>()
    var productosMatchCodigoBarras = mutableListOf<ProductoComplejo>()
    var etiqueta = true


    fun porControlar(): List<ProductoComplejo>{
        return productos.filter{item -> item.faltaControlar() >0}
    }

    fun controlados() : List<ProductoComplejo>{
        return productos.filter{item -> item.contado() >0}
    }

    fun lectura(codigoBarras: String) {
        val barras = codigoBarras.toLong()
        productosMatchCodigoBarras = devolverListaConMatchCodigoBarras(barras, productos)

        if (productosMatchCodigoBarras.isEmpty()) {
            val productoDesconocido = ProductoComplejo(barras, "****", "****","****",0,"Producto Desconocido")
            productos.add(productoDesconocido)
            productosMatchCodigoBarras.add(productoDesconocido)
        }
    }

    fun cargarCantidad(cantidad:String){
        productoComplejoIngresado?.agregar(cantidad.toInt())
        if(etiqueta){
            imprimirEtiquetaRecepcion(cantidad)
        }
    }

    fun limpiar() {
        productos.clear()
        productosMatchCodigoBarras.clear()
        productoComplejoIngresado = null
    }

    fun imprimirEtiquetaRecepcion(cantidad:String){

        val date = Calendar.getInstance().time
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
                productoComplejoIngresado?.codigoBarras.toString() + "<CR>\n" + //H0
                productoComplejoIngresado?.nombre?.take(26)+ "<CR>\n" + //H1
                productoComplejoIngresado?.nombre?.drop(25)+ "<CR>\n" + // H2
                productoComplejoIngresado?.localizador+ "<CR>\n" +  //H3
                "Lote: "+ productoComplejoIngresado?.lote+ "<CR>\n" +  //H4
                "Venc.: "+ productoComplejoIngresado?.vencimiento+ "<CR>\n" + //H5
                cantidad+ "<CR>\n" +//H6
                dateInString+ //H7
                "<ETX>\n" +
                "<STX><ETB><ETX>"

        BTHandler.imprimir(etiqueta)
    }

    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }
}