package com.example.masalog

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Checkbox
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.masalog.ui.theme.GrisOscuro
import com.example.masalog.ui.theme.NaranjaMuySuave

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PantallaEtiquetado_Escaneado() {
    Scaffold(
        topBar = {barraTOP()},
        content = {
            var ingresoBarras by remember { mutableStateOf("") }

            Column(modifier= Modifier.fillMaxWidth()){
                Row(
                    Modifier
                        .height(40.dp)
                        .background(NaranjaMuySuave)
                        .padding(horizontal = 10.dp, vertical = 3.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Producto:")
                    Spacer(modifier= Modifier.size(5.dp))

                    Box(
                        modifier = Modifier
                            .border(2.dp, GrisOscuro, RoundedCornerShape(10))
                            .background(color = Color.White)
                            .size(140.dp, 30.dp)
                            .padding(horizontal = 5.dp)
                            .focusTarget(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        BasicTextField(value = ingresoBarras,
                            onValueChange = { ingresoBarras = it },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            keyboardActions = KeyboardActions(onDone = {
                                ListadoEtiquetado.imprimeLocalizador(ingresoBarras)
                            }),
                            enabled = true,
                            singleLine = true,
                            modifier = Modifier
                                .onKeyEvent {
                                    if (it.key.keyCode == Key.Enter.keyCode) {
                                        ListadoEtiquetado.imprimeLocalizador(ingresoBarras.dropLast(1))
                                    }
                                    false
                                }
                        )
                    }
                }
            }
        }
    )
}