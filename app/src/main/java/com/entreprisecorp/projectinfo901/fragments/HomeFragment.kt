package com.entreprisecorp.projectinfo901.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.entreprisecorp.middlewareinfo901.ComSocket
import com.entreprisecorp.middlewareinfo901.model.Message
import com.entreprisecorp.projectinfo901.App
import com.entreprisecorp.projectinfo901.MainActivity
import com.entreprisecorp.projectinfo901.R
import com.entreprisecorp.projectinfo901.databinding.FragmentHomeBinding
import com.entreprisecorp.projectinfo901.fastitem.MessageItem
import com.entreprisecorp.projectinfo901.model.MessageUi
import com.entreprisecorp.projectinfo901.viewmodels.HomeFragmentViewModel
import com.entreprisecorp.projectinfo901.viewmodels.HomeFragmentViewModelFactory
import com.mikepenz.fastadapter.adapters.GenericFastItemAdapter

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var binding: FragmentHomeBinding? = null

    private val adapter = GenericFastItemAdapter()

    private val viewModel : HomeFragmentViewModel by viewModels {
        HomeFragmentViewModelFactory((activity as MainActivity).middleware)
    } //le view model va disparaître quand le fragment est quitté.

    private var listMessage = arrayListOf<Message>()

    //récupérer les arguments
    private val args : HomeFragmentArgs by navArgs()

    fun refreshScreen() {
        val itemList = listMessage.map {
            MessageItem(it.sender,it.text)
        }

        adapter.setNewList(itemList)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        binding?.recyclerView?.adapter = adapter
        binding?.recyclerView?.layoutManager = LinearLayoutManager(activity)
        refreshScreen()

        (activity as AppCompatActivity).supportActionBar?.title = args.userName

        viewModel.broadcastedMessage.observe(viewLifecycleOwner){
            listMessage += it
            refreshScreen()
        }

        binding?.sendMessageButton?.setOnClickListener {
            val textMessage = binding?.messageEditText?.text.toString()
            viewModel.sendMessage(Message(textMessage, 0, sender = args.userName))
        }
    }
}