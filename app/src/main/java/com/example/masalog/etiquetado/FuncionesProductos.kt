package com.example.masalog.etiquetado

import com.example.masalog.controlado.ProductoControl


fun devolverListaConMatchCodigoBarras(codigo: String, lista: MutableList<ProductoControl>): MutableList<ProductoControl> {
    val productosEncontrados = mutableListOf<ProductoControl>()
    val iterador = lista.listIterator()
    while (iterador.hasNext()) {
        val producto = iterador.next()
        if (producto.codigoBarras == codigo) {
            productosEncontrados.add(producto)
        }
    }
    return productosEncontrados
}