package com.example.jorge.mandopc.data.remote

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.DataOutputStream
import java.io.PrintWriter
import java.net.InetSocketAddress
import java.net.Socket

internal class SocketSender() {
    private var socket: Socket? = null
    private var writer: PrintWriter? = null
    private var dataOutputStream: DataOutputStream? = null
    private var isConnected = false
    private val messageQueue = Channel<String>(capacity = Channel.Factory.UNLIMITED)
    private var sendJob: Job? = null
    private var lastSendTime = 0L
    private val minSendInterval = 16L

    suspend fun connect(ip: String, port: Int = 7070, timeoutMs: Int = 5000): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            if (isConnected) {
                return@withContext Result.success(Unit)
            }

            socket = Socket().apply {
                connect(InetSocketAddress(ip, port), timeoutMs)

                tcpNoDelay = true
                sendBufferSize = 8192
                keepAlive = true
                soTimeout = 0
            }

            writer = PrintWriter(socket!!.getOutputStream(), true)
            dataOutputStream = DataOutputStream(socket!!.getOutputStream())
            isConnected = true

            startSendJob()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun sendImmediate(message: String): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            if (!isConnected) {
                return@withContext Result.failure(IllegalStateException("Not connected"))
            }

            writer?.println(message)
            writer?.flush() // Force immediate send
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun send(message: String) {
        if (isConnected) {
            messageQueue.trySend(message)
        }
    }

    fun sendThrottled(message: String) {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastSendTime >= minSendInterval) {
            send(message)
            lastSendTime = currentTime
        }
    }

    suspend fun disconnect() = withContext(Dispatchers.IO) {
        try {
            isConnected = false
            sendJob?.cancel()
            messageQueue.close()

            writer?.close()
            dataOutputStream?.close()
            socket?.close()

            writer = null
            dataOutputStream = null
            socket = null
        } catch (e: Exception) {
            // Ignore
        }
    }

    fun isConnected(): Boolean = isConnected

    private fun startSendJob() {
        sendJob = CoroutineScope(Dispatchers.IO).launch {
            try {
                for (message in messageQueue) {
                    if (!isConnected) break
                    writer?.println(message)
                }
            } catch (e: Exception) {
                disconnect()
            }
        }
    }
}