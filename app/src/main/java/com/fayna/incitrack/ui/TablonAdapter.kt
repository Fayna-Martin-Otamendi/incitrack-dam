package com.fayna.incitrack.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fayna.incitrack.R
import com.fayna.incitrack.dao.TablonDAO
import com.fayna.incitrack.db.DatabaseHelper
import com.fayna.incitrack.model.Tablon


// Adapter del RecyclerView que muestra los avisos del tablón.
// Se usa tanto para vecinos como para administrador.

class TablonAdapter(
    private val context: Context,
    private val listaTablon: List<Tablon>,
    private val modoAdmin: Boolean
) : RecyclerView.Adapter<TablonAdapter.ViewHolder>() {

    // ViewHolder que guarda las referencias a los elementos del layout
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTituloAviso: TextView = itemView.findViewById(R.id.tvTituloAviso)
        val tvFechaAviso: TextView = itemView.findViewById(R.id.tvFechaAviso)
        val btnBorrarAviso: Button = itemView.findViewById(R.id.btnBorrarAviso)
        val btnEditarAviso: Button = itemView.findViewById(R.id.btnEditarAviso)
    }

    // Se crea la vista de cada elemento del RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tablon, parent, false)
        return ViewHolder(view)
    }

    // Aquí se rellenan los datos de cada aviso
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val aviso = listaTablon[position]

        holder.tvTituloAviso.text = aviso.getTitulo()
        holder.tvFechaAviso.text = "Fecha: " + aviso.getFechaPublicacion()

        // Al pulsar el aviso se muestra el contenido completo en un diálogo
        holder.itemView.setOnClickListener {
            androidx.appcompat.app.AlertDialog.Builder(context)
                .setTitle(aviso.getTitulo())
                .setMessage(aviso.getTexto())
                .setPositiveButton("Cerrar", null)
                .show()
        }

        // Si el usuario es administrador se muestran los botones de gestión
        if (modoAdmin) {
            holder.btnBorrarAviso.visibility = View.VISIBLE
            holder.btnEditarAviso.visibility = View.VISIBLE

            // Borrar aviso
            holder.btnBorrarAviso.setOnClickListener {
                androidx.appcompat.app.AlertDialog.Builder(context)
                    .setTitle("Borrar aviso")
                    .setMessage("¿Seguro que quieres borrar este aviso?")
                    .setPositiveButton("Sí") { _, _ ->
                        val dbHelper = DatabaseHelper(context)
                        val tablonDAO = TablonDAO(dbHelper)
                        tablonDAO.deleteTablon(aviso)

                        context.startActivity(Intent(context, PantallaGestionTablon::class.java))
                        if (context is PantallaGestionTablon) context.finish()
                    }
                    .setNegativeButton("No", null)
                    .show()
            }

            // Editar aviso
            holder.btnEditarAviso.setOnClickListener {
                val intent = Intent(context, PantallaEditarAviso::class.java)
                intent.putExtra("idAviso", aviso.getIdAviso())
                context.startActivity(intent)

                if (context is PantallaGestionTablon) context.finish()
            }

        } else {
            // Los vecinos solo pueden ver los avisos
            holder.btnBorrarAviso.visibility = View.GONE
            holder.btnEditarAviso.visibility = View.GONE
        }
    }

    // Número total de avisos que hay en la lista
    override fun getItemCount(): Int {
        return listaTablon.size
    }
}