package com.example.masalog

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.masalog.ui.theme.GrisOscuro
import com.example.masalog.ui.theme.MoradoMuySuave
import com.example.masalog.ui.theme.NaranjaMuySuave
import com.example.masalog.ui.theme.VioletaOscuro

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PantallaControlaListadoIngresoProducto (onClickControladorProductos: () -> Unit = {}){
    var ingresoCantidad by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val estadoCheckBox = remember { mutableStateOf(ControlProductos.etiqueta) }
    val interlineado = 6.dp
    Scaffold(
        topBar = {barraTOP()},
        content = {
            Column(modifier= Modifier.fillMaxWidth()){

                //Nombre Producto
                ControlProductos.productoIngresado?.let { it1 -> Text(text=it1.nombre,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign=TextAlign.Center,
                    modifier= Modifier.fillMaxWidth().background(NaranjaMuySuave).padding(vertical = interlineado)) }

                //Código Barras
                Text(text=ControlProductos.productoIngresado?.codigoBarras.toString(),
                    textAlign = TextAlign.End,modifier= Modifier.fillMaxWidth().padding(interlineado))

                //Lote
                Row(modifier= Modifier.fillMaxWidth()){
                    Text(text="Lote:  ", fontWeight = FontWeight.Bold)
                    Text(text=ControlProductos.productoIngresado?.lote.toString())
                }

                //Vencimiento
                Row(modifier= Modifier.fillMaxWidth()) {
                    Text(text="Vencimiento:  ", fontWeight = FontWeight.Bold)
                    Text(text = ControlProductos.productoIngresado?.vencimiento.toString())
                }

                //Contable
                Row(modifier= Modifier.fillMaxWidth().padding(vertical = 10.dp)){

                    //CONTABLE
                    Column(modifier = Modifier.weight(1.0f)){
                        Text(text="Contable",
                            modifier=Modifier.padding(2.dp)
                                .background(MoradoMuySuave)
                                .fillMaxWidth(),
                            textAlign=TextAlign.Center)
                        Text(text=ControlProductos.productoIngresado?.cantidad.toString(),
                            modifier=Modifier.fillMaxWidth(),
                            textAlign=TextAlign.Center)
                    }

                    //CONTADO
                    Column(modifier = Modifier.weight(1.0f)) {
                        Text(
                            text = "Contado",
                            modifier = Modifier.padding(2.dp)
                                .background(MoradoMuySuave)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center)
                        Text(
                            text = ControlProductos.productoIngresado?.contado().toString(),
                            modifier=Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center)
                    }

                    //DIFERENCIA
                    Column(modifier = Modifier.weight(1.0f)) {
                        Text(
                            text = "Diferencia",
                            modifier = Modifier.padding(2.dp)
                                .background(MoradoMuySuave)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = ControlProductos.productoIngresado?.diferencia().toString(),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth())
                    }
                }

                //Localizador
                Row(modifier=Modifier
                        .background(NaranjaMuySuave)
                        .padding(interlineado)
                        .fillMaxWidth(),

                    verticalAlignment = Alignment.CenterVertically){
                    ControlProductos.productoIngresado?.let { it1 -> Text(text= it1.localizador,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign=TextAlign.Center,
                        modifier= Modifier.fillMaxWidth()) }

                }

                //Input Text
                Row(modifier= Modifier.fillMaxWidth().padding(vertical = interlineado),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){
                    Text(text="Cantidad: ")
                    Box(modifier= Modifier.border(2.dp, GrisOscuro, RoundedCornerShape(10))
                        .background(color= Color.White)
                        .size(80.dp,30.dp).padding(horizontal = 5.dp)
                        .focusTarget(),
                        contentAlignment = Alignment.CenterStart
                    ){

                        BasicTextField(value = ingresoCantidad,
                            onValueChange = {ingresoCantidad = it},
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            keyboardActions = KeyboardActions(onDone={
                                ControlProductos.cargarCantidad(ingresoCantidad)
                                onClickControladorProductos()
                            }),
                            enabled = true,
                            singleLine = true,
                            modifier = Modifier
                                .onKeyEvent {
                                    if(it.key.keyCode == Key.Enter.keyCode) {
                                        ControlProductos.cargarCantidad(ingresoCantidad.dropLast(1))
                                        onClickControladorProductos()
                                    }
                                    false
                                }
                                .focusRequester(focusRequester)
                        )
                    }
                    Spacer(modifier=Modifier.size(30.dp))
                    Text("Etiqueta:",modifier=Modifier.padding(interlineado))
                    Checkbox(checked = estadoCheckBox.value,
                        onCheckedChange = { estadoCheckBox.value = it
                            ControlProductos.etiqueta = estadoCheckBox.value},
                        modifier= Modifier.padding(vertical = interlineado))
                }
            }
        }
    )

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}


fun imprimirEtiquetaRecepcion(producto:Producto, cantidad:Int){
    val etiqueta = "<STX><ESC>C<ETX>\n" +
            "<STX><ESC>P<ETX>\n" +
            "<STX>E22;F22;<ETX>      \n" +
            "<STX>H0;o360,55;f3;c26;d0,30;k20;<ETX>\n" +
            "<STX>H1;o260,55;f3;c26;d0,30;k17;<ETX>\n" +
            "<STX>H2;o210,55;f3;c26;d0,30;k14;<ETX>\n" +
            "<STX>H3;o140,55;f3;c26;d0,20;k24;<ETX>\n" +
            "<STX>H4;o040,55;f3;c21;d0,30;k10;<ETX>\n" +
            "<STX>R<ETX>"

}