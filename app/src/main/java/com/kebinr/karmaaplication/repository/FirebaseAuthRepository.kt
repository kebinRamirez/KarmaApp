package com.kebinr.karmaaplication.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.kebinr.karmaaplication.model.User

class FirebaseAuthRepository {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database = Firebase.database.reference
    var logged = MutableLiveData<String>()
    var useremail = MutableLiveData<String>()
    var userCreated = MutableLiveData<Boolean>()

    init {
        logged.value = ""
        useremail.value= ""
    }

    private fun writeNewUser(user: User, uid: String){
        database.child("users").child(uid).setValue(user)
    }

    fun signUp(email: String, password : String, nombre: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("MyOut", "createUserWithEmail:success")
                    val user = auth.currentUser
                    if (user != null) {
                        writeNewUser(
                            User(
                                auth.currentUser!!.email!!,
                                2,
                                auth.currentUser!!.uid,
                                nombre,
                                0
                            ),auth.currentUser!!.uid
                        )
                    }
                    userCreated.value = true;
                } else {
                    Log.d("MyOut", "createUserWithEmail:failure", task.exception)
                    userCreated.value = false;
                }
            }
    }

    fun signIn(email: String, password : String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null) {
                        Log.d("MyOut", "signInWithEmailAndPassword:success " + user.email)
                        Log.d("MyOut", "signInWithEmailAndPassword:success " + user.uid)
                        logged.value = user.uid
                        useremail.value = user.email
                    }

                } else {
                    logged.value = ""
                    useremail.value= ""
                    Log.d("MyOut", "signInWithEmailAndPassword:failure", task.exception)
                }
            }
    }

    fun logOut(){
        logged.value = ""
        useremail.value= ""
        Firebase.auth.signOut()
    }


}