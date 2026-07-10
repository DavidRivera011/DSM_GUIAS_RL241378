package com.example.practica03dsm104_02

import android.app.Activity
import android.graphics.Color
import android.widget.TextView
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
class LifeCycleObserver (
    private val activity: Activity, // Referencia a la actividad para cambiar el fondo
    private val txtAbierto: TextView, // TextView para mostrar veces abierto
    private val txtSuspendido: TextView, // TextView para mostrar veces suspendido
    private val txtCerrado: TextView, // TextView para mostrar veces cerrado
    private val txtEstado: TextView // TextView para mostrar el estado actual
) : DefaultLifecycleObserver {

    // Contadores para cada evento del ciclo de vida
    private var contadorAbierto = 0
    private var contadorSuspendido = 0
    private var contadorCerrado = 0

    /**
     * Se llama cuando la actividad entra en estado Started (visible para
    el usuario).
     */
    override fun onStart(owner: LifecycleOwner) {
        contadorAbierto++ // Incrementa contador de veces abierto
        txtEstado.text = "Estado actual: onStart" // Actualiza texto de estado
        activity.window.decorView.setBackgroundColor(Color.GREEN) // Cambia fondo a verde
        actualizarPantalla() // Actualiza los TextViews con los contadores
    }

    /**
     * Se llama cuando la actividad entra en estado Paused (parcialmente
    visible, por ejemplo, al abrir otra app encima).
     */
    override fun onPause(owner: LifecycleOwner) {
        contadorSuspendido++ // Incrementa contador de veces suspendido
        txtEstado.text = "Estado actual: onPause" // Actualiza texto de estado
        activity.window.decorView.setBackgroundColor(Color.YELLOW) // Cambia fondo a amarillo
        actualizarPantalla() // Actualiza los TextViews con los contadores
    }

    /**
     * Se llama cuando la actividad es destruida (cerrada).
     */
    override fun onDestroy(owner: LifecycleOwner) {
        contadorCerrado++ // Incrementa contador de veces cerrado
        txtEstado.text = "Estado actual: onDestroy" // Actualiza texto de estado
        activity.window.decorView.setBackgroundColor(Color.RED) // Cambia fondo a rojo actualizarPantalla() // Actualiza los TextViews con los contadores
    }

    /**
     * Actualiza los TextViews con los valores actuales de los contadores.

     */
    private fun actualizarPantalla() {
        txtAbierto.text = "Veces abierto: $contadorAbierto"
        txtSuspendido.text = "Veces suspendido: $contadorSuspendido"
        txtCerrado.text = "Veces cerrado: $contadorCerrado"
    }

}

