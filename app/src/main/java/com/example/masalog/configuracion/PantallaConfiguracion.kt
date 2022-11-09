package com.example.masalog.configuracion

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.masalog.EstructuraTituloCuerpo
import com.example.masalog.ToggleHorizontal
import kotlinx.coroutines.launch

@Composable
fun PantallaConfiguracion() {

    //Contexto, scope y Configuracion
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val configuracion = Configuracion(context)
    val estadoLenguaje = configuracion.getLenguaje.collectAsState(initial = true).value!!

    EstructuraTituloCuerpo("Configuración"){
        Text("Lenguaje")
        Spacer(Modifier.size(20.dp))

        ToggleHorizontal(estadoA = estadoLenguaje,
            onClick = {scope.launch {configuracion.cambiarLenguaje(!estadoLenguaje)}},
            textoA = "IPL",
            textoB = "ZPL")

    }


}