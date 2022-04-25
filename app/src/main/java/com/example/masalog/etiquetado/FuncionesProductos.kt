package com.example.masalog.etiquetado

import com.example.masalog.controlado.ProductoComplejo

fun buscarProductoEnLista(codigo: Long, lote: String, lista: MutableList<ProductoComplejo>): ProductoComplejo? {
    val iterador = lista.listIterator()

    while (iterador.hasNext() ) {
        val producto = iterador.next()
        if (producto.codigoBarras == codigo && producto.lote== lote) {
            return producto
        }
    }
    return null
}

fun devolverListaConMatchCodigoBarras(codigo: Long, lista: MutableList<ProductoComplejo>): MutableList<ProductoComplejo> {
    val productosEncontrados = mutableListOf<ProductoComplejo>()
    val iterador = lista.listIterator()
    while (iterador.hasNext()) {
        val producto = iterador.next()
        if (producto.codigoBarras == codigo) {
            productosEncontrados.add(producto)
        }
    }
    return productosEncontrados
}