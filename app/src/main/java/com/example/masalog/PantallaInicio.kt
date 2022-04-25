package com.example.masalog
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController


@Composable
fun PantallaInicio(navController: NavHostController ) {

    EstructuraTituloCuerpo(textoTitulo = "Seleccione una tarea") {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            BotonStandard("Configurar Impresora") { navController.navigate(Pantallas.Impresoras.name) }
            Spacer(Modifier.size(PADDING_HORIZONTAL))
            BotonStandard("Configurar Etiquetas") { navController.navigate((Pantallas.Etiquetas.name)) }
            Spacer(Modifier.size(PADDING_HORIZONTAL))
            //BotonStandard("Controlador",onClickControladorInput)
            //Spacer(Modifier.size(PADDING_HORIZONTAL))
            BotonStandard("Etiquetado desde Archivo") { navController.navigate(Pantallas.EtiquetadoPlantaInicio.name) }
            //Spacer(Modifier.size(PADDING_HORIZONTAL))
            //BotonStandard(texto = "Etiquetas Varias", onClick = {})
        }
    }
}
