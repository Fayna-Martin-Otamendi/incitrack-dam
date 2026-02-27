package com.fayna.incitrack.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fayna.incitrack.R
import com.fayna.incitrack.dao.IncidenciaDAO
import com.fayna.incitrack.db.DatabaseHelper

class PantallaMisIncidencias : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_mis_incidencias)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerMisIncidencias)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val dbHelper = DatabaseHelper(this)
        val incidenciaDAO = IncidenciaDAO(dbHelper)

        val listaIncidencias = incidenciaDAO.getIncidenciasPorUsuario(1)
        val adapter = IncidenciaAdapter(this, listaIncidencias)
        recyclerView.adapter = adapter
    }
}