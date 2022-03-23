package com.example.masalog

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.nio.charset.StandardCharsets

@Composable
fun PantallaControlaListadoInput(
    onClickControladorProductos: () -> Unit = {}
) {
    Scaffold(
        topBar = {barraTOP()},
        content = {
            EstructuraTituloCuerpo(textoTitulo = "Seleccione origen de datos") {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    elegirArchivo(onClickControladorProductos)
                }
            }
        }
    )

}

@Composable
private fun elegirArchivo(onClickControladorProductos: () -> Unit = {}) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { result ->

        try{
            val item = result?.let {
                //context.contentResolver.takePersistableUriPermission(it,Intent.FLAG_GRANT_READ_URI_PERMISSION)
                context.contentResolver.openInputStream(it)
            }

            val bytes = item?.readBytes()
            val text = String(bytes!!, StandardCharsets.UTF_8)

            construirControlProductosDesdeString(text)

            item.close()
        }catch(exception: Exception){
            //Este Try/Catch está por si se arrepiente al elegir archivo
        }

    }

    if(ControlProductos.productos.isEmpty()){
        // ARCHIVO DE MIERDA
    }else{
        onClickControladorProductos()
    }

    return BotonStandard(texto="Desde Archivo CSV",  onClick = { launcher.launch("*/*")})

}



fun construirControlProductosDesdeString(texto:String){

    ControlProductos.limpiar()

    var lineas = texto.lines()

    print(texto)
    try {
        lineas.forEach {
            var lista = it.split(";")
            if (lista.isNotEmpty()){
                val producto = ProductoComplejo(lista[0].toLong(),lista[1],lista[2],lista[3],lista[4].toInt(),lista[5])
                ControlProductos.productos.add(producto)
                }
            }
    }catch(exception: NumberFormatException){
        ControlProductos.limpiar()
    }
}
/*
fun construirControlProductosDesdeString(texto:String){

    ControlProductos.limpiar()
    //codigo barras; localizador; lote; vencimiento;  cantidad ;nombre
    val regex = "([0-9]{0,15});(.{0,10});(.{0,});(.{0,8});([0-9]{1,6});(.{0,})".toRegex()

    var lineas = texto.lines()

    print(texto)
    try {
        lineas.forEach {
            var match = regex.matchEntire(it)
            match?.destructured?.let { (barras, localizador, lote, vencimiento, cantidad, nombre) ->
                if (barras !=""){
                    val prod = Producto(barras.toLong(), localizador, lote, vencimiento,cantidad.toInt(), nombre)
                    ControlProductos.productos.add(prod)
                }
            }
                ?: throw IllegalArgumentException("Bad input '$texto'")
        }
    }catch(exception: NumberFormatException){
        ControlProductos.limpiar()
    }
}
*/
