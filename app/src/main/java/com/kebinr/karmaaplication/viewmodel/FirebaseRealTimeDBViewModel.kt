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
import com.kebinr.karmaaplication.model.Favor
import com.kebinr.karmaaplication.model.Message
import com.kebinr.karmaaplication.model.User
import kotlinx.coroutines.processNextEventInCurrentThread
import java.util.*
import kotlin.collections.HashMap

class FirebaseRealTimeDBViewModel : ViewModel(){
    val database = Firebase.database.reference

    var ldMessageList = MutableLiveData<List<Message>>()
    val messageList = mutableListOf<Message>()

    fun writeNewMessage(message: Message){
        database.child("messages").push().setValue(message)
    }

    fun getValues(userid1: String, userid2: String){
        val databaseReference = FirebaseDatabase.getInstance().getReference("messages")
        databaseReference.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                messageList.clear()
                for (childDataSnapshot in snapshot.children) {
                    var mensaje  = childDataSnapshot.getValue(Message::class.java)!!
                    if ((mensaje.user == userid1 && mensaje.destinoid == userid2)||(mensaje.user == userid2 && mensaje.destinoid==userid1)){
                        messageList.add(mensaje)
                    }
                }
                ldMessageList.value = messageList
            }
        })
    }

}