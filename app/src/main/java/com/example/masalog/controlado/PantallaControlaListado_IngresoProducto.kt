package com.example.masalog.controlado

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import com.example.masalog.*

@Composable
fun PantallaControlaListado_IngresoProducto (navController: NavHostController){

    Scaffold(
        content = {
            MuestraProducto(navController = navController, ControlProductos.productoControlIngresado) {

                ContableCargaCantidadEtiqueta(navController)
            }
        }
    )
}