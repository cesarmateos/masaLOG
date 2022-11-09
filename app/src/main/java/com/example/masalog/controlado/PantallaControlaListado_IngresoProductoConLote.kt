package com.example.masalog.configuraImpresora

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.masalog.ContableCargaCantidadEtiqueta
import com.example.masalog.MuestraProducto
import com.example.masalog.controlado.ControlProductos
import com.example.masalog.ui.theme.GrisPurple
import com.example.masalog.ui.theme.MoradoMuySuave


@Composable
fun PantallaControlaListado_IngresoProductoConLote(navController: NavHostController) {
    var loteElegido: Boolean by remember { mutableStateOf(false) }

    Scaffold(
        content = {
            MuestraProducto(navController = navController,ControlProductos.productosMatchCodigoBarras.first()) {
                if(loteElegido){
                    MuestraLoteVencimiento()
                    ContableCargaCantidadEtiqueta(navController)
                }else{
                    Text(text = "Seleccionar:",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Left)
                    Spacer(modifier=Modifier.size(10.dp))
                    Row(Modifier.width(280.dp)){
                        Text(text= "Lote",
                            modifier= Modifier.weight(1.0f)
                                .background(MoradoMuySuave)
                                .border(2.dp, MaterialTheme.colors.primary)
                                .padding(5.dp),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold)
                        Text(text = "Vencimiento",
                            modifier = Modifier.weight(1.0f)
                                .background(MoradoMuySuave)
                                .border(2.dp, MaterialTheme.colors.primary)
                                .padding(5.dp),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold)
                    }
                    LazyColumn( modifier = Modifier
                                            .width(280.dp)
                                            .background(GrisPurple)
                                            .border(3.dp, MaterialTheme.colors.primary),
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        items(items = ControlProductos.productosMatchCodigoBarras){ producto ->

                            Row(
                                Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 3.dp)
                                    .clickable { ControlProductos.productoControlIngresado = producto
                                                loteElegido = true}){
                                Text(text= producto.lote,
                                     modifier= Modifier.weight(1.0f),
                                     textAlign = TextAlign.Center)
                                Text(text = producto.vencimiento,
                                     modifier = Modifier.weight(1.0f),
                                     textAlign = TextAlign.Center)
                            }

                        }
                    }
                }
            }
        }
    )
}

@Composable
fun MuestraLoteVencimiento(){
    Row(modifier= Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center){
        Text("Lote: ",fontWeight = FontWeight.Bold)
        ControlProductos.productoControlIngresado.let { Text(it.lote) }
        Spacer(modifier=Modifier.size(27.dp))
        Text("Vencimiento: ",fontWeight = FontWeight.Bold)
        ControlProductos.productoControlIngresado.let { Text(it.vencimiento) }
    }
}
