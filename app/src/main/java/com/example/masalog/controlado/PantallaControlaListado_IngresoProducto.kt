package com.example.masalog.controlado

import androidx.compose.foundation.background

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.masalog.InputTexto
import com.example.masalog.PADDING_HORIZONTAL
import com.example.masalog.ui.theme.MoradoMuySuave
import com.example.masalog.ui.theme.NaranjaMuySuave

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PantallaControlaListadoIngresoProducto (onClickControladorProductos: () -> Unit = {}){
    val estadoCheckBox = remember { mutableStateOf(ControlProductos.etiqueta) }
    val interlineado = 6.dp

    Scaffold(
        content = {
            Column(modifier= Modifier.fillMaxWidth()){

                //Nombre Producto
                ControlProductos.productoControlIngresado?.let { it1 -> Text(text=it1.nombre,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign=TextAlign.Center,
                    modifier= Modifier
                        .fillMaxWidth()
                        .background(NaranjaMuySuave)
                        .padding(vertical = interlineado)) }

                //Código Barras
                Text(text= ControlProductos.productoControlIngresado?.codigoBarras.toString(),
                    textAlign = TextAlign.End,
                    modifier= Modifier
                        .fillMaxWidth()
                        .padding(vertical = interlineado, horizontal = PADDING_HORIZONTAL))

                //Tabla Conteos
                Row(modifier= Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)){

                    //CONTABLE
                    Column(modifier = Modifier.weight(1.0f)){
                        Text(text="Contable",
                            modifier= Modifier
                                .padding(2.dp)
                                .background(MoradoMuySuave)
                                .fillMaxWidth(),
                            textAlign=TextAlign.Center)
                        Text(text= ControlProductos.productoControlIngresado?.cantidad.toString(),
                            modifier=Modifier.fillMaxWidth(),
                            textAlign=TextAlign.Center)
                    }

                    //CONTADO
                    Column(modifier = Modifier.weight(1.0f)) {
                        Text(
                            text = "Contado",
                            modifier = Modifier
                                .padding(2.dp)
                                .background(MoradoMuySuave)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center)
                        Text(
                            text = ControlProductos.productoControlIngresado?.contado().toString(),
                            modifier=Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center)
                    }

                    //DIFERENCIA
                    Column(modifier = Modifier.weight(1.0f)) {
                        Text(
                            text = "Diferencia",
                            modifier = Modifier
                                .padding(2.dp)
                                .background(MoradoMuySuave)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = ControlProductos.productoControlIngresado?.diferencia().toString(),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth())
                    }
                }


                //Fila Input Text + Etiquetado
                Row(modifier= Modifier
                    .fillMaxWidth()
                    .padding(vertical = interlineado),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center) {

                    //INGRESO CANTIDAD
                    Text(text = "Cantidad: ")
                    InputTexto(
                        onClick = { texto: String -> ControlProductos.cargarCantidad(texto) },
                        keyboardType = KeyboardType.Number,
                        largo = 80.dp)
                    Spacer(modifier=Modifier.size(30.dp))

                    //CHECKBOX ETIQUETA
                    Text("Etiqueta:",modifier=Modifier.padding(interlineado))
                    Checkbox(checked = estadoCheckBox.value,
                             onCheckedChange = { estadoCheckBox.value = it
                                                 ControlProductos.etiqueta = estadoCheckBox.value},
                            modifier= Modifier.padding(vertical = interlineado))
                }

                //BOTÓN CANCELAR
                Spacer(Modifier.size(10.dp))
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = PADDING_HORIZONTAL),
                    horizontalArrangement = Arrangement.End){
                    Button(onClick = {onClickControladorProductos() },
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary )) {
                        Text(text="Cancelar", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }

            }
        }
    )
}