package com.example.masalog

enum class Pantallas {
    Inicio,
    Impresoras,
    ControladorInput,
    ControladorProductos,
    ControladorIngreso;

    companion object {
        fun fromRoute(route: String?): Pantallas =
            when (route?.substringBefore("/")) {
                Inicio.name -> Inicio
                Impresoras.name -> Impresoras
                ControladorInput.name -> ControladorInput
                ControladorProductos.name -> ControladorProductos
                ControladorIngreso.name -> ControladorIngreso
                null -> Inicio
                else -> throw IllegalArgumentException("La ruta $route no es reconocida.")
            }
    }
}