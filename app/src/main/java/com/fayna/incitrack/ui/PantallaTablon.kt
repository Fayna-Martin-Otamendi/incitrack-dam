package com.fayna.incitrack.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fayna.incitrack.R
import com.fayna.incitrack.dao.TablonDAO
import com.fayna.incitrack.db.DatabaseHelper


// Pantalla donde se muestra el tablón de avisos.
// Aquí se cargan todos los avisos publicados y se muestran en un RecyclerView.

class PantallaTablon : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_tablon)

        // Se recibe el modo de usuario para que el adapter sepa
        // si tiene que mostrar opciones de administrador o no
        val modoAdmin = intent.getBooleanExtra("modoAdmin", false)

        // Referencia al RecyclerView donde se mostrarán los avisos
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerTablon)

        // El RecyclerView se mostrará en vertical
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Se accede a la base de datos para obtener los avisos guardados
        val dbHelper = DatabaseHelper(this)
        val tablonDAO = TablonDAO(dbHelper)

        val listaAvisos = tablonDAO.getAllAvisos()

        // Se crea el adapter con la lista de avisos y el modo de usuario
        val adapter = TablonAdapter(this, listaAvisos, modoAdmin)

        recyclerView.adapter = adapter
    }
}