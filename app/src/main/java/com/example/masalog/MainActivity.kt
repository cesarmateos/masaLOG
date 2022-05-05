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
import com.example.masalog.configuraEtiqueta.PantallaConfiguraEtiqueta
import com.example.masalog.configuraImpresora.PantallaConfiguraImpresora
import com.example.masalog.configuraImpresora.PantallaConfiguraImpresoraRP4
import com.example.masalog.configuraImpresora.PantallaConfiguraImpresoraSATO
import com.example.masalog.controlado.ControlProductos
import com.example.masalog.controlado.PantallaControlaListadoIngresoProducto
import com.example.masalog.controlado.PantallaControlaListado
import com.example.masalog.etiquetado.PantallaEtiquetadoInicio
import com.example.masalog.ui.theme.GrisPurple
import com.example.masalog.ui.theme.MasaLOGTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        BTHandler.iniciar(this)

        setContent {
            MasaLOGTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
                    val scope = rememberCoroutineScope()
                    val navController = rememberNavController()
                    ControlProductos.asignarNavController(navController)
                    Scaffold(
                        scaffoldState = scaffoldState,
                        topBar = { BarraTOP(scope = scope, scaffoldState = scaffoldState)},
                        drawerBackgroundColor = GrisPurple,
                        drawerContent = {
                            Drawer(scope = scope, scaffoldState = scaffoldState, navController = navController)
                       },
                    ) {
                        Navegador(navController = navController)
                    }
                }
            }
        }
    }
}


@Composable
fun Navegador(navController: NavHostController,modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Pantallas.Inicio.name,
        modifier = modifier
    ) {
        composable(Pantallas.Inicio.name) {
            PantallaInicio(navController)
        }

        composable(Pantallas.Etiquetas.name) {
            PantallaConfiguraEtiqueta()
        }

        composable(Pantallas.Impresoras.name) {
            PantallaConfiguraImpresora(navController)
        }

        composable(Pantallas.ConfiguraRP4.name) {
            PantallaConfiguraImpresoraRP4()
        }

        composable(Pantallas.ConfiguraSATO.name) {
            PantallaConfiguraImpresoraSATO()
        }

        composable(Pantallas.EtiquetadoPlantaInicio.name) {
            PantallaEtiquetadoInicio(navController)
        }

         composable(Pantallas.ControladorProductos.name){
             PantallaControlaListado(navController)
         }

        composable(Pantallas.ControladorIngreso.name){
            PantallaControlaListadoIngresoProducto(
                onClickControladorProductos = {navController.navigate(Pantallas.ControladorProductos.name)}
            )
        }

    }
}

@Preview(name = "Inicio")
@Composable
fun InicioDark(){
    MasaLOGTheme(darkTheme=  true) {
        PantallaInicio(navController = rememberNavController())
    }
}
