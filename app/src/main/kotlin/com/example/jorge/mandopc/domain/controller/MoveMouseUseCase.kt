package com.example.jorge.mandopc.domain.controller

import com.example.jorge.mandopc.data.remote.SocketSender

internal interface MoveMouseUseCase {

    fun moveMouse(dragX: Int, dragY: Int)
    suspend fun dragFinish()

}

internal class MoveMouseUseCaseImpl(
    val socketSender: SocketSender
): MoveMouseUseCase {

    override fun moveMouse(dragX: Int, dragY: Int) {
        socketSender.sendThrottled("movement $dragX,$dragY")
    }

    override suspend fun dragFinish() {
        socketSender.sendImmediate("movement_finish")
    }
}