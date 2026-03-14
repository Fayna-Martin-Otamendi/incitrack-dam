package com.fayna.incitrack.ui

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.fayna.incitrack.R
import com.fayna.incitrack.dao.CategoriaDAO
import com.fayna.incitrack.dao.ComentarioIncidenciaDAO
import com.fayna.incitrack.dao.IncidenciaDAO
import com.fayna.incitrack.dao.IncidenciaImagenDAO
import com.fayna.incitrack.dao.UsuarioDAO
import com.fayna.incitrack.db.DatabaseHelper
import com.fayna.incitrack.model.ComentarioIncidencia
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


// Pantalla donde se muestra el detalle completo de una incidencia.
// Desde aquí se pueden ver los datos, las imágenes, los comentarios
// y también gestionar la incidencia según el rol del usuario.

class PantallaDetalleIncidencia : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_detalle_incidencia)

        // Referencias a los elementos del layout
        val tvTitulo = findViewById<TextView>(R.id.tvDetalleTitulo)
        val tvEstado = findViewById<TextView>(R.id.tvDetalleEstado)
        val tvPrioridad = findViewById<TextView>(R.id.tvDetallePrioridad)
        val tvVecino = findViewById<TextView>(R.id.tvDetalleVecino)
        val tvPropiedad = findViewById<TextView>(R.id.tvDetallePropiedad)
        val tvTelefono = findViewById<TextView>(R.id.tvDetalleTelefono)
        val tvFecha = findViewById<TextView>(R.id.tvDetalleFecha)
        val tvUbicacion = findViewById<TextView>(R.id.tvDetalleUbicacion)
        val tvTipo = findViewById<TextView>(R.id.tvDetalleTipo)
        val tvDescripcion = findViewById<TextView>(R.id.tvDetalleDescripcion)
        val tvListaComentarios = findViewById<TextView>(R.id.tvListaComentarios)

        val etNuevoComentario = findViewById<EditText>(R.id.etNuevoComentario)
        val btnGuardarComentario = findViewById<Button>(R.id.btnGuardarComentario)

        val ivImagen1 = findViewById<ImageView>(R.id.ivDetalleImagen1)
        val ivImagen2 = findViewById<ImageView>(R.id.ivDetalleImagen2)

        val btnEditar = findViewById<Button>(R.id.btnEditarIncidencia)
        val btnBorrar = findViewById<Button>(R.id.btnBorrarIncidencia)
        val btnEnProceso = findViewById<Button>(R.id.btnEstadoEnProceso)
        val btnResuelta = findViewById<Button>(R.id.btnEstadoResuelta)

        // Datos recibidos desde la pantalla anterior
        val idIncidencia = intent.getIntExtra("idIncidencia", -1)
        val origen = intent.getStringExtra("origen")
        val modoAdmin = intent.getBooleanExtra("modoAdmin", false)
        val idUsuario = intent.getIntExtra("idUsuario", -1)

        // Si no llega un id válido, la pantalla no puede cargar la incidencia
        if (idIncidencia == -1) {
            tvTitulo.text = "Incidencia no encontrada"
            btnEditar.visibility = View.GONE
            btnBorrar.visibility = View.GONE
            btnEnProceso.visibility = View.GONE
            btnResuelta.visibility = View.GONE
            tvPrioridad.visibility = View.GONE
            tvVecino.visibility = View.GONE
            tvPropiedad.visibility = View.GONE
            tvTelefono.visibility = View.GONE
            etNuevoComentario.visibility = View.GONE
            btnGuardarComentario.visibility = View.GONE
            ivImagen1.visibility = View.GONE
            ivImagen2.visibility = View.GONE
            return
        }

        // Se preparan los DAO necesarios para cargar todos los datos del detalle
        val dbHelper = DatabaseHelper(this)
        val incidenciaDAO = IncidenciaDAO(dbHelper)
        val categoriaDAO = CategoriaDAO(dbHelper)
        val incidenciaImagenDAO = IncidenciaImagenDAO(dbHelper)
        val usuarioDAO = UsuarioDAO(dbHelper)
        val comentarioDAO = ComentarioIncidenciaDAO(dbHelper)

        val incidencia = incidenciaDAO.getIncidenciaById(idIncidencia)

        // Si la incidencia no existe, se ocultan los elementos de gestión
        if (incidencia == null) {
            tvTitulo.text = "Incidencia no encontrada"
            btnEditar.visibility = View.GONE
            btnBorrar.visibility = View.GONE
            btnEnProceso.visibility = View.GONE
            btnResuelta.visibility = View.GONE
            tvPrioridad.visibility = View.GONE
            tvVecino.visibility = View.GONE
            tvPropiedad.visibility = View.GONE
            tvTelefono.visibility = View.GONE
            etNuevoComentario.visibility = View.GONE
            btnGuardarComentario.visibility = View.GONE
            ivImagen1.visibility = View.GONE
            ivImagen2.visibility = View.GONE
            return
        }

        // Cuando el administrador abre una incidencia pendiente,
        // pasa automáticamente a estado Abierta
        if (modoAdmin && incidencia.getEstado() == "Pendiente") {
            incidencia.setEstado("Abierta")
            incidenciaDAO.updateIncidencia(incidencia)
        }

        // Se muestran los datos principales de la incidencia
        tvTitulo.text = incidencia.getTitulo()
        tvEstado.text = "Estado: " + incidencia.getEstado()
        tvFecha.text = "Fecha: " + incidencia.getFechaCreacion()
        tvUbicacion.text = "Ubicación: " + incidencia.getUbicacion()
        tvTipo.text = "Tipo: " + incidencia.getTipo()
        tvDescripcion.text = incidencia.getDescripcion()

        // Si entra el administrador, se muestra también la prioridad
        // y los datos del vecino que creó la incidencia
        if (modoAdmin) {
            tvPrioridad.visibility = View.VISIBLE

            val categoria = categoriaDAO.getCategoriaById(incidencia.getIdCategoria())

            var prioridadReal = 1

            if (categoria != null) {
                prioridadReal = categoria.getUrgenciaBase()
            }

            if (incidencia.getTipo() == "General") {
                prioridadReal = prioridadReal + 1
            }

            val prioridadTexto = when (prioridadReal) {
                5, 6 -> "Alta"
                3, 4 -> "Media"
                else -> "Baja"
            }

            tvPrioridad.text = "Prioridad: " + prioridadTexto

            val usuario = usuarioDAO.getUsuarioById(incidencia.getIdUsuario())

            if (usuario != null) {
                tvVecino.visibility = View.VISIBLE
                tvPropiedad.visibility = View.VISIBLE
                tvTelefono.visibility = View.VISIBLE

                tvVecino.text = "Vecino: " + usuario.getNombre()
                tvPropiedad.text = "Propiedad: " + usuario.getPropiedad()
                tvTelefono.text = "Teléfono: " + usuario.getTelefono()
            } else {
                tvVecino.visibility = View.GONE
                tvPropiedad.visibility = View.GONE
                tvTelefono.visibility = View.GONE
            }

            // El administrador puede escribir comentarios de seguimiento
            etNuevoComentario.visibility = View.VISIBLE
            btnGuardarComentario.visibility = View.VISIBLE

        } else {
            tvPrioridad.visibility = View.GONE
            tvVecino.visibility = View.GONE
            tvPropiedad.visibility = View.GONE
            tvTelefono.visibility = View.GONE
            etNuevoComentario.visibility = View.GONE
            btnGuardarComentario.visibility = View.GONE
        }

        // Se cargan las imágenes asociadas a la incidencia
        val listaImagenes = incidenciaImagenDAO.getImagenesPorIncidencia(idIncidencia)

        if (listaImagenes.size > 0) {
            ivImagen1.visibility = View.VISIBLE
            ivImagen1.setImageURI(Uri.parse(listaImagenes[0].getRutaImagen()))
        } else {
            ivImagen1.visibility = View.GONE
        }

        if (listaImagenes.size > 1) {
            ivImagen2.visibility = View.VISIBLE
            ivImagen2.setImageURI(Uri.parse(listaImagenes[1].getRutaImagen()))
        } else {
            ivImagen2.visibility = View.GONE
        }

        // Se cargan los comentarios guardados para esta incidencia
        cargarComentarios(idIncidencia, tvListaComentarios, comentarioDAO)

        // Guardar un comentario nuevo
        btnGuardarComentario.setOnClickListener {
            val textoComentario = etNuevoComentario.text.toString().trim()

            if (textoComentario.isEmpty()) {
                Toast.makeText(this, "Escribe un comentario", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val fechaComentario = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())

            val comentario = ComentarioIncidencia()
            comentario.setIdIncidencia(idIncidencia)
            comentario.setIdAdmin(idUsuario)
            comentario.setTexto(textoComentario)
            comentario.setFechaComentario(fechaComentario)

            val idInsertado = comentarioDAO.insertComentario(comentario)

            if (idInsertado != -1L) {
                etNuevoComentario.setText("")
                Toast.makeText(this, "Comentario guardado", Toast.LENGTH_SHORT).show()
                cargarComentarios(idIncidencia, tvListaComentarios, comentarioDAO)
            } else {
                Toast.makeText(this, "Error al guardar comentario", Toast.LENGTH_SHORT).show()
            }
        }

        // Puede gestionar la incidencia el administrador
        // o el vecino cuando entra desde "Mis incidencias"
        val puedeGestionar = modoAdmin || (origen == "MIS")

        if (puedeGestionar) {
            btnEditar.visibility = View.VISIBLE
            btnBorrar.visibility = View.VISIBLE
        } else {
            btnEditar.visibility = View.GONE
            btnBorrar.visibility = View.GONE
        }

        // Solo el administrador puede cambiar el estado
        if (modoAdmin) {
            btnEnProceso.visibility = View.VISIBLE
            btnResuelta.visibility = View.VISIBLE
        } else {
            btnEnProceso.visibility = View.GONE
            btnResuelta.visibility = View.GONE
        }

        // Botón para editar la incidencia
        btnEditar.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intentEditar = Intent(this@PantallaDetalleIncidencia, PantallaEditarIncidencia::class.java)
                intentEditar.putExtra("idIncidencia", idIncidencia)
                intentEditar.putExtra("modoAdmin", modoAdmin)
                intentEditar.putExtra("idUsuario", idUsuario)
                intentEditar.putExtra("origen", origen)
                startActivity(intentEditar)
            }
        })

        // Botón para borrar la incidencia
        btnBorrar.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                AlertDialog.Builder(this@PantallaDetalleIncidencia)
                    .setTitle("Borrar incidencia")
                    .setMessage("¿Seguro que quieres borrar esta incidencia?")
                    .setPositiveButton("Sí", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            val dbHelper2 = DatabaseHelper(this@PantallaDetalleIncidencia)
                            val incidenciaDAO2 = IncidenciaDAO(dbHelper2)
                            val incidenciaImagenDAO2 = IncidenciaImagenDAO(dbHelper2)
                            val comentarioDAO2 = ComentarioIncidenciaDAO(dbHelper2)
                            val inc = incidenciaDAO2.getIncidenciaById(idIncidencia)

                            if (inc != null) {
                                // Antes de borrar la incidencia se borran también
                                // sus imágenes y sus comentarios
                                incidenciaImagenDAO2.deleteImagenesPorIncidencia(idIncidencia)
                                comentarioDAO2.deleteComentariosPorIncidencia(idIncidencia)
                                incidenciaDAO2.deleteIncidencia(inc)

                                Toast.makeText(
                                    this@PantallaDetalleIncidencia,
                                    "Incidencia borrada",
                                    Toast.LENGTH_SHORT
                                ).show()
                                finish()
                            } else {
                                Toast.makeText(
                                    this@PantallaDetalleIncidencia,
                                    "Error al borrar",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    })
                    .setNegativeButton("No", null)
                    .show()
            }
        })

        // Cambiar estado a En proceso
        btnEnProceso.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                incidencia.setEstado("En proceso")
                val filas = incidenciaDAO.updateIncidencia(incidencia)

                if (filas > 0) {
                    tvEstado.text = "Estado: " + incidencia.getEstado()
                    Toast.makeText(
                        this@PantallaDetalleIncidencia,
                        "Estado cambiado a En proceso",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this@PantallaDetalleIncidencia,
                        "Error al cambiar estado",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })

        // Cambiar estado a Resuelta
        btnResuelta.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                incidencia.setEstado("Resuelta")
                val filas = incidenciaDAO.updateIncidencia(incidencia)

                if (filas > 0) {
                    tvEstado.text = "Estado: " + incidencia.getEstado()
                    Toast.makeText(
                        this@PantallaDetalleIncidencia,
                        "Estado cambiado a Resuelta",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this@PantallaDetalleIncidencia,
                        "Error al cambiar estado",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    // Carga todos los comentarios de una incidencia
    // y los muestra dentro del TextView
    private fun cargarComentarios(
        idIncidencia: Int,
        tvListaComentarios: TextView,
        comentarioDAO: ComentarioIncidenciaDAO
    ) {
        val listaComentarios = comentarioDAO.getComentariosPorIncidencia(idIncidencia)

        if (listaComentarios.isEmpty()) {
            tvListaComentarios.text = "Sin comentarios todavía"
        } else {
            var textoFinal = ""

            for (comentario in listaComentarios) {
                textoFinal += comentario.getFechaComentario() + " - " + comentario.getTexto() + "\n\n"
            }

            tvListaComentarios.text = textoFinal
        }
    }
}
