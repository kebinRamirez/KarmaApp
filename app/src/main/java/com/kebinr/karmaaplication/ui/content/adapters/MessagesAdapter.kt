package com.kebinr.karmaaplication.ui.content.adapters

import android.graphics.Color
import android.icu.text.Transliterator
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kebinr.karmaaplication.R
import com.kebinr.karmaaplication.model.Message
import kotlinx.android.synthetic.main.list_item_post.view.*

class MessagesAdapter(val posts: ArrayList<Message>): RecyclerView.Adapter<MessagesAdapter.ViewHolder>() {

    var uid : String = ""
    var mes: String =""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View
        if( viewType == 1){
            view =
                LayoutInflater.from(parent.context).inflate(R.layout.list_item_post2, parent, false)
        }else{
            view =
                LayoutInflater.from(parent.context).inflate(R.layout.list_item_post, parent, false)
        }

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
        fun bind(message: Message, uid: String) {

            itemView.message.text = message.text
            if (uid == message.user){
                //itemView.setBackgroundColor(Color.GREEN)
                itemView.user.text = "me"
                itemView.user.gravity = Gravity.RIGHT
                itemView.message.gravity = Gravity.RIGHT
            } else {
               // itemView.setBackgroundColor(Color.GRAY)
                itemView.user.text = message.destino
                itemView.user.gravity = Gravity.LEFT
                itemView.message.gravity = Gravity.LEFT
            }
        }
    }

   override fun getItemViewType(position: Int): Int {
        if(posts[position].user != null){
            if(uid == posts[position].user){
                return 1
            }else{
                return -1
            }
        }else{
            return -1
        }


        //return super.getItemViewType(position)
    }
}
