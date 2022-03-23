package com.example.masalog

class ListadoEtiquetado() {
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

    fun cargarProductos(stream:String){

        var lineas = stream.lines()

        try {
            lineas.forEach {
                var lista = it.split(";")
                if (lista.isNotEmpty()){
                    val producto = Producto(lista[0].toLong(),lista[1],lista[2])
                    productos.add(producto)
                }
            }
        }catch(exception: NumberFormatException){
        }
    }

}