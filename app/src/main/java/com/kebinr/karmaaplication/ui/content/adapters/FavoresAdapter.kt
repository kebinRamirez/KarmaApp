package com.kebinr.karmaaplication.ui.content.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kebinr.karmaaplication.R
import com.kebinr.karmaaplication.model.Favor
import kotlinx.android.synthetic.main.list_item_favores.view.*

class FavoresAdapter(val posts: ArrayList<Favor>): RecyclerView.Adapter<FavoresAdapter.ViewHolder>() {

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
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(favor: Favor, uid: String) {
            if (uid == favor.user_askingid){
                itemView.setBackgroundColor(Color.GREEN)
                itemView.tipofavor.text = favor.type
                itemView.estado.text = "Estado: " +favor.status
                itemView.Pedidopor.text = "Pedido por: " + favor.user_asking
                itemView.detalles.text = "Detalles: " + favor.details
                itemView.entrega.text = "Punto de entrega: " + favor.delivery
            }
        }
    }
}