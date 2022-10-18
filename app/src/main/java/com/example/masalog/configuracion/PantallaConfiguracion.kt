package com.example.masalog.configuracion

import android.content.SharedPreferences
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.masalog.BTHandler
import com.example.masalog.EstructuraTituloCuerpo
import com.example.masalog.ToggleHorizontal
import com.example.masalog.redondeoFlechas
import kotlinx.coroutines.flow.first
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