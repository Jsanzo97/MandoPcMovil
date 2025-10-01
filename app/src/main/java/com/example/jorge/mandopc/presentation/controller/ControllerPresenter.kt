package com.example.jorge.mandopc.presentation.controller

import com.example.jorge.mandopc.presentation.controller.model.ControllerAction
import com.example.jorge.mandopc.presentation.controller.model.ControllerState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal interface ControllerPresenter {

    fun invokeAction(action: ControllerAction)

    val state: StateFlow<ControllerState>

}

internal class ControllerPresenterImpl: ControllerPresenter {
    private val _state = MutableStateFlow<ControllerState>(ControllerState.Ready)
    override val state: StateFlow<ControllerState>
        get() = _state.asStateFlow()

    override fun invokeAction(action: ControllerAction) {
        TODO("Not yet implemented")
    }

}