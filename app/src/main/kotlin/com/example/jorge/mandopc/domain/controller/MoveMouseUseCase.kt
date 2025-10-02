package com.example.jorge.mandopc.domain.controller

import com.example.jorge.mandopc.data.remote.SocketSender

internal interface MoveMouseUseCase {

    fun moveMouse(x: Int, y: Int)
    suspend fun dragFinish()

}

internal class MoveMouseUseCaseImpl(
    val socketSender: SocketSender
): MoveMouseUseCase {

    override fun moveMouse(x: Int, y: Int) {
        socketSender.sendThrottled("movement $x,$y")
    }

    override suspend fun dragFinish() {
        socketSender.sendImmediate("movement_finish")
    }
}