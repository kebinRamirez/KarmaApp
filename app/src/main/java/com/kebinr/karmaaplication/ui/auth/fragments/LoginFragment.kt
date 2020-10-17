package com.kebinr.karmaaplication.ui.auth.fragments

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.kebinr.karmaaplication.R
import com.kebinr.karmaaplication.viewmodel.FirebaseAuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {
    //val sp: SharedPrefManager by activityViewModels()
    private val firebaseAuthViewModel: FirebaseAuthViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAuthViewModel.logged().observe(viewLifecycleOwner, Observer { uid ->
            Log.d("MyOut", "LoginFragment logged with $uid")
            if (uid != "") {
                /*
                Toast.makeText(
                    this.requireContext(), "Logged Ok." + uid,
                    Toast.LENGTH_SHORT
                ).show()
                */
                view.findNavController().navigate(R.id.action_loginFragment_to_mainActivity)
            }

        })
        button2.setOnClickListener() {
            view.findNavController().navigate(R.id.registroFragment)
        }
        button.setOnClickListener() {
            val email = editTextTextEmailAddress.text.toString()
            val password = editTextTextPassword.text.toString()
            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password))
                firebaseAuthViewModel.signIn(email, password)
            else
                Toast.makeText(requireContext(), "Debes llenar ambos campos", Toast.LENGTH_SHORT)
                    .show()
        }
    }
}