package com.entreprisecorp.middlewareinfo901

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.entreprisecorp.middlewareinfo901.model.Com
import com.entreprisecorp.middlewareinfo901.model.Message
import com.entreprisecorp.middlewareinfo901.model.log
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Create a connexion the bus, send private and broadcast messages with SC
 * @param ip the ip adress on the node server
 * @param lifecycleOwner lifecycleOwner of your app
 * @param delaySendToken the delay awaited before sending the SC token to next node
 * @see ComSocket.Builder
 */
class ComSocket private constructor(
    ip: String,
    private val lifecycleOwner: LifecycleOwner,
    private val delaySendToken: Int
) : Com {

    override var clock: Int = 0
    override var id: Int = 0
    override var userCount: Int = 0

    private val _token = MutableLiveData<String?>()
    val token: LiveData<String?> = _token

    private val _isRequestingSC = MutableLiveData<Boolean>()
    val isRequestingSC: LiveData<Boolean> = _isRequestingSC


    val socket: Socket by lazy {
        IO.socket(ip)
    }

    init {
        socket.connect()
        _isRequestingSC.postValue(false)
    }

    private val dataSource: SocketDataSource by lazy {
        SocketDataSource(
            this
        )
    }

    // Launch the observers on newPerson and ID endpoints
    init {
        onNewPersonConnected()
        onReceiveId()
    }

    /**
     * To Build a communication via socket with Critical Section
     * @param ipAdress the ip adress of the node server
     * @param lifecycleOwner lifecycleOwner used to listen Server and observe the datas
     * @param delaySendToken the time the system is keeping the token before sending it to the next node
     * @see build
     */
    class Builder(
        private val ipAdress: String = "http://10.7.41.185:3000",
        private val lifecycleOwner: LifecycleOwner,
        private val delaySendToken: Int = 1000
    ) {

        /**
         * Create a ComSocket
         */
        fun build(): ComSocket {
            return ComSocket(ipAdress, lifecycleOwner, delaySendToken)
        }
    }

    /**
     * Send a private message
     * @param message the message sent
     */
    override fun sendTo(message: Message) {
        dataSource.sendTo(message)
    }

    /**
     * Send a message to everyone on the bus
     * @param message the message sent
     */
    override fun broadcast(message: Message) = dataSource.broadcast(message)

    /**
     * Allow you to collect the received private messages
     * @param userName your username for filtering the private messages
     */
    override fun onReceive(userName: String): Flow<Message> = dataSource.onReceive(userName)

    /**
     * Allow you to collect the received broadcast messages
     */
    override fun onBroadcast(): Flow<Message> = dataSource.onBroadcast()

    /**
     * Request the critical section
     */
    override fun requestSC() = _isRequestingSC.postValue(true)

    /**
     * Release the critical section
     */
    override fun releaseSC() = _isRequestingSC.postValue(false)

    override fun synchronize() {
        TODO("Not yet implemented")
    }

    /**
     * Manage the critical section token
     * send the token is critical section released,
     * send the token to the next if we dont request the SC
     */
    override fun manageReceiveToken() {
        isRequestingSC.observe(lifecycleOwner) {
            if (!it) {
                sendTokenWithDelay()
            }
        }

        lifecycleOwner.lifecycleScope.launch {
            dataSource.onReceiveToken(id).collect {
                "received token $it".log()
                _token.postValue(it)
            }
        }

        token.observe(lifecycleOwner) {
            if (isRequestingSC.value == false) {
                sendTokenWithDelay()
            }
        }

    }

    /**
     * Send the token to the next with delay to avoid error on demo
     */
    private fun sendTokenWithDelay() {
        Handler(Looper.getMainLooper()).postDelayed(
            {
                sendTokenToNext()
            },
            delaySendToken.toLong()
        )
    }

    /**
     * Send the token to the next node on the bus
     */
    private fun sendTokenToNext() {
        token.value?.let { token ->
            if (id == userCount - 1) {
                "send token to 0".log()
                dataSource.sendToken(0, token)
            } else {
                "send token to ${id + 1}".log()
                dataSource.sendToken(id + 1, token)
            }
            // We clear the token after sending it
            _token.postValue(null)
        }
    }

    /**
     * Update the id coming from the server
     */
    private fun onReceiveId() {
        lifecycleOwner.lifecycleScope.launch {
            dataSource.onReceiveId().collect {
                "identifiant: $it".log()
                id = it

                // Once we get our ID, we can manage the tokens
                manageReceiveToken()
                //Create new token if we are the first one connected
                if (id == 0) {
                    createNewToken()
                }
            }
        }
    }

    /**
     * Create new token for SC if we are the first node on the bus
     */
    private fun createNewToken() {
        _token.postValue("testSocket")
    }

    /**
     * Allow you to collect if new persons are connected on the bus and update the userCount
     */
    private fun onNewPersonConnected() {
        lifecycleOwner.lifecycleScope.launch {
            dataSource.onNewPersonConnected().collect {
                "nombre de personnes connectées : $it".log()
                userCount = it
            }
        }
    }


}