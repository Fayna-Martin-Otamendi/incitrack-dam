package com.fayna.incitrack.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.fayna.incitrack.R

class PantallaMisIncidencias : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_mis_incidencias)

        val btnVerDetalle = findViewById<Button>(R.id.btnVerDetalle)
        btnVerDetalle.setOnClickListener {
            startActivity(Intent(this, PantallaDetalleIncidencia::class.java))
        }
    }
}