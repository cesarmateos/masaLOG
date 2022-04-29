package com.example.masalog
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.masalog.controlado.ControlProductos
import java.nio.charset.StandardCharsets


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
            BotonStandard("Controlador desde Archivo"){ navController.navigate((Pantallas.ControladorProductos.name)) }
            Spacer(Modifier.size(PADDING_HORIZONTAL))
            BotonStandard("Etiquetado desde Archivo") { navController.navigate(Pantallas.EtiquetadoPlantaInicio.name) }
            //Spacer(Modifier.size(PADDING_HORIZONTAL))
            //BotonStandard(texto = "Etiquetas Varias", onClick = {})
        }
    }
}


