package com.example.masalog.controlado

import androidx.navigation.NavHostController


abstract class TipoControl() {

    abstract fun lectura(codigoBarras: String, navController : NavHostController)

    abstract fun cargarInicial(stream:String)

}