package com.example.jorge.mandopc.domain.controller

import com.example.jorge.mandopc.data.remote.SocketSender

internal interface ScrollUseCase {

    operator fun invoke(scrollX: Int, scrollY: Int)

}

internal class ScrollUseCaseImpl(
    val socketSender: SocketSender
): ScrollUseCase {

    override fun invoke(scrollX: Int, scrollY: Int) {
        socketSender.sendThrottled("Scroll $scrollX,$scrollY")
    }
}