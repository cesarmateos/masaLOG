package com.example.masalog

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BotonStandard(texto: String, onClick: () -> Unit){
    Button(onClick =  onClick,
        modifier = Modifier.fillMaxWidth()){
        Text(texto)
    }
}

