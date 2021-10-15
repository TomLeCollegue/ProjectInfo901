package com.entreprisecorp.middlewareinfo901

import com.entreprisecorp.middlewareinfo901.model.Message
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

enum class Event(val event: String) {
    ON_BROADCAST("onBroadcast"),
    ON_RECEIVE("onReceive"),
    ON_RECEIVE_TOKEN("onReceiveToken"),
    ON_RECEIVE_ID("onReceiveId"),
    ON_NEW_PERSON_CONNECTED("onNewPersonConnected")
}

/**
 * Subscribe and send data and the bus
 */
class SocketDataSource(val com: ComSocket) {

    /**
     * Emit every time we receive a broadcast message
     */
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

    /**
     * send a broadcast message on the bus
     */
    fun broadcast(message: Message) {
        com.socket.emit(Event.ON_BROADCAST.event, message.text, message.clock, message.sender)
    }

    /**
     * Send a private message on the bus
     */
    fun sendTo(message: Message) {
        com.socket.emit(
            Event.ON_RECEIVE.event,
            message.text,
            message.clock,
            message.receiver,
            message.sender
        )
    }


    /**
     * Emit every time we receive a private message
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    fun onReceive(userName: String): Flow<Message> {
        return callbackFlow {
            com.socket.on(Event.ON_RECEIVE.event) {
                if (it[2] == userName || it[3] == userName) {
                    val message = Message(
                        text = it[0].toString(),
                        clock = it[1] as Int,
                        receiver = it[2].toString(),
                        sender = it[3].toString(),
                        isPrivate = true
                    )
                    this@callbackFlow.trySend(message)
                }
            }
            awaitClose()
        }
    }

    /**
     * Emit every time we receive the SC token
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    fun onReceiveToken(id: Int): Flow<String> {
        return callbackFlow {
            com.socket.on(Event.ON_RECEIVE_TOKEN.event) {
                if (it[1] == id) {
                    val token = it[0].toString()
                    this@callbackFlow.trySend(token)
                }
            }
            awaitClose()
        }
    }

    /**
     * /**
     * Emit when we receive an id from the server
    */
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    fun onReceiveId(): Flow<Int> {
        return callbackFlow {
            com.socket.on(Event.ON_RECEIVE_ID.event) {
                this@callbackFlow.trySend(it[0] as Int)
            }
            awaitClose()
        }
    }

    /**
     * Send the token to the next node on the bus
     */
    fun sendToken(id: Int, token: String) {
        com.socket.emit(Event.ON_RECEIVE_TOKEN.event, token, id)
    }

    /**
     * Emit every time a new person is connected
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    fun onNewPersonConnected(): Flow<Int> {
        return callbackFlow {
            com.socket.on(Event.ON_NEW_PERSON_CONNECTED.event) {
                this@callbackFlow.trySend(it[0] as Int)
            }
            awaitClose()
        }
    }
}