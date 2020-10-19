package com.kebinr.karmaaplication.ui.content.fragments


import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kebinr.karmaaplication.R
import com.kebinr.karmaaplication.model.Favor
import com.kebinr.karmaaplication.ui.content.MainActivity
import com.kebinr.karmaaplication.ui.content.adapters.FavoresAdapter
import com.kebinr.karmaaplication.viewmodel.FirebaseAuthViewModel
import com.kebinr.karmaaplication.viewmodel.FirebaseFavorRTViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.favor_dialog.view.*
import kotlinx.android.synthetic.main.fragment_favor.*
import kotlinx.android.synthetic.main.fragment_favor.view.*
import kotlinx.android.synthetic.main.fragment_perfil.*
import kotlinx.android.synthetic.main.nav_header_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*

@AndroidEntryPoint
class favorFragment : Fragment(R.layout.fragment_favor) , FavoresAdapter.onListIteration{
    val firebaseAuthViewModel: FirebaseAuthViewModel by activityViewModels()
    val firebasefavorRTVM : FirebaseFavorRTViewModel by activityViewModels()
    private val adapter =
        FavoresAdapter(ArrayList(),this)
    var userUid : String = "_"
    var name : String = "_"
    var karma : Int =0
    var favores: Int = 0
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
            firebasefavorRTVM.getuserInfo(userUid)
            firebasefavorRTVM.user.observe(getViewLifecycleOwner(), Observer{
                name = it.nombre!!
                (activity as AppCompatActivity).supportActionBar?.title =name
                (activity)?.NameNav?.setText(name)
                (activity)?.emailNav?.setText(it.email)
                karma = it.karma!!
                favores = it.favores!!

            })

            firebasefavorRTVM.getValues(userUid,"")
            firebasefavorRTVM.ldfavoreslist.observe(getViewLifecycleOwner(), Observer {
                Log.d("MyOut","Número de favores "+it.size)
                adapter.posts.clear()
                adapter.posts.addAll(it)
                adapter.notifyDataSetChanged()
            })
            firebasefavorRTVM.karma.observe(getViewLifecycleOwner(), Observer {
                karma = it
            })
            firebasefavorRTVM.favores.observe(getViewLifecycleOwner(), Observer {
                favores = it
            })
            button3.setOnClickListener{
                if (karma>=2 && favores<1){
                    //se crea cuadro de dialogo personalizado
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
                        firebasefavorRTVM.writeFavor(userUid, Favor(name,"",userUid,"",tipofavor,detalles,"Inicial",entrega))

                    }
                    mdialog.dialogCancelBtn.setOnClickListener(){
                        alerdialog.dismiss()
                    }
                }else{
                    if (karma<2 && favores==1){
                        //escribir que no se pudo por el karma y favores
                        MaterialAlertDialogBuilder(requireContext())
                            .setTitle("Favor")
                            .setMessage("su karma es menor a 2 (minimo de puntos requeridos para un favor) y ya tiene un favor activo")
                            .setPositiveButton("Aceptar") { dialog, which ->
                                // Respond to positive button press
                            }
                            .show()
                    }else{
                        if(karma<2){
                            //escribir que no se pudo por el karma
                            MaterialAlertDialogBuilder(requireContext())
                                .setTitle("Favor")
                                .setMessage("su karma es menor a 2 (minimo de puntos requeridos para un favor)")
                                .setPositiveButton("Aceptar") { dialog, which ->
                                    // Respond to positive button press
                                }
                                .show()
                        }else{
                            if (favores==1){
                                //escribir que no se pudo por favores
                                MaterialAlertDialogBuilder(requireContext())
                                    .setTitle("Favor")
                                    .setMessage("ya tiene un favor activo")
                                    .setPositiveButton("Aceptar") { dialog, which ->
                                        // Respond to positive button press
                                    }
                                    .show()
                            }
                        }
                    }
                }
                //se actualiza la data
                firebasefavorRTVM.getuserInfo(userUid)
                firebasefavorRTVM.user.observe(getViewLifecycleOwner(), Observer{
                    name = it.nombre!!
                    karma = it.karma!!
                    favores = it.favores!!
                })
            }
        })
    }
    override fun onListButtonInteractio(favor: Favor) {
        val navController = findNavController()
        var info : String = favor.user_askingid +";"+ favor.user_toDoid
        val bundle = bundleOf("usuarios" to info)
        navController.navigate(R.id.action_favorFragment_to_messagesFragment,bundle)
    }
}