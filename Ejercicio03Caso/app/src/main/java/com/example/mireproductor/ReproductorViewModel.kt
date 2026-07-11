package com.example.mireproductor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class ReproductorViewModel(
    private val estado: SavedStateHandle
) : ViewModel() {

    // Posición del video en milisegundos
    var posicionMs: Int
        get() = estado["pos"] ?: 0
        set(valor) { estado["pos"] = valor }

    // ¿Estaba reproduciéndose?
    var reproduciendo: Boolean
        get() = estado["playing"] ?: true
        set(valor) { estado["playing"] = valor }
}