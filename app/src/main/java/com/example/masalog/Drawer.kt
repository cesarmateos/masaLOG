package com.example.masalog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.masalog.ui.theme.MoradoMuySuave
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun Drawer(scope: CoroutineScope, scaffoldState: ScaffoldState, navController: NavHostController) {

    val espacioEntreBotones = 10.dp

    Column(modifier= Modifier.fillMaxWidth().background(MaterialTheme.colors.background)){

        //Encabezado
        Row(modifier= Modifier
            //.background(MaterialTheme.colors.background)
            .padding(start = 20.dp, end = 30.dp, top = 10.dp, bottom = 10.dp)){
            Image(painter= painterResource(id = R.drawable.logo_grande), contentDescription = "Masa")
        }

        Column{
            Row(modifier= Modifier
                .fillMaxWidth()
                .weight(1.0f)){

                Column{
                    //Configura Impresora

                    Spacer(Modifier.size(espacioEntreBotones))
                    OpcionDrawerPrimaria(
                        scope = scope,
                        scaffoldState = scaffoldState,
                        navController = navController,
                        ruta = Pantallas.Impresoras.name,
                        etiqueta = stringResource(R.string.config_impresora))

                    OpcionDrawerSecundaria(
                        scope = scope,
                        scaffoldState = scaffoldState,
                        navController = navController,
                        ruta = Pantallas.ConfiguraSATO.name,
                        etiqueta = stringResource(R.string.config_impresora_CL4NX))

                    OpcionDrawerSecundaria(
                        scope = scope,
                        scaffoldState = scaffoldState,
                        navController = navController,
                        ruta = Pantallas.ConfiguraRP4.name,
                        etiqueta = stringResource(R.string.config_impresora_RP4))



                    //Configura Etiqueta
                    Spacer(Modifier.size(espacioEntreBotones))
                    OpcionDrawerPrimaria(
                        scope = scope,
                        scaffoldState = scaffoldState,
                        navController = navController,
                        ruta = Pantallas.Etiquetas.name,
                        etiqueta = stringResource(R.string.config_etiquetas)
                    )

                    //EtiquetaHeladera
                    Spacer(Modifier.size(espacioEntreBotones))
                    OpcionDrawerPrimaria(
                        scope = scope,
                        scaffoldState = scaffoldState,
                        navController = navController,
                        ruta = Pantallas.EtiquetaHeladeras.name,
                        etiqueta = stringResource(R.string.etiqueta_heladeras)
                    )

                    //Etiquetado
                    Spacer(Modifier.size(espacioEntreBotones))
                    OpcionDrawerPrimaria(
                        scope = scope,
                        scaffoldState = scaffoldState,
                        navController = navController,
                        ruta = Pantallas.EtiquetadoPlantaInicio.name,
                        etiqueta = stringResource(R.string.etiquetado_archivo)
                    )

                    //Control
                    Spacer(Modifier.size(espacioEntreBotones))
                    OpcionDrawerPrimaria(
                        scope = scope,
                        scaffoldState = scaffoldState,
                        navController = navController,
                        ruta = Pantallas.ControladorProductos.name,
                        etiqueta = stringResource(R.string.control_archivo)
                    )

                    //Lenguaje
                    Spacer(Modifier.size(espacioEntreBotones))
                    OpcionDrawerPrimaria(
                        scope = scope,
                        scaffoldState = scaffoldState,
                        navController = navController,
                        ruta = Pantallas.Configuracion.name,
                        etiqueta = "Lenguaje"
                    )
                }

            }
            Row(modifier= Modifier
                .fillMaxWidth()
                .padding(PADDING_HORIZONTAL)) {
                Text(text= stringResource(R.string.app_name_version),
                    textAlign = TextAlign.End,
                    modifier= Modifier.fillMaxWidth())
            }
        }
    }
}

@Composable
fun OpcionDrawerPrimaria(scope: CoroutineScope,
                 scaffoldState: ScaffoldState,
                 navController: NavHostController,
                 ruta: String,
                 etiqueta : String){
    Row(modifier= Modifier.fillMaxWidth())
    {
        Box( modifier= Modifier
            .fillMaxWidth()
            .padding(0.dp,0.dp,30.dp,0.dp)
            .clip(RoundedCornerShape(0.dp,20.dp,20.dp,0.dp))
            .background(MaterialTheme.colors.primary)
            .clickable {
                navController.navigate(ruta)
                scope.launch { scaffoldState.drawerState.close() }
            }){
            Text(text=etiqueta, color= Color.White, modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp))
        }

    }
}

@Composable
fun OpcionDrawerPrimariaB(scope: CoroutineScope,
                         scaffoldState: ScaffoldState,
                         navController: NavHostController,
                         ruta: String,
                         etiqueta : String){
    Row(modifier= Modifier
        .fillMaxWidth()
        .background(MaterialTheme.colors.primary)
        .padding(horizontal = 20.dp, vertical = 10.dp)
        .clickable {
            navController.navigate(ruta)
            scope.launch { scaffoldState.drawerState.close() }
        })
    {
        Text(text=etiqueta, color= Color.White)
    }
}

@Composable
fun OpcionDrawerSecundaria(scope: CoroutineScope,
                           scaffoldState: ScaffoldState,
                           navController: NavHostController,
                           ruta: String,
                           etiqueta : String){
    Row(modifier= Modifier.fillMaxWidth())
    {
        Box( modifier= Modifier
            .fillMaxWidth()
            .padding(0.dp,0.dp,50.dp,0.dp)
            .clip(RoundedCornerShape(0.dp,20.dp,20.dp,0.dp))
            .background(MoradoMuySuave)
            .clickable {
                navController.navigate(ruta)
                scope.launch { scaffoldState.drawerState.close() }
            }){
            Text(text=etiqueta, color= MaterialTheme.colors.primary, modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp))
        }
    }
}

@Composable
fun OpcionDrawerSecundariaB(scope: CoroutineScope,
                           scaffoldState: ScaffoldState,
                           navController: NavHostController,
                           ruta: String,
                           etiqueta : String){
    Row(modifier= Modifier
        .fillMaxWidth()
        .background(MoradoMuySuave)
        .padding(horizontal = 40.dp, vertical = 10.dp)
        .clickable {
            navController.navigate(ruta)
            scope.launch { scaffoldState.drawerState.close() }
        })
    {
        Text(text=etiqueta, color= MaterialTheme.colors.primary)
    }
}


