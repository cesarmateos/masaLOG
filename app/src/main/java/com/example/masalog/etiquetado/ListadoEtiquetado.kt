package com.example.masalog.etiquetado

import com.example.masalog.BTHandler

object ListadoEtiquetado {
    val productos = mutableListOf<ProductoEtiquetado>()
    var archivoCargado = false

    fun lectura(codigoBarras: String): ProductoEtiquetado? {
        val iterador = productos.listIterator()

        while (iterador.hasNext()) {
            val producto = iterador.next()
            if (producto.matchCodigoBarras(codigoBarras)) {
                return producto
            }
        }

        return null
    }

    fun imprimeLocalizador(codigoBarras: String){
        val producto = lectura(codigoBarras)
        if(producto !=null){
            BTHandler.imprimir(
                "<STX><ESC>C<ETX>\n" +
                        "<STX><ESC>P<ETX>\n" +
                        "<STX>E22;F22;<ETX>\n" +
                        "<STX>H0;o355,55;f3;c21;k14;d3," + producto.codigoBarras + "<ETX>\n" +
                        "<STX>H1;o280,55;f3;c26;k14;d3," + producto.nombre.take(30) + "<ETX>\n" +
                        "<STX>H2;o245,55;f3;c26;k14;d3," + producto.nombre.drop(30) + "<ETX>\n" +
                        "<STX>H3;o165,55;f3;c26;k24;d3," + producto.localizador + "<ETX>\n" +
                        "<STX>R<ETX>\n" +
                        "<STX><ESC>E22<CAN><ETX>\n" +
                        "<STX><RS>1<ETB><FF><ETX>"
            )
        }else{
            BTHandler.imprimir(
                "<STX><ESC>C<ETX>\n" +
                        "<STX><ESC>P<ETX>\n" +
                        "<STX>E22;F22;<ETX>\n" +
                        "<STX>H0;o355,55;f3;c21;k14;d3," + codigoBarras + "<ETX>\n" +
                        "<STX>H1;o280,55;f3;c26;k14;d3, NO EXISTE ESTE <ETX>\n" +
                        "<STX>H2;o245,55;f3;c26;k14;d3, PRODUCTO EN EL LISTADO <ETX>\n" +
                        "<STX>H3;o165,55;f3;c26;d0,30;k14<ETX>\n" +
                        "<STX>R<ETX>\n" +
                        "<STX><ESC>E22<CAN><ETX>\n" +
                        "<STX><RS>1<ETB><FF><ETX>"
            )
        }
    }

    fun cargarProductos(stream:String) {

        val lineas = stream.lines()

        try {
            lineas.forEach {
                val lista = it.split(";")
                if (lista.isNotEmpty() && lista[0]!="") {
                    val producto = ProductoEtiquetado(lista[0], lista[1], lista[2])
                    productos.add(producto)
                }
            }
            archivoCargado = true
        } catch (exception: NumberFormatException) {
        }
    }

    fun descargarArchivo(){
        archivoCargado = false
        productos.clear()
    }
}