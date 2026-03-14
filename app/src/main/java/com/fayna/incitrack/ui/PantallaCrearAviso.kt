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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


// Pantalla donde el administrador puede crear y publicar un aviso en el tablón.

class PantallaCrearAviso : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_crear_aviso)

        // Se reciben los datos necesarios de la pantalla anterior
        val modoAdmin = intent.getBooleanExtra("modoAdmin", true)
        val idUsuario = intent.getIntExtra("idUsuario", -1)

        // Referencias a los elementos del layout
        val etTitulo = findViewById<EditText>(R.id.etTituloAviso)
        val etTexto = findViewById<EditText>(R.id.etTextoAviso)
        val btnPublicar = findViewById<Button>(R.id.btnPublicarAviso)

        // Se prepara el acceso a la base de datos
        val dbHelper = DatabaseHelper(this)
        val tablonDAO = TablonDAO(dbHelper)

        btnPublicar.setOnClickListener {

            val titulo = etTitulo.text.toString().trim()
            val texto = etTexto.text.toString().trim()

            // Se comprueba que los campos obligatorios no estén vacíos
            if (titulo.isEmpty() || texto.isEmpty()) {
                Toast.makeText(this, "Rellena título y texto", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Se comprueba que el usuario recibido sea válido
            if (idUsuario == -1) {
                Toast.makeText(this, "Error: usuario no válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Se genera la fecha actual para guardar cuándo se publicó el aviso
            val fecha = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())

            // Se crea el objeto Tablon con los datos introducidos
            val aviso = Tablon()
            aviso.setTitulo(titulo)
            aviso.setTexto(texto)
            aviso.setIdAdminPublicador(idUsuario)
            aviso.setFechaPublicacion(fecha)

            val idNuevo = tablonDAO.insertTablon(aviso)

            // Si el insert sale bien, vuelve a la pantalla de gestión del tablón
            if (idNuevo > 0) {
                Toast.makeText(this, "Aviso publicado", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, PantallaGestionTablon::class.java)
                intent.putExtra("modoAdmin", modoAdmin)
                intent.putExtra("idUsuario", idUsuario)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Error al publicar aviso", Toast.LENGTH_SHORT).show()
            }
        }
    }
}