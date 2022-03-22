package com.example.masalog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.masalog.ui.theme.Naranja
import com.example.masalog.ui.theme.NaranjaMuySuave

@Composable
fun  PantallaConfiguraImpresoraSATO() {
    val sizeFuente = 18.sp
    Scaffold(
        topBar = { barraTOP() },
        content = {
            var velocidadLimitada :IntLimitado by remember { mutableStateOf(IntLimitado(6,1,10)) }
            var oscuridadLimitada :IntLimitado by remember { mutableStateOf(IntLimitado(5,1,10)) }
            var modificaPapel : Boolean by remember{ mutableStateOf(false)}
            var modificaVelocidad : Boolean by remember{ mutableStateOf(false)}
            var modificaOscuridad : Boolean by remember{ mutableStateOf(false)}
            var papel: Boolean by remember { mutableStateOf(true) }
            var velocidad: Int by remember { mutableStateOf(velocidadLimitada.valor) }
            var oscuridad: Int by remember { mutableStateOf(oscuridadLimitada.valor) }

            Column( // Columna General
                modifier = Modifier
                    .fillMaxSize())
            {

                //Título
                Row(
                    Modifier
                        .height(50.dp)
                        .background(NaranjaMuySuave)
                        .padding(horizontal = 10.dp, vertical = 3.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center)
                {
                    Text(text= "Configurar SATO CL4NX",fontSize = 18.sp, style = TextStyle(fontWeight = FontWeight.Bold))
                }

                Spacer(Modifier.size(30.dp))

                //Tipo Papel
                Row(
                    modifier= Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
                    verticalAlignment = Alignment.CenterVertically)
                {
                    Text(text="Tipo de papel:",fontSize = sizeFuente)
                    Spacer(Modifier.size(10.dp))
                    if(modificaPapel){
                        ToggleHorizontal(estadoA = papel, onClick = {papel = it}, textoA= "Papel", textoB="Etiqueta")
                    }else{
                        Text(text="Modificar",fontSize = sizeFuente, modifier= Modifier.clickable{modificaPapel=true}, color = Naranja)
                    }
                }

                Spacer(Modifier.size(10.dp))

                //Velocidad
                Row(
                    modifier= Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
                    verticalAlignment = Alignment.CenterVertically)
                {
                    Text(text="Velocidad:",fontSize = sizeFuente)
                    Spacer(Modifier.size(10.dp))
                    if(modificaVelocidad){
                        Text(text= velocidad.toString().padStart(2, '0'), fontSize = sizeFuente)
                        Spacer(modifier= Modifier.size(20.dp))
                        flechita (intLimitado= velocidadLimitada, intMostrable =velocidad, rotacion = 0f, onClick = { velocidad = it },operacion= {a:IntLimitado->a.restar()})
                        Spacer(modifier= Modifier.size(10.dp))
                        flechita (intLimitado= velocidadLimitada, intMostrable =velocidad, rotacion = 180f, onClick = { velocidad = it },operacion= {a:IntLimitado->a.sumar()})
                    }else{
                        Text(text="Modificar",fontSize = sizeFuente, modifier= Modifier.clickable{modificaVelocidad=true}, color = Naranja)
                    }
                }

                Spacer(Modifier.size(10.dp))

                //Oscuridad
                Row(
                    modifier= Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
                    verticalAlignment = Alignment.CenterVertically)
                {
                    Text(text="Oscuridad:",fontSize = sizeFuente)
                    Spacer(Modifier.size(10.dp))
                    if(modificaOscuridad){
                        Text(text= oscuridad.toString().padStart(2, '0'), fontSize = sizeFuente)
                        Spacer(modifier= Modifier.size(20.dp))
                        flechita (intLimitado= oscuridadLimitada, intMostrable=oscuridad, rotacion = 0f, onClick = { oscuridad = it },operacion= {a:IntLimitado->a.restar()})
                        Spacer(modifier= Modifier.size(10.dp))
                        flechita (intLimitado= oscuridadLimitada, intMostrable=oscuridad, rotacion = 0f, onClick = { oscuridad = it },operacion= {a:IntLimitado->a.sumar()})
                    }else{
                        Text(text="Modificar",fontSize = sizeFuente, modifier= Modifier.clickable{modificaOscuridad=true}, color = Naranja)
                    }
                }

                Spacer(Modifier.size(30.dp))

                //Enviar
                Row(modifier= Modifier
                    .fillMaxWidth()
                    .padding(horizontal = PADDING_HORIZONTAL)){
                    BotonStandard("Enviar Configuración", {cambiarConfiguracionSATO(papel,modificaPapel,oscuridad, modificaOscuridad,velocidad,modificaVelocidad)})
                }
            }
        }
    )
}

fun cambiarConfiguracionSATO(papel: Boolean, modificaPapel: Boolean,
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

