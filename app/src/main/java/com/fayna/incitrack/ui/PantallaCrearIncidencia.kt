package com.fayna.incitrack.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fayna.incitrack.R
import com.fayna.incitrack.dao.CategoriaDAO
import com.fayna.incitrack.dao.IncidenciaDAO
import com.fayna.incitrack.dao.IncidenciaImagenDAO
import com.fayna.incitrack.db.DatabaseHelper
import com.fayna.incitrack.model.Categoria
import com.fayna.incitrack.model.Incidencia
import com.fayna.incitrack.model.IncidenciaImagen
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


// Pantalla donde se crea una incidencia nueva.
// Aquí se recogen los datos del formulario, se calcula la prioridad y se guarda todo en la base de datos.

class PantallaCrearIncidencia : AppCompatActivity() {

    // Variables para guardar las dos imágenes seleccionadas
    private var uriImagen1: Uri? = null
    private var uriImagen2: Uri? = null

    companion object {
        private const val REQUEST_IMAGEN_1 = 1001
        private const val REQUEST_IMAGEN_2 = 1002
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_crear_incidencia)

        // Referencias a los elementos del layout
        val etTitulo = findViewById<EditText>(R.id.etTitulo)
        val etDescripcion = findViewById<EditText>(R.id.etDescripcion)
        val etUbicacion = findViewById<EditText>(R.id.etUbicacion)

        val rgTipo = findViewById<RadioGroup>(R.id.rgTipo)
        val rbGeneral = findViewById<RadioButton>(R.id.rbGeneral)

        val spCategoria = findViewById<Spinner>(R.id.spCategoria)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)

        val btnSeleccionarImagen1 = findViewById<Button>(R.id.btnSeleccionarImagen1)
        val btnSeleccionarImagen2 = findViewById<Button>(R.id.btnSeleccionarImagen2)
        val ivImagen1 = findViewById<ImageView>(R.id.ivImagen1)
        val ivImagen2 = findViewById<ImageView>(R.id.ivImagen2)

        // Datos recibidos desde la pantalla anterior
        val modoAdmin = intent.getBooleanExtra("modoAdmin", false)
        val idUsuario = intent.getIntExtra("idUsuario", -1)

        // Se cargan las categorías desde la base de datos para mostrarlas en el spinner
        val dbHelper = DatabaseHelper(this)
        val categoriaDAO = CategoriaDAO(dbHelper)

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

        // Botones para elegir hasta dos imágenes
        btnSeleccionarImagen1.setOnClickListener {
            abrirGaleria(REQUEST_IMAGEN_1)
        }

        btnSeleccionarImagen2.setOnClickListener {
            abrirGaleria(REQUEST_IMAGEN_2)
        }

        btnGuardar.setOnClickListener {

            val titulo = etTitulo.text.toString().trim()
            val descripcion = etDescripcion.text.toString().trim()
            val ubicacion = etUbicacion.text.toString().trim()

            // Se comprueba que los campos obligatorios estén rellenos
            if (titulo.isEmpty() || descripcion.isEmpty() || ubicacion.isEmpty()) {
                Toast.makeText(this, "Rellena título, descripción y ubicación", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Se comprueba que el usuario recibido sea válido
            if (idUsuario == -1) {
                Toast.makeText(this, "Error: usuario no válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Se comprueba que existan categorías en la base de datos
            if (listaCategorias.isEmpty()) {
                Toast.makeText(this, "No hay categorías disponibles", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val posicionCategoria = spCategoria.selectedItemPosition
            val categoriaSeleccionada: Categoria = listaCategorias[posicionCategoria]

            // Se recogen los datos que no vienen de EditText
            val tipo = if (rgTipo.checkedRadioButtonId == rbGeneral.id) "General" else "Privada"
            val estado = "Pendiente"
            val fecha = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())

            val idCategoria = categoriaSeleccionada.getIdCategoria()

            // La prioridad se calcula a partir de la urgencia base de la categoría
            // y se suma 1 si la incidencia es de tipo general
            var prioridad = categoriaSeleccionada.getUrgenciaBase()

            if (tipo == "General") {
                prioridad = prioridad + 1
            }

            // Se crea el objeto Incidencia con todos los datos
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

            val incidenciaDAO = IncidenciaDAO(dbHelper)
            val idInsertado = incidenciaDAO.insertIncidencia(incidencia)

            // Si la incidencia se guarda bien, después se guardan también las imágenes
            if (idInsertado != -1L) {

                val incidenciaImagenDAO = IncidenciaImagenDAO(dbHelper)

                if (uriImagen1 != null) {
                    val imagen1 = IncidenciaImagen()
                    imagen1.setIdIncidencia(idInsertado.toInt())
                    imagen1.setRutaImagen(uriImagen1.toString())
                    incidenciaImagenDAO.insertImagen(imagen1)
                }

                if (uriImagen2 != null) {
                    val imagen2 = IncidenciaImagen()
                    imagen2.setIdIncidencia(idInsertado.toInt())
                    imagen2.setRutaImagen(uriImagen2.toString())
                    incidenciaImagenDAO.insertImagen(imagen2)
                }

                Toast.makeText(this, "Incidencia guardada", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, PantallaListado::class.java)
                intent.putExtra("modoAdmin", modoAdmin)
                intent.putExtra("idUsuario", idUsuario)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Abre la galería para seleccionar una imagen
    private fun abrirGaleria(requestCode: Int) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        startActivityForResult(intent, requestCode)
    }

    // Recoge la imagen seleccionada y la muestra en su ImageView correspondiente
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && data != null && data.data != null) {
            val uriSeleccionada = data.data!!

            try {
                contentResolver.takePersistableUriPermission(
                    uriSeleccionada,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }

            val ivImagen1 = findViewById<ImageView>(R.id.ivImagen1)
            val ivImagen2 = findViewById<ImageView>(R.id.ivImagen2)

            if (requestCode == REQUEST_IMAGEN_1) {
                uriImagen1 = uriSeleccionada
                ivImagen1.setImageURI(uriImagen1)
            }

            if (requestCode == REQUEST_IMAGEN_2) {
                uriImagen2 = uriSeleccionada
                ivImagen2.setImageURI(uriImagen2)
            }
        }
    }
}