package com.example.masalog


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun  PantallaConfiguraImpresoraRP4() {
    val sizeFuente = 18.sp
    Scaffold(
        topBar = { barraTOP() },
        content = {
            var velocidadLimitada :IntLimitado by remember { mutableStateOf(IntLimitado(8,2,10)) }
            var oscuridadLimitada :IntLimitado by remember { mutableStateOf(IntLimitado(32,1,64)) }
            var bluetoothLimitado :IntLimitado by remember { mutableStateOf(IntLimitado(300,30,300)) }
            var modificaBluetooth : Boolean by remember{ mutableStateOf(false) }
            var modificaVelocidad : Boolean by remember{ mutableStateOf(false) }
            var modificaOscuridad : Boolean by remember{ mutableStateOf(false) }
            var bluetooth: Int by remember { mutableStateOf(bluetoothLimitado.valor) }
            var velocidad: Int by remember { mutableStateOf(velocidadLimitada.valor) }
            var oscuridad: Int by remember { mutableStateOf(oscuridadLimitada.valor) }

            EsctructuraTituloCuerpoBoton(
                textoTitulo = "Configurar Honeywell RP4",
                textoBoton = stringResource(R.string.envia_configuracion),
                onClick = { cambiarConfiguracionRP4(bluetooth,modificaBluetooth,oscuridad, modificaOscuridad,velocidad,modificaVelocidad) })
            {

                Column() { //COLUMNA GENERAL

                    //Blootooth
                    Row(
                        modifier= Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically)
                    {
                        Text(text="Tiempo Conectada:",fontSize = sizeFuente)
                        Spacer(Modifier.size(10.dp))
                        if(modificaBluetooth){
                            Text(text= bluetooth.toString().padStart(3, '0'), fontSize = sizeFuente)
                            Spacer(modifier= Modifier.size(20.dp))
                            flechita (intLimitado= bluetoothLimitado, intMostrable =bluetooth, rotacion = 0f, onClick = { bluetooth = it },operacion= {a:IntLimitado->a.restar(30)})
                            Spacer(modifier= Modifier.size(10.dp))
                            flechita (intLimitado= bluetoothLimitado, intMostrable =bluetooth, rotacion = 180f, onClick = { bluetooth = it },operacion= {a:IntLimitado->a.sumar(30)})
                        }else{
                            Text(text="Modificar",fontSize = sizeFuente, modifier= Modifier.clickable{modificaBluetooth=true}, color = MaterialTheme.colors.secondary)
                        }
                    }

                    Spacer(Modifier.size(10.dp))

                    //VELOCIDAD
                    Row(
                        modifier= Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically)
                    {
                        Text(text="Velocidad:",fontSize = sizeFuente)
                        Spacer(Modifier.size(10.dp))
                        if(modificaVelocidad){
                            Text(text= (velocidad.toFloat()/2).toString(), fontSize = sizeFuente)
                            Spacer(modifier= Modifier.size(20.dp))
                            flechita (intLimitado= velocidadLimitada, intMostrable =velocidad, rotacion = 0f, onClick = { velocidad = it },operacion= {a:IntLimitado->a.restar(1)})
                            Spacer(modifier= Modifier.size(10.dp))
                            flechita (intLimitado= velocidadLimitada, intMostrable =velocidad, rotacion = 180f, onClick = { velocidad = it },operacion= {a:IntLimitado->a.sumar(1)})
                        }else{
                            Text(text="Modificar",fontSize = sizeFuente, modifier= Modifier.clickable{modificaVelocidad=true}, color = MaterialTheme.colors.secondary)
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
                            flechita (intLimitado= oscuridadLimitada, intMostrable=oscuridad, rotacion = 0f, onClick = { oscuridad = it },operacion= {a:IntLimitado->a.restar(1)})
                            Spacer(modifier= Modifier.size(10.dp))
                            flechita (intLimitado= oscuridadLimitada, intMostrable=oscuridad, rotacion = 180f, onClick = { oscuridad = it },operacion= {a:IntLimitado->a.sumar(1)})
                        }else{
                            Text(text="Modificar",fontSize = sizeFuente, modifier= Modifier.clickable{modificaOscuridad=true}, color = MaterialTheme.colors.secondary)
                        }
                    }
                }
            }
        }
    )
}

fun cambiarConfiguracionRP4(bluetooth: Int, modificaBluetooth: Boolean,
                             oscuridad: Int, modificaOscuridad:Boolean,
                             velocidad: Int, modificaVelcoidad:Boolean){
    var setBluetooth = ""
    var setVelocidad = ""
    var setOscuridad = ""

    if (modificaBluetooth){
        setBluetooth = "BT[9," + bluetooth.toString() + ":];"
    }

    if (modificaOscuridad){
        setOscuridad = "DK" + oscuridad.toString() + ";"
    }

    if (modificaVelcoidad){
        var velocidadCHAR = ""
        when (velocidad.toFloat()/2) {
            1.0f -> velocidadCHAR = "A"
            1.5f -> velocidadCHAR = "B"
            2.0f -> velocidadCHAR = "C"
            2.5f -> velocidadCHAR = "D"
            3.0f -> velocidadCHAR = "E"
            3.5f -> velocidadCHAR = "F"
            4.0f -> velocidadCHAR = "G"
            4.5f -> velocidadCHAR = "H"
            5.0f -> velocidadCHAR = "I"
        }
      setVelocidad = "pS" + velocidadCHAR + ";"
    }


    BTHandler.imprimir("Kc"+
            setBluetooth +
            setOscuridad +
            setVelocidad + "\n"
    )
}