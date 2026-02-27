package com.fayna.incitrack.ui

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.fayna.incitrack.R
import com.fayna.incitrack.dao.IncidenciaDAO
import com.fayna.incitrack.db.DatabaseHelper
import com.fayna.incitrack.model.Incidencia
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PantallaCrearIncidencia : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_crear_incidencia)

        val etTitulo = findViewById<EditText>(R.id.etTitulo)
        val etDescripcion = findViewById<EditText>(R.id.etDescripcion)
        val etUbicacion = findViewById<EditText>(R.id.etUbicacion)

        val rgTipo = findViewById<RadioGroup>(R.id.rgTipo)
        val rbGeneral = findViewById<RadioButton>(R.id.rbGeneral)

        val spCategoria = findViewById<Spinner>(R.id.spCategoria)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)


        val categoriasFake = listOf("General", "Agua", "Ascensor", "Electricidad")
        spCategoria.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categoriasFake)

        btnGuardar.setOnClickListener {
            val titulo = etTitulo.text.toString().trim()
            val descripcion = etDescripcion.text.toString().trim()
            val ubicacion = etUbicacion.text.toString().trim()

            if (titulo.isEmpty() || descripcion.isEmpty() || ubicacion.isEmpty()) {
                Toast.makeText(this, "Rellena título, descripción y ubicación", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val tipo = if (rgTipo.checkedRadioButtonId == rbGeneral.id) "General" else "Privada"
            val estado = "Pendiente"
            val fecha = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())


            val idUsuario = 1
            val idCategoria = 1
            val prioridad = 0

            val incidencia = Incidencia()
            incidencia.setTitulo(titulo)
            incidencia.setDescripcion(descripcion)
            incidencia.setUbicacion(ubicacion)
            incidencia.setTipo(tipo)
            incidencia.setEstado(estado)
            incidencia.setFechaCreacion(fecha)
            incidencia.setIdUsuario(idUsuario)
            incidencia.setIdCategoria(idCategoria)
            incidencia.setPrioridadCalculada(prioridad)

            val dbHelper = DatabaseHelper(this)
            val incidenciaDAO = IncidenciaDAO(dbHelper)

            val idInsertado = incidenciaDAO.insertIncidencia(incidencia)

            if (idInsertado != -1L) {
                Toast.makeText(this, "Incidencia guardada", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, PantallaListado::class.java))
                finish()
            } else {
                Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show()
            }
        }
    }
}