package com.example.masalog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

open class EstadoBT(btHandler: BTHandler, cambiaEstadoBT: (EstadoDispositivo)-> Unit ){

    @Composable
    open fun derecha(): Unit{}

    @Composable
    open fun izquierda(): Unit{}
}
/*
class BTConectado(val barraTOP: BarraTOP): EstadoBT {

    lateinit var dispositivo : String

    @Composable
    override fun izquierda() {
        Text("Conectado a " + dispositivo)
    }

    @Composable
    override fun derecha() {
        BotonCierreBT(barraTOP)
    }
}

class BTDesconectado: EstadoBT() {

    lateinit var contenedorEstadoBT : ContenedorEstadoBlueToot
    init{
        println("Entre BTDesconectado")
    }
    override fun asignarContenedor(estado : ContenedorEstadoBlueToot){
        contenedorEstadoBT = estado
    }

    @Composable
    override fun izquierda(){
        Text("Sin Impresora")
    }

    @Composable
    override fun derecha(){
        Button(
            onClick = {},
            modifier = Modifier.size(25.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue),
            contentPadding = PaddingValues(1.dp)){
            Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "")
        }
    }
}
*/
class BTSinHardware(btHandler: BTHandler,
                    cambiaEstadoBT: (EstadoDispositivo) -> Unit
) : EstadoBT(btHandler, cambiaEstadoBT) {

    @Composable
    override fun izquierda(){
        Text("No Bluetooth")
    }

}
/*
class BTImprimiendo(val barraTOP: BarraTOP): EstadoBT {

    @Composable
    override fun izquierda(){
        Text("Imprimiendo....")
    }

    @Composable
    override fun derecha(){
        BotonCierreBT(barraTOP)
    }
}

class BTConectando(btHandler: BTHandler, cambiaEstadoBT: (EstadoDispositivo) -> Unit)
    : EstadoBT(btHandler, cambiaEstadoBT) {

    val btHandler = btHandler
    val cambiaEstadoBT = cambiaEstadoBT

    @Composable
    override fun izquierda(){
        Text("Conectando....")
    }

    @Composable
    override fun derecha(){
        BotonCierreBT(btHandler, cambiaEstadoBT)
    }
}
*/

/*
class BTBuscando(val barraTOP: BarraTOP): EstadoBT {

    lateinit var dipositivos : List<String>

    @Composable
    override fun derecha(){
        Button(
            onClick = {},
            modifier = Modifier.size(25.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green),
            contentPadding = PaddingValues(1.dp)){
            Icon(imageVector = Icons.Filled.ArrowForward, contentDescription = "")
        }
    }

    @Composable
    override fun izquierda(){
        var expanded by remember { mutableStateOf(false) }
        val suggestions = dipositivos

        Column(){
            Button(onClick = { expanded = !expanded }){
                Text ("DropDown")
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = null,
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                suggestions.forEach { label ->
                    DropdownMenuItem(onClick = {
                        expanded = false
                    //do something ...
                    }) {
                        Text(text = label)
                    }
                }
            }
        }
    }
}

*/

