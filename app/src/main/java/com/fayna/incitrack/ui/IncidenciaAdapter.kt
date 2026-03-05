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

class IncidenciaAdapter(
    private val context: Context,
    private val listaIncidencias: List<Incidencia>,
    private val origen: String,
    private val modoAdmin: Boolean,
    private val idUsuario: Int
) : RecyclerView.Adapter<IncidenciaAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitulo: TextView = itemView.findViewById(R.id.tvItemTitulo)
        val tvEstado: TextView = itemView.findViewById(R.id.tvItemEstado)
        val tvFecha: TextView = itemView.findViewById(R.id.tvItemFecha)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_incidencia, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val incidencia = listaIncidencias[position]
        holder.tvTitulo.text = incidencia.getTitulo()
        holder.tvEstado.text = "Estado: " + incidencia.getEstado()
        holder.tvFecha.text = "Fecha: " + incidencia.getFechaCreacion()

        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(context, PantallaDetalleIncidencia::class.java)
                intent.putExtra("idIncidencia", incidencia.getIdIncidencia())
                intent.putExtra("origen", origen) // "LISTADO" o "MIS"
                intent.putExtra("modoAdmin", modoAdmin)
                intent.putExtra("idUsuario", idUsuario)
                context.startActivity(intent)
            }
        })
    }

    override fun getItemCount(): Int {
        return listaIncidencias.size
    }
}