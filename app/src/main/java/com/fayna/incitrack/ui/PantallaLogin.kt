package com.fayna.incitrack.ui
import android.content.Intent

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.fayna.incitrack.R

class PantallaLogin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_login)

        val btnEntrarVecino = findViewById<Button>(R.id.btnEntrarVecino)
        val btnEntrarAdmin = findViewById<Button>(R.id.btnEntrarAdmin)

        btnEntrarVecino.setOnClickListener {
            val intent = Intent(this, PantallaPanelVecino::class.java)
            startActivity(intent)
        }

        btnEntrarAdmin.setOnClickListener {
            val intent = Intent(this, PantallaPanelAdmin::class.java)
            startActivity(intent)
        }
    }
}