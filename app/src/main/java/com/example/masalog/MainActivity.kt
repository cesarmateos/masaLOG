package com.example.masalog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.masalog.ui.theme.MasaLOGTheme
import com.google.gson.Gson


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
                onClickImpresoras = { navController.navigate(Pantallas.Impresoras.name) },
                onClickControladorInput = { navController.navigate(Pantallas.ControladorInput.name) },
            )
        }

        composable(Pantallas.Impresoras.name) {
            PantallaConfiguraEtiqueta()
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