package com.entreprisecorp.projectinfo901.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.entreprisecorp.projectinfo901.model.Message

class HomeFragmentViewModel:ViewModel() {

    val liveDataMessage = MutableLiveData<List<Message>>()

    fun addMessage(message: Message){
        val listMessage = liveDataMessage.value?.toMutableList() ?: mutableListOf()

        listMessage.add(message)
        liveDataMessage.postValue(listMessage)
    }
}