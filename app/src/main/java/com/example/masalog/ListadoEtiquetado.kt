package com.example.masalog

object ListadoEtiquetado {
    val productos = mutableListOf<Producto>()

    fun lectura(codigoBarrasString: String): Producto? {
        val iterador = productos.listIterator()

        while (iterador.hasNext()) {
            val producto = iterador.next()
            if (producto.matchCodigoBarras(codigoBarrasString)) {
                return producto
            }
        }

        return null
    }

    fun imprimeLocalizador(codigoBarrasString: String){
        val producto = lectura(codigoBarrasString)
        if(producto !=null){
            BTHandler.imprimir("")
        }else{
            BTHandler.imprimir("")
        }
    }

    fun cargarProductos(stream:String) {

        var lineas = stream.lines()

        try {
            lineas.forEach {
                var lista = it.split(";")
                if (lista.isNotEmpty()) {
                    val producto = Producto(lista[0].toLong(), lista[1], lista[2])
                    productos.add(producto)
                }
            }
        } catch (exception: NumberFormatException) {
        }
    }

}