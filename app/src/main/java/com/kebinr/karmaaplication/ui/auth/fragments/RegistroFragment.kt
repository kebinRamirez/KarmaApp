package com.kebinr.karmaaplication.ui.auth.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kebinr.karmaaplication.viewmodel.FirebaseAuthViewModel
import com.kebinr.karmaaplication.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_registro.*

@AndroidEntryPoint
class RegistroFragment : Fragment(R.layout.fragment_registro){
    lateinit var navController: NavController
    val firebaseAuthViewModel: FirebaseAuthViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        firebaseAuthViewModel.userCreated().observe(getViewLifecycleOwner(), Observer { status ->
            if (status == true){
                Toast.makeText(
                    this.requireContext(), "Authentication Ok.",
                    Toast.LENGTH_SHORT
                ).show()
                navController!!.navigate(R.id.action_registroFragment_to_loginFragment)
            } else {
                Toast.makeText(
                    this.requireContext(), "Authentication failed.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
        view.findViewById<Button>(R.id.button2).setOnClickListener(){
            // se registra usuario
            if (editTextTextPassword.text.toString().equals(editTextTextPassword2.text.toString()) && editTextTextEmailAddress.text.toString()!="" &&editTextTextPersonName.text.toString()!=""){
                //bien, las contraseñas coinciden
                firebaseAuthViewModel.signUp(editTextTextEmailAddress.text.toString(),editTextTextPassword.text.toString(),editTextTextPersonName.text.toString())
                editTextTextPassword.setText("")
                editTextTextPassword2.setText("")
                editTextTextEmailAddress.setText("")
                editTextTextPersonName.setText("")
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Registro")
                    .setMessage("Registrado Correctamente")
                    .setPositiveButton("Aceptar") { dialog, which ->
                        // Respond to positive button press
                    }
                    .show()

            }else{
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Registro")
                    .setMessage("Contraseñas no coinciden a campo de usuario vacio")
                    .setPositiveButton("Aceptar") { dialog, which ->
                        // Respond to positive button press
                    }
                    .show()
            }

        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegistroFragment().apply {
            }
    }
}