package com.kebinr.karmaaplication.ui.content.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.kebinr.karmaaplication.viewmodel.FirebaseAuthViewModel
import androidx.lifecycle.Observer
import androidx.fragment.app.activityViewModels
import com.kebinr.karmaaplication.R
import com.kebinr.karmaaplication.ui.content.adapters.FavoresAdapter
import com.kebinr.karmaaplication.viewmodel.FirebaseFavorRTViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint



class ProfileFragment: Fragment(R.layout.fragment_profile) {
    var name : String = "_"
    var userUid : String = "_"
    var karma : Int =0
    var favores: Int = 0
    private val adapter =
        FavoresAdapter(ArrayList(),this)
    val firebaseAuthViewModel: FirebaseAuthViewModel by activityViewModels()
    val firebasefavorRTVM : FirebaseFavorRTViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        firebaseAuthViewModel.logged().observe(getViewLifecycleOwner(), Observer {
            userUid = it
            firebasefavorRTVM.getuserInfo(userUid)
            firebasefavorRTVM.user.observe(getViewLifecycleOwner(), Observer{
                name = it.nombre!!
                karma = it.karma!!
                favores = it.favores!!
            })
            firebasefavorRTVM.getValues(userUid)
            firebasefavorRTVM.ldfavoreslist.observe(getViewLifecycleOwner(), Observer {
                Log.d("MyOut","Número de favores "+it.size)
                adapter.posts.clear()
                adapter.posts.addAll(it)
                adapter.notifyDataSetChanged()
            })


        })

        }


}