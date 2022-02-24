package com.example.masalog

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import kotlin.properties.ReadOnlyProperty

enum class EstadoDispositivo {
    SINHARDWARE,
    IMPRIMIENDO,
    DESCONECTADO,
    CONECTANDO,
    CONECTADO,
    LISTABT
}