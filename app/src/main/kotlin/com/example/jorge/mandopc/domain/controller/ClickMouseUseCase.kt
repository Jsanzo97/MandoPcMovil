package com.example.jorge.mandopc.domain.controller

import com.example.jorge.mandopc.data.remote.SocketSender

internal interface ClickMouseUseCase {

    suspend fun rightClick()
    suspend fun leftClick()

}

internal class ClickMouseUseCaseImpl(
    val socketSender: SocketSender
): ClickMouseUseCase {

    override suspend fun rightClick() {
        socketSender.sendImmediate("right_click")
    }

    override suspend fun leftClick() {
        socketSender.sendImmediate("left_click")
    }
}