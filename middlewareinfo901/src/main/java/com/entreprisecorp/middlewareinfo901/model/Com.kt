package com.entreprisecorp.middlewareinfo901.model

import kotlinx.coroutines.flow.Flow

interface Com {

    fun sendTo(message: Message)
    fun broadcast(message: Message)

    fun onReceive(): Flow<Message>
    fun onBroadcast(): Flow<Message>

    fun requestSC()
    fun releaseSC()

    fun synchronize()
}