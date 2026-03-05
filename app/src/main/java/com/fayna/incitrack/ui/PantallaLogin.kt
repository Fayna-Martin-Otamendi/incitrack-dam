package com.fayna.incitrack.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fayna.incitrack.R
import com.fayna.incitrack.dao.UsuarioDAO
import com.fayna.incitrack.db.DatabaseHelper

class PantallaLogin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_login)

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnEntrar = findViewById<Button>(R.id.btnEntrar)

        btnEntrar.setOnClickListener {

            val email = etEmail.text.toString().trim()
            val pass = etPassword.text.toString().trim()

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Rellena email y contraseña", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val dbHelper = DatabaseHelper(this)
            val usuarioDAO = UsuarioDAO(dbHelper)

            val usuario = usuarioDAO.getUsuarioByEmail(email)

            if (usuario == null) {
                Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (usuario.getPassword() != pass) {
                Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val modoAdmin = usuario.getRol() == "ADMIN"

            if (modoAdmin) {
                val intent = Intent(this, PantallaPanelAdmin::class.java)
                intent.putExtra("idUsuario", usuario.getIdUsuario())
                intent.putExtra("modoAdmin", true)
                startActivity(intent)
            } else {
                val intent = Intent(this, PantallaPanelVecino::class.java)
                intent.putExtra("idUsuario", usuario.getIdUsuario())
                intent.putExtra("modoAdmin", false)
                startActivity(intent)
            }

            finish()
        }
    }
}