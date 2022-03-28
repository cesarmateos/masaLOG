package com.example.masalog.etiquetado

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.masalog.*
import com.example.masalog.ui.theme.*
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PantallaEtiquetado_Inicio(onClickEtiquetadoPlantaEscaneado: () -> Unit = {}) {
    var carga: Boolean by remember { mutableStateOf(true) }
    var archivo: Boolean by remember { mutableStateOf(ListadoEtiquetado.archivoCargado) }

    Scaffold(
        topBar = { barraTOP() },
        content = {
            EstructuraTituloCuerpo("Etiquetado Planta")
            {
                Column(){
                    if(archivo){ //Archivo Ya Cargado

                        Column(modifier= Modifier.fillMaxWidth()){
                            Text(text="Se han cargado "+ ListadoEtiquetado.productos.size.toString()+" registros",
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colors.secondary,
                                modifier= Modifier
                                    .padding(5.dp)
                                    .fillMaxWidth())
                            Spacer(modifier= Modifier.size(10.dp))
                            Row(
                                Modifier
                                    .height(40.dp)
                                    .padding(horizontal = 10.dp, vertical = 3.dp)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Producto:")
                                Spacer(modifier= Modifier.size(5.dp))
                                InputTexto(onClick = {texto: String -> ListadoEtiquetado.imprimeLocalizador(texto) }, keyboardType =KeyboardType.Number )
                            }
                        /*
                        Row(){
                            Text(text= "Cargar Unidades:")
                            Spacer(Modifier.size(10.dp))
                            ToggleHorizontal(estadoA = carga, onClick = {carga= it}, textoA = "Sí", textoB = "No")
                        }
                        */
                        }

                    }else{ //Cargar Archivo
                        //Formato del Archivo CSV
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .weight(1.0f)){
                            Text(text="Formato del archivo CSV",
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                modifier= Modifier
                                    .padding(5.dp)
                                    .fillMaxWidth())
                            Row(modifier = Modifier
                                .border(3.dp, MaterialTheme.colors.onSecondary)
                                .height(60.dp)
                                .background(GrisClaro),
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                CajaTextoGris(text = "Código Barras", modifier = Modifier
                                    .weight(1.0f)
                                    .padding(5.dp))
                                CajaTextoGris(text = "Localizador", modifier = Modifier
                                    .weight(1.0f)
                                    .padding(5.dp))
                                CajaTextoGris(text = "Nombre Producto", modifier = Modifier
                                    .weight(1.0f)
                                    .padding(5.dp))
                            }

                        }
                        //Boton Carga CSV
                        elegirArchivoEtiquetado(onClickEtiquetadoPlantaEscaneado = {onClickEtiquetadoPlantaEscaneado},
                            archivoCargado = archivo,
                            modificaArchivoCargado = {archivo= it}
                        )
                    }
                }
            }
        }
    )
}


@Composable
private fun elegirArchivoEtiquetado(onClickEtiquetadoPlantaEscaneado: () -> Unit = {},
                                    archivoCargado:Boolean,
                                    modificaArchivoCargado: (Boolean)->Unit = {}) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { result ->
        try{
            val item = result?.let {
                context.contentResolver.openInputStream(it)
            }

            val bytes = item?.readBytes()
            val text = String(bytes!!, StandardCharsets.UTF_8)

            ListadoEtiquetado.cargarProductos(text)
            modificaArchivoCargado(ListadoEtiquetado.archivoCargado)

            item.close()
        }catch(exception: Exception){
            //Por si se arrepiente al elegir archivo
        }

    }

    if(ListadoEtiquetado.productos.isEmpty()){
        // ARCHIVO DE MIERDA
    }else{
        onClickEtiquetadoPlantaEscaneado()
    }

    return BotonStandard(texto = "Cargar Archivo CSV",
            onClick = {launcher.launch("*/*")})
}