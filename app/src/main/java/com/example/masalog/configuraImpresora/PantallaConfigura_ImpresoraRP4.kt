package com.example.masalog.configuraImpresora

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.masalog.*
import com.example.masalog.R
import com.example.masalog.ui.theme.Naranja


@Composable
fun  PantallaConfiguraImpresoraRP4(navController: NavHostController) {
    val sizeFuente = 18.sp

    val velocidadLimitada : IntLimitado by remember { mutableStateOf(IntLimitado(8,2,10)) }
    val oscuridadLimitada : IntLimitado by remember { mutableStateOf(IntLimitado(32,1,64)) }
    val calorLimitado : IntLimitado by remember { mutableStateOf(IntLimitado(15,1,20)) }
    val bluetoothLimitado : IntLimitado by remember { mutableStateOf(IntLimitado(300,30,300)) }
    var modificaBluetooth : Boolean by remember{ mutableStateOf(false) }
    var modificaVelocidad: Boolean by remember { mutableStateOf(false) }
    var modificaCalor: Boolean by remember { mutableStateOf(false) }
    var modificaOscuridad: Boolean by remember { mutableStateOf(false) }
    var bluetooth: Int by remember { mutableStateOf(bluetoothLimitado.valor) }
    var velocidad: Int by remember { mutableStateOf(velocidadLimitada.valor) }
    var calor: Int by remember{ mutableStateOf(calorLimitado.valor)}
    var oscuridad: Int by remember { mutableStateOf(oscuridadLimitada.valor) }
    val alertaAbierto = remember { mutableStateOf(false)}

    EsctructuraTituloCuerpoBoton(
        textoTitulo = "Configurar Honeywell RP4",
        textoBoton = stringResource(R.string.envia_configuracion),
        onClick = {
            if(calor>15){
                alertaAbierto.value = true
            }else{
                    cambiarConfiguracionRP4(
                        bluetooth,
                        modificaBluetooth,
                        oscuridad,
                        modificaOscuridad,
                        calor,
                        modificaCalor,
                        velocidad,
                        modificaVelocidad
                    )
                    navController.navigate(Pantallas.Etiquetas.name)
            }
        })
    {

        //ALERTA CALOR
        if(alertaAbierto.value){
            AlertDialog(onDismissRequest = { alertaAbierto.value = false},
                title = { Text(text = "Alerta Calor", color = Color.Red) },
                text = { Text(text= stringResource(R.string.alerta_calor)) },
                confirmButton = {
                    BotonColor(texto = stringResource(R.string.envia_configuracion),
                        onClick = {cambiarConfiguracionRP4(
                                    bluetooth,
                                    modificaBluetooth,
                                    oscuridad,
                                    modificaOscuridad,
                                    calor,
                                    modificaCalor,
                                    velocidad,
                                    modificaVelocidad)
                                    alertaAbierto.value = false
                                  },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Naranja)
                    )
                },
                dismissButton = {
                    BotonStandard(texto = "No enviar", onClick = {alertaAbierto.value = false})
                }
            )
        }

        Column { //COLUMNA GENERAL

            //Bluetooth
            Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Text(text = "Tiempo Conectada:", fontSize = sizeFuente)
                Spacer(Modifier.size(10.dp))
                if (modificaBluetooth) {
                    Text(text = bluetooth.toString().padStart(3, '0'), fontSize = sizeFuente)
                    Spacer(modifier = Modifier.size(20.dp))
                    ParDeFlechitas(bluetoothLimitado,bluetooth,{bluetooth=it},30)
                } else {
                    Text(
                        text = "Modificar",
                        fontSize = sizeFuente,
                        modifier = Modifier.clickable { modificaBluetooth = true },
                        color = MaterialTheme.colors.secondary
                    )
                }
            }

            Spacer(Modifier.size(10.dp))

            //VELOCIDAD
            Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Text(text = "Velocidad:", fontSize = sizeFuente)
                Spacer(Modifier.size(10.dp))
                if (modificaVelocidad) {
                    Text(text = (velocidad.toFloat() / 2).toString(), fontSize = sizeFuente)
                    Spacer(modifier = Modifier.size(20.dp))
                    ParDeFlechitas(velocidadLimitada,velocidad,{ velocidad = it },1)
                } else {
                    Text(
                        text = "Modificar",
                        fontSize = sizeFuente,
                        modifier = Modifier.clickable { modificaVelocidad = true },
                        color = MaterialTheme.colors.secondary
                    )
                }
            }

            Spacer(Modifier.size(10.dp))

            //Oscuridad
            Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Text(text = "Oscuridad:", fontSize = sizeFuente)
                Spacer(Modifier.size(10.dp))
                if (modificaOscuridad) {
                    Text(text = oscuridad.toString().padStart(2, '0'), fontSize = sizeFuente)
                    Spacer(modifier = Modifier.size(20.dp))
                    ParDeFlechitas(oscuridadLimitada,oscuridad,{oscuridad=it},1)
                } else {
                    Text(
                        text = "Modificar",
                        fontSize = sizeFuente,
                        modifier = Modifier.clickable { modificaOscuridad = true },
                        color = MaterialTheme.colors.secondary
                    )
                }
            }
            Spacer(Modifier.size(10.dp))
            //Calor
            Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Text(text = "Calor:", fontSize = sizeFuente)
                Spacer(Modifier.size(10.dp))
                if (modificaCalor) {
                    Text(text = calor.toString().padStart(2, '0'), fontSize = sizeFuente)
                    Spacer(modifier = Modifier.size(20.dp))
                    ParDeFlechitas(calorLimitado,calor,{calor=it},1)
                } else {
                    Text(
                        text = "Modificar",
                        fontSize = sizeFuente,
                        modifier = Modifier.clickable { modificaCalor = true },
                        color = MaterialTheme.colors.secondary
                    )
                }
            }
        }
    }
}

fun cambiarConfiguracionRP4(bluetooth: Int, modificaBluetooth: Boolean,
                            oscuridad: Int, modificaOscuridad:Boolean,
                            calor: Int, modificaCalor:Boolean,
                            velocidad: Int, modificaVelocidad:Boolean){
    var setBluetooth = ""
    var setVelocidad = ""
    var setOscuridad = ""
    var setCalor = ""

    if (modificaBluetooth){
        setBluetooth = "BT[9,$bluetooth:];"
    }

    if (modificaOscuridad){
        setOscuridad = "DK$oscuridad;"
    }

    if (modificaCalor){
        setCalor = "HE$calor;"
    }

    if (modificaVelocidad){
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
      setVelocidad = "pS$velocidadCHAR;"
    }


    BTHandler.imprimir(
        "Kc" +
                setBluetooth +
                setOscuridad +
                setCalor +
                setVelocidad + "\n"
    )
}