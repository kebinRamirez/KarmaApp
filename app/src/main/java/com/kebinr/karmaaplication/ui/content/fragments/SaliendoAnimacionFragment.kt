package com.kebinr.karmaaplication.ui.content.fragments

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.kebinr.karmaaplication.R
import com.kebinr.karmaaplication.viewmodel.FirebaseAuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SaliendoAnimacionFragment : Fragment(R.layout.fragment_saliendo_animacion) {

    val firebaseAuthViewModel: FirebaseAuthViewModel by activityViewModels()
    lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saliendo_animacion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAuthViewModel.logOut()
        val handler = Handler()
        handler.postDelayed({
            navController.navigate(R.id.authActivity)
        }, 1000)


    }
}