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

class TablonAdapter(
    private val context: Context,
    private val listaTablon: List<Tablon>,
    private val modoAdmin: Boolean
) : RecyclerView.Adapter<TablonAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTituloAviso: TextView = itemView.findViewById(R.id.tvTituloAviso)
        val tvFechaAviso: TextView = itemView.findViewById(R.id.tvFechaAviso)
        val btnBorrarAviso: Button = itemView.findViewById(R.id.btnBorrarAviso)
        val btnEditarAviso: Button = itemView.findViewById(R.id.btnEditarAviso)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tablon, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val aviso = listaTablon[position]

        holder.tvTituloAviso.text = aviso.getTitulo()
        holder.tvFechaAviso.text = "Fecha: " + aviso.getFechaPublicacion()

        // popup para leer el detalle (vecino y admin)
        holder.itemView.setOnClickListener {
            androidx.appcompat.app.AlertDialog.Builder(context)
                .setTitle(aviso.getTitulo())
                .setMessage(aviso.getTexto())
                .setPositiveButton("Cerrar", null)
                .show()
        }

        if (modoAdmin) {
            holder.btnBorrarAviso.visibility = View.VISIBLE
            holder.btnEditarAviso.visibility = View.VISIBLE

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

            holder.btnEditarAviso.setOnClickListener {
                val intent = Intent(context, PantallaEditarAviso::class.java)
                intent.putExtra("idAviso", aviso.getIdAviso())
                context.startActivity(intent)

                if (context is PantallaGestionTablon) context.finish()
            }

        } else {
            holder.btnBorrarAviso.visibility = View.GONE
            holder.btnEditarAviso.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return listaTablon.size
    }
}