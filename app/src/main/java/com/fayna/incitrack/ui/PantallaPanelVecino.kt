package com.fayna.incitrack.ui

// Importamos las clases necesarias para trabajar con Activities, Intents y botones
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.fayna.incitrack.R

// Esta Activity corresponde al panel principal del vecino.
// Desde aquí el vecino puede acceder a las diferentes funciones de la aplicación.
class PantallaPanelVecino : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Cargamos el layout asociado a esta pantalla
        // El diseño está definido en el XML pantalla_panel_vecino
        setContentView(R.layout.pantalla_panel_vecino)

        // Recuperamos el id del usuario que ha iniciado sesión
        // Este dato lo recibimos desde la pantalla anterior mediante un Intent
        val idUsuario = intent.getIntExtra("idUsuario", -1)

        // Referencias a los botones de la interfaz
        val btnVerListado = findViewById<Button>(R.id.btnVerListado)
        val btnMisIncidencias = findViewById<Button>(R.id.btnMisIncidencias)
        val btnVerTablon = findViewById<Button>(R.id.btnVerTablon)
        val btnCrearIncidencia = findViewById<Button>(R.id.btnCrearIncidencia)

        // BOTÓN: Ver listado de incidencias
        // Abre la pantalla donde se muestran las incidencias registradas
        btnVerListado.setOnClickListener {

            val intent = Intent(this, PantallaListado::class.java)

            // Indicamos que el usuario NO es administrador
            intent.putExtra("modoAdmin", false)

            // Enviamos también el id del usuario
            intent.putExtra("idUsuario", idUsuario)

            startActivity(intent)
        }

        // BOTÓN: Ver mis incidencias
        // Abre la pantalla donde el vecino puede ver solo las incidencias que ha creado él
        btnMisIncidencias.setOnClickListener {

            val intent = Intent(this, PantallaMisIncidencias::class.java)

            intent.putExtra("modoAdmin", false)
            intent.putExtra("idUsuario", idUsuario)

            startActivity(intent)
        }

        // BOTÓN: Ver tablón de avisos
        // Permite acceder al tablón donde se publican avisos de la comunidad
        btnVerTablon.setOnClickListener {

            val intent = Intent(this, PantallaTablon::class.java)

            intent.putExtra("modoAdmin", false)
            intent.putExtra("idUsuario", idUsuario)

            startActivity(intent)
        }

        // BOTÓN: Crear nueva incidencia
        // Abre la pantalla donde el vecino puede registrar una incidencia
        btnCrearIncidencia.setOnClickListener {

            val intent = Intent(this, PantallaCrearIncidencia::class.java)

            intent.putExtra("modoAdmin", false)
            intent.putExtra("idUsuario", idUsuario)

            startActivity(intent)
        }
    }
}