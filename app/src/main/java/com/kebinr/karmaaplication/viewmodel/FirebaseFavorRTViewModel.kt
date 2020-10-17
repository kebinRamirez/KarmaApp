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
    val Movimientos = MutableLiveData<List<Favor>>()
    val karma = MutableLiveData<Int>()
    val favores = MutableLiveData<Int>()
    val favoreslist = mutableListOf<Favor>()
    val favoresotroslist2 = mutableListOf<Favor>()
    val ultimos3 = mutableListOf<Favor>()
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
                favores.value = cuserid.favores!! + 1
                hashMap["karma"] = cuserid.karma!! - 2
                karma.value = cuserid.karma!! - 2
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
                karma.value = cuserid.karma!! + 1
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
                favores.value =cuserid.favores!! - 1
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
                   if (favormodi.user_askingid == userID && favormodi.status!="Completado"){
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
        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                var cuserid = snapshot.getValue(User::class.java)!!
                user.value =cuserid
            }
        })
    }

    fun getValues(userid: String, tipoFavor : String){
        if (tipoFavor==""){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                favoreslist.clear()
                favoresotroslist.clear()
                for (childDataSnapshot in dataSnapshot.children) {
                    var favor :Favor  = childDataSnapshot.getValue(Favor::class.java)!!
                    if (userid == favor.user_askingid && favor.status!= "Completado"){
                        favoreslist.add(favor)
                    }else{
                        if (favor.status=="Inicial"){
                            favoresotroslist.add(favor)
                        }
                    }
                }
                ldfavoreslist.value = favoreslist
                //se revisan los usuarios
                val databaseReference = FirebaseDatabase.getInstance().getReference("users")
                databaseReference.orderByChild("karma").addValueEventListener(object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val users = ArrayList<User>()
                        for (dataSnapshot: DataSnapshot in snapshot.children) {
                            val user = dataSnapshot.getValue((User::class.java))
                            users.add(user!!)
                        }
                        //se organizan los usuarios por karma
                        val usersordenados = users.sortedWith(compareBy{it.karma}).reversed()
                        //se recorre lista de usuarios ordenados por karma para mostrar los favores en el mismo orden
                        println("Usuarios ordenados: "+usersordenados)
                        println("Favores: "+ favoresotroslist)
                        favoresotroslist2.clear()
                        for (usuario: User in usersordenados){
                            for (favor: Favor in favoresotroslist){
                                //se compara si el favor actual fue pedido por el user actual
                                if (usuario.key == favor.user_askingid){
                                    favoresotroslist2.add(favor)
                                }
                            }
                        }
                        ldfavoresotroslist.value = favoresotroslist2
                    }
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("MyOut", "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        }
        database.child("favores").orderByChild("user_asking").addValueEventListener(postListener)
        }else{
            val postListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    favoreslist.clear()
                    favoresotroslist.clear()
                    for (childDataSnapshot in dataSnapshot.children) {
                        var favor :Favor  = childDataSnapshot.getValue(Favor::class.java)!!
                        if (favor.status=="Inicial" && userid!= favor.user_asking && favor.type==tipoFavor){
                            favoresotroslist.add(favor)
                        }
                    }
                    ldfavoreslist.value = favoreslist
                    //se revisan los usuarios
                    val databaseReference = FirebaseDatabase.getInstance().getReference("users")
                    databaseReference.orderByChild("karma").addValueEventListener(object : ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val users = ArrayList<User>()
                            for (dataSnapshot: DataSnapshot in snapshot.children) {
                                val user = dataSnapshot.getValue((User::class.java))
                                users.add(user!!)
                            }
                            //se organizan los usuarios por karma
                            val usersordenados = users.sortedWith(compareBy{it.karma}).reversed()
                            //se recorre lista de usuarios ordenados por karma para mostrar los favores en el mismo orden
                            println("Usuarios ordenados: "+usersordenados)
                            println("Favores: "+ favoresotroslist)
                            favoresotroslist2.clear()
                            for (usuario: User in usersordenados){
                                for (favor: Favor in favoresotroslist){
                                    //se compara si el favor actual fue pedido por el user actual
                                    if (usuario.key == favor.user_askingid){
                                        favoresotroslist2.add(favor)
                                    }
                                }
                            }
                            ldfavoresotroslist.value = favoresotroslist2
                        }
                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }
                    })
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message
                    Log.w("MyOut", "loadPost:onCancelled", databaseError.toException())
                    // ...
                }
            }
            database.child("favores").orderByChild("user_asking").addValueEventListener(postListener)

        }
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
  
    fun getValues3(userid: String){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                favoreslist.clear()
                favoresotroslist.clear()
                ultimos3.clear()
                for (childDataSnapshot in dataSnapshot.children) {
                    var favor :Favor  = childDataSnapshot.getValue(Favor::class.java)!!
                    //Log.v("MyOut", "" + childDataSnapshot.getKey()); //displays the key for the node
                    //Log.v("MyOut", "" + message.id);
                    if (userid == favor.user_askingid || (userid == favor.user_toDoid &&  ( favor.status == "Asignado" || favor.status == "Completado"))){
                        favoreslist.add(favor)
                    }
                }
                if(favoreslist.size >= 3) {

                    ultimos3.add(favoreslist[favoreslist.size-1])
                    ultimos3.add(favoreslist[favoreslist.size - 2])
                    ultimos3.add(favoreslist[favoreslist.size - 3])
                    Movimientos.value = ultimos3
                }else {
                    Movimientos.value = favoreslist
                }


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