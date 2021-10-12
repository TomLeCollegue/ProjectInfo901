package com.entreprisecorp.projectinfo901.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.entreprisecorp.middlewareinfo901.model.Message
import com.entreprisecorp.projectinfo901.MainActivity
import com.entreprisecorp.projectinfo901.R
import com.entreprisecorp.projectinfo901.databinding.FragmentHomeBinding
import com.entreprisecorp.projectinfo901.fastitem.MessageItem
import com.entreprisecorp.projectinfo901.fastitem.MessageSendItem
import com.entreprisecorp.projectinfo901.setColor
import com.entreprisecorp.projectinfo901.setDrawableEnd
import com.entreprisecorp.projectinfo901.viewmodels.HomeFragmentViewModel
import com.entreprisecorp.projectinfo901.viewmodels.HomeFragmentViewModelFactory
import com.mikepenz.fastadapter.GenericItem
import com.mikepenz.fastadapter.adapters.GenericFastItemAdapter

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var binding: FragmentHomeBinding? = null

    private val adapter = GenericFastItemAdapter()

    private val viewModel: HomeFragmentViewModel by viewModels {
        HomeFragmentViewModelFactory((activity as MainActivity).middleware, args.userName)
    } //le view model va disparaître quand le fragment est quitté.

    private var listMessage = arrayListOf<Message>()

    //récupérer les arguments
    private val args: HomeFragmentArgs by navArgs()

    private fun refreshScreen() {
        val itemList = arrayListOf<GenericItem>()
        listMessage.forEach {
            if (it.sender == args.userName) {
                itemList += MessageSendItem(it.receiver ?: "tout le monde", it.text, it.isPrivate)
            } else {
                itemList += MessageItem(it.sender, it.text, it.isPrivate)
            }
        }

        adapter.setNewList(itemList)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        binding?.recyclerView?.adapter = adapter
        binding?.recyclerView?.layoutManager = LinearLayoutManager(activity)
        refreshScreen()

        (activity as MainActivity).username = args.userName
        (activity as AppCompatActivity).supportActionBar?.title =
            (activity as MainActivity).username


        viewModel.broadcastedMessage.observe(viewLifecycleOwner) {
            listMessage += it
            refreshScreen()
        }


        viewModel.reveivedMessage.observe(viewLifecycleOwner) {
            listMessage += it
            refreshScreen()
        }

        viewModel.token.observe(viewLifecycleOwner) {
            setButtonRequestSC()
            binding?.apply {
                sendPrivateButton.isEnabled = it != null
                broadcastButton.isEnabled = it != null
            }
        }

        viewModel.isRequestingSC.observe(viewLifecycleOwner) {
            setButtonRequestSC()
        }

        binding?.requestScButton?.setOnClickListener {
            if(viewModel.isRequestingSC.value == true) {
                viewModel.releaseSC()
            } else {
                viewModel.requestSC()
            }
        }



        binding?.broadcastButton?.setOnClickListener {
            val textMessage = binding?.messageEditText?.text.toString()
            viewModel.broadcastMessage(
                Message(
                    textMessage,
                    0,
                    sender = (activity as MainActivity).username
                )
            )
        }

        binding?.sendPrivateButton?.setOnClickListener {
            val usernameReceiver = binding?.usernameText?.text.toString()
            val textMessage = binding?.messageEditText?.text.toString()
            viewModel.sendMessage(
                Message(
                    textMessage,
                    0,
                    sender = (activity as MainActivity).username,
                    receiver = usernameReceiver
                )
            )
        }
    }

    private fun setButtonRequestSC() {
        val isRequestingSC = viewModel.isRequestingSC.value == true
        val token = viewModel.token.value != null
        binding?.requestScButton?.apply {
            when {
                isRequestingSC && token -> {
                    setText(R.string.releaseSC)
                    binding?.progressBar?.isVisible = false
                    setDrawableEnd(R.drawable.ic_unlock, context)
                    setColor(R.color.purple_200, context)
                }
                isRequestingSC && !token -> {
                    setText(R.string.loadingSC)
                    binding?.progressBar?.isVisible = true
                    setDrawableEnd(null, context)
                    setColor(R.color.teal_700, context)
                }
                !isRequestingSC -> {
                    setText(R.string.AskSC)
                    binding?.progressBar?.isVisible = false
                    setDrawableEnd(R.drawable.ic_lock, context)
                    setColor(android.R.color.holo_green_light, context)
                }
            }
        }
    }
}