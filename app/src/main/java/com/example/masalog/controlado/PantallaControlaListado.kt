package com.example.masalog.controlado

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.masalog.*
import com.example.masalog.ui.theme.*
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PantallaControlaListado(navController: NavHostController) {
    var archivo: Boolean by remember { mutableStateOf(ControlProductos.archivoCargado) }
    val estadoCheckBoxEtiqueta = remember { mutableStateOf(ControlProductos.etiqueta) }
    val estadoCheckBoxBultos = remember { mutableStateOf(ControlProductos.crearBulto) }
    val cantidadItemsBulto = remember { mutableStateOf(0) }
    val keyboardController = LocalSoftwareKeyboardController.current

    keyboardController?.hide()

    if (archivo) { //Archivo Ya Cargado
        Column(Modifier.fillMaxWidth()) {

            //Fila Superior
            Row(
                modifier = Modifier
                    .height(40.dp)
                    .background(NaranjaMuySuave)
                    .padding(horizontal = 10.dp, vertical = 4.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Producto:")
                Spacer(modifier = Modifier.size(5.dp))
                InputTexto(
                    onClick = { texto: String ->
                        ControlProductos.lectura(texto)
                    },
                    keyboardType = KeyboardType.Number
                )
                Spacer(modifier = Modifier.size(30.dp))
                Button(onClick = { ControlProductos.terminar()},
                        modifier = Modifier.height(30.dp)) {
                    Text(text="Terminar", fontSize = 12.sp)
                }
            }

            //Productos por Leer
            Row(modifier = Modifier.weight(1.0f)) {
                CajaScrolleable(
                    productoControls = ControlProductos.porControlar(),
                    navController,
                    "Por Leer",
                    ::sumarUnidadesPorContar
                )
            }

            //Producots Leídos
            Row(modifier = Modifier.weight(1.0f)) {
                CajaScrolleable(
                    productoControls = ControlProductos.controlados(),
                    navController,
                    "Leídos",
                    ::sumarUnidadesContadas
                )

            }

            //Bultos
            if(estadoCheckBoxBultos.value){

                cantidadItemsBulto.value = ControlProductos.itemsEnBulto()
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .background(NaranjaMuySuave)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text("Bulto ")
                    Text(ControlProductos.nroBulto.toString())
                    Row(modifier= Modifier.weight(1.0f),
                        horizontalArrangement = Arrangement.Center){
                        Text("Ítems: ")
                        Text(cantidadItemsBulto.value.toString())
                    }
                    Button(onClick = { ControlProductos.cerrarBulto()
                                     cantidadItemsBulto.value = 0},
                        modifier = Modifier.height(30.dp)) {
                        Text(text="Cerrar", fontSize = 12.sp)
                    }
                }
            }

        }
        keyboardController?.hide()
    } else { //Cargar Archivo
        EstructuraTituloCuerpo("Control Productos") {
            Column() {
                //Formato del Archivo CSV
                Column(Modifier.weight(1.0f)) {
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
                            .border(3.dp, MaterialTheme.colors.onSecondary)
                            .height(60.dp)
                            .background(GrisClaro),
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
                            text = "Nombre Producto", modifier = Modifier
                                .weight(1.0f)
                                .padding(5.dp)
                        )
                        CajaTextoGris(
                            text = "Cantidad", modifier = Modifier
                                .weight(1.0f)
                                .padding(5.dp)
                        )
                    }


                    Spacer(Modifier.size(30.dp))

                    Text(
                        text = "Opciones",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth()
                    )

                    //Etiqueta Producto
                    Spacer(Modifier.size(10.dp))
                    Row(){
                        Text("Etiqueta producto: ")
                        Checkbox(checked = estadoCheckBoxEtiqueta.value,
                            onCheckedChange = { estadoCheckBoxEtiqueta.value = it
                                ControlProductos.etiqueta = estadoCheckBoxEtiqueta.value})
                    }

                    //Arma Bultos
                    Spacer(Modifier.size(10.dp))
                    Row(){
                        Text("Armar Bultos: ")
                        Checkbox(checked = estadoCheckBoxBultos.value,
                            onCheckedChange = { estadoCheckBoxBultos.value = it
                                ControlProductos.crearBulto = estadoCheckBoxBultos.value})
                    }
                }
                //Boton Carga CSV
                ElegirArchivoControl(
                    archivoCargado = archivo,
                    modificaArchivoCargado = { archivo = it }
                )
            }
        }
    }
}




@Composable
fun CajaScrolleable(productoControls:List<ProductoControl>,
                    navController: NavHostController,
                    texto:String,
                    sumador: (productoControls:List<ProductoControl>)-> String)
{
    val tamanioFuente : TextUnit = 13.sp
    Column(modifier = Modifier.fillMaxWidth()) {

        //Titulos
        Row(modifier= Modifier.fillMaxWidth()){

            //Celda Izquierda
            Text(text= texto,
                textAlign = TextAlign.Center,
                color = VioletaOscuro,
                fontWeight = FontWeight.Bold,
                modifier= Modifier
                    .weight(1.0f)
                    .border(3.dp, VioletaOscuro)
                    .background(
                        MoradoMuySuave
                    )
                    .padding(5.dp))

            //Celda Derecha
            Row(modifier = Modifier
                .width(200.dp)
                .background(GrisClaro)
                .border(3.dp, GrisOscuro)
                .padding(5.dp)){

                //Líneas
                Row(modifier = Modifier.width(100.dp)){
                    Text(text= "L: ", color= GrisOscuro,fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start =10.dp))
                    Text(text= productoControls.size.toString(), color= GrisOscuro,
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .fillMaxWidth())
                }

                //Unidades
                Row(modifier = Modifier.width(100.dp)) {
                    Text(text = "U: ", color = GrisOscuro, fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start =10.dp))
                    Text(text = sumador(productoControls),color= GrisOscuro,
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .fillMaxWidth())
                }
            }

        }
        LazyColumn(
            Modifier
                .fillMaxSize()
                .border(3.dp, GrisOscuro)){
            items(items=productoControls, itemContent = {
                    item ->
                var color = Color.Red
                if(item.diferencia()==0) {
                    color = VerdeOK
                }
                if(item.diferencia()>0){
                    color = Naranja
                }

                Row(
                    Modifier
                        .fillMaxSize()
                        .padding(horizontal = 3.dp)
                        .clickable {
                            ControlProductos.lectura(item.codigoBarras)
                            navController.navigate((Pantallas.ControladorIngreso.name))
                        }){
                    Text(item.codigoBarras.takeLast(6),
                        modifier= Modifier
                            .padding(3.dp)
                            .width(45.dp),
                        fontSize = tamanioFuente)
                    Text(item.nombre.take(29),
                        modifier= Modifier
                            .padding(3.dp)
                            .weight(5.0f),
                        fontSize = tamanioFuente)

                    Text(item.cantidad.toString(), textAlign = TextAlign.Right, modifier= Modifier
                        .padding(3.dp)//.size(40.dp))
                        .weight(1.0f),
                        fontSize = tamanioFuente)
                    Text(item.contado().toString(), color = color, textAlign = TextAlign.Right, modifier= Modifier
                        .padding(3.dp)//.size(40.dp))
                        .weight(1.0f),
                        fontSize = tamanioFuente)

                }
            })
        }
    }
}

fun sumarUnidadesPorContar(productoControls:List<ProductoControl>): String{
    return productoControls.fold(0){ total, producto -> total + (producto.cantidad-producto.contado())}.toString()
}

fun sumarUnidadesContadas(productoControls:List<ProductoControl>): String{
    return productoControls.fold(0){ total, producto -> total + producto.contado()}.toString()
}


@Composable
private fun ElegirArchivoControl(archivoCargado:Boolean,
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

            ControlProductos.cargarProductos(text)
            modificaArchivoCargado(ControlProductos.archivoCargado)

            item.close()
        }catch(exception: Exception){
            //Por si se arrepiente al elegir archivo
        }

    }

    return BotonStandard(texto = "Cargar Archivo CSV",
        onClick = {launcher.launch("*/*")})
}