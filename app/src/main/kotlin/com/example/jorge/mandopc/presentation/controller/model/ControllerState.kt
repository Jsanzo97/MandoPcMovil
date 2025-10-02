package com.example.jorge.mandopc.presentation.controller.model

internal sealed class ControllerState {

    data object Ready : ControllerState()
    data object Connected: ControllerState()
    data object Disconnected: ControllerState()

}
