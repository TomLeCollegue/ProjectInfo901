package com.entreprisecorp.middlewareinfo901

import com.entreprisecorp.middlewareinfo901.model.Message
import io.socket.client.Socket
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

enum class Event(val event: String) {
    ON_BROADCAST("onBroadcast"),
    ON_RECEIVE("onReceive"),

}

class SocketDataSource(private val socket: Socket, val clock: Int) {

    @OptIn(ExperimentalCoroutinesApi::class)
    fun onBroadcast(): Flow<Message> {
        return callbackFlow {
            socket.on(Event.ON_BROADCAST.event) {
                val message = Message(
                    text = it[0].toString(),
                    clock = it[1] as Int,
                    sender = it[2].toString()
                )
                this@callbackFlow.trySend(message)
            }
            awaitClose()
        }
    }

    fun broadcast(message: Message) {
        socket.io().emit(Event.ON_BROADCAST.event, message.text, message.clock, message.sender)
    }


}