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
    EtiquetadoPlantaInicio,
    EtiquetadoPlantaEscaneado;

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
                EtiquetadoPlantaInicio.name -> EtiquetadoPlantaInicio
                EtiquetadoPlantaEscaneado.name -> EtiquetadoPlantaEscaneado
                null -> Inicio
                else -> throw IllegalArgumentException("La ruta $route no es reconocida.")
            }
    }
}