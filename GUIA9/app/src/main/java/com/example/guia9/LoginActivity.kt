package com.example.guia9

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.guia9.model.Usuario

class LoginActivity : AppCompatActivity() {

    private lateinit var txtNick: EditText
    private lateinit var txtPassword: EditText
    private lateinit var btnIniciarSesion: Button
    private lateinit var btnRegistrarse: Button

    private lateinit var usuario: Usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        txtNick = findViewById(R.id.txtNick)
        txtPassword = findViewById(R.id.txtPassword)
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion)
        btnRegistrarse = findViewById(R.id.btnRegistrarse)

        usuario = Usuario(this)

        btnIniciarSesion.setOnClickListener {
            iniciarSesion()
        }

        btnRegistrarse.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
    }

    private fun iniciarSesion() {

        val nick = txtNick.text.toString().trim()
        val password = txtPassword.text.toString().trim()

        if (nick.isEmpty()) {
            txtNick.error = "Ingrese su Nick"
            txtNick.requestFocus()
            return
        }

        if (password.isEmpty()) {
            txtPassword.error = "Ingrese su contraseña"
            txtPassword.requestFocus()
            return
        }

        val cursor = usuario.validarUsuario(nick, password)

        if (cursor != null && cursor.moveToFirst()) {

            cursor.close()

            Toast.makeText(
                this,
                "Inicio de sesión exitoso",
                Toast.LENGTH_SHORT
            ).show()

            val intent = Intent(this, MainActivity::class.java)

            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)

        } else {

            cursor?.close()

            Toast.makeText(
                this,
                "Nick o contraseña incorrectos",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}