package com.fayna.incitrack.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fayna.incitrack.R
import com.fayna.incitrack.dao.TablonDAO
import com.fayna.incitrack.db.DatabaseHelper

class PantallaTablon : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_tablon)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerTablon)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val dbHelper = DatabaseHelper(this)
        val tablonDAO = TablonDAO(dbHelper)

        val listaAvisos= tablonDAO.getAllAvisos()
        val adapter = TablonAdapter(this, listaAvisos, false)
        recyclerView.adapter = adapter
    }
}