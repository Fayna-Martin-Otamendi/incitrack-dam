package com.fayna.incitrack.ui

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fayna.incitrack.R
import com.fayna.incitrack.dao.CategoriaDAO
import com.fayna.incitrack.dao.IncidenciaDAO
import com.fayna.incitrack.db.DatabaseHelper
import com.fayna.incitrack.model.Categoria
import com.fayna.incitrack.model.Incidencia
import java.util.Locale


// Pantalla donde el vecino puede ver solo sus incidencias.
// También permite filtrarlas por texto, fecha, estado, categoría y tipo.

class PantallaMisIncidencias : AppCompatActivity() {

    private var idUsuario: Int = -1
    private var modoAdmin: Boolean = false

    private lateinit var etBuscar: EditText
    private lateinit var etFecha: EditText
    private lateinit var spEstado: Spinner
    private lateinit var spCategoria: Spinner
    private lateinit var spTipo: Spinner
    private lateinit var btnFiltrar: Button
    private lateinit var btnLimpiar: Button
    private lateinit var recyclerView: RecyclerView

    private var listaCategorias = ArrayList<Categoria>()
    private var listaIncidenciasOriginal = ArrayList<Incidencia>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_mis_incidencias)

        // Datos recibidos desde la pantalla anterior
        idUsuario = intent.getIntExtra("idUsuario", -1)
        modoAdmin = intent.getBooleanExtra("modoAdmin", false)

        // Referencias a los elementos del layout
        etBuscar = findViewById(R.id.etBuscarMisIncidencias)
        etFecha = findViewById(R.id.etFechaMisIncidencias)
        spEstado = findViewById(R.id.spEstadoMisIncidencias)
        spCategoria = findViewById(R.id.spCategoriaMisIncidencias)
        spTipo = findViewById(R.id.spTipoMisIncidencias)
        btnFiltrar = findViewById(R.id.btnFiltrarMisIncidencias)
        btnLimpiar = findViewById(R.id.btnLimpiarMisIncidencias)
        recyclerView = findViewById(R.id.recyclerMisIncidencias)

        recyclerView.layoutManager = LinearLayoutManager(this)

        // Se preparan los filtros y se carga la lista inicial
        prepararFiltros()
        cargarLista()

        btnFiltrar.setOnClickListener {
            aplicarFiltros()
        }

        // Limpia todos los filtros y vuelve a mostrar la lista completa
        btnLimpiar.setOnClickListener {
            etBuscar.setText("")
            etFecha.setText("")
            spEstado.setSelection(0)
            spCategoria.setSelection(0)
            spTipo.setSelection(0)
            mostrarLista(listaIncidenciasOriginal)
        }
    }

    // Al volver a esta pantalla se recarga la lista por si hubo cambios
    override fun onResume() {
        super.onResume()
        cargarLista()
    }

    // Carga los datos que se van a mostrar en los spinners de filtros
    private fun prepararFiltros() {
        val dbHelper = DatabaseHelper(this)
        val categoriaDAO = CategoriaDAO(dbHelper)

        listaCategorias = ArrayList(categoriaDAO.getAllCategorias())

        val estados = arrayListOf("Todos", "Pendiente", "Abierta", "En proceso", "Resuelta")
        val tipos = arrayListOf("Todos", "General", "Privada")

        val nombresCategorias = ArrayList<String>()
        nombresCategorias.add("Todas")

        for (categoria in listaCategorias) {
            nombresCategorias.add(categoria.getNombre())
        }

        spEstado.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, estados)
        spTipo.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tipos)
        spCategoria.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, nombresCategorias)
    }

    // Carga solo las incidencias que pertenecen al usuario actual
    private fun cargarLista() {
        val dbHelper = DatabaseHelper(this)
        val incidenciaDAO = IncidenciaDAO(dbHelper)

        listaIncidenciasOriginal = ArrayList(incidenciaDAO.getIncidenciasPorUsuario(idUsuario))
        mostrarLista(listaIncidenciasOriginal)
    }

    // Aplica todos los filtros seleccionados sobre la lista original
    private fun aplicarFiltros() {
        val textoBusqueda = etBuscar.text.toString().trim().lowercase(Locale.getDefault())
        val fechaBusqueda = etFecha.text.toString().trim().lowercase(Locale.getDefault())
        val estadoSeleccionado = spEstado.selectedItem.toString()
        val categoriaSeleccionada = spCategoria.selectedItem.toString()
        val tipoSeleccionado = spTipo.selectedItem.toString()

        val listaFiltrada = ArrayList<Incidencia>()

        for (incidencia in listaIncidenciasOriginal) {
            var cumple = true

            // Filtro por texto en título, descripción o ubicación
            if (textoBusqueda.isNotEmpty()) {
                val titulo = incidencia.getTitulo().lowercase(Locale.getDefault())
                val descripcion = incidencia.getDescripcion().lowercase(Locale.getDefault())
                val ubicacion = incidencia.getUbicacion().lowercase(Locale.getDefault())

                if (!titulo.contains(textoBusqueda) &&
                    !descripcion.contains(textoBusqueda) &&
                    !ubicacion.contains(textoBusqueda)
                ) {
                    cumple = false
                }
            }

            // Filtro por fecha
            if (cumple && fechaBusqueda.isNotEmpty()) {
                val fecha = incidencia.getFechaCreacion().lowercase(Locale.getDefault())
                if (!fecha.contains(fechaBusqueda)) {
                    cumple = false
                }
            }

            // Filtro por estado
            if (cumple && estadoSeleccionado != "Todos") {
                if (incidencia.getEstado() != estadoSeleccionado) {
                    cumple = false
                }
            }

            // Filtro por tipo
            if (cumple && tipoSeleccionado != "Todos") {
                if (incidencia.getTipo() != tipoSeleccionado) {
                    cumple = false
                }
            }

            // Filtro por categoría
            if (cumple && categoriaSeleccionada != "Todas") {
                var nombreCategoriaIncidencia = ""

                for (categoria in listaCategorias) {
                    if (categoria.getIdCategoria() == incidencia.getIdCategoria()) {
                        nombreCategoriaIncidencia = categoria.getNombre()
                    }
                }

                if (nombreCategoriaIncidencia != categoriaSeleccionada) {
                    cumple = false
                }
            }

            if (cumple) {
                listaFiltrada.add(incidencia)
            }
        }

        mostrarLista(listaFiltrada)
    }

    // Muestra en el RecyclerView la lista que se le pase
    private fun mostrarLista(lista: List<Incidencia>) {
        val adapter = IncidenciaAdapter(this, lista, "MIS", modoAdmin, idUsuario)
        recyclerView.adapter = adapter
    }
}