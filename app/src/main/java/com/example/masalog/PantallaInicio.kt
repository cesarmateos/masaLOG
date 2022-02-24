package com.example.masalog
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun PantallaInicio(
    onClickImpresoras: () -> Unit = {},
    onClickControladorInput: () -> Unit = {},
) {
    val espaciado = 20.dp
    Scaffold(
        topBar = { barraTOP() },
        content = {

            Column(
                modifier = Modifier.fillMaxSize().padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                BotonStandard("Impresoras",onClickImpresoras)
                Spacer(Modifier.size(espaciado))
                BotonStandard("Controlador",onClickControladorInput)
            }
       }
    )

}
