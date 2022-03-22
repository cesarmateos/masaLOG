package com.example.masalog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.masalog.ui.theme.MasaLOGTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        BTHandler.iniciar(this)

        setContent {
            MasaLOGTheme {
                Surface(color = MaterialTheme.colors.background) {
                    MasaLOG()
                }
            }
        }
    }
}

@Composable
fun MasaLOG(){
        val navController = rememberNavController()
        MasaNavHost(navController)
}

@Composable
fun MasaNavHost(navController: NavHostController,modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Pantallas.Inicio.name,
        modifier = modifier
    ) {
        composable(Pantallas.Inicio.name) {
            PantallaInicio(
                onClickEtiquetas = { navController.navigate(Pantallas.Etiquetas.name) },
                onClickControladorInput = { navController.navigate(Pantallas.ControladorInput.name) },
                onClickImpresoras = {navController.navigate(Pantallas.Impresoras.name)},
                onClickEtiquetado = {navController.navigate(Pantallas.EtiquetadoPlanta.name)}
            )
        }

        composable(Pantallas.Etiquetas.name) {
            PantallaConfiguraEtiqueta()
        }

        composable(Pantallas.Impresoras.name) {
            PantallaConfiguraImpresora(
                onClickConfiguraRP4 = {navController.navigate(Pantallas.ConfiguraRP4.name)},
                onClickConfiguraSATO = {navController.navigate(Pantallas.ConfiguraSATO.name)}
            )
        }

        composable(Pantallas.ConfiguraRP4.name) {
            PantallaConfiguraImpresoraRP4()
        }

        composable(Pantallas.ConfiguraSATO.name) {
            PantallaConfiguraImpresoraSATO()
        }

        composable(Pantallas.ControladorInput.name){
            PantallaControlaListadoInput(
                //onClickControladorProductos = {control -> navController.navigate(Pantallas.ControladorProductos.name+"/{control}")}
                onClickControladorProductos = {navController.navigate(Pantallas.ControladorProductos.name)}
            )
        }

         composable(Pantallas.ControladorProductos.name){
             PantallaControlaListadoProductos(
                 onClickControladorIngreso = {navController.navigate(Pantallas.ControladorIngreso.name)},
             )
         }

        composable(Pantallas.ControladorIngreso.name){
            PantallaControlaListadoIngresoProducto(
                onClickControladorProductos = {navController.navigate(Pantallas.ControladorProductos.name)}
            )
        }

        composable(Pantallas.EtiquetadoPlanta.name) {
            PantallaEtiquetado_Seleccion()
        }
        /*
composable(route= Pantallas.ControladorProductos.name+ "/{control}",
arguments = listOf(navArgument("control"){type = NavType.StringType})) {
    backStackEntry ->
    backStackEntry?.arguments?.getString("control")?.let{ json->
        val control = Gson().fromJson(json, ControlProductos::class.java)
        PantallaControlaListadoProductos(controlProductos = control)
    }
}
*/
    }
}

@Preview
@Composable
fun default(){
    MasaLOG()
}