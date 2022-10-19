package com.example.masalog.etiquetaRefrigerados

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.masalog.*
import com.example.masalog.R
import com.example.masalog.configuraEtiqueta.IPL
import com.example.masalog.configuraEtiqueta.LogoIPL
import com.example.masalog.configuraEtiqueta.ZPL
import com.example.masalog.configuracion.Configuracion

@Composable
fun PantallaEtiquetaHeladeras() {
    var ejeX : Int by remember { mutableStateOf(0) }
    var ejeY : Int by remember { mutableStateOf(0) }
    val cantidadLimitada : IntLimitado by remember { mutableStateOf(IntLimitado(1,1,200)) }
    var cantidad: Int by remember { mutableStateOf(cantidadLimitada.valor) }
    var mediana: Boolean by remember { mutableStateOf(true) }

    val configuracion = Configuracion(LocalContext.current)
    val estadoLenguaje = configuracion.getLenguaje.collectAsState(initial = true).value!!

    val lenguaje = if (estadoLenguaje){
        IPL()
    }else{
        ZPL()
    }


    EsctructuraTituloCuerpoBoton(textoTitulo= stringResource(R.string.etiqueta_heladeras),"Imprimir Etiquetas",{lenguaje.imprimirRefrigerados(ejeX,ejeY,cantidad,mediana)}){

        Column{
            BotoneraDireccionConIndicadores(ejeX,ejeY,{ejeX= it}, {ejeY= it}, lenguaje)
            Spacer(Modifier.size(10.dp))
            Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)){
                Text(text="Tamaño Etiqueta: ",style = TextStyle(fontWeight = FontWeight.Bold),fontSize =18.sp)
                ToggleHorizontal(estadoA = mediana, onClick = {mediana = it}, textoA = "50 mm", textoB = "65 mm")
            }
            Spacer(Modifier.size(5.dp))
            ParDeFlechitasConTexto("Cantidad:",cantidadLimitada,cantidad,{cantidad=it},1,3)
            Spacer(Modifier.size(15.dp))
            Row(modifier = Modifier
                .fillMaxWidth()
                .background(Color.Yellow)
                .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.Center)
            {
                Text(text="USAR SOLO EN IMPRESORAS FIJAS",
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    fontSize =18.sp,color= Color.Red,
                    textAlign = TextAlign.Center)
            }
        }
    }
}

