package com.entreprisecorp.projectinfo901.viewmodels

import androidx.lifecycle.*
import com.entreprisecorp.middlewareinfo901.ComSocket
import com.entreprisecorp.middlewareinfo901.model.Com
import com.entreprisecorp.middlewareinfo901.model.Message
import com.entreprisecorp.projectinfo901.model.MessageUi

class HomeFragmentViewModel(val middleware: Com, userName: String) : ViewModel() {

    val broadcastedMessage: LiveData<Message> =
        middleware.onBroadcast().asLiveData(timeoutInMs = 0L)

    val reveivedMessage: LiveData<Message> =
        middleware.onReceive(userName).asLiveData(timeoutInMs = 0L)

    val isRequestingSC = (middleware as ComSocket).isRequestingSC

    val token = (middleware as ComSocket).token

    fun broadcastMessage(message: Message) = middleware.broadcast(message)
    fun sendMessage(message: Message) = middleware.sendTo(message)

    fun releaseSC() = middleware.releaseSC()
    fun requestSC() = middleware.requestSC()

}

class HomeFragmentViewModelFactory(
    private val middleware: Com,
    private val userName: String
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeFragmentViewModel(
            middleware = middleware,
            userName = userName
        ) as T
    }
}