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

    private var modoAdmin: Boolean = true
    private var idUsuario: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_gestion_tablon)

        modoAdmin = intent.getBooleanExtra("modoAdmin", true)
        idUsuario = intent.getIntExtra("idUsuario", -1)

        val btnCrearAviso = findViewById<Button>(R.id.btnCrearAviso)
        val recycler = findViewById<RecyclerView>(R.id.recyclerGestionTablon)

        recycler.layoutManager = LinearLayoutManager(this)

        btnCrearAviso.setOnClickListener {

            val intent = Intent(this, PantallaCrearAviso::class.java)
            intent.putExtra("modoAdmin", modoAdmin)
            intent.putExtra("idUsuario", idUsuario)

            startActivity(intent)
        }

        cargarLista()
    }

    override fun onResume() {
        super.onResume()
        cargarLista()
    }

    private fun cargarLista() {

        val recycler = findViewById<RecyclerView>(R.id.recyclerGestionTablon)

        val dbHelper = DatabaseHelper(this)
        val tablonDAO = TablonDAO(dbHelper)

        val listaAvisos = tablonDAO.getAllAvisos()

        val adapter = TablonAdapter(this, listaAvisos, modoAdmin)

        recycler.adapter = adapter
    }
}
