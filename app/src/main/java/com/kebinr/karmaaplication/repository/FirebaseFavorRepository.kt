package com.kebinr.karmaaplication.repository

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.kebinr.karmaaplication.model.Favor

class FirebaseFavorRepository {
    val database = Firebase.database.reference

    fun writeFavor(userID: String, favor : Favor){
        database.child("favores").child(userID).setValue(favor)
    }

    fun updateFavorStatus(userID: String, status: String){
        database.child("favores").child(userID).child("status").setValue(status)


    }
}