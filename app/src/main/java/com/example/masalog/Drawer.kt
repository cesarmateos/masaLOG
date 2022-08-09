package com.example.masalog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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

    Column(modifier= Modifier.fillMaxWidth()){

        //Encabezado
        Row(modifier= Modifier.background(MaterialTheme.colors.background).padding(start= 10.dp, end= 40.dp, top= 20.dp, bottom = 30.dp)){
            Image(painter= painterResource(id = R.drawable.logo_grande), contentDescription = "Masa")
        }

        Column{
            Row(modifier= Modifier
                .fillMaxWidth()
                .weight(1.0f)){

                Column{
                    //Configura Impresora
                    Spacer(Modifier.size(30.dp))
                    Row(modifier= Modifier.fillMaxWidth()
                        .background(MaterialTheme.colors.primary)
                        .padding(horizontal = 20.dp, vertical= 10.dp)
                        .clickable{ navController.navigate(Pantallas.Impresoras.name)
                            scope.launch{ scaffoldState.drawerState.close()} })
                    {
                        Text(text=stringResource(R.string.config_impresora), color= Color.White)
                    }
                    Row(modifier= Modifier.fillMaxWidth()
                        .background(MoradoMuySuave)
                        .padding(horizontal = 40.dp, vertical= 10.dp)
                        .clickable{ navController.navigate(Pantallas.ConfiguraSATO.name)
                            scope.launch{ scaffoldState.drawerState.close()} })
                    {
                        Text(text=stringResource(R.string.config_impresora_CL4NX), color= MaterialTheme.colors.primary)
                    }
                    Row(modifier= Modifier.fillMaxWidth()
                        .background(MoradoMuySuave)
                        .padding(horizontal = 40.dp, vertical= 10.dp)
                        .clickable{ navController.navigate(Pantallas.ConfiguraRP4.name)
                            scope.launch{ scaffoldState.drawerState.close()} })
                    {
                        Text(text=stringResource(R.string.config_impresora_RP4), color= MaterialTheme.colors.primary)
                    }


                    //Configura Etiqueta
                    Spacer(Modifier.size(espacioEntreBotones))
                    Row(modifier= Modifier.fillMaxWidth()
                        .background(MaterialTheme.colors.primary)
                        .padding(horizontal = 20.dp, vertical= 10.dp)
                        .clickable{ navController.navigate(Pantallas.Etiquetas.name)
                            scope.launch{ scaffoldState.drawerState.close()} })
                    {
                        Text(text=stringResource(R.string.config_etiquetas), color= Color.White)
                    }

                    //EtiquetaHeladera
                    Spacer(Modifier.size(espacioEntreBotones))
                    Row(modifier= Modifier.fillMaxWidth()
                        .background(MaterialTheme.colors.primary)
                        .padding(horizontal = 20.dp, vertical= 10.dp)
                        .clickable{ navController.navigate(Pantallas.EtiquetaHeladeras.name)
                            scope.launch{ scaffoldState.drawerState.close()} })
                    {
                        Text(text=stringResource(R.string.etiqueta_heladeras), color= Color.White)
                    }

                    //Etiquetado
                    Spacer(Modifier.size(espacioEntreBotones))
                    Row(modifier= Modifier.fillMaxWidth()
                        .background(MaterialTheme.colors.primary)
                        .padding(horizontal = 20.dp, vertical= 10.dp)
                        .clickable{ navController.navigate(Pantallas.EtiquetadoPlantaInicio.name)
                            scope.launch{ scaffoldState.drawerState.close()} })
                    {
                        Text(text=stringResource(R.string.etiquetado_archivo), color= Color.White)
                    }

                    //Control
                    Spacer(Modifier.size(espacioEntreBotones))
                    Row(modifier= Modifier.fillMaxWidth()
                        .background(MaterialTheme.colors.primary)
                        .padding(horizontal = 20.dp, vertical= 10.dp)
                        .clickable{ navController.navigate(Pantallas.ControladorProductos.name)
                            scope.launch{ scaffoldState.drawerState.close()} })
                    {
                        Text(text=stringResource(R.string.control_archivo), color= Color.White)
                    }
                }

            }
            Row(modifier=Modifier.fillMaxWidth().padding(PADDING_HORIZONTAL)) {
                Text(text= stringResource(R.string.app_name_version),
                    textAlign = TextAlign.End,
                    modifier= Modifier.fillMaxWidth())
            }
        }
    }
}


