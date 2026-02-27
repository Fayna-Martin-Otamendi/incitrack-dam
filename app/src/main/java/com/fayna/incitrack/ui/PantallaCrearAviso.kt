package com.fayna.incitrack.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fayna.incitrack.R
import com.fayna.incitrack.dao.TablonDAO
import com.fayna.incitrack.db.DatabaseHelper
import com.fayna.incitrack.model.Tablon
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PantallaCrearAviso : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_crear_aviso)

        val etTitulo = findViewById<EditText>(R.id.etTituloAviso)
        val etTexto = findViewById<EditText>(R.id.etTextoAviso)
        val btnPublicar = findViewById<Button>(R.id.btnPublicarAviso)

        val dbHelper = DatabaseHelper(this)
        val tablonDAO = TablonDAO(dbHelper)

        btnPublicar.setOnClickListener {

            val titulo = etTitulo.text.toString().trim()
            val texto = etTexto.text.toString().trim()

            if (titulo.isEmpty() || texto.isEmpty()) {
                Toast.makeText(this, "Rellena título y texto", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val fecha = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())

            val aviso = Tablon()
            aviso.setTitulo(titulo)
            aviso.setTexto(texto)
            aviso.setIdAdminPublicador(1)
            aviso.setFechaPublicacion(fecha)

            val idNuevo = tablonDAO.insertTablon(aviso)

            if (idNuevo > 0) {
                Toast.makeText(this, "Aviso publicado", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, PantallaGestionTablon::class.java))
                finish()
            } else {
                Toast.makeText(this, "Error al publicar aviso", Toast.LENGTH_SHORT).show()
            }
        }
    }
}