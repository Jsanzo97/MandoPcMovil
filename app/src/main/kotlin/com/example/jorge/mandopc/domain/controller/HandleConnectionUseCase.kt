package com.example.jorge.mandopc.domain.controller

import com.example.jorge.mandopc.data.remote.SocketSender

internal interface HandleConnectionUseCase {

    suspend fun createConnection(ip: String)
    suspend fun removeConnection()


}

internal class HandleConnectionUseCaseImpl(
    val socketSender: SocketSender
): HandleConnectionUseCase {

    override suspend fun createConnection(ip: String) {
        socketSender.connect(ip)
    }

    override suspend fun removeConnection() {
        socketSender.disconnect()
    }
}