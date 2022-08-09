package com.example.masalog

enum class Pantallas {
    Inicio,
    Etiquetas,
    Impresoras,
    ConfiguraSATO,
    ConfiguraRP4,
    ControladorProductos,
    ControladorIngreso,
    EtiquetadoPlantaInicio,
    EtiquetaHeladeras;

    companion object {
        fun fromRoute(route: String?): Pantallas =
            when (route?.substringBefore("/")) {
                Inicio.name -> Inicio
                Etiquetas.name -> Etiquetas
                Impresoras.name -> Impresoras
                ConfiguraSATO.name -> ConfiguraSATO
                ConfiguraRP4.name -> ConfiguraRP4
                ControladorProductos.name -> ControladorProductos
                ControladorIngreso.name -> ControladorIngreso
                EtiquetadoPlantaInicio.name -> EtiquetadoPlantaInicio
                EtiquetaHeladeras.name ->EtiquetaHeladeras
                null -> Inicio
                else -> throw IllegalArgumentException("La ruta $route no es reconocida.")
            }
    }
}