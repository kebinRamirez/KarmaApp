package com.kebinr.karmaaplication.ui.content.fragments

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
import com.kebinr.karmaaplication.R
import com.kebinr.karmaaplication.model.Favor
import com.kebinr.karmaaplication.ui.content.adapters.FavoresDoingAdapter
import com.kebinr.karmaaplication.ui.content.adapters.FavoresOtrosAdapter
import com.kebinr.karmaaplication.viewmodel.FirebaseAuthViewModel
import com.kebinr.karmaaplication.viewmodel.FirebaseFavorRTViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favor.view.*
import kotlinx.android.synthetic.main.nav_header_main.*

@AndroidEntryPoint
class FavoresDoingFragment : Fragment(R.layout.fragment_favores_doing), FavoresDoingAdapter.onListIteration {

    val firebaseAuthViewModel: FirebaseAuthViewModel by activityViewModels()
    val firebasefavorRTVM : FirebaseFavorRTViewModel by activityViewModels()
    private val adapter =
        FavoresDoingAdapter(
            ArrayList(),
            this
        )
    var userUid : String = "_"
    var name : String = "_"
    var karma : Int =0
    var favores: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favores_doing, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireView().favores_recycler.adapter = adapter
        requireView().favores_recycler.layoutManager = LinearLayoutManager(requireContext())

        firebaseAuthViewModel.logged().observe(getViewLifecycleOwner(), Observer {
            userUid = it
            adapter.uid = it
            firebasefavorRTVM.getuserInfo(userUid)
            firebasefavorRTVM.user.observe(getViewLifecycleOwner(), Observer{
                name = it.nombre!!
                karma = it.karma!!
                favores = it.favores!!
            })
            firebasefavorRTVM.getValues2(userUid)
            firebasefavorRTVM.ldfavoresselected.observe(getViewLifecycleOwner(), Observer {
                Log.d("MyOut","Número de favores "+it.size)
                adapter.posts.clear()
                adapter.posts.addAll(it)
                adapter.notifyDataSetChanged()
            })
        })
    }

    override fun onListButtonInteraction(favor: Favor, uid: String) {
        firebasefavorRTVM.updateFavorStatus2(favor.user_askingid!!)
        firebasefavorRTVM.actualizarKarma(uid)
        firebasefavorRTVM.actualizarFavor(favor.user_askingid)
    }

    override fun onListButtonInteraction(favor: Favor) {
        //ya se verá que se hace
        val navController = findNavController()
        var info : String = favor.user_toDoid +";"+favor.user_askingid
        val bundle = bundleOf("usuarios" to info)
        navController.navigate(R.id.action_favoresDoingFragment_to_messagesFragment,bundle)
    }


}