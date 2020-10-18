package com.kebinr.karmaaplication.ui.content.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.kebinr.karmaaplication.R
import com.kebinr.karmaaplication.model.Favor
import kotlinx.android.synthetic.main.list_item_favores.view.*
import kotlinx.android.synthetic.main.list_item_favores.view.Pedidopor
import kotlinx.android.synthetic.main.list_item_favores.view.detalles
import kotlinx.android.synthetic.main.list_item_favores.view.entrega
import kotlinx.android.synthetic.main.list_item_favores.view.estado
import kotlinx.android.synthetic.main.list_item_favores.view.tipofavor
import kotlinx.android.synthetic.main.list_item_movimientos.view.*

class FavoresAdapter(val posts: ArrayList<Favor>, private val Listener: onListIteration): RecyclerView.Adapter<FavoresAdapter.ViewHolder>() {

    var uid : String = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_favores , parent, false)
        return ViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(posts[position], uid)

        holder.button.setOnClickListener{
            //ver que parametros se le mandan
            Listener?.onListButtonInteractio(posts[position])
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(favor: Favor, uid: String) {
            if (uid == favor.user_askingid){
                itemView.tipofavor.text = favor.type
                itemView.estado.text = "Estado: " +favor.status
                itemView.Pedidopor.text = "Pedido por: " + favor.user_asking
                itemView.detalles.text = "Detalles: " + favor.details
                itemView.entrega.text = "Punto de entrega: " + favor.delivery
                itemView.chat2.isEnabled = favor.status == "Asignado"
            }
        }
        val button : Button = itemView.chat2
    }
    interface onListIteration{
        fun onListButtonInteractio(favor: Favor)
    }
}