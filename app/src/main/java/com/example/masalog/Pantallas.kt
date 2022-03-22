package com.example.masalog

enum class Pantallas {
    Inicio,
    Etiquetas,
    Impresoras,
    ConfiguraSATO,
    ConfiguraRP4,
    ControladorInput,
    ControladorProductos,
    ControladorIngreso,
    EtiquetadoPlanta;

    companion object {
        fun fromRoute(route: String?): Pantallas =
            when (route?.substringBefore("/")) {
                Inicio.name -> Inicio
                Etiquetas.name -> Etiquetas
                Impresoras.name -> Impresoras
                ConfiguraSATO.name -> ConfiguraSATO
                ConfiguraRP4.name -> ConfiguraRP4
                ControladorInput.name -> ControladorInput
                ControladorProductos.name -> ControladorProductos
                ControladorIngreso.name -> ControladorIngreso
                EtiquetadoPlanta.name -> EtiquetadoPlanta
                null -> Inicio
                else -> throw IllegalArgumentException("La ruta $route no es reconocida.")
            }
    }
}