package com.example.masalog

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController



@Composable
fun PantallaInicio(navController: NavHostController ) {

    EstructuraTituloCuerpo(textoTitulo = "Seleccione una tarea") {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            BotonStandard(stringResource(R.string.config_impresora)) { navController.navigate(Pantallas.Impresoras.name) }
            Spacer(Modifier.size(PADDING_HORIZONTAL))
            BotonStandard(stringResource(R.string.config_etiquetas)) { navController.navigate((Pantallas.Etiquetas.name)) }
            Spacer(Modifier.size(PADDING_HORIZONTAL))
            BotonStandard(stringResource(R.string.etiquetado_archivo)) { navController.navigate(Pantallas.EtiquetadoPlantaInicio.name) }
            Spacer(Modifier.size(PADDING_HORIZONTAL))
            BotonStandard(stringResource(R.string.control_archivo)){ navController.navigate((Pantallas.ControladorProductos.name)) }
        }
    }
}


