package com.example.masalog.configuraImpresora


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.masalog.*
import com.example.masalog.R


@Composable
fun  PantallaConfiguraImpresoraSATO() {
    val sizeFuente = 18.sp

    var velocidadLimitada: IntLimitado by remember { mutableStateOf(IntLimitado(6, 1, 10)) }
    var oscuridadLimitada: IntLimitado by remember { mutableStateOf(IntLimitado(5, 1, 10)) }
    var modificaPapel: Boolean by remember { mutableStateOf(false) }
    var modificaVelocidad: Boolean by remember { mutableStateOf(false) }
    var modificaOscuridad: Boolean by remember { mutableStateOf(false) }
    var papel: Boolean by remember { mutableStateOf(true) }
    var velocidad: Int by remember { mutableStateOf(velocidadLimitada.valor) }
    var oscuridad: Int by remember { mutableStateOf(oscuridadLimitada.valor) }

    EsctructuraTituloCuerpoBoton(
        textoTitulo = "Configurar SATO CL4NX",
        textoBoton = stringResource(R.string.envia_configuracion),
        onClick = {
            cambiarConfiguracionSATO(
                papel,
                modificaPapel,
                oscuridad,
                modificaOscuridad,
                velocidad,
                modificaVelocidad
            )
        }) {

        Column { //COLUMNA GENERAL
            Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Text(text = "Tipo de papel:", fontSize = sizeFuente)
                Spacer(Modifier.size(10.dp))
                if (modificaPapel) {
                    ToggleHorizontal(
                        estadoA = papel,
                        onClick = { papel = it },
                        textoA = "Papel",
                        textoB = "Etiqueta"
                    )
                } else {
                    Text(
                        text = "Modificar",
                        fontSize = sizeFuente,
                        modifier = Modifier.clickable { modificaPapel = true },
                        color = MaterialTheme.colors.secondary
                    )
                }
            }

            Spacer(Modifier.size(10.dp))

            //Velocidad
            Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Text(text = "Velocidad:", fontSize = sizeFuente)
                Spacer(Modifier.size(10.dp))
                if (modificaVelocidad) {
                    Text(text = velocidad.toString().padStart(2, '0'), fontSize = sizeFuente)
                    Spacer(modifier = Modifier.size(20.dp))
                    Flechita(
                        intLimitado = velocidadLimitada,
                        intMostrable = velocidad,
                        rotacion = 0f,
                        onClick = { velocidad = it },
                        operacion = { a: IntLimitado -> a.restar(1) })
                    Spacer(modifier = Modifier.size(10.dp))
                    Flechita(
                        intLimitado = velocidadLimitada,
                        intMostrable = velocidad,
                        rotacion = 180f,
                        onClick = { velocidad = it },
                        operacion = { a: IntLimitado -> a.sumar(1) })
                } else {
                    Text(
                        text = "Modificar",
                        fontSize = sizeFuente,
                        modifier = Modifier.clickable { modificaVelocidad = true },
                        color = MaterialTheme.colors.secondary
                    )
                }
            }

            Spacer(Modifier.size(10.dp))

            //Oscuridad
            Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Text(text = "Oscuridad:", fontSize = sizeFuente)
                Spacer(Modifier.size(10.dp))
                if (modificaOscuridad) {
                    Text(text = oscuridad.toString().padStart(2, '0'), fontSize = sizeFuente)
                    Spacer(modifier = Modifier.size(20.dp))
                    Flechita(
                        intLimitado = oscuridadLimitada,
                        intMostrable = oscuridad,
                        rotacion = 0f,
                        onClick = { oscuridad = it },
                        operacion = { a: IntLimitado -> a.restar(1) })
                    Spacer(modifier = Modifier.size(10.dp))
                    Flechita(
                        intLimitado = oscuridadLimitada,
                        intMostrable = oscuridad,
                        rotacion = 180f,
                        onClick = { oscuridad = it },
                        operacion = { a: IntLimitado -> a.sumar(1) })
                } else {
                    Text(
                        text = "Modificar",
                        fontSize = sizeFuente,
                        modifier = Modifier.clickable { modificaOscuridad = true },
                        color = MaterialTheme.colors.secondary
                    )
                }
            }

            Spacer(Modifier.size(10.dp))
            //Reiniciar
            BotonStandard(texto = "Reiniciar", onClick = { BTHandler.imprimir("DC") })
        }
    }
}

fun cambiarConfiguracionSATO(papel: Boolean, modificaPapel: Boolean,
                            oscuridad: Int, modificaOscuridad:Boolean,
                            velocidad: Int, modificaVelcoidad:Boolean){
    var tipoPapel = ""
    var setVelocidad = ""
    var setOscuridad = ""

    if (modificaPapel){
        tipoPapel = if(papel){
            "IG2" + "PM0"
        }else{
            "IG1" + "PM1"
        }
    }

    if (modificaOscuridad){
        setOscuridad = "#F" + oscuridad.toString()
    }

    if (modificaVelcoidad){
        setVelocidad = "CS" + velocidad.toString()
    }


    BTHandler.imprimir(
        "A" +
                tipoPapel +
                setOscuridad +
                setVelocidad +
                "Z"
    )
}

