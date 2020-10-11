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
    val ldfavoresotroslist =MutableLiveData<List<Favor>>()
    val ldfavoresselected = MutableLiveData<List<Favor>>()
    val favoreslist = mutableListOf<Favor>()
    val favoresotroslist = mutableListOf<Favor>()
    val favoresselected = mutableListOf<Favor>()
    val user = MutableLiveData<User>()


    fun writeFavor(userid: String, favor: Favor){
        database.child("favores").push().setValue(favor)
        val databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userid)
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                var cuserid = snapshot.getValue(User::class.java)!!
                val hashMap : HashMap<String,Any> = HashMap()
                hashMap["favores"] =cuserid.favores!! + 1
                hashMap["karma"] = cuserid.karma!! - 2
                databaseReference.updateChildren(hashMap)
            }
        })
    }
    fun actualizarKarma(userid: String){
        val databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userid)
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                var cuserid = snapshot.getValue(User::class.java)!!
                val hashMap : HashMap<String,Any> = HashMap()
                hashMap["karma"] = cuserid.karma!! + 1
                databaseReference.updateChildren(hashMap)
            }
        })
    }
    fun actualizarFavor(userid: String){
        val databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userid)
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                var cuserid = snapshot.getValue(User::class.java)!!
                val hashMap : HashMap<String,Any> = HashMap()
                hashMap["favores"] = cuserid.favores!! - 1
                databaseReference.updateChildren(hashMap)
            }
        })
    }

    fun updateFavorStatus(userID: String,usertodo: String ,usertodoid: String ){
        val databaseReference = FirebaseDatabase.getInstance().getReference("favores")
        databaseReference.orderByChild("user_askingid").equalTo(userID).limitToFirst(1).ref.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (childDataSnapshot in snapshot.children) {
                    var favormodi  = childDataSnapshot.getValue(Favor::class.java)!!
                    var key = childDataSnapshot.key
                   if (favormodi.user_askingid == userID){
                       val hashMap : HashMap<String,Any> = HashMap()
                       hashMap["status"]= "Asignado"
                       hashMap["user_toDo"] =  usertodo
                       hashMap["user_toDoid"] = usertodoid
                       databaseReference.child(key!!).updateChildren(hashMap)
                   }
                }
            }
        })
    }
    fun updateFavorStatus2(userID: String){
        val databaseReference = FirebaseDatabase.getInstance().getReference("favores")
        databaseReference.orderByChild("user_askingid").equalTo(userID).limitToFirst(1).ref.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (childDataSnapshot in snapshot.children) {
                    var favormodi  = childDataSnapshot.getValue(Favor::class.java)!!
                    var key = childDataSnapshot.key
                    if (favormodi.user_askingid == userID){
                        val hashMap : HashMap<String,Any> = HashMap()
                        hashMap["status"]= "Completado"
                        databaseReference.child(key!!).updateChildren(hashMap)
                    }
                }
            }
        })
    }

    fun getuserInfo(userid : String){
        val databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userid)
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                var cuserid = snapshot.getValue(User::class.java)!!
                user.value =cuserid
            }
        })
    }

    fun getValues(userid: String){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                favoreslist.clear()
                favoresotroslist.clear()
                for (childDataSnapshot in dataSnapshot.children) {
                    var favor :Favor  = childDataSnapshot.getValue(Favor::class.java)!!
                    //Log.v("MyOut", "" + childDataSnapshot.getKey()); //displays the key for the node
                    //Log.v("MyOut", "" + message.id);
                    if (userid == favor.user_askingid && favor.status!= "Completado"){
                        favoreslist.add(favor)
                    }else{
                        if (favor.status=="Inicial"){
                            favoresotroslist.add(favor)
                        }
                    }
                }
                ldfavoreslist.value = favoreslist
                ldfavoresotroslist.value = favoresotroslist

            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("MyOut", "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        }
        database.child("favores").addValueEventListener(postListener)
    }
    fun getValues2(userid: String){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                favoresselected.clear()
                for (childDataSnapshot in dataSnapshot.children) {
                    var favor :Favor  = childDataSnapshot.getValue(Favor::class.java)!!
                    //Log.v("MyOut", "" + childDataSnapshot.getKey()); //displays the key for the node
                    //Log.v("MyOut", "" + message.id);
                    if (userid == favor.user_toDoid && favor.status == "Asignado"){
                        favoresselected.add(favor)
                    }
                }
                ldfavoresselected.value = favoresselected
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