package com.fayna.incitrack.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fayna.incitrack.R
import com.fayna.incitrack.dao.IncidenciaDAO
import com.fayna.incitrack.db.DatabaseHelper

class PantallaListado : AppCompatActivity() {

    private var modoAdmin: Boolean = false
    private var idUsuario: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_listado)

        modoAdmin = intent.getBooleanExtra("modoAdmin", false)
        idUsuario = intent.getIntExtra("idUsuario", -1)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerIncidencias)
        recyclerView.layoutManager = LinearLayoutManager(this)

        cargarLista()
    }

    override fun onResume() {
        super.onResume()
        cargarLista()
    }

    private fun cargarLista() {

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerIncidencias)

        val dbHelper = DatabaseHelper(this)
        val incidenciaDAO = IncidenciaDAO(dbHelper)

        val listaIncidencias = incidenciaDAO.getAllIncidencias()

        val adapter = IncidenciaAdapter(this, listaIncidencias, "LISTADO", modoAdmin, idUsuario)
        recyclerView.adapter = adapter
    }
}