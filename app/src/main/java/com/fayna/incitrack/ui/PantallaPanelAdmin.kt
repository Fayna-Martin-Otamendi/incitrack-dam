package com.fayna.incitrack.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.fayna.incitrack.R
import com.fayna.incitrack.dao.IncidenciaDAO
import com.fayna.incitrack.dao.TablonDAO
import com.fayna.incitrack.db.DatabaseHelper


// Panel principal del administrador.
// Desde aquí se muestra un resumen general de incidencias y avisos,
// y se puede ir a las pantallas principales de gestión.

class PantallaPanelAdmin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_panel_admin)

        // Se recibe el id del usuario que ha iniciado sesión
        val idUsuario = intent.getIntExtra("idUsuario", -1)

        // Referencias a los elementos del resumen
        val tvTotalIncidencias = findViewById<TextView>(R.id.tvTotalIncidencias)
        val tvPendientes = findViewById<TextView>(R.id.tvPendientes)
        val tvAbiertas = findViewById<TextView>(R.id.tvAbiertas)
        val tvEnProceso = findViewById<TextView>(R.id.tvEnProceso)
        val tvResueltas = findViewById<TextView>(R.id.tvResueltas)
        val tvTotalAvisos = findViewById<TextView>(R.id.tvTotalAvisos)

        // Referencias a los botones de navegación
        val btnVerListadoAdmin = findViewById<Button>(R.id.btnVerListadoAdmin)
        val btnGestionTablon = findViewById<Button>(R.id.btnGestionTablon)
        val btnCrearIncidencia = findViewById<Button>(R.id.btnCrearIncidencia)

        // Se carga el resumen al entrar en la pantalla
        cargarResumen(
            tvTotalIncidencias,
            tvPendientes,
            tvAbiertas,
            tvEnProceso,
            tvResueltas,
            tvTotalAvisos
        )

        // Abre el listado general de incidencias en modo administrador
        btnVerListadoAdmin.setOnClickListener {
            val intent = Intent(this, PantallaListado::class.java)
            intent.putExtra("modoAdmin", true)
            intent.putExtra("idUsuario", idUsuario)
            startActivity(intent)
        }

        // Abre la pantalla de gestión del tablón
        btnGestionTablon.setOnClickListener {
            val intent = Intent(this, PantallaGestionTablon::class.java)
            intent.putExtra("modoAdmin", true)
            intent.putExtra("idUsuario", idUsuario)
            startActivity(intent)
        }

        // Permite crear una nueva incidencia
        btnCrearIncidencia.setOnClickListener {
            val intent = Intent(this, PantallaCrearIncidencia::class.java)
            intent.putExtra("modoAdmin", true)
            intent.putExtra("idUsuario", idUsuario)
            startActivity(intent)
        }
    }

    // Cuando se vuelve a esta pantalla se actualiza el resumen
    override fun onResume() {
        super.onResume()

        val tvTotalIncidencias = findViewById<TextView>(R.id.tvTotalIncidencias)
        val tvPendientes = findViewById<TextView>(R.id.tvPendientes)
        val tvAbiertas = findViewById<TextView>(R.id.tvAbiertas)
        val tvEnProceso = findViewById<TextView>(R.id.tvEnProceso)
        val tvResueltas = findViewById<TextView>(R.id.tvResueltas)
        val tvTotalAvisos = findViewById<TextView>(R.id.tvTotalAvisos)

        cargarResumen(
            tvTotalIncidencias,
            tvPendientes,
            tvAbiertas,
            tvEnProceso,
            tvResueltas,
            tvTotalAvisos
        )
    }

    // Calcula los datos del resumen y los muestra en pantalla
    private fun cargarResumen(
        tvTotalIncidencias: TextView,
        tvPendientes: TextView,
        tvAbiertas: TextView,
        tvEnProceso: TextView,
        tvResueltas: TextView,
        tvTotalAvisos: TextView
    ) {
        val dbHelper = DatabaseHelper(this)
        val incidenciaDAO = IncidenciaDAO(dbHelper)
        val tablonDAO = TablonDAO(dbHelper)

        val listaIncidencias = incidenciaDAO.getAllIncidencias()
        val listaAvisos = tablonDAO.getAllAvisos()

        var pendientes = 0
        var abiertas = 0
        var enProceso = 0
        var resueltas = 0

        // Se recorren las incidencias para contar cuántas hay de cada estado
        for (incidencia in listaIncidencias) {
            if (incidencia.getEstado() == "Pendiente") {
                pendientes++
            } else if (incidencia.getEstado() == "Abierta") {
                abiertas++
            } else if (incidencia.getEstado() == "En proceso") {
                enProceso++
            } else if (incidencia.getEstado() == "Resuelta") {
                resueltas++
            }
        }

        tvTotalIncidencias.text = "Total incidencias: " + listaIncidencias.size
        tvPendientes.text = "Pendientes: " + pendientes
        tvAbiertas.text = "Abiertas: " + abiertas
        tvEnProceso.text = "En proceso: " + enProceso
        tvResueltas.text = "Resueltas: " + resueltas
        tvTotalAvisos.text = "Total avisos: " + listaAvisos.size
    }
}