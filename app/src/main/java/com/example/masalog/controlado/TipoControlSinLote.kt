package com.example.masalog.controlado

import androidx.navigation.NavHostController
import com.example.masalog.Pantallas

class TipoControlSinLote: TipoControl() {

    override fun lectura(codigoBarras: String, navController : NavHostController){
        //Find devuelve el primer match o null si no hay match
        var encontrado = ControlProductos.productos.find{it.matchCodigoBarras(codigoBarras)}

        if (encontrado == null) {
            val productoDesconocido =
                ProductoControl(codigoBarras, "****", "Producto Desconocido", 0)
            ControlProductos.productos.add(productoDesconocido)
            encontrado = productoDesconocido
        }

        ControlProductos.productoControlIngresado = encontrado
        navController.navigate((Pantallas.ControladorIngreso.name))
    }

    override fun cargarInicial(stream:String) {

        val lineas = stream.lines()

        try {
            lineas.forEach {
                val lista = it.split(";")
                if (lista.isNotEmpty() && lista[0]!="") {
                    val producto = ProductoControl(lista[0],lista[1],lista[2],lista[3].toInt())
                    ControlProductos.productos.add(producto)
                }
            }

            ControlProductos.controlActivo.postValue(true)

        } catch (exception: NumberFormatException) {
            ControlProductos.limpiar()
        }
    }
}