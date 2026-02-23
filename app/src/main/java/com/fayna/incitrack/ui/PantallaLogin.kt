package com.fayna.incitrack.ui
import android.content.Intent

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.fayna.incitrack.R

import android.util.Log
import com.fayna.incitrack.db.DatabaseHelper
import com.fayna.incitrack.dao.UsuarioDAO
import com.fayna.incitrack.model.Usuario

class PantallaLogin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_login)

        val btnEntrarVecino = findViewById<Button>(R.id.btnEntrarVecino)
        val btnEntrarAdmin = findViewById<Button>(R.id.btnEntrarAdmin)

        btnEntrarVecino.setOnClickListener {

            val dbHelper = DatabaseHelper(this)
            val usuarioDAO = UsuarioDAO(dbHelper)

            val encontrado = usuarioDAO.getUsuarioByEmail("prueba@incitrack.com")
            Log.d("INCI_TEST", "ENCONTRADO = ${encontrado?.toString()}")

            val encontradoPorId = usuarioDAO.getUsuarioById(1)
            Log.d("INCI_TEST", "ENCONTRADO_ID = ${encontradoPorId?.toString()}")

            val u = Usuario()
            u.setNombre("Prueba")
            u.setEmail("prueba@incitrack.com")
            u.setTelefono("600000000")
            u.setPropiedad("3B")
            u.setRol("VECINO")
            u.setPassword("1234")

            val id = usuarioDAO.insertUsuario(u)
            Log.d("INCI_TEST", "ID INSERTADO = $id")


            val intent = Intent(this, PantallaPanelVecino::class.java)
            startActivity(intent)
        }

        btnEntrarAdmin.setOnClickListener {
            val intent = Intent(this, PantallaPanelAdmin::class.java)
            startActivity(intent)
        }
    }
}