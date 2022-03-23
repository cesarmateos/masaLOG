package com.example.masalog

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.masalog.ui.theme.NaranjaMuySuave
import java.nio.charset.StandardCharsets

@Composable
fun PantallaEtiquetado_Inicio(onClickEtiquetadoPlantaEscaneado: (ListadoEtiquetado) -> Unit = {}) {
    var carga: Boolean by remember { mutableStateOf(true) }
    Scaffold(
        topBar = { barraTOP() },
        content = {
            EstructuraTituloCuerpo("Etiquetado Planta")
            {
                Column(){
                    Row(modifier=Modifier.fillMaxWidth().weight(1.0f)){
                        Text(text= "Cargar Unidades:")
                        Spacer(Modifier.size(10.dp))
                        ToggleHorizontal(estadoA = carga, onClick = {carga= it}, textoA = "Sí", textoB = "No")
                    }
                    elegirArchivoEtiquetado(onClickEtiquetadoPlantaEscaneado)
                }


            }

        }
    )
}


@Composable
private fun elegirArchivoEtiquetado(onClickEtiquetadoPlantaEscaneado: (ListadoEtiquetado) -> Unit = {}) {
    val context = LocalContext.current
    val productos = ListadoEtiquetado()
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { result ->
        try{
            val item = result?.let {
                context.contentResolver.openInputStream(it)
            }

            val bytes = item?.readBytes()
            val text = String(bytes!!, StandardCharsets.UTF_8)

            productos.cargarProductos(text)

            item.close()
        }catch(exception: Exception){
            //Por si se arrepiente al elegir archivo
    }

    }

    if(productos.productos.isEmpty()){
        // ARCHIVO DE MIERDA
    }else{
        onClickEtiquetadoPlantaEscaneado(productos)
    }

    return BotonStandard(texto = "Cargar Archivo CSV",
            onClick = {launcher.launch("*/*")})
}
