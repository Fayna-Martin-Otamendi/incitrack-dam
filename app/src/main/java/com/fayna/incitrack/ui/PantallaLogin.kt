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


// Pantalla de login de la aplicación.
// Aquí se comprueba el usuario y se decide si entra al panel admin o al panel vecino.

class PantallaLogin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_login)

        // Referencias a los elementos del layout
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnEntrar = findViewById<Button>(R.id.btnEntrar)

        btnEntrar.setOnClickListener {

            val email = etEmail.text.toString().trim()
            val pass = etPassword.text.toString().trim()

            // Se comprueba que los campos no estén vacíos
            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Rellena email y contraseña", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Se busca el usuario en la base de datos por su email
            val dbHelper = DatabaseHelper(this)
            val usuarioDAO = UsuarioDAO(dbHelper)

            val usuario = usuarioDAO.getUsuarioByEmail(email)

            if (usuario == null) {
                Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Se comprueba si la contraseña coincide
            if (usuario.getPassword() != pass) {
                Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Según el rol del usuario se abre el panel correspondiente
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