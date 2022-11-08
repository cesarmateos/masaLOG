package com.example.masalog.controlado

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.masalog.*
import com.example.masalog.ui.theme.*


@Composable
fun PantallaControlaListado_Listado(navController: NavHostController) {

    val cantidadItemsBulto = remember { mutableStateOf(0) }


    Column(Modifier.fillMaxWidth()) {

        //Fila Superior
        Row(modifier = Modifier
                .height(40.dp)
                .background(MaterialTheme.colors.surface)
                .padding(horizontal = 10.dp, vertical = 4.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Producto:")
                Spacer(modifier = Modifier.size(5.dp))
                InputTexto(
                    onClick = { texto: String ->
                        ControlProductos.TipoControl.lectura(texto,navController) },
                    keyboardType = KeyboardType.Number,
                    largo = 140.dp,
                    tecladoActivo = false)
                Spacer(modifier = Modifier.size(30.dp))


                Button(onClick = { ControlProductos.terminar(navController)},
                        modifier = Modifier.height(30.dp)) {
                    Text(text="Terminar", fontSize = 12.sp)
                }
            }

        //Productos por Leer
        Row(modifier = Modifier.weight(1.0f)) {
            CajaScrolleable(productoControls = ControlProductos.porControlar(),
                                                 navController,
                                                "Por Leer",
                                                 ::sumarUnidadesPorContar
                )
            }

        //Producots Leídos
        Row(modifier = Modifier.weight(1.0f)) {
            CajaScrolleable(productoControls = ControlProductos.controlados(),
                                                navController,
                                                "Leídos",
                                                ::sumarUnidadesContadas
                )

            }



        //Bultos
        if(ControlProductos.crearBulto.value == true){

            cantidadItemsBulto.value = ControlProductos.itemsEnBulto()
            Row(
                modifier = Modifier
                    .height(40.dp)
                    .background(MaterialTheme.colors.surface)
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

}




@Composable
fun CajaScrolleable(productoControls:List<ProductoControl>,
                    navController: NavHostController,
                    texto:String,
                    sumador: (productoControls:List<ProductoControl>)-> String)
{
    val tamanioFuente : TextUnit = 12.sp
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
                    .border(3.dp, MaterialTheme.colors.primary)
                    .background(MoradoMuySuave)
                    .padding(5.dp))

            //Celda Derecha
            Row(modifier = Modifier
                .width(200.dp)
                .background(MaterialTheme.colors.secondaryVariant)
                .border(3.dp, MaterialTheme.colors.onBackground)
                .padding(5.dp)){

                //Líneas
                Row(modifier = Modifier.width(100.dp)){
                    Text(text= "L: ", color= MaterialTheme.colors.onBackground,fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start =10.dp))
                    Text(text= productoControls.size.toString(), color= MaterialTheme.colors.onBackground,
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .fillMaxWidth())
                }

                //Unidades
                Row(modifier = Modifier.width(100.dp)) {
                    Text(text = "U: ", color = MaterialTheme.colors.onBackground, fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start =9.dp))
                    Text(text = sumador(productoControls),color= MaterialTheme.colors.onBackground,
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .fillMaxWidth())
                }
            }

        }
        LazyColumn(
            Modifier
                .fillMaxSize()
                .border(3.dp, MaterialTheme.colors.onBackground)){
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
                            ControlProductos.TipoControl.lectura(item.codigoBarras,navController)
                        }){
                    Text(item.codigoBarras.takeLast(6),
                        modifier= Modifier
                            .padding(3.dp)
                            .width(43.dp),
                        fontSize = tamanioFuente)
                    Text(item.nombre.take(30),
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


