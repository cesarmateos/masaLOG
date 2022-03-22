package com.example.masalog

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PantallaEtiquetado_Seleccion() {
    var carga: Boolean by remember { mutableStateOf(true) }
    Scaffold(
        topBar = { barraTOP() },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(PADDING_HORIZONTAL),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Row(modifier=Modifier.fillMaxWidth()){
                    Text(text= "Cargar Unidades:")
                    Spacer(Modifier.size(10.dp))
                    ToggleHorizontal(estadoA = carga, onClick = {carga= it}, textoA = "Sí", textoB = "No")
                }
                Spacer(Modifier.size(PADDING_HORIZONTAL*2))
                BotonStandard("Cargar Archivo CSV", { } )
            }
        }
    )
}