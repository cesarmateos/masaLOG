package com.example.masalog.controlado

import android.icu.text.SimpleDateFormat
import androidx.navigation.NavHostController
import com.example.masalog.BTHandler
import com.example.masalog.Pantallas
import java.util.*
import kotlin.math.ceil
import kotlin.math.min

object ControlProductos {
    var productoControlIngresado: ProductoControl? = null
    val productos = mutableListOf<ProductoControl>()
    lateinit var navController : NavHostController
    var etiqueta = true
    var crearBulto = false
    val bulto = mutableListOf<Pair<ProductoControl?,Int>>()
    var nroBulto = 1
    var archivoCargado = false

    fun asignarNavController(nav: NavHostController){
        navController = nav
    }

    fun porControlar(): List<ProductoControl>{
        return productos.filter{ item -> item.faltaControlar() >0}
    }

    fun controlados() : List<ProductoControl>{
        return productos.filter{ item -> item.contado() >0}
    }
/* //CUANDO LO HABIA HECHO CON LOTE
    fun lectura(codigoBarras: String) {
        val barras = codigoBarras
        productosMatchCodigoBarras = devolverListaConMatchCodigoBarras(barras, productos)

        if (productosMatchCodigoBarras.isEmpty()) {
            val productoDesconocido = ProductoComplejo(barras, "****", 0,"Producto Desconocido")
            productos.add(productoDesconocido)
            productosMatchCodigoBarras.add(productoDesconocido)
        }
    }
 */
    fun lectura(codigoBarras: String){
        val encontrado = productos.find{it.matchCodigoBarras(codigoBarras)}

        if (encontrado != null){
            productoControlIngresado = encontrado
        }else{
            val productoDesconocido = ProductoControl(codigoBarras, "****","Producto Desconocido", 0,)
            productos.add(productoDesconocido)
            productoControlIngresado = productoDesconocido
        }
        navController.navigate((Pantallas.ControladorIngreso.name))
    }

    fun cargarCantidad(cantidad:String){
        val cantidadInt = cantidad.toInt()
        productoControlIngresado?.agregar(cantidadInt)

        if(cantidadInt>0){
            bulto.add(Pair(productoControlIngresado,cantidadInt))
        }

        if(etiqueta){
            imprimirEtiquetaRecepcion(cantidad)
        }
    }

    fun limpiar() {
        productos.clear()
        archivoCargado = false
        productoControlIngresado = null
        nroBulto = 1
        bulto.clear()
    }

    fun imprimirEtiquetaRecepcion(cantidad:String){

        val etiqueta = "<STX><ESC>P;E32;F32;<ETX>\n" +
                "<STX>H0;o360,55;f3;c26;d0,30;k20;<ETX>\n" +
                "<STX>H1;o260,55;f3;c26;d0,30;k17;<ETX>\n" +
                "<STX>H2;o210,55;f3;c26;d0,30;k14;<ETX>\n" +
                "<STX>H3;o140,55;f3;c26;d0,20;k26;<ETX>\n" +
                "<STX>H6;o80,610;f3;c26;d0,10;k22;<ETX>\n" +
                "<STX>H7;o035,55;f3;c21;d0,20;k10;<ETX>\n" +
                "<STX>R<ESC>E32<CAN><ETX>\n" +
                "<STX>"+
                productoControlIngresado?.codigoBarras.toString() + "<CR>\n" + //H0
                productoControlIngresado?.nombre?.take(26)+ "<CR>\n" + //H1
                productoControlIngresado?.nombre?.drop(25)+ "<CR>\n" + // H2
                productoControlIngresado?.localizador +"<CR>\n" + // H3
                cantidad+ "<CR>\n" +//H6
                fechaActual("dd/MM/yyyy HH:mm:ss")+ //H7
                "<ETX>\n" +
                "<STX><ETB><ETX>"

        BTHandler.imprimir(etiqueta)
    }

    fun fechaActual(formato: String): String {
        val formatoFecha = SimpleDateFormat(formato)
        return formatoFecha.format(Date())
    }

    fun cargarProductos(stream:String) {
        val lineas = stream.lines()

        try {
            lineas.forEach {
                val lista = it.split(";")
                if (lista.isNotEmpty() && lista[0]!="") {
                    val producto = ProductoControl(lista[0],lista[1],lista[2],lista[3].toInt())
                    productos.add(producto)
                }
            }
            archivoCargado = true
        } catch (exception: NumberFormatException) {
            limpiar()
        }
    }

    fun itemsEnBulto():Int{
        return bulto.size
    }

    fun cerrarBulto(){
        val baseY = 420
        val renglon = 26

        bulto.sortBy{ it.first?.nombre }

        val cantidadEtiquetas = ceil(itemsEnBulto().toDouble() / 16).toInt()

        for (j in 0 until cantidadEtiquetas){
            var piso = j * 16
            var techo = min(16,itemsEnBulto()-piso)
            var stringProductos = ""

            for ( i in 0 until techo){
                stringProductos = stringProductos +
                        "<STX>H" + (3 + i * 3 ).toString() + ";o"+ (baseY - i*renglon).toString() +",040;f3;c26;k9;d3,"+ bulto[piso + i].first?.codigoBarras+"<ETX>\n" +
                        "<STX>H" + (4 + i * 3 ).toString() + ";o"+ (baseY - i*renglon).toString() +",230;f3;c26;k9;d3,"+ bulto[piso + i].first?.nombre?.take(33)+"<ETX>\n" +
                        "<STX>H" + (5 + i * 3 ).toString() + ";o"+ (baseY - i*renglon).toString() +",720;f3;c26;k9;d3,"+ bulto[piso + i].second?.toString() +"<ETX>\n"
            }
            BTHandler.imprimir(
                "<STX><ESC>C<ETX>\n" +
                        "<STX><ESC>P<ETX>\n" +
                        "<STX>E23;F23;<ETX>\n" +
                        "<STX>H0;o470,40;f3;c21;k14;d3,Bulto:$nroBulto<ETX>\n" +
                        "<STX>H1;o470,475;f3;c21;k14;d3,Fecha:${fechaActual("dd/MM/yyyy")}<ETX>\n" +
                        "<STX>H2;o470,240;f3;c21;k14;d3,Etiq:"+(j+1)+"/"+"$cantidadEtiquetas<ETX>\n" +
                        stringProductos +
                        "<STX>R<ETX>\n" +
                        "<STX><ESC>E23<CAN><ETX>\n" +
                        "<STX><RS>1<ETB><FF><ETX>")
        }
        bulto.clear()
        nroBulto++
    }

    fun terminar(){
        limpiar()
        navController.navigate((Pantallas.Inicio.name))
    }
}