package com.entreprisecorp.projectinfo901.viewmodels

import androidx.lifecycle.*
import com.entreprisecorp.middlewareinfo901.model.Com
import com.entreprisecorp.middlewareinfo901.model.Message
import com.entreprisecorp.projectinfo901.model.MessageUi

class HomeFragmentViewModel(val middleware: Com) : ViewModel() {

    val liveDataMessage = MutableLiveData<List<MessageUi>>()
    val broadcastedMessage: LiveData<Message> = middleware.onBroadcast().asLiveData(timeoutInMs = 0L)

    fun addMessage(messageUi: MessageUi) {
        val listMessage = liveDataMessage.value?.toMutableList() ?: mutableListOf()

        listMessage.add(messageUi)
        liveDataMessage.postValue(listMessage)
    }

    fun sendMessage(message: Message){
        middleware.broadcast(message)
    }

}

class HomeFragmentViewModelFactory(private val middleware: Com) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeFragmentViewModel(middleware = middleware) as T
    }
}