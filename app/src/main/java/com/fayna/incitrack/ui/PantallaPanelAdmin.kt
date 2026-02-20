package com.fayna.incitrack.ui

import android.widget.Button
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fayna.incitrack.R

class PantallaPanelAdmin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_panel_admin)

        val btnVerListadoAdmin = findViewById<Button>(R.id.btnVerListadoAdmin)
        val btnGestionTablon = findViewById<Button>(R.id.btnGestionTablon)
        val btnCrearIncidencia = findViewById<Button>(R.id.btnCrearIncidencia)

        btnVerListadoAdmin.setOnClickListener {
            startActivity(Intent(this, PantallaListado::class.java))
        }

        btnGestionTablon.setOnClickListener {
            startActivity(Intent(this, PantallaGestionTablon::class.java))
        }

        btnCrearIncidencia.setOnClickListener {
            val intent = Intent(this, PantallaCrearIncidencia::class.java)
            startActivity(intent)
        }
    }
}