package com.example.calculadoradepropina

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calculadoradepropina.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.calcular.setOnClickListener { calcular() }
        binding.limpiar.setOnClickListener { limpiar() }
    }

    private fun calcular() {
        val montoTotal = binding.montoT.text.toString().toDoubleOrNull() ?: 0.0
        val numTotal = binding.numPersonas.text.toString().toDoubleOrNull() ?: 0.0
        val otroPorcentaje = (binding.propPersonalizada.text.toString().toDoubleOrNull() ?: 0.0) / 100.0

        if (numTotal == 0.0) {
            binding.porPersona.text = "Número de personas no puede ser 0"
            return
        }
        if (montoTotal == null) {
            binding.total.text = "Ingrese un monto válido"
            return
        }

        val porcentajePropina = when {
            binding.num10.isChecked -> 0.10
            binding.num15.isChecked -> 0.15
            binding.num20.isChecked -> 0.20
            binding.otro.isChecked -> otroPorcentaje
            else -> 0.0
        }

        val iva = if (binding.iva.isChecked) montoTotal * 0.16 else 0.0
        val propina = montoTotal * porcentajePropina
        val totalFinal = montoTotal + propina + iva

        val propinaStr = String.format("%.2f", propina)
        val ivaStr = String.format("%.2f", iva)
        val totalStr = String.format("%.2f", totalFinal)

        binding.propina.text = "Propina: $$propinaStr | IVA: $$ivaStr"
        binding.total.text = "Total a pagar: $$totalStr"

        if (numTotal != null && numTotal > 0) {
            val porPersona = totalFinal / numTotal
            binding.porPersona.text = "Total por persona: $${String.format("%.2f", porPersona)}"
        } else if (numTotal == 0.0) {
            binding.porPersona.text = "Número de personas no puede ser 0"
        } else {
            binding.porPersona.text = ""
        }
    }

    private fun limpiar() {
        binding.montoT.text.clear()
        binding.numPersonas.text.clear()
        binding.propina.text = ""
        binding.total.text = ""
        binding.porPersona.text = ""
        binding.num10.isChecked = false
        binding.num15.isChecked = false
        binding.num20.isChecked = false
        binding.propPersonalizada.text.clear()
        binding.iva.isChecked = false
        binding.otro.isChecked = false
    }
}