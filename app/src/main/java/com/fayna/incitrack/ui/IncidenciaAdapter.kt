package com.fayna.incitrack.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fayna.incitrack.R
import com.fayna.incitrack.model.Incidencia


// Adapter del RecyclerView que muestra la lista de incidencias.
// Se encarga de crear cada item de la lista y rellenarlo con los datos de la incidencia.

class IncidenciaAdapter(
    private val context: Context,
    private val listaIncidencias: List<Incidencia>,
    private val origen: String,
    private val modoAdmin: Boolean,
    private val idUsuario: Int
) : RecyclerView.Adapter<IncidenciaAdapter.ViewHolder>() {

    // ViewHolder que guarda las referencias a los elementos de la vista
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitulo: TextView = itemView.findViewById(R.id.tvItemTitulo)
        val tvEstado: TextView = itemView.findViewById(R.id.tvItemEstado)
        val tvPrioridad: TextView = itemView.findViewById(R.id.tvItemPrioridad)
        val tvFecha: TextView = itemView.findViewById(R.id.tvItemFecha)
    }

    // Aquí se crea la vista de cada item de la lista usando el layout item_incidencia
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_incidencia, parent, false)
        return ViewHolder(view)
    }

    // Aquí se rellenan los datos de cada incidencia en la vista correspondiente
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val incidencia = listaIncidencias[position]

        holder.tvTitulo.text = incidencia.getTitulo()
        holder.tvEstado.text = "Estado: " + incidencia.getEstado()
        holder.tvFecha.text = "Fecha: " + incidencia.getFechaCreacion()

        // Si el usuario es administrador se muestra también la prioridad
        if (modoAdmin) {
            holder.tvPrioridad.visibility = View.VISIBLE

            val prioridadTexto = when (incidencia.getPrioridadCalculada()) {
                5, 6 -> "Alta"
                3, 4 -> "Media"
                else -> "Baja"
            }

            holder.tvPrioridad.text = "Prioridad: " + prioridadTexto
        } else {
            holder.tvPrioridad.visibility = View.GONE
        }

        // Al pulsar una incidencia se abre la pantalla de detalle
        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(context, PantallaDetalleIncidencia::class.java)
                intent.putExtra("idIncidencia", incidencia.getIdIncidencia())
                intent.putExtra("origen", origen)
                intent.putExtra("modoAdmin", modoAdmin)
                intent.putExtra("idUsuario", idUsuario)
                context.startActivity(intent)
            }
        })
    }

    // Devuelve el número de incidencias que hay en la lista
    override fun getItemCount(): Int {
        return listaIncidencias.size
    }
}