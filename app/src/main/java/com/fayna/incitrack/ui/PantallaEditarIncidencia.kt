package com.fayna.incitrack.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.fayna.incitrack.R
import com.fayna.incitrack.dao.CategoriaDAO
import com.fayna.incitrack.dao.IncidenciaDAO
import com.fayna.incitrack.db.DatabaseHelper
import com.fayna.incitrack.model.Categoria


// Pantalla para editar una incidencia que ya existe.
// Reutiliza el mismo layout de crear incidencia, pero cargando los datos anteriores.

class PantallaEditarIncidencia : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_crear_incidencia)

        // Referencias a los elementos del layout
        val etTitulo = findViewById<EditText>(R.id.etTitulo)
        val etDescripcion = findViewById<EditText>(R.id.etDescripcion)
        val etUbicacion = findViewById<EditText>(R.id.etUbicacion)

        val rgTipo = findViewById<RadioGroup>(R.id.rgTipo)
        val rbGeneral = findViewById<RadioButton>(R.id.rbGeneral)
        val rbPrivada = findViewById<RadioButton>(R.id.rbPrivada)

        val spCategoria = findViewById<Spinner>(R.id.spCategoria)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)

        val btnSeleccionarImagen1 = findViewById<Button>(R.id.btnSeleccionarImagen1)
        val btnSeleccionarImagen2 = findViewById<Button>(R.id.btnSeleccionarImagen2)
        val ivImagen1 = findViewById<ImageView>(R.id.ivImagen1)
        val ivImagen2 = findViewById<ImageView>(R.id.ivImagen2)

        // Se cambia el texto del botón porque aquí no se crea, sino que se edita
        btnGuardar.text = "Guardar cambios"

        // En esta pantalla no se editan imágenes, por eso se ocultan
        btnSeleccionarImagen1.visibility = View.GONE
        btnSeleccionarImagen2.visibility = View.GONE
        ivImagen1.visibility = View.GONE
        ivImagen2.visibility = View.GONE

        val idIncidencia = intent.getIntExtra("idIncidencia", -1)

        if (idIncidencia == -1) {
            Toast.makeText(this, "Incidencia no encontrada", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Se cargan los datos de la incidencia y las categorías disponibles
        val dbHelper = DatabaseHelper(this)
        val incidenciaDAO = IncidenciaDAO(dbHelper)
        val categoriaDAO = CategoriaDAO(dbHelper)

        val incidencia = incidenciaDAO.getIncidenciaById(idIncidencia)

        if (incidencia == null) {
            Toast.makeText(this, "Incidencia no encontrada", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val listaCategorias = categoriaDAO.getAllCategorias()
        val nombresCategorias = ArrayList<String>()

        for (categoria in listaCategorias) {
            nombresCategorias.add(categoria.getNombre())
        }

        val adapterCategorias = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            nombresCategorias
        )
        spCategoria.adapter = adapterCategorias

        // Se cargan en el formulario los datos actuales de la incidencia
        etTitulo.setText(incidencia.getTitulo())
        etDescripcion.setText(incidencia.getDescripcion())
        etUbicacion.setText(incidencia.getUbicacion())

        if (incidencia.getTipo() == "General") {
            rbGeneral.isChecked = true
        } else {
            rbPrivada.isChecked = true
        }

        // Se selecciona en el spinner la categoría que ya tenía la incidencia
        var posicionCategoriaSeleccionada = 0

        for (i in listaCategorias.indices) {
            if (listaCategorias[i].getIdCategoria() == incidencia.getIdCategoria()) {
                posicionCategoriaSeleccionada = i
            }
        }

        spCategoria.setSelection(posicionCategoriaSeleccionada)

        btnGuardar.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                val titulo = etTitulo.text.toString().trim()
                val descripcion = etDescripcion.text.toString().trim()
                val ubicacion = etUbicacion.text.toString().trim()

                // Se comprueba que los campos obligatorios estén rellenos
                if (titulo.isEmpty() || descripcion.isEmpty() || ubicacion.isEmpty()) {
                    Toast.makeText(this@PantallaEditarIncidencia, "Rellena todos los campos", Toast.LENGTH_SHORT).show()
                    return
                }

                if (listaCategorias.isEmpty()) {
                    Toast.makeText(this@PantallaEditarIncidencia, "No hay categorías disponibles", Toast.LENGTH_SHORT).show()
                    return
                }

                val posicionCategoria = spCategoria.selectedItemPosition
                val categoriaSeleccionada: Categoria = listaCategorias[posicionCategoria]

                val tipo = if (rgTipo.checkedRadioButtonId == rbGeneral.id) "General" else "Privada"

                // Se recalcula la prioridad según la categoría y el tipo
                var prioridad = categoriaSeleccionada.getUrgenciaBase()

                if (tipo == "General") {
                    prioridad = prioridad + 1
                }

                // Antes de guardar, se pide confirmación
                AlertDialog.Builder(this@PantallaEditarIncidencia)
                    .setTitle("Guardar cambios")
                    .setMessage("¿Quieres guardar los cambios?")
                    .setPositiveButton("Sí", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            val dbHelper2 = DatabaseHelper(this@PantallaEditarIncidencia)
                            val incidenciaDAO2 = IncidenciaDAO(dbHelper2)
                            val inc = incidenciaDAO2.getIncidenciaById(idIncidencia)

                            if (inc != null) {
                                // Se actualizan los datos modificados de la incidencia
                                inc.setTitulo(titulo)
                                inc.setDescripcion(descripcion)
                                inc.setUbicacion(ubicacion)
                                inc.setTipo(tipo)
                                inc.setIdCategoria(categoriaSeleccionada.getIdCategoria())
                                inc.setPrioridadCalculada(prioridad)

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