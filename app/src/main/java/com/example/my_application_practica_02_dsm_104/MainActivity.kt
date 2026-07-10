package com.example.my_application_practica_02_dsm_104

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var numero1: EditText
    private lateinit var numero2: EditText
    private lateinit var enviar: Button
    private lateinit var resultado: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numero1 = findViewById(R.id.txtNumero1)
        numero2 = findViewById(R.id.txtNumero2)
        enviar = findViewById(R.id.btnEnviar)
        resultado = findViewById(R.id.txtResultado)

        enviar.setOnClickListener {

            val texto1 = numero1.text.toString()
            val texto2 = numero2.text.toString()

            if (texto1.isEmpty() || texto2.isEmpty()) {
                Toast.makeText(this, "Ingrese ambos números", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val n1: Float = texto1.toFloat()
            val n2: Float = texto2.toFloat()

            val suma = n1 + n2

            resultado.text = "Resultado: $suma"
        }
    }
}