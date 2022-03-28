package com.example.masalog

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun  PantallaConfiguraImpresora(
    onClickConfiguraSATO: () -> Unit = {},
    onClickConfiguraRP4: () -> Unit = {},
) {
    Scaffold(
        topBar = {barraTOP()},
        content = {
            Column(){
                TituloSeccion(texto = "Configurar Impresora")
                Row(modifier = Modifier
                    .padding(15.dp)
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ){
                    Card(elevation = 4.dp,
                        onClick = onClickConfiguraSATO){
                        Column(horizontalAlignment = Alignment.CenterHorizontally){
                            Image(painter= painterResource(id = R.drawable.sato),
                                contentDescription = "SATO CL4NX",
                                modifier = Modifier
                                    .size(160.dp)
                                    .clip(RoundedCornerShape(10.dp)))
                            Text(text="SATO CL4NX")
                        }

                    }
                    Spacer(modifier = Modifier.size(15.dp))
                    Card(elevation = 4.dp,
                        onClick = onClickConfiguraRP4){
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                painter = painterResource(id = R.drawable.rp4),
                                contentDescription = "Honeywell RP4",
                                modifier = Modifier
                                    .size(160.dp)
                                    .clip(RoundedCornerShape(10.dp))
                            )
                            Text(text = "Honeywell RP4")
                        }
                    }
                }
            }
        }
    )
}