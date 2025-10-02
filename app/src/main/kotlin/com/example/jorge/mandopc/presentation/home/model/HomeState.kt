package com.example.jorge.mandopc.presentation.home.model

internal sealed class HomeState {
    data class Ready(
        val defaultIp: String = ""
    ): HomeState ()

    data class Success(
        val ip: String
    ): HomeState()
}