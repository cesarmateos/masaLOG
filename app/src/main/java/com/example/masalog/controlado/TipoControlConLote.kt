package com.example.masalog.controlado

import androidx.navigation.NavHostController
import com.example.masalog.Pantallas

class TipoControlConLote : TipoControl() {

    override fun cargarInicial(stream: String) {
        val lineas = stream.lines()

        try {
            lineas.forEach {
                val lista = it.split(";")
                if (lista.isNotEmpty() && lista[0]!="") {
                    val producto = ProductoControl(lista[0],lista[1],lista[2],lista[3].toInt(),lista[4],lista[5])
                    ControlProductos.productos.add(producto)
                }
            }

            ControlProductos.controlActivo.postValue(true)

        } catch (exception: NumberFormatException) {
            ControlProductos.limpiar()
        }
    }


    override fun lectura(codigoBarras: String, navController : NavHostController){
        val productosMatchCodigoBarras = devolverListaConMatchCodigoBarras(codigoBarras, ControlProductos.productos)

        if (productosMatchCodigoBarras.isEmpty()){
            val productoDesconocido =
                ProductoControl(codigoBarras, "****", "Producto Desconocido",0,"Lote","Venc")
            ControlProductos.productos.add(productoDesconocido)
            productosMatchCodigoBarras.add(productoDesconocido)
        }

        ControlProductos.productosMatchCodigoBarras = productosMatchCodigoBarras
        navController.navigate((Pantallas.ControladorIngresoLote.name))
    }

    fun devolverListaConMatchCodigoBarras(codigo: String, lista: MutableList<ProductoControl>): MutableList<ProductoControl> {
        val productosEncontrados = mutableListOf<ProductoControl>()
        val iterador = lista.listIterator()
        while (iterador.hasNext()) {
            val producto = iterador.next()
            if (producto.codigoBarras == codigo) {
                productosEncontrados.add(producto)
            }
        }
        return productosEncontrados
    }
}