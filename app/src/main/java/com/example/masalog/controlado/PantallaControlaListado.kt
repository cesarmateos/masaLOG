package com.example.masalog.controlado

import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavHostController


@Composable
fun PantallaControlaListado(navHostController: NavHostController) {

    val controlEnMarcha: Boolean? by ControlProductos.controlActivo.observeAsState()

    //ACLARACIÓN : Esto está hecho así, porque haciéndolo con pantallas distintas navegando
    // con el navHostController, había un memory leak y un sobreprocesamiento.
    if(controlEnMarcha == true){
        PantallaControlaListado_Listado(navHostController)
    }else {
        PantallaControlaListado_Configura()
    }
}

