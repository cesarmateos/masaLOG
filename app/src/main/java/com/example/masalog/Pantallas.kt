package com.example.masalog

enum class Pantallas {
    Inicio,
    Etiquetas,
    Impresoras,
    ConfiguraSATO,
    ConfiguraRP4,
    ControladorProductos,
    ControladorIngreso,
    ControladorIngresoLote,
    ControladorInicio,
    EtiquetadoPlantaInicio,
    EtiquetaHeladeras,
    Configuracion;

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
                ControladorIngresoLote.name -> ControladorIngresoLote
                ControladorInicio.name -> ControladorInicio
                EtiquetadoPlantaInicio.name -> EtiquetadoPlantaInicio
                EtiquetaHeladeras.name ->EtiquetaHeladeras
                Configuracion.name ->Configuracion
                null -> Inicio
                else -> throw IllegalArgumentException("La ruta $route no es reconocida.")
            }
    }
}