package com.kebinr.karmaaplication.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.kebinr.karmaaplication.model.Favor
import com.kebinr.karmaaplication.model.User

class FirebaseFavorRTViewModel: ViewModel() {
    val database = Firebase.database.reference
    val ldfavoreslist = MutableLiveData<List<Favor>>()
    val favoreslist = mutableListOf<Favor>()
    val user = MutableLiveData<String>()
    init{
        getValues()
    }

    fun writeFavor(favor1: String, favor: Favor){
        database.child("favores").push().setValue(favor)
    }

    fun updateFavorStatus(userID: String, status: String){
        database.child("favores").child("status").push().setValue(status)
    }

    fun getuserInfo(userid : String){
        val databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userid)
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                var cuserid = snapshot.getValue(User::class.java)!!
                user.value =cuserid.nombre
            }
        })
    }

    fun getValues(){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                favoreslist.clear()
                for (childDataSnapshot in dataSnapshot.children) {
                    val favor: Favor = childDataSnapshot.getValue(
                        Favor::class.java)!!
                    //Log.v("MyOut", "" + childDataSnapshot.getKey()); //displays the key for the node
                    //Log.v("MyOut", "" + message.id);
                    favoreslist.add(favor)
                }
                ldfavoreslist.value = favoreslist

            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("MyOut", "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        }
        database.child("favores").addValueEventListener(postListener)

    }
}