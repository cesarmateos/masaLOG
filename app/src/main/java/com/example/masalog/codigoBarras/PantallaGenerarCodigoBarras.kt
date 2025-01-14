package com.example.masalog.codigoBarras

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.masalog.configuracion.Configuracion
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.masalog.EsctructuraTituloCuerpoBoton
import com.example.masalog.InputTexto
import com.example.masalog.IntLimitado
import com.example.masalog.ParDeFlechitas
import com.example.masalog.R
import com.example.masalog.configuraEtiqueta.IPL
import com.example.masalog.configuraEtiqueta.ZPL
import com.example.masalog.controlado.ControlProductos


@Composable
fun PantallaGenerarCodigoBarras() {
    val configuracion = Configuracion(LocalContext.current)
    val estadoLenguaje = configuracion.getLenguaje.collectAsState(initial = true).value!!
    val lenguaje = if (estadoLenguaje){
        IPL()
    }else{
        ZPL()
    }

    var codigoIngresado: Boolean by remember { mutableStateOf(false) }

    val sizeFuente = 18.sp
    val tamanioLimitado: IntLimitado by remember { mutableStateOf(IntLimitado(3, 1, 6)) }
    var tamanio: Int by remember { mutableStateOf(tamanioLimitado.valor) }
    var codigo = ""

    EsctructuraTituloCuerpoBoton(
        textoTitulo = stringResource(R.string.generar_CB),
        textoBoton = stringResource(R.string.imprimir_CB),
        onClick = {lenguaje.imprimirCodigoBarras(codigo, tamanio)}) {
        Column { //COLUMNA GENERAL
            Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
                verticalAlignment = Alignment.Top)
            {
                Text(text=lenguaje.nombreLenguaje(),
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 36.sp,
                    textAlign = TextAlign.Right)
            }
            Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Text(
                    text = stringResource(R.string.tamanio_etiqueta),
                    fontSize = sizeFuente,
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )
                Spacer(Modifier.size(10.dp))
                Text(text = tamanio.toString().padStart(2, '0'), fontSize = sizeFuente)
                Spacer(modifier = Modifier.size(20.dp))
                ParDeFlechitas(
                    cantidadLimitada = tamanioLimitado,
                    cantidad = tamanio,
                    onClick = { tamanio = it },
                    modificador = 1
                )
            }
            Spacer(Modifier.size(5.dp))
            Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
                verticalAlignment = Alignment.Top
            )
            {
                Text(
                    text = stringResource(R.string.codigo),
                    fontSize = sizeFuente,
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )
                Spacer(Modifier.size(10.dp))

                if(!codigoIngresado) {
                    InputTexto(
                        onClick = { codigo = it
                                    codigoIngresado= true},
                        keyboardType = KeyboardType.Number,
                        largo = 160.dp,
                        tecladoActivo = true
                    )
                }else{
                    Text(text = codigo, fontSize = sizeFuente)
                    Spacer(Modifier.size(10.dp))
                    Text(
                        text = "Modificar",
                        fontSize = sizeFuente,
                        modifier = Modifier.clickable { codigoIngresado = false },
                        color = MaterialTheme.colors.secondary
                    )
                }
            }
        }

    }
}

