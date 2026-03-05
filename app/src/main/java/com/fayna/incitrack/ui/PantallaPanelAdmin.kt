package com.fayna.incitrack.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.fayna.incitrack.R

class PantallaPanelAdmin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_panel_admin)

        val idUsuario = intent.getIntExtra("idUsuario", -1)

        val btnVerListadoAdmin = findViewById<Button>(R.id.btnVerListadoAdmin)
        val btnGestionTablon = findViewById<Button>(R.id.btnGestionTablon)
        val btnCrearIncidencia = findViewById<Button>(R.id.btnCrearIncidencia)

        btnVerListadoAdmin.setOnClickListener(object : android.view.View.OnClickListener {
            override fun onClick(v: android.view.View?) {
                val intent = Intent(this@PantallaPanelAdmin, PantallaListado::class.java)
                intent.putExtra("modoAdmin", true)
                intent.putExtra("idUsuario", idUsuario)
                startActivity(intent)
            }
        })

        btnGestionTablon.setOnClickListener(object : android.view.View.OnClickListener {
            override fun onClick(v: android.view.View?) {
                val intent = Intent(this@PantallaPanelAdmin, PantallaGestionTablon::class.java)
                intent.putExtra("modoAdmin", true)
                intent.putExtra("idUsuario", idUsuario)
                startActivity(intent)
            }
        })

        btnCrearIncidencia.setOnClickListener(object : android.view.View.OnClickListener {
            override fun onClick(v: android.view.View?) {
                val intent = Intent(this@PantallaPanelAdmin, PantallaCrearIncidencia::class.java)
                intent.putExtra("modoAdmin", true)
                intent.putExtra("idUsuario", idUsuario)
                startActivity(intent)
            }
        })
    }
}