package com.kebinr.karmaaplication.ui.content

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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_messages.*
import kotlinx.android.synthetic.main.fragment_messages.view.*

@AndroidEntryPoint
class MessagesFragment : Fragment(R.layout.fragment_messages) {

    val firebaseAuthViewModel: FirebaseAuthViewModel by activityViewModels()
    val firebaseRealTimeDBViewModelViewModel : FirebaseRealTimeDBViewModel by activityViewModels()
    private val adapter =
        MessagesAdapter(ArrayList())

    var userUid : String = "_"
    var userUid2 : String = "_"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("MyOut","MessagesFragment onViewCreated")

        requireView().messages_recycler.adapter = adapter
        requireView().messages_recycler.layoutManager = LinearLayoutManager(requireContext())

        firebaseAuthViewModel.logged().observe(getViewLifecycleOwner(), Observer { uid ->
            Log.d("MyOut","MessagesFragment logged with "+uid)
            userUid = uid
            adapter.uid = uid
        })

        firebaseRealTimeDBViewModelViewModel.ldMessageList.observe(getViewLifecycleOwner(), Observer { lista ->
            Log.d("MyOut","Número de mensajes "+lista.size)
            adapter.posts.clear()
            adapter.posts.addAll(lista)
            adapter.notifyDataSetChanged()
            messages_recycler.scrollToPosition(lista.size -1)
        })

        buttonWriteTest.setOnClickListener {
            userUid = firebaseAuthViewModel.logged().value!!

            Log.d("MyOut","Writing message for user <"+userUid+">")
            var chat = chatid.text.toString();
            firebaseRealTimeDBViewModelViewModel.writeNewMessage(
                Message(
                    (0..100).random(),
                    chat,
                    userUid

                )
            )
            chatid.setText("")
        }

        buttonLogOut.setOnClickListener {
            firebaseAuthViewModel.logOut()
            view.findNavController().navigate(R.id.action_messagesFragment_to_authActivity)
        }
    }
}