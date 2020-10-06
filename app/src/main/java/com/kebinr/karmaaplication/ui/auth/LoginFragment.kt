package com.kebinr.karmaaplication.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.kebinr.karmaaplication.viewmodel.FirebaseAuthViewModel
import com.kebinr.karmaaplication.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    val firebaseAuthViewModel: FirebaseAuthViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAuthViewModel.logged().observe(getViewLifecycleOwner(), Observer { uid ->
            Log.d("MyOut","LoginFragment logged with "+uid)
            if (uid != ""){
                Toast.makeText(
                    this.requireContext(), "Logged Ok."+uid,
                    Toast.LENGTH_SHORT
                ).show()
                view.findNavController().navigate(R.id.action_loginFragment_to_mainActivity)
            } else {
                Toast.makeText(
                    this.requireContext(), "Logged failed.",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
        button2.setOnClickListener(){
            view.findNavController().navigate(R.id.registroFragment)
        }
        button.setOnClickListener(){
            firebaseAuthViewModel.signIn(editTextTextEmailAddress.text.toString(),editTextTextPassword.text.toString())
        }
    }
}