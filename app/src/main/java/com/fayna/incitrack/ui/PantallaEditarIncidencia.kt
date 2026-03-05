package com.fayna.incitrack.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.fayna.incitrack.R
import com.fayna.incitrack.dao.IncidenciaDAO
import com.fayna.incitrack.db.DatabaseHelper

class PantallaEditarIncidencia : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_crear_incidencia)

        val etTitulo = findViewById<EditText>(R.id.etTitulo)
        val etDescripcion = findViewById<EditText>(R.id.etDescripcion)
        val etUbicacion = findViewById<EditText>(R.id.etUbicacion)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)

        btnGuardar.text = "Guardar cambios"

        val idIncidencia = intent.getIntExtra("idIncidencia", -1)

        if (idIncidencia == -1) {
            Toast.makeText(this, "Incidencia no encontrada", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val dbHelper = DatabaseHelper(this)
        val incidenciaDAO = IncidenciaDAO(dbHelper)
        val incidencia = incidenciaDAO.getIncidenciaById(idIncidencia)

        if (incidencia == null) {
            Toast.makeText(this, "Incidencia no encontrada", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        etTitulo.setText(incidencia.getTitulo())
        etDescripcion.setText(incidencia.getDescripcion())
        etUbicacion.setText(incidencia.getUbicacion())

        btnGuardar.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                val titulo = etTitulo.text.toString().trim()
                val descripcion = etDescripcion.text.toString().trim()
                val ubicacion = etUbicacion.text.toString().trim()

                if (titulo.isEmpty() || descripcion.isEmpty() || ubicacion.isEmpty()) {
                    Toast.makeText(this@PantallaEditarIncidencia, "Rellena todos los campos", Toast.LENGTH_SHORT).show()
                    return
                }

                AlertDialog.Builder(this@PantallaEditarIncidencia)
                    .setTitle("Guardar cambios")
                    .setMessage("¿Quieres guardar los cambios?")
                    .setPositiveButton("Sí", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            val dbHelper2 = DatabaseHelper(this@PantallaEditarIncidencia)
                            val incidenciaDAO2 = IncidenciaDAO(dbHelper2)
                            val inc = incidenciaDAO2.getIncidenciaById(idIncidencia)

                            if (inc != null) {
                                inc.setTitulo(titulo)
                                inc.setDescripcion(descripcion)
                                inc.setUbicacion(ubicacion)

                                val filas = incidenciaDAO2.updateIncidencia(inc)
                                if (filas > 0) {
                                    Toast.makeText(this@PantallaEditarIncidencia, "Incidencia actualizada", Toast.LENGTH_SHORT).show()
                                    finish()
                                } else {
                                    Toast.makeText(this@PantallaEditarIncidencia, "Error al actualizar", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Toast.makeText(this@PantallaEditarIncidencia, "Error al actualizar", Toast.LENGTH_SHORT).show()
                            }
                        }
                    })
                    .setNegativeButton("No", null)
                    .show()
            }
        })
    }
}