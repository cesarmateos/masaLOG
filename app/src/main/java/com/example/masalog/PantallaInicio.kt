package com.example.masalog
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
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
            EstructuraTituloCuerpo(textoTitulo = "Seleccione una tarea") {
                Column(){
                    Row(modifier= Modifier
                        .fillMaxWidth()
                        .weight(1.0f)){
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            BotonStandard("Configurar Impresora", onClickImpresoras)
                            Spacer(Modifier.size(PADDING_HORIZONTAL))
                            BotonStandard("Configurar Etiquetas",onClickEtiquetas)
                            Spacer(Modifier.size(PADDING_HORIZONTAL))
                            //BotonStandard("Controlador",onClickControladorInput)
                            //Spacer(Modifier.size(PADDING_HORIZONTAL))
                            BotonStandard("Etiquetado desde Archivo",onClickEtiquetado)
                            //Spacer(Modifier.size(PADDING_HORIZONTAL))
                            //BotonStandard(texto = "Etiquetas Varias", onClick = {})
                        }
                    }
                    Row(modifier=Modifier.fillMaxWidth()) {
                        Text(text= stringResource(R.string.app_name_version),
                            textAlign = TextAlign.End,
                            modifier= Modifier.fillMaxWidth())
                    }
                }

            }
       }
    )

}
