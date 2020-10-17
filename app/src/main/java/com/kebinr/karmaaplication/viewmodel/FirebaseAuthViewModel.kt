package com.kebinr.karmaaplication.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.kebinr.karmaaplication.repository.FirebaseAuthRepository

class FirebaseAuthViewModel
@ViewModelInject constructor(
    private val repository: FirebaseAuthRepository
) : ViewModel() {

    fun logged() = repository.logged

    fun userCreated() = repository.userCreated

    fun signUp(email: String, password: String, nombre: String) {
        repository.signUp(email, password, nombre)
    }

    fun signIn(email: String, password: String) {
        repository.signIn(email, password)
    }

    fun logOut() {
        repository.logOut()
    }

}