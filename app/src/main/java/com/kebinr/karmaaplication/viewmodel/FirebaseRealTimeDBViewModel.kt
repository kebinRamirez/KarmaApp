package com.kebinr.karmaaplication.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.kebinr.karmaaplication.model.Message

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

    /*  fun writeTest(){
          val myRef = database.getReference("lista")
          myRef.setValue(Message(1,"Hola"))
          // Read from the database
          myRef.addValueEventListener(object : ValueEventListener {
              override fun onDataChange(dataSnapshot: DataSnapshot) {
                  // This method is called once with the initial value and again
                  // whenever data at this location is updated.
                  val value = dataSnapshot.getValue<Message>()
                  Log.d("MyOut", "Value is: ${value.toString()}")
              }

              override fun onCancelled(error: DatabaseError) {
                  // Failed to read value
                  Log.w("MyOut", "Failed to read value.", error.toException())
              }
          })

      }*/
}