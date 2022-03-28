package com.example.masalog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.masalog.ui.theme.Naranja
import com.example.masalog.ui.theme.NaranjaMuySuave

@Composable
fun  PantallaConfiguraImpresoraRP4() {
    val sizeFuente = 18.sp
    Scaffold(
        topBar = { barraTOP() },
        content = {
            var velocidadLimitada :IntLimitado by remember { mutableStateOf(IntLimitado(6,1,10)) }
            var oscuridadLimitada :IntLimitado by remember { mutableStateOf(IntLimitado(5,1,10)) }
            var modificaPapel : Boolean by remember{ mutableStateOf(false) }
            var modificaVelocidad : Boolean by remember{ mutableStateOf(false) }
            var modificaOscuridad : Boolean by remember{ mutableStateOf(false) }
            var papel: Boolean by remember { mutableStateOf(true) }
            var velocidad: Int by remember { mutableStateOf(velocidadLimitada.valor) }
            var oscuridad: Int by remember { mutableStateOf(oscuridadLimitada.valor) }

            EsctructuraTituloCuerpoBoton(
                textoTitulo = "Configurar Honeywell RP4",
                textoBoton = "Enviar Configuración",
                onClick = { cambiarConfiguracionSATO(papel,modificaPapel,oscuridad, modificaOscuridad,velocidad,modificaVelocidad)}) {

            }
            Column() { //COLUMNA GENERAL
            }
        }
    )
}

fun cambiarConfiguracionRP4(papel: Boolean, modificaPapel: Boolean,
                             oscuridad: Int, modificaOscuridad:Boolean,
                             velocidad: Int, modificaVelcoidad:Boolean){
    var tipoPapel = ""
    var setVelocidad = ""
    var setOscuridad = ""

    if (modificaPapel){
        if(papel){
            tipoPapel = "IG2" + "PM0"
        }else{
            tipoPapel = "IG1" + "PM1"
        }
    }

    if (modificaOscuridad){
        setOscuridad = "#F" + oscuridad.toString()
    }

    if (modificaVelcoidad){
        setVelocidad = "CS" + velocidad.toString()
    }


    BTHandler.imprimir("A"+
            tipoPapel +
            setOscuridad +
            setVelocidad +
            "Z"+
            "A"+
            "*"+
            "Z"
    )
}