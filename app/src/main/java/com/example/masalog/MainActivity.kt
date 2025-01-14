package com.example.masalog

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.masalog.configuraEtiqueta.PantallaConfiguraEtiqueta
import com.example.masalog.configuraImpresora.PantallaConfiguraImpresora
import com.example.masalog.configuraImpresora.PantallaConfiguraImpresoraRP4
import com.example.masalog.configuraImpresora.PantallaConfiguraImpresoraSATO
import com.example.masalog.controlado.PantallaControlaListado_IngresoProductoConLote
import com.example.masalog.configuracion.PantallaConfiguracion
import com.example.masalog.controlado.PantallaControlaListado_IngresoProducto
import com.example.masalog.controlado.PantallaControlaListado
import com.example.masalog.controlado.PantallaControlaListado_Listado
import com.example.masalog.codigoBarras.PantallaGenerarCodigoBarras
import com.example.masalog.etiquetaRefrigerados.PantallaEtiquetaHeladeras
import com.example.masalog.etiquetado.PantallaEtiquetadoInicio
import com.example.masalog.ui.theme.GrisPurple
import com.example.masalog.ui.theme.MasaLOGTheme


class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        BTHandler.iniciar(this)

        setContent {
            MasaLOGTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
                    val scope = rememberCoroutineScope()
                    val navController = rememberNavController()

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
    //val navController = rememberNavController()
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
            PantallaConfiguraImpresoraRP4(navController)
        }

        composable(Pantallas.ConfiguraSATO.name) {
            PantallaConfiguraImpresoraSATO(navController)
        }

        composable(Pantallas.EtiquetadoPlantaInicio.name) {
            PantallaEtiquetadoInicio(navController)
        }

        composable(Pantallas.EtiquetaHeladeras.name) {
            PantallaEtiquetaHeladeras()
        }

        composable(Pantallas.ControladorInicio.name){
            PantallaControlaListado(navController)
        }

         composable(Pantallas.ControladorProductos.name){
             PantallaControlaListado_Listado(navController)
         }

        composable(Pantallas.ControladorIngreso.name){
            PantallaControlaListado_IngresoProducto(navController)
        }

        composable(Pantallas.ControladorIngresoLote.name){
            PantallaControlaListado_IngresoProductoConLote(navController)
        }

        composable(Pantallas.GenerarCodigoBarras.name){
            PantallaGenerarCodigoBarras()
        }

        composable(Pantallas.Configuracion.name){
            PantallaConfiguracion()
        }

    }
}
