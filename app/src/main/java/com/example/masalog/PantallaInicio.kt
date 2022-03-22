package com.example.masalog
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun PantallaInicio(
    onClickEtiquetas: () -> Unit = {},
    onClickControladorInput: () -> Unit = {},
    onClickImpresoras: () -> Unit = {},
    onClickEtiquetado: () -> Unit = {}
) {

    Scaffold(
        topBar = { barraTOP() },
        content = {
            Row(horizontalArrangement = Arrangement.Center, modifier= Modifier.padding(PADDING_HORIZONTAL)){
                Text(text= "Selecciona una tarea a realizar:")
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                BotonStandard("Configurar Impresora", onClickImpresoras)
                Spacer(Modifier.size(PADDING_HORIZONTAL))
                BotonStandard("Configurar Etiquetas",onClickEtiquetas)
                Spacer(Modifier.size(PADDING_HORIZONTAL))
                BotonStandard("Controlador",onClickControladorInput)
                Spacer(Modifier.size(PADDING_HORIZONTAL))
                BotonStandard("Etiquetado Planta",onClickEtiquetado)

            }
       }
    )

}
