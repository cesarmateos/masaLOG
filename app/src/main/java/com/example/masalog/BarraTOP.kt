package com.example.masalog

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun BarraTOP(scope: CoroutineScope, scaffoldState: ScaffoldState){
    TopAppBar (
        navigationIcon =  {
            IconButton(onClick = {  scope.launch{ scaffoldState.drawerState.open()} }) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
            }
                          },

        title = {
            Row(Modifier.padding(vertical=8.dp)){
                Image(
                    painterResource(R.drawable.logo_grande),
                    contentDescription = "Masa",)
            }
                },
        actions = {
            TopDerecho() },
        backgroundColor = MaterialTheme.colors.background
    )
}

@Composable
fun TopDerecho(){

    val estado: EstadoDispositivo? by BTHandler.estadoBT.observeAsState()
    val alerta: Boolean? by BTHandler.alerta.observeAsState()

    //ALERTA DE IMPRESORA DESCONECTADA
    if(alerta == true){
        AlertDialog(onDismissRequest = { BTHandler.cerrarDialogo() },
            title = { Text(text = "Impresora Desconectada") },
            text = { Text(text= stringResource(R.string.alerta_impresora_no_activa)) },
            buttons = {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(PADDING_HORIZONTAL)){
                    BotonStandard(texto = "Cerrar", onClick = {BTHandler.cerrarDialogo()})
                }}
        )
    }

    Row(modifier = Modifier.padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)){

        when(estado) {
            EstadoDispositivo.IMPRIMIENDO ->{
                Text("Imprimiendo...")
                BotonCierreBT(Color.Green)
            }
            EstadoDispositivo.DESCONECTADO ->{
                SinImpresoraClickeable()
            }
            EstadoDispositivo.BTAPAGADO ->{
                Text("BT desactivado")
            }
            EstadoDispositivo.CONECTANDO -> {
                var nombre = BTHandler.nombreDispositivoConectado()
                if (nombre?.length!! > 8){
                    nombre = nombre.take(3) + ".." + nombre.takeLast(3)
                }
                Text("Conectando a "+ nombre)
                BotonCierreBT(Color.Red)
            }
            EstadoDispositivo.LISTABT ->{
                SelectorDispositivo()
            }
            EstadoDispositivo.CONECTADO ->{
                BTHandler.nombreDispositivoConectado()?.let { Text(it.take(16)) }
                BotonCierreBT(Color.Green)
            }
            else -> {
                Text("Algo anda mal")
            }
        }

    }

}

@Composable
fun BotonCierreBT(color: Color){
    Button(
        onClick = {
            BTHandler.desconectar()
        },
        modifier = Modifier.size(25.dp),
        colors = ButtonDefaults.buttonColors(color),
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
    var listaActiva by remember{mutableStateOf(false)}
    var elegido = "Seleccionar"
    val dispositivos = BTHandler.dispositivos()

        Row(Modifier.clickable { listaActiva = true }, horizontalArrangement = Arrangement.End) {

            if (listaActiva) {
                DropdownMenu(expanded = true,
                    onDismissRequest = { listaActiva = false },
                    modifier= Modifier.size(230.dp,600.dp))
                {
                    dispositivos.forEach {  dispositivo ->
                        DropdownMenuItem(onClick = {
                            listaActiva = false
                            elegido = dispositivo.nombre
                            BTHandler.conectar(dispositivo.posicion)
                        }) {
                            Text(text = dispositivo.nombre)
                        }
                    }
                }

            } else {
                Text(text= elegido)
                Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "")
            }
        }
}





