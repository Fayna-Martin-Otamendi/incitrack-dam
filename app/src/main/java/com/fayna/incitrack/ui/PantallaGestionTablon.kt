package com.fayna.incitrack.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fayna.incitrack.R
import com.fayna.incitrack.dao.TablonDAO
import com.fayna.incitrack.db.DatabaseHelper

class PantallaGestionTablon : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_gestion_tablon)

        val btnCrearAviso = findViewById<Button>(R.id.btnCrearAviso)
        val recycler = findViewById<RecyclerView>(R.id.recyclerGestionTablon)
        recycler.layoutManager = LinearLayoutManager(this)

        val dbHelper = DatabaseHelper(this)
        val tablonDAO = TablonDAO(dbHelper)

        val listaAvisos = tablonDAO.getAllAvisos()
        val adapter = TablonAdapter(this, listaAvisos, true)
        recycler.adapter = adapter

        btnCrearAviso.setOnClickListener {
            startActivity(Intent(this, PantallaCrearAviso::class.java))
        }
    }
}
