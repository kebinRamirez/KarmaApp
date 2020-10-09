package com.kebinr.karmaaplication.ui.content

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kebinr.karmaaplication.R
import com.kebinr.karmaaplication.model.Favor
import com.kebinr.karmaaplication.viewmodel.FirebaseAuthViewModel
import com.kebinr.karmaaplication.viewmodel.FirebaseFavorRTViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.favor_dialog.view.*
import kotlinx.android.synthetic.main.fragment_favor.*
import kotlinx.android.synthetic.main.fragment_favor.view.*
import kotlinx.android.synthetic.main.fragment_messages.buttonLogOut

@AndroidEntryPoint
class favorFragment : Fragment(R.layout.fragment_favor) {
    val firebaseAuthViewModel: FirebaseAuthViewModel by activityViewModels()
    val firebasefavorRTVM : FirebaseFavorRTViewModel by activityViewModels()
    private val adapter =
        FavoresAdapter(ArrayList())
    var userUid : String = "_"
    var correo : String = "_"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireView().favores_recycler.adapter = adapter
        requireView().favores_recycler.layoutManager = LinearLayoutManager(requireContext())
        firebaseAuthViewModel.logged().observe(getViewLifecycleOwner(), Observer {
            userUid = it
            adapter.uid = it
        })
        firebasefavorRTVM.ldfavoreslist.observe(getViewLifecycleOwner(), Observer {
            Log.d("MyOut","Número de favores "+it.size)
            adapter.posts.clear()
            adapter.posts.addAll(it)
            adapter.notifyDataSetChanged()
        })

        button3.setOnClickListener{
            val mdialog = getLayoutInflater().inflate(R.layout.favor_dialog, null)
            val builder = AlertDialog.Builder(requireContext()).setView(mdialog)
            val alerdialog = builder.show()
            mdialog.enviar.setOnClickListener{
                alerdialog.dismiss()
                var tipofavor =""
                if (mdialog.rb1.isChecked()){
                    tipofavor="Sacar fotocopias"
                }
                if (mdialog.rb2.isChecked()){
                    tipofavor = "Comprar km5"
                }
                if (mdialog.rb3.isChecked()){
                    tipofavor = "Buscar domicilio puerta 7"
                }
                val detalles = mdialog.detalles.text.toString()
                val entrega = mdialog.entrega.text.toString()

                userUid = firebaseAuthViewModel.logged().value!!
                correo = firebaseAuthViewModel.email().value!!
                firebasefavorRTVM.writeFavor(userUid, Favor(correo,"",userUid,"",tipofavor,detalles,"Inicial",entrega))
            }
            mdialog.dialogCancelBtn.setOnClickListener(){
                alerdialog.dismiss()
            }


        }

        buttonLogOut.setOnClickListener {
            firebaseAuthViewModel.logOut()
            view.findNavController().navigate(R.id.action_favorFragment_to_authActivity)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            favorFragment().apply {

            }
    }
}