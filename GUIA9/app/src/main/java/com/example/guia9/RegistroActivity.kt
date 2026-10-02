package com.example.guia9

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.guia9.model.Usuario

class RegistroActivity : AppCompatActivity() {

    private lateinit var txtNickRegistro: EditText
    private lateinit var txtPasswordRegistro: EditText
    private lateinit var txtConfirmarPassword: EditText
    private lateinit var btnRegistrar: Button
    private lateinit var btnCancelar: Button

    private lateinit var usuario: Usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_registro)

        txtNickRegistro = findViewById(R.id.txtNickRegistro)
        txtPasswordRegistro = findViewById(R.id.txtPasswordRegistro)
        txtConfirmarPassword = findViewById(R.id.txtConfirmarPassword)
        btnRegistrar = findViewById(R.id.btnRegistrar)
        btnCancelar = findViewById(R.id.btnCancelar)

        usuario = Usuario(this)

        btnRegistrar.setOnClickListener {
            registrarUsuario()
        }

        btnCancelar.setOnClickListener {
            finish()
        }
    }

    private fun registrarUsuario() {

        val nick = txtNickRegistro.text.toString().trim()
        val password = txtPasswordRegistro.text.toString().trim()
        val confirmarPassword =
            txtConfirmarPassword.text.toString().trim()

        if (nick.isEmpty()) {
            txtNickRegistro.error = "Ingrese un Nick"
            txtNickRegistro.requestFocus()
            return
        }

        if (password.isEmpty()) {
            txtPasswordRegistro.error = "Ingrese una contraseña"
            txtPasswordRegistro.requestFocus()
            return
        }

        if (confirmarPassword.isEmpty()) {
            txtConfirmarPassword.error =
                "Confirme la contraseña"
            txtConfirmarPassword.requestFocus()
            return
        }

        if (password != confirmarPassword) {
            txtConfirmarPassword.error =
                "Las contraseñas no coinciden"
            txtConfirmarPassword.requestFocus()
            return
        }

        if (usuario.existeNick(nick)) {
            txtNickRegistro.error =
                "Este Nick ya está registrado"
            txtNickRegistro.requestFocus()
            return
        }

        val resultado = usuario.registrarUsuario(
            nick,
            password
        )

        if (resultado != -1L) {

            Toast.makeText(
                this,
                "Usuario registrado correctamente",
                Toast.LENGTH_SHORT
            ).show()

            finish()

        } else {

            Toast.makeText(
                this,
                "No se pudo registrar el usuario",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}