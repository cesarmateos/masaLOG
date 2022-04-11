package com.example.masalog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun Drawer(scope: CoroutineScope, scaffoldState: ScaffoldState, navController: NavHostController) {

    Row(modifier= Modifier.fillMaxWidth().clickable { navController.navigate(Pantallas.Impresoras.name)
                                                        scope.launch{ scaffoldState.drawerState.close()} })
    {
        Text(text="Configurar Impresora")
    }
}


