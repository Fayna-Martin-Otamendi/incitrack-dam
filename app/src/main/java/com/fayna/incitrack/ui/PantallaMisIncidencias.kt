package com.fayna.incitrack.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fayna.incitrack.R
import com.fayna.incitrack.dao.IncidenciaDAO
import com.fayna.incitrack.db.DatabaseHelper

class PantallaMisIncidencias : AppCompatActivity() {

    private var idUsuario: Int = -1
    private var modoAdmin: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_mis_incidencias)

        idUsuario = intent.getIntExtra("idUsuario", -1)
        modoAdmin = intent.getBooleanExtra("modoAdmin", false)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerMisIncidencias)
        recyclerView.layoutManager = LinearLayoutManager(this)

        cargarLista()
    }

    override fun onResume() {
        super.onResume()
        cargarLista()
    }

    private fun cargarLista() {

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerMisIncidencias)

        val dbHelper = DatabaseHelper(this)
        val incidenciaDAO = IncidenciaDAO(dbHelper)

        val listaIncidencias = incidenciaDAO.getIncidenciasPorUsuario(idUsuario)

        val adapter = IncidenciaAdapter(this, listaIncidencias, "MIS", modoAdmin, idUsuario)
        recyclerView.adapter = adapter
    }
}