package com.example.masalog.configuraEtiqueta

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.masalog.*
import com.example.masalog.configuracion.Configuracion


val espaciado = 20.dp


@Composable
fun  PantallaConfiguraEtiqueta() {
    var state by remember { mutableStateOf(0) }
    val titles = listOf("Pack/Ref/IOMA", "Farmabox")

    val configuracion = Configuracion(LocalContext.current)
    val estadoLenguaje = configuracion.getLenguaje.collectAsState(initial = true).value!!

    val lenguaje = if (estadoLenguaje){
                        IPL()
                    }else{
                        ZPL()
                    }

    Column {
        TabRow(
            selectedTabIndex = state,
            contentColor = MaterialTheme.colors.primary,
            backgroundColor = MaterialTheme.colors.primaryVariant,

            ) {
            titles.forEachIndexed { index, title ->
                Tab(
                    text = { Text(text= title,
                        style = MaterialTheme.typography.h6) },
                    selected = state == index,
                    selectedContentColor = MaterialTheme.colors.onBackground,
                    unselectedContentColor  = Color.LightGray,
                    onClick = { state = index }
                )
            }
        }
        if(state==0){
            Despacho(lenguaje)
        }else{
            Predespacho(lenguaje)
        }
    }
}

@Composable
fun Despacho(lenguaje: Lenguaje){
    var horizontal : Int by remember { mutableStateOf(0)}
    var vertical : Int by remember { mutableStateOf(0)}
    var sentidoNormal: Boolean by remember { mutableStateOf(true)}
    var barras: Boolean by remember {mutableStateOf(true)}

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = PADDING_HORIZONTAL, vertical = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        BotoneraDireccionConIndicadores(horizontal,vertical,{horizontal= it}, {vertical= it},lenguaje)
        Spacer(modifier = Modifier.size(espaciado /2))

        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically){
            if(lenguaje.admiteBarrasPack()){
                Column(horizontalAlignment = Alignment.Start) {
                    ToggleVertical(normal = barras, onClick = {barras = it}, textoA= "C.Barras", textoB="QR")
                }
                Spacer(modifier = Modifier.size(espaciado))
            }
            if(lenguaje.admiteGiro()){
                Column(modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.End) {
                    ToggleVertical(normal = sentidoNormal, onClick = {sentidoNormal = it}, textoA= "Normal", textoB="Invertida")
                }
            }

        }

        //BOTONES
        Spacer(modifier = Modifier.size(espaciado))
        BotonStandard(texto = "Ejemplo Pack / G.Volumen",
                    onClick = { lenguaje.cargarFormatoDespacho(vertical,horizontal, sentidoNormal,barras)
                                lenguaje.imprimirDespachoPack()
                    }
        )
        Spacer(modifier = Modifier.size(espaciado))
        BotonStandard(texto = "Ejemplo IOMA",
                    onClick = { lenguaje.cargarFormatoDespacho(vertical,horizontal, sentidoNormal,barras)
                                lenguaje.imprimirDespachoIOMA()
                    }
        )

        Spacer(modifier = Modifier.size(espaciado))
        BotonStandard(texto = "Ejemplo Refrigerados",
                    onClick = { lenguaje.cargarFormatoDespacho(vertical,horizontal, sentidoNormal,barras)
                                lenguaje.imprimirDespachoRefrigerado()
                    }
        )
    }

}

@Composable
fun Predespacho(lenguaje: Lenguaje){
    var horizontal : Int by remember { mutableStateOf(0)}
    var vertical : Int by remember { mutableStateOf(0)}
    var barras: Boolean by remember {mutableStateOf(false)}

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = PADDING_HORIZONTAL, vertical = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        BotoneraDireccionConIndicadores(horizontal,vertical,{horizontal= it}, {vertical= it},lenguaje)
        Spacer(modifier = Modifier.size(espaciado /2))
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically){
            if(lenguaje.admiteBarrasTapadora()){
                Column(horizontalAlignment = Alignment.Start) {
                    ToggleVertical(normal = barras, onClick = {barras = it}, textoA= "C.Barras", textoB="QR")
                }
                Spacer(modifier = Modifier.size(espaciado))
            }
        }

        Spacer(modifier = Modifier.size(espaciado))

        //BOTONES
        BotonStandard(texto = "Formulario Papel Continuo",
            onClick = { lenguaje.cargarFormatoPredespacho(vertical,horizontal, 225, barras)
                        lenguaje.imprimirPredespacho()
            }
        )
        Spacer(modifier = Modifier.size(espaciado))
        BotonStandard(texto = "Formulario Etiqueta",
            onClick = { lenguaje.cargarFormatoPredespacho(vertical,horizontal, 0,barras)
                        lenguaje.imprimirPredespacho()
            }
        )

        Spacer(modifier = Modifier.size(espaciado))
        BotonStandard(texto = "Formulario Emergencia",
            onClick = { lenguaje.cargarFormatoPredespacho(vertical,horizontal, 825,barras)
                        lenguaje.imprimirPredespacho() }
        )
    }
}