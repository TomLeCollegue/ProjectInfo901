package com.entreprisecorp.middlewareinfo901

import com.entreprisecorp.middlewareinfo901.model.Com
import com.entreprisecorp.middlewareinfo901.model.Message
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.flow.Flow

class ComSocket : Com {

    private val clock: Int = 0

    private val socket: Socket by lazy {
        IO.socket("http://192.168.86.171:3000")
    }

    init {
        socket.connect()
    }

    private val dataSource: SocketDataSource by lazy {
        SocketDataSource(
            socket = socket,
            clock = clock
        )
    }

    override fun sendTo(message: Message) {
    }

    override fun broadcast(message: Message) = dataSource.broadcast(message)

    override fun onReceive(): Flow<Message> {
        TODO("Not yet implemented")
    }

    override fun onBroadcast(): Flow<Message> = dataSource.onBroadcast()

    override fun requestSC() {
    }

    override fun releaseSC() {
    }

    override fun synchronize() {
        TODO("Not yet implemented")
    }


}