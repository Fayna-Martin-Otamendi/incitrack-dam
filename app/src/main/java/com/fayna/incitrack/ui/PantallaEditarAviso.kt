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


// Pantalla para editar un aviso que ya existe en el tablón.
// Reutiliza el mismo layout de crear aviso, pero cargando los datos anteriores.

class PantallaEditarAviso : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_crear_aviso)

        // Datos recibidos desde la pantalla anterior
        val modoAdmin = intent.getBooleanExtra("modoAdmin", true)
        val idUsuario = intent.getIntExtra("idUsuario", -1)

        // Referencias a los elementos del layout
        val etTitulo = findViewById<EditText>(R.id.etTituloAviso)
        val etTexto = findViewById<EditText>(R.id.etTextoAviso)
        val btnPublicar = findViewById<Button>(R.id.btnPublicarAviso)

        // Se cambia el texto del botón porque aquí no se crea, sino que se edita
        btnPublicar.text = "Guardar cambios"

        val dbHelper = DatabaseHelper(this)
        val tablonDAO = TablonDAO(dbHelper)

        // Se recoge el id del aviso que se quiere editar
        val idAviso = intent.getIntExtra("idAviso", -1)
        if (idAviso == -1) {
            Toast.makeText(this, "Error: aviso no encontrado", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Se busca el aviso en la base de datos
        val avisoExistente = tablonDAO.getTablonById(idAviso)
        if (avisoExistente == null) {
            Toast.makeText(this, "Error: aviso no encontrado", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Se cargan los datos actuales del aviso en el formulario
        etTitulo.setText(avisoExistente.getTitulo())
        etTexto.setText(avisoExistente.getTexto())

        btnPublicar.setOnClickListener {

            val titulo = etTitulo.text.toString().trim()
            val texto = etTexto.text.toString().trim()

            // Se comprueba que los campos obligatorios estén rellenos
            if (titulo.isEmpty() || texto.isEmpty()) {
                Toast.makeText(this, "Rellena título y texto", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Se crea un nuevo objeto con los datos actualizados
            val avisoActualizado = Tablon()
            avisoActualizado.setIdAviso(idAviso)
            avisoActualizado.setTitulo(titulo)
            avisoActualizado.setTexto(texto)

            // Se mantienen los datos que ya tenía el aviso
            avisoActualizado.setFechaPublicacion(avisoExistente.getFechaPublicacion())
            avisoActualizado.setIdAdminPublicador(avisoExistente.getIdAdminPublicador())

            val filas = tablonDAO.updateTablon(avisoActualizado)

            // Si se actualiza bien, vuelve a la pantalla de gestión del tablón
            if (filas > 0) {
                Toast.makeText(this, "Aviso actualizado", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, PantallaGestionTablon::class.java)
                intent.putExtra("modoAdmin", modoAdmin)
                intent.putExtra("idUsuario", idUsuario)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Error al actualizar aviso", Toast.LENGTH_SHORT).show()
            }
        }
    }
}