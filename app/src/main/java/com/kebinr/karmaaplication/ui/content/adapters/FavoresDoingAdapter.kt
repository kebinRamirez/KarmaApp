package com.kebinr.karmaaplication.ui.content.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.kebinr.karmaaplication.R
import com.kebinr.karmaaplication.model.Favor
import kotlinx.android.synthetic.main.list_item_favores_doing.view.*

class FavoresDoingAdapter(val posts: ArrayList<Favor>, private val llListener: onListIteration): RecyclerView.Adapter<FavoresDoingAdapter.ViewHolder>() {

    var uid : String = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_favores_doing , parent, false)
        return ViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val favor = posts[position]
        holder.bind(posts[position], uid)
        holder.button.setOnClickListener{
            //ver que parametros se le mandan
            llListener?.onListButtonInteraction(favor,uid)
        }
        holder.button2.setOnClickListener{
            llListener?.onListButtonInteraction(favor)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(favor: Favor, uid: String) {
            if (uid != favor.user_askingid){
                itemView.tipofavor2.text = favor.type
                itemView.estado2.text = "Estado: " +favor.status
                itemView.Pedidopor2.text = "Pedido por: " + favor.user_asking
                itemView.detalles2.text = "Detalles: " + favor.details
                itemView.entrega2.text = "Punto de entrega: " + favor.delivery
            }
        }
        val button : Button = itemView.end
        val button2 : Button = itemView.chat
    }
    interface onListIteration{
        fun onListButtonInteraction(favor: Favor,uid: String)
        fun onListButtonInteraction(favor: Favor)
    }
}