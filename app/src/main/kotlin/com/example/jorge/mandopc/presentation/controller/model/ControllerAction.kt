package com.example.jorge.mandopc.presentation.controller.model

internal sealed class ControllerAction {

    data class CreateConnection(val ip: String): ControllerAction()
    data object RightClick : ControllerAction()
    data object LeftClick : ControllerAction()
    data class MoveMouse(val x: Int, val y: Int) : ControllerAction()
    data object DragFinish : ControllerAction()
    data object Disconnect : ControllerAction()

}