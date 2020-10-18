package com.kebinr.karmaaplication.ui.content.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kebinr.karmaaplication.R
import com.kebinr.karmaaplication.model.Favor
import com.kebinr.karmaaplication.ui.content.fragments.PerfilFragment
import kotlinx.android.synthetic.main.list_item_favores.view.Pedidopor
import kotlinx.android.synthetic.main.list_item_favores.view.detalles
import kotlinx.android.synthetic.main.list_item_favores.view.entrega
import kotlinx.android.synthetic.main.list_item_favores.view.estado
import kotlinx.android.synthetic.main.list_item_favores.view.tipofavor
import kotlinx.android.synthetic.main.list_item_favores_doing.view.*
import kotlinx.android.synthetic.main.list_item_movimientos.view.*

class PerfilAdapter(val posts: ArrayList<Favor>, private val Listener: PerfilFragment): RecyclerView.Adapter<PerfilAdapter.ViewHolder>() {

    var uid : String = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_movimientos , parent, false)
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

            if (uid != favor.user_askingid){
                itemView.cvp.setCardBackgroundColor(Color.parseColor("#3F51B5"))
                itemView.tipofavor.text = favor.type
                itemView.estado.text = "Estado: " +favor.status
                itemView.estado.setTextColor(Color.parseColor("#3F51B5"))
                itemView.Pedidopor.text = "Pedido por: " + favor.user_asking
                itemView.Pedidopor.setTextColor(Color.parseColor("#3F51B5"))
                itemView.detalles.text = "Detalles: " + favor.details
                itemView.detalles.setTextColor(Color.parseColor("#3F51B5"))
                itemView.entrega.text = "Punto de entrega: " + favor.delivery
                itemView.entrega.setTextColor(Color.parseColor("#3F51B5"))
            }else {
                itemView.cvp.setCardBackgroundColor(Color.parseColor("#4CAF50"))
                itemView.tipofavor.text = favor.type
                itemView.estado.text = "Estado: " + favor.status
                itemView.estado.setTextColor(Color.parseColor("#4CAF50"))
                itemView.Pedidopor.text = "Pedido por: " + favor.user_asking
                itemView.Pedidopor.setTextColor(Color.parseColor("#4CAF50"))
                itemView.detalles.text = "Detalles: " + favor.details
                itemView.detalles.setTextColor(Color.parseColor("#4CAF50"))
                itemView.entrega.text = "Punto de entrega: " + favor.delivery
                itemView.entrega.setTextColor(Color.parseColor("#4CAF50"))
            }

        }

    }

}