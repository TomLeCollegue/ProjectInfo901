package com.entreprisecorp.middlewareinfo901

import com.entreprisecorp.middlewareinfo901.model.Message
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

enum class Event(val event: String) {
    ON_BROADCAST("onBroadcast"),
    ON_RECEIVE("onReceive"),

}

class SocketDataSource(val com: ComSocket) {

    @OptIn(ExperimentalCoroutinesApi::class)
    fun onBroadcast(): Flow<Message> {
        return callbackFlow {
            com.socket.on(Event.ON_BROADCAST.event) {
                val message = Message(
                    text = it[0].toString(),
                    clock = it[1] as Int,
                    sender = it[2].toString()
                )
                com.clock = kotlin.math.max(com.clock, message.clock) + 1
                this@callbackFlow.trySend(message)
            }
            awaitClose()
        }
    }

    fun broadcast(message: Message) {
        com.socket.emit(Event.ON_BROADCAST.event, message.text, message.clock, message.sender)
    }

    fun sendTo(message: Message) {
        com.socket.emit(
            Event.ON_RECEIVE.event,
            message.text,
            message.clock,
            message.receiver,
            message.sender
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun onReceive(userName: String): Flow<Message> {
        return callbackFlow {
            com.socket.on(Event.ON_RECEIVE.event) {
                if (it[2] == userName || it[3] == userName) {
                    val message = Message(
                        text = it[0].toString(),
                        clock = it[1] as Int,
                        receiver = it[2].toString(),
                        sender = it[3].toString() + " (privée)"
                    )
                    this@callbackFlow.trySend(message)
                }
            }
            awaitClose()
        }
    }
}