package com.example.masalog.controlado

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.masalog.BotonStandard
import com.example.masalog.CajaTextoGris
import com.example.masalog.EstructuraTituloCuerpo
import java.nio.charset.StandardCharsets

@Composable
fun PantallaControlaListado_Configura() {

    val estadoCheckControlLote = remember { mutableStateOf(false) }
    val estadoCheckBoxEtiqueta: Boolean by ControlProductos.etiqueta.observeAsState(false)
    val estadoCheckBoxBultos: Boolean by ControlProductos.crearBulto.observeAsState(false)

    EstructuraTituloCuerpo("Control Productos") {
        Column {

            Column(Modifier.weight(1.0f)) {
                Text(
                    text = "Opciones",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                )
                Spacer(Modifier.size(10.dp))
                //Etiqueta Producto
                Row {
                    Text("Etiqueta producto: ")
                    Checkbox(checked = estadoCheckBoxEtiqueta,
                        onCheckedChange = {
                            ControlProductos.etiqueta.postValue(it)
                        })
                }

                //Arma Bultos
                Spacer(Modifier.size(10.dp))
                Row {
                    Text("Armar Bultos: ")
                    Checkbox(checked = estadoCheckBoxBultos,
                        onCheckedChange = {
                            ControlProductos.crearBulto.postValue(it)
                        })
                }
                //Control Lote
                Spacer(Modifier.size(10.dp))
                Row {
                    Text("Con Control de Lote y Vencimiento: ")
                    Checkbox(checked = estadoCheckControlLote.value,
                        onCheckedChange = {
                            estadoCheckControlLote.value = it
                            if(estadoCheckControlLote.value){
                                ControlProductos.TipoControl = TipoControlConLote()
                            }else{
                                ControlProductos.TipoControl = TipoControlSinLote()
                            }
                        })
                }
                Spacer(Modifier.size(30.dp))
                //Formato del Archivo CSV
                Text(
                    text = "Formato del archivo CSV",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                )
                Row(
                    modifier = Modifier
                        .border(3.dp, MaterialTheme.colors.onBackground)
                        .height(60.dp)
                        .background(MaterialTheme.colors.secondaryVariant),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CajaTextoGris(
                        text = "Código Barras", modifier = Modifier
                            .weight(1.0f)
                            .padding(5.dp)
                    )
                    CajaTextoGris(
                        text = "Localiz.", modifier = Modifier
                            .weight(1.0f)
                            .padding(5.dp)
                    )
                    CajaTextoGris(
                        text = "Nombre Prod.", modifier = Modifier
                            .weight(1.0f)
                            .padding(5.dp)
                    )
                    if (estadoCheckControlLote.value) {
                        CajaTextoGris(
                            text = "Lote", modifier = Modifier
                                .weight(1.0f)
                                .padding(5.dp)
                        )
                        CajaTextoGris(
                            text = " Fecha Venc.", modifier = Modifier
                                .weight(1.0f)
                                .padding(5.dp)
                        )
                    }
                    CajaTextoGris(
                        text = "Cant.", modifier = Modifier
                            .weight(1.0f)
                            .padding(5.dp)
                    )
                }


            }
            //Boton Carga CSV
            ElegirArchivoControl()
        }
    }


}


@Composable
private fun ElegirArchivoControl() {

    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult( contract = ActivityResultContracts.GetContent()) { result ->
        try{
            val item = result?.let {
                context.contentResolver.openInputStream(it)
            }

            val bytes = item?.readBytes()
            val text = String(bytes!!, StandardCharsets.UTF_8)

            item.close()

            ControlProductos.TipoControl.cargarInicial(text)

        }catch(exception: Exception){
            //Por si se arrepiente al elegir archivo
        }

    }

    return BotonStandard(texto = "Cargar Archivo CSV",
        onClick = {launcher.launch("*/*")})

}