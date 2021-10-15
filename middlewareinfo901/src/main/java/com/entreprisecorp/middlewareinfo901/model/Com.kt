package com.entreprisecorp.middlewareinfo901.model

import kotlinx.coroutines.flow.Flow

interface Com {

    var clock: Int
    var id: Int
    val userCount: Int

    fun sendTo(message: Message)
    fun broadcast(message: Message)

    fun onReceive(userName: String): Flow<Message>
    fun onBroadcast(): Flow<Message>

    fun requestSC()
    fun releaseSC()

    fun synchronize()

    fun manageReceiveToken()
}