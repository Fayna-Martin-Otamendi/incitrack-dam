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

class PantallaEditarAviso : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_crear_aviso)

        val etTitulo = findViewById<EditText>(R.id.etTituloAviso)
        val etTexto = findViewById<EditText>(R.id.etTextoAviso)
        val btnPublicar = findViewById<Button>(R.id.btnPublicarAviso)

        btnPublicar.text = "Guardar cambios"

        val dbHelper = DatabaseHelper(this)
        val tablonDAO = TablonDAO(dbHelper)

        val idAviso = intent.getIntExtra("idAviso", -1)

        if (idAviso == -1) {
            Toast.makeText(this, "Error: aviso no encontrado", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val avisoExistente = tablonDAO.getTablonById(idAviso)

        if (avisoExistente == null) {
            Toast.makeText(this, "Error: aviso no encontrado", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        etTitulo.setText(avisoExistente.getTitulo())
        etTexto.setText(avisoExistente.getTexto())

        btnPublicar.setOnClickListener {

            val titulo = etTitulo.text.toString().trim()
            val texto = etTexto.text.toString().trim()

            if (titulo.isEmpty() || texto.isEmpty()) {
                Toast.makeText(this, "Rellena título y texto", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val avisoActualizado = Tablon()
            avisoActualizado.setIdAviso(idAviso)
            avisoActualizado.setTitulo(titulo)
            avisoActualizado.setTexto(texto)


            avisoActualizado.setFechaPublicacion(avisoExistente.getFechaPublicacion())
            avisoActualizado.setIdAdminPublicador(avisoExistente.getIdAdminPublicador())

            val filas = tablonDAO.updateTablon(avisoActualizado)

            if (filas > 0) {
                Toast.makeText(this, "Aviso actualizado", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, PantallaGestionTablon::class.java))
                finish()
            } else {
                Toast.makeText(this, "Error al actualizar aviso", Toast.LENGTH_SHORT).show()
            }
        }
    }
}