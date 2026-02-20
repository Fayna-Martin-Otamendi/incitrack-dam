package com.fayna.incitrack.ui


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.fayna.incitrack.R

class PantallaPanelVecino : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_panel_vecino)

        val btnVerListado = findViewById<Button>(R.id.btnVerListado)
        val btnMisIncidencias = findViewById<Button>(R.id.btnMisIncidencias)
        val btnVerTablon = findViewById<Button>(R.id.btnVerTablon)
        val btnCrearIncidencia = findViewById<Button>(R.id.btnCrearIncidencia)

        btnVerListado.setOnClickListener {
            val intent = Intent(this, PantallaListado::class.java)
            startActivity(intent)
        }

        btnMisIncidencias.setOnClickListener {
            val intent = Intent(this, PantallaMisIncidencias::class.java)
            startActivity(intent)
        }

        btnVerTablon.setOnClickListener {
            val intent = Intent(this, PantallaTablon::class.java)
            startActivity(intent)
        }

        btnCrearIncidencia.setOnClickListener {
            val intent = Intent(this, PantallaCrearIncidencia::class.java)
            startActivity(intent)
        }


    }
}