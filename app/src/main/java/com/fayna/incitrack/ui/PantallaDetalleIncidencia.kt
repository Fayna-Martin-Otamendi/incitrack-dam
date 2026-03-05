package com.fayna.incitrack.ui

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.fayna.incitrack.R
import com.fayna.incitrack.dao.IncidenciaDAO
import com.fayna.incitrack.db.DatabaseHelper

class PantallaDetalleIncidencia : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_detalle_incidencia)

        val tvTitulo = findViewById<TextView>(R.id.tvDetalleTitulo)
        val tvEstado = findViewById<TextView>(R.id.tvDetalleEstado)
        val tvFecha = findViewById<TextView>(R.id.tvDetalleFecha)
        val tvUbicacion = findViewById<TextView>(R.id.tvDetalleUbicacion)
        val tvTipo = findViewById<TextView>(R.id.tvDetalleTipo)
        val tvDescripcion = findViewById<TextView>(R.id.tvDetalleDescripcion)

        val btnEditar = findViewById<Button>(R.id.btnEditarIncidencia)
        val btnBorrar = findViewById<Button>(R.id.btnBorrarIncidencia)

        val idIncidencia = intent.getIntExtra("idIncidencia", -1)
        val origen = intent.getStringExtra("origen") // "LISTADO" o "MIS"
        val modoAdmin = intent.getBooleanExtra("modoAdmin", false)
        val idUsuario = intent.getIntExtra("idUsuario", -1)

        if (idIncidencia == -1) {
            tvTitulo.text = "Incidencia no encontrada"
            btnEditar.visibility = View.GONE
            btnBorrar.visibility = View.GONE
            return
        }

        val dbHelper = DatabaseHelper(this)
        val incidenciaDAO = IncidenciaDAO(dbHelper)
        val incidencia = incidenciaDAO.getIncidenciaById(idIncidencia)

        if (incidencia == null) {
            tvTitulo.text = "Incidencia no encontrada"
            btnEditar.visibility = View.GONE
            btnBorrar.visibility = View.GONE
            return
        }

        tvTitulo.text = incidencia.getTitulo()
        tvEstado.text = "Estado: " + incidencia.getEstado()
        tvFecha.text = "Fecha: " + incidencia.getFechaCreacion()
        tvUbicacion.text = "Ubicación: " + incidencia.getUbicacion()
        tvTipo.text = "Tipo: " + incidencia.getTipo()
        tvDescripcion.text = incidencia.getDescripcion()

        // Permisos (como lo tenías):
        val puedeGestionar = modoAdmin || (origen == "MIS")

        if (puedeGestionar) {
            btnEditar.visibility = View.VISIBLE
            btnBorrar.visibility = View.VISIBLE
        } else {
            btnEditar.visibility = View.GONE
            btnBorrar.visibility = View.GONE
        }

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

        btnBorrar.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                AlertDialog.Builder(this@PantallaDetalleIncidencia)
                    .setTitle("Borrar incidencia")
                    .setMessage("¿Seguro que quieres borrar esta incidencia?")
                    .setPositiveButton("Sí", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            val dbHelper2 = DatabaseHelper(this@PantallaDetalleIncidencia)
                            val incidenciaDAO2 = IncidenciaDAO(dbHelper2)
                            val inc = incidenciaDAO2.getIncidenciaById(idIncidencia)

                            if (inc != null) {
                                incidenciaDAO2.deleteIncidencia(inc)
                                Toast.makeText(this@PantallaDetalleIncidencia, "Incidencia borrada", Toast.LENGTH_SHORT).show()
                                finish()
                            } else {
                                Toast.makeText(this@PantallaDetalleIncidencia, "Error al borrar", Toast.LENGTH_SHORT).show()
                            }
                        }
                    })
                    .setNegativeButton("No", null)
                    .show()
            }
        })
    }
}
