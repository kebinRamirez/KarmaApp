package com.kebinr.karmaaplication.viewmodel

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.kebinr.karmaaplication.model.Message
import com.kebinr.karmaaplication.model.User
import kotlinx.coroutines.processNextEventInCurrentThread
import java.util.*
import kotlin.collections.HashMap

class FirebaseRealTimeDBViewModel : ViewModel(){
    val database = Firebase.database.reference

    var ldMessageList = MutableLiveData<List<Message>>()
    val messageList = mutableListOf<Message>()


    init{
        getValues()
    }


    fun writeNewMessage(message: Message){
        database.child("messages").push().setValue(message)
    }

    fun getuserInfo(userid : String){
        val databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userid)
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                val cuserdata = snapshot.getValue(User::class.java)
                val prueba: String = cuserdata!!.nombre!!
            }
        })
    }

    fun getValues(){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                messageList.clear()
                for (childDataSnapshot in dataSnapshot.children) {
                    val message: Message = childDataSnapshot.getValue(
                        Message::class.java)!!
                    //Log.v("MyOut", "" + childDataSnapshot.getKey()); //displays the key for the node
                    //Log.v("MyOut", "" + message.id);
                    messageList.add(message)
                }
                ldMessageList.value = messageList

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("MyOut", "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        }
        database.child("messages").addValueEventListener(postListener)

    }

}