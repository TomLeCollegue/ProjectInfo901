package com.entreprisecorp.projectinfo901.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.entreprisecorp.middlewareinfo901.Distribleware
import com.entreprisecorp.projectinfo901.R
import com.entreprisecorp.projectinfo901.databinding.FragmentHomeBinding
import com.entreprisecorp.projectinfo901.fastitem.MessageItem
import com.mikepenz.fastadapter.GenericItem
import com.mikepenz.fastadapter.adapters.GenericFastItemAdapter

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val distribleware = Distribleware()
    val text: String = distribleware.testFunction()

    private var binding: FragmentHomeBinding? = null

    private val adapter = GenericFastItemAdapter()

    fun refreshScreen() {
        val itemList = arrayListOf<GenericItem>()
        itemList += MessageItem(
            nameSender = "Toto",
            message = "Hello"
        )
        itemList += MessageItem(
            nameSender = "Toto",
            message = "Hello"
        )
        itemList += MessageItem(
            nameSender = "Toto",
            message = "Hello"
        )

        adapter.setNewList(itemList)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        binding?.recyclerView?.adapter = adapter
        binding?.recyclerView?.layoutManager = LinearLayoutManager(activity)
        refreshScreen()
    }
}