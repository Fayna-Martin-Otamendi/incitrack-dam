package com.fayna.incitrack.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.fayna.incitrack.R
import com.fayna.incitrack.dao.IncidenciaDAO
import com.fayna.incitrack.db.DatabaseHelper
import com.fayna.incitrack.model.Incidencia

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

        val idIncidencia = intent.getIntExtra("idIncidencia", -1)

        if (idIncidencia == -1) {
            tvTitulo.text = "Incidencia no encontrada"
            return
        }

        val dbHelper = DatabaseHelper(this)
        val incidenciaDAO = IncidenciaDAO(dbHelper)

        val incidencia = incidenciaDAO.getIncidenciaById(idIncidencia)

        if (incidencia == null) {
            tvTitulo.text = "Incidencia no encontrada"
            return
        }

        tvTitulo.text = incidencia.getTitulo()
        tvEstado.text = "Estado: " + incidencia.getEstado()
        tvFecha.text = "Fecha: " + incidencia.getFechaCreacion()
        tvUbicacion.text = "Ubicación: " + incidencia.getUbicacion()
        tvTipo.text = "Tipo: " + incidencia.getTipo()
        tvDescripcion.text = incidencia.getDescripcion()
    }
}