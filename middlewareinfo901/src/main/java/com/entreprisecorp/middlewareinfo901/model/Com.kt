package com.entreprisecorp.middlewareinfo901.model

import kotlinx.coroutines.flow.Flow

interface Com {

    var clock: Int

    fun sendTo(message: Message)
    fun broadcast(message: Message)

    fun onReceive(userName: String): Flow<Message>
    fun onBroadcast(): Flow<Message>

    fun requestSC()
    fun releaseSC()

    fun synchronize()
}