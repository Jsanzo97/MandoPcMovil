package com.example.jorge.mandopc.domain.controller

import com.example.jorge.mandopc.data.remote.SocketSender

internal interface SendKeyUseCase {

    suspend operator fun invoke(key: String)

}

internal class SendKeyUseCaseImpl(
    val socketSender: SocketSender
): SendKeyUseCase {

    override suspend fun invoke(key: String) {
        socketSender.sendImmediate(key)
    }
}