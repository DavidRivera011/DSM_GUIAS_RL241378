package com.example.mireproductor

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.mireproductor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val tag = "CicloDeVida"          // filtra por esto en Logcat
    private lateinit var binding: ActivityMainBinding
    private val vm: ReproductorViewModel by viewModels()

    // Video de ejemplo público (Big Buck Bunny)
    private val videoUrl = "https://archive.org/download/BigBuckBunny_124/Content/big_buck_bunny_720p_surround.mp4"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(tag, "onCreate  -> creo la UI (aún NO reproduzco)")

        binding.video.setVideoPath(videoUrl)

        // Cuando el video está listo, restauramos la posición guardada
        binding.video.setOnPreparedListener { mp ->
            mp.isLooping = true
            binding.video.seekTo(vm.posicionMs)          // <- restaura tras rotar / process death
            if (vm.reproduciendo) binding.video.start()
            actualizarEstado("listo")
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(tag, "onStart   -> visible (sin foco): preparo recursos")
    }

    override fun onResume() {
        super.onResume()
        Log.d(tag, "onResume  -> foco total: reanudo reproducción")
        if (vm.reproduciendo) binding.video.start()
        actualizarEstado("reproduciendo")
    }

    override fun onPause() {
        super.onPause()
        // ⚡ Ultra rápido: guardar y pausar, sin I/O pesada
        vm.posicionMs = binding.video.currentPosition
        vm.reproduciendo = binding.video.isPlaying
        binding.video.pause()
        Log.d(tag, "onPause   -> pierdo foco: PAUSO y guardo pos=${vm.posicionMs}ms")
        actualizarEstado("en pausa")
    }

    override fun onStop() {
        super.onStop()
        Log.d(tag, "onStop    -> ya NO visible: liberaría recursos pesados")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(tag, "onRestart -> vuelvo a la pantalla (solo al regresar)")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tag, "onDestroy -> fin de la instancia: limpio el reproductor")
        binding.video.stopPlayback()
    }

    private fun actualizarEstado(texto: String) {
        binding.estado.text = "Estado: $texto  ·  ${vm.posicionMs / 1000}s"
    }
}