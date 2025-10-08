package com.example.jorge.mandopc.presentation.controller

import com.example.jorge.mandopc.domain.controller.ClickMouseUseCase
import com.example.jorge.mandopc.domain.controller.HandleConnectionUseCase
import com.example.jorge.mandopc.domain.controller.MoveMouseUseCase
import com.example.jorge.mandopc.domain.controller.ScrollUseCase
import com.example.jorge.mandopc.domain.controller.SendKeyUseCase
import com.example.jorge.mandopc.presentation.controller.model.ControllerAction
import com.example.jorge.mandopc.presentation.controller.model.ControllerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal interface ControllerPresenter {

    fun invokeAction(action: ControllerAction)

    val state: StateFlow<ControllerState>

}

internal class ControllerPresenterImpl(
    val scope: CoroutineScope,
    val handleConnectionUseCase: HandleConnectionUseCase,
    val clickMouseUseCase: ClickMouseUseCase,
    val moveMouseUseCase: MoveMouseUseCase,
    val sendKeyUseCase: SendKeyUseCase,
    val scrollUseCase: ScrollUseCase
): ControllerPresenter {
    private val _state = MutableStateFlow<ControllerState>(ControllerState.Ready)
    override val state: StateFlow<ControllerState>
        get() = _state.asStateFlow()

    override fun invokeAction(action: ControllerAction) {
        scope.launch {
            when(action) {
                is ControllerAction.CreateConnection -> {
                    handleConnectionUseCase.createConnection(action.ip)
                    _state.emit(ControllerState.Connected)
                }

                ControllerAction.LeftClick -> {
                    clickMouseUseCase.leftClick()
                }

                ControllerAction.RightClick -> {
                    clickMouseUseCase.rightClick()
                }

                ControllerAction.DragFinish -> {
                    moveMouseUseCase.dragFinish()
                }

                is ControllerAction.MoveMouse -> {
                    moveMouseUseCase.moveMouse(action.x, action.y)
                }

                ControllerAction.Disconnect -> {
                    handleConnectionUseCase.removeConnection()
                }

                is ControllerAction.KeyPressed -> {
                    sendKeyUseCase.invoke(action.key)
                }

                is ControllerAction.Scroll -> {
                    scrollUseCase.invoke(action.x, action.y)
                }
            }
        }
    }
}