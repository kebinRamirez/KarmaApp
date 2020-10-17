package com.kebinr.karmaaplication.ui.content.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kebinr.karmaaplication.viewmodel.FirebaseAuthViewModel
import com.kebinr.karmaaplication.viewmodel.FirebaseRealTimeDBViewModel
import com.kebinr.karmaaplication.R
import com.kebinr.karmaaplication.model.Message
import com.kebinr.karmaaplication.ui.content.adapters.MessagesAdapter
import com.kebinr.karmaaplication.viewmodel.FirebaseFavorRTViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_messages.*
import kotlinx.android.synthetic.main.fragment_messages.view.*

@AndroidEntryPoint
class MessagesFragment : Fragment(R.layout.fragment_messages) {

    val firebaseAuthViewModel: FirebaseAuthViewModel by activityViewModels()
    val firebasefavorRTVM : FirebaseFavorRTViewModel by activityViewModels()
    val firebaseRealTimeDBViewModelViewModel : FirebaseRealTimeDBViewModel by activityViewModels()
    private val adapter =
        MessagesAdapter(ArrayList())

    var userUid : String = "_"
    var name2 : String ="_"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireView().messages_recycler.adapter = adapter
        requireView().messages_recycler.layoutManager = LinearLayoutManager(requireContext())
        val info: String? =requireArguments()!!.getString("usuarios")
        val secuencia = info!!.split(";")
        val userid1 = secuencia[0]
        val userid2 = secuencia[1]

        firebaseAuthViewModel.logged().observe(getViewLifecycleOwner(), Observer { uid ->
            Log.d("MyOut","MessagesFragment logged with "+uid)
            userUid = uid
            adapter.uid = uid
            firebasefavorRTVM.getuserInfo(userid1)
            firebaseRealTimeDBViewModelViewModel.getValues(userid1,userid2)
            firebaseRealTimeDBViewModelViewModel.ldMessageList.observe(getViewLifecycleOwner(), Observer { lista ->
                Log.d("MyOut","Número de mensajes "+lista.size)
                adapter.posts.clear()
                adapter.posts.addAll(lista)
                adapter.notifyDataSetChanged()
                messages_recycler.scrollToPosition(lista.size -1)
            })
            firebasefavorRTVM.user.observe(getViewLifecycleOwner(), Observer{
                name2 = it.nombre!!
                buttonWriteTest.setOnClickListener {
                    var chat = chatid.text.toString();
                    firebaseRealTimeDBViewModelViewModel.writeNewMessage(
                        Message(
                            (0..100).random(),
                            chat,
                            userid1,
                            userid2,
                            name2
                        )
                    )
                    chatid.setText("")
                    firebaseRealTimeDBViewModelViewModel.getValues(userid1,userid2)
                }
            })
        })
    }
}