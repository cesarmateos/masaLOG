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
import com.example.masalog.configuraImpresora.PantallaControlaListado_IngresoProductoConLote
import com.example.masalog.configuracion.PantallaConfiguracion
import com.example.masalog.controlado.PantallaControlaListado_IngresoProducto
import com.example.masalog.controlado.PantallaControlaListado
import com.example.masalog.controlado.PantallaControlaListado_Listado
import com.example.masalog.etiquetaRefrigerados.PantallaEtiquetaHeladeras
import com.example.masalog.etiquetado.PantallaEtiquetadoInicio
import com.example.masalog.ui.theme.GrisPurple
import com.example.masalog.ui.theme.MasaLOGTheme


class MainActivity() : ComponentActivity() {

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

/*    private val barcode = StringBuffer()

    @SuppressLint("RestrictedApi")
    override fun dispatchKeyEvent(event: android.view.KeyEvent?): Boolean {

        if (event?.action == 0) {
            val pressedKey = event?.unicodeChar?.toChar()
            barcode.append(pressedKey)
        }
        if (event?.action == 0 && event?.keyCode == 66) {
            Toast.makeText(baseContext, barcode.toString(), Toast.LENGTH_SHORT).show()
            barcode.delete(0, barcode.length)
        }

        return super.dispatchKeyEvent(event)
    }*/


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

        composable(Pantallas.Configuracion.name){
            PantallaConfiguracion()
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
