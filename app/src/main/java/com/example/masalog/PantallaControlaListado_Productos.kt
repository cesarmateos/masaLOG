package com.example.masalog

import android.text.style.StyleSpan
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.SemanticsProperties.ImeAction
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.masalog.ui.theme.*

@Composable
fun  PantallaControlaListadoProductos(onClickControladorIngreso: () -> Unit = {})
{
    val estadoCheckBox = remember { mutableStateOf(ControlProductos.etiqueta) }

    Scaffold(
        topBar = {barraTOP()},
        content = {
            Column(modifier = Modifier.fillMaxWidth()){
                Row(
                    Modifier
                        .height(40.dp)
                        .background(NaranjaMuySuave)
                        .padding(horizontal = 10.dp, vertical = 3.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Producto:")
                    Spacer(modifier= Modifier.size(5.dp))
                    lectorBarras(onClickControladorIngreso)
                    Spacer(modifier= Modifier.size(20.dp))
                    Text("Etiqueta:")
                    Checkbox(checked = estadoCheckBox.value,
                        onCheckedChange = { estadoCheckBox.value = it
                                            ControlProductos.etiqueta = estadoCheckBox.value})
                }

                Row(modifier=Modifier.weight(1.0f)){
                    cajaScrolleable(productos = ControlProductos.porControlar(),
                        onClickControladorIngreso,
                        "Por Leer",
                        ::sumarUnidadesPorContar)
                }
                Row(modifier=Modifier.weight(1.0f)){
                    cajaScrolleable(productos = ControlProductos.controlados(),
                        onClickControladorIngreso,
                        "Leídos",
                        ::sumarUnidadesContadas)
                }
                //bultosHandler()
            }
        }
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun lectorBarras(onClickControladorIngreso: () -> Unit = {}) {

    var ingresoBarras by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    keyboardController?.hide()

        Box(
            modifier = Modifier
                .border(2.dp, GrisOscuro, RoundedCornerShape(10))
                .background(color = Color.White)
                .size(150.dp, 30.dp)
                .padding(horizontal = 5.dp)
                .focusTarget(),
            contentAlignment = Alignment.CenterStart
        ) {
            BasicTextField(value = ingresoBarras,
                onValueChange = { ingresoBarras = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                keyboardActions = KeyboardActions(onDone = {
                    ControlProductos.lectura(ingresoBarras)
                    onClickControladorIngreso()
                }),
                enabled = true,
                singleLine = true,
                modifier = Modifier
                    .onKeyEvent {
                        if (it.key.keyCode == Key.Enter.keyCode) {
                            ControlProductos.lectura(ingresoBarras.dropLast(1))
                            onClickControladorIngreso()
                        }
                        false
                    }
                    .focusRequester(focusRequester)
            )
        }
/*
        Spacer(Modifier.size(10.dp))

        Button(onClick = {keyboardController?.show()}) {
            Text("Teclado")
        }

 */

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

}

@Composable
fun cajaScrolleable(productos:List<Producto>,
                    onClickControladorIngreso: () -> Unit = {},
                    texto:String,
                    sumador: (productos:List<Producto>)-> String)
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
                    Text(text= productos.size.toString(), color= GrisOscuro,
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .fillMaxWidth())
                }

                //Unidades
                Row(modifier = Modifier.width(100.dp)) {
                    Text(text = "U: ", color = GrisOscuro, fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start =10.dp))
                    Text(text = sumador(productos),color= GrisOscuro,
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
            items(items=productos, itemContent = {
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
                        .padding(horizontal = 3.dp).clickable {
                            ControlProductos.lectura(item.codigoBarras.toString())
                            onClickControladorIngreso()
                        }){
                    Text(item.codigoBarras.toString().takeLast(6),
                        modifier= Modifier.padding(3.dp).width(45.dp),
                        fontSize = tamanioFuente)
                    Text(item.nombre.take(29),
                        modifier= Modifier.padding(3.dp).weight(5.0f),
                        fontSize = tamanioFuente)
                    /*
                    Text(item.diferencia().toString(), color = color, textAlign = TextAlign.Right, modifier= Modifier
                        .padding(3.dp)//.size(40.dp))
                        .weight(1.0f))
                    */

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

/*
@Composable
fun bultosHandler(){
    Row(
        Modifier
            .height(50.dp)
            .background(NaranjaMuySuave)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically){
        Text(text="Bulto : ", fontWeight = FontWeight.Bold)
        Button(onClick={}){
            Text("Cerrar Bulto")
        }
    }
}
*/
fun sumarUnidadesPorContar(productos:List<Producto>): String{
    return productos.fold(0){ total, producto -> total + (producto.cantidad-producto.contado())}.toString()
}

fun sumarUnidadesContadas(productos:List<Producto>): String{
    return productos.fold(0){ total, producto -> total + producto.contado()}.toString()
}