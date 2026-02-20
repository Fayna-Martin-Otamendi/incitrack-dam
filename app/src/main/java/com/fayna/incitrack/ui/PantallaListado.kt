package com.fayna.incitrack.ui


import android.content.Intent
import android.widget.Button
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fayna.incitrack.R

class PantallaListado : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_listado)

        val btnVerDetalle = findViewById<Button>(R.id.btnVerDetalle)
        btnVerDetalle.setOnClickListener {
            startActivity(Intent(this, PantallaDetalleIncidencia::class.java))
        }
    }
}