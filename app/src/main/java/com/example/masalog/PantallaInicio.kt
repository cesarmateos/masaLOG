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
            Spacer(Modifier.size(PADDING_HORIZONTAL_REDUCIDO))
            BotonStandard(stringResource(R.string.config_etiquetas)) { navController.navigate((Pantallas.Etiquetas.name)) }
            Spacer(Modifier.size(PADDING_HORIZONTAL_REDUCIDO))
            BotonStandard(stringResource(R.string.etiqueta_heladeras)) { navController.navigate(Pantallas.EtiquetaHeladeras.name) }
            Spacer(Modifier.size(PADDING_HORIZONTAL_REDUCIDO))
            BotonStandard(stringResource(R.string.etiquetado_archivo)) { navController.navigate(Pantallas.EtiquetadoPlantaInicio.name) }
            Spacer(Modifier.size(PADDING_HORIZONTAL_REDUCIDO))
            BotonStandard(stringResource(R.string.control_archivo)){ navController.navigate((Pantallas.ControladorInicio.name)) }
            Spacer(Modifier.size(PADDING_HORIZONTAL_REDUCIDO))
            BotonStandard("Generar Código de Barras"){ navController.navigate((Pantallas.GenerarCodigoBarras.name))}
            Spacer(Modifier.size(PADDING_HORIZONTAL_REDUCIDO))
            BotonStandard("Lenguaje"){ navController.navigate((Pantallas.Configuracion.name))}
            /*
            Spacer(Modifier.size(PADDING_HORIZONTAL))
            BotonStandard("Prueba Loca ZPL"){ BTHandler.imprimir(LocZPL)}

             */
        }

    }
}

//val LocZPL= "^XA^FO40,40^A0N,30,30^FD7793640215622                 ^FS^FO40,100^A0N,43,43^FDACTRON ^FS^FO40,140^A0N,43,43^FD 600MG CAP.BL X20^FS^FO40,230^A0N,88,88^FD01  K_2216/2216^FS^FO40,350^A0N,30,30^FD15/11/22 15:59 TESTING^FS^XZ"