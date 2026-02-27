package com.fayna.incitrack.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fayna.incitrack.R
import com.fayna.incitrack.dao.IncidenciaDAO
import com.fayna.incitrack.db.DatabaseHelper

class PantallaListado : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_listado)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerIncidencias)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val dbHelper = DatabaseHelper(this)
        val incidenciaDAO = IncidenciaDAO(dbHelper)

        val listaIncidencias = incidenciaDAO.getAllIncidencias()
        val adapter = IncidenciaAdapter(this, listaIncidencias)
        recyclerView.adapter = adapter
    }
}