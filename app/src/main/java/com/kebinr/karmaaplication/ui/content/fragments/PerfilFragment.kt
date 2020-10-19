package com.kebinr.karmaaplication.ui.content.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kebinr.karmaaplication.R
import com.kebinr.karmaaplication.model.Favor
import com.kebinr.karmaaplication.ui.content.adapters.FavoresAdapter
import com.kebinr.karmaaplication.ui.content.adapters.PerfilAdapter
import com.kebinr.karmaaplication.viewmodel.FirebaseAuthViewModel
import com.kebinr.karmaaplication.viewmodel.FirebaseFavorRTViewModel
import kotlinx.android.synthetic.main.favor_dialog.view.*
import kotlinx.android.synthetic.main.fragment_favor.*
import kotlinx.android.synthetic.main.fragment_favor.view.*
import kotlinx.android.synthetic.main.fragment_favor.view.favores_recycler
import kotlinx.android.synthetic.main.fragment_perfil.*
import kotlinx.android.synthetic.main.fragment_perfil.view.*
import kotlinx.android.synthetic.main.nav_header_main.*

class PerfilFragment : Fragment(R.layout.fragment_perfil) {

    val firebaseAuthViewModel: FirebaseAuthViewModel by activityViewModels()
    val firebasefavorRTVM : FirebaseFavorRTViewModel by activityViewModels()
    private val adapter = PerfilAdapter(ArrayList(),this)
    var userUid : String = "_"
    var name : String = "_"
    var karma : Int =0
    var favores: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_perfil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireView().MovimientoRecycler.adapter = adapter
        requireView().MovimientoRecycler.layoutManager = LinearLayoutManager(requireContext())

        firebaseAuthViewModel.logged().observe(getViewLifecycleOwner(), Observer {
            userUid = it
            adapter.uid = it
            firebasefavorRTVM.getuserInfo(userUid)
            firebasefavorRTVM.user.observe(getViewLifecycleOwner(), Observer{
                name = it.nombre!!
                karma = it.karma!!
                favores = it.favores!!

            })
            firebasefavorRTVM.karma.observe(getViewLifecycleOwner(), Observer{
                karma = it
                karmaP.text = karma.toString()
            })
            NombreP.text = name
            karmaP.text = karma.toString()

            firebasefavorRTVM.getValues3(userUid)
            firebasefavorRTVM.Movimientos.observe(getViewLifecycleOwner(), Observer {
                Log.d("MyOut","Número de favores "+it.size)
                adapter.posts.clear()
                adapter.posts.addAll(it)
                adapter.notifyDataSetChanged()
            })



        })
    }

}