package com.example.masalog

import android.content.Context
import android.content.res.Resources
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import androidx.compose.runtime.livedata.observeAsState


@Composable
fun barraTOP(){
    TopAppBar (
        title = {
            Image(
                painterResource(R.drawable.logo),
                contentDescription = "",)
                },
        actions = {
            topDerecho() },
        backgroundColor = Color.White
    )
}

@Composable
fun topDerecho(){

    val estado: EstadoDispositivo? by BTHandler.estadoBT.observeAsState()

    Row(modifier = Modifier.padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)){

        when(estado) {
            EstadoDispositivo.SINHARDWARE ->{
                Text("Dispositivo sin BT")
            }
            EstadoDispositivo.IMPRIMIENDO ->{
                Text("Imprimiendo...")
                BotonCierreBT()
            }
            EstadoDispositivo.DESCONECTADO ->{
                SinImpresoraClickeable()
            }
            EstadoDispositivo.CONECTANDO -> {
                Text("Conectando a "+BTHandler.btDevice!!.name.take(8))
                BotonCierreBT()
            }
            EstadoDispositivo.LISTABT ->{
                //val lista = btHandler.dispositivos()
                //var posicionSeleccion by rememberSaveable { mutableStateOf(-1)}
                //SelectorDispositivo(dispositivos = lista,posicion = posicionSeleccion, cambiaPosicion = {posicionSeleccion = it})
                SelectorDispositivo()
                //BotonConectar(btHandler = btHandler, posicion= posicionSeleccion)
            }
            EstadoDispositivo.CONECTADO ->{
                Text(BTHandler.btDevice!!.name.take(8))
                BotonCierreBT()
            }
        }

    }
}

@Composable
fun BotonCierreBT(){
    Button(
        onClick = {
            BTHandler.desconectar()
        },
        modifier = Modifier.size(25.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
        contentPadding = PaddingValues(1.dp)
    ) {
        Icon(imageVector = Icons.Filled.Close, contentDescription = "")
    }
}

@Composable
fun SinImpresoraClickeable(){
    Text(text ="Sin Impresora   ", Modifier.clickable {
        BTHandler.listando()
    })
}

@Composable
fun SelectorDispositivo(){
//fun SelectorDispositivo(dispositivos: MutableList<String>,posicion: Int, cambiaPosicion: (Int)-> Unit){
    var listaActiva by remember{mutableStateOf(false)}
    //var elegido by remember{mutableStateOf("Seleccionar")}
    var elegido = "Seleccionar"
    val dispositivos = BTHandler.dispositivos()

        Row(Modifier.clickable { listaActiva = true }, horizontalArrangement = Arrangement.End) {

            if (listaActiva) {
                DropdownMenu(expanded = true,
                    onDismissRequest = { listaActiva = false },
                    modifier= Modifier.size(230.dp,600.dp))
                {
                    dispositivos.forEachIndexed { index, dispositivo ->
                        DropdownMenuItem(onClick = {
                            listaActiva = false
                            elegido = dispositivo
                            //cambiaPosicion(index)
                            BTHandler.conectar(index)
                        }) {
                            Text(text = dispositivo)
                        }
                    }
                }

            } else {
                Text(text= elegido)
                Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "")
            }
        }
}



