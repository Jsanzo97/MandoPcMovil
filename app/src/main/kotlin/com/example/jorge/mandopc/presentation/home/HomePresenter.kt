package com.example.jorge.mandopc.presentation.home

import com.example.jorge.mandopc.domain.home.GetStoredDefaultIpUseCase
import com.example.jorge.mandopc.domain.home.RemoveDefaultIpUseCase
import com.example.jorge.mandopc.domain.home.SaveDefaultIpUseCase
import com.example.jorge.mandopc.presentation.home.model.HomeAction
import com.example.jorge.mandopc.presentation.home.model.HomeState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal interface HomePresenter {
    fun invokeAction(action: HomeAction)
    val state: StateFlow<HomeState>
}

internal class HomePresenterImpl(
    val scope: CoroutineScope,
    val getStoredDefaultIpUseCase: GetStoredDefaultIpUseCase,
    val removeDefaultIpUseCase: RemoveDefaultIpUseCase,
    val saveDefaultIpUseCase: SaveDefaultIpUseCase,
): HomePresenter {
    private val _state = MutableStateFlow<HomeState>(HomeState.Ready())
    override val state: StateFlow<HomeState>
        get() = _state.asStateFlow()


    override fun invokeAction(action: HomeAction) {
        when (action) {
            is HomeAction.RetrieveDefaultIp -> getDefaultIp()
            is HomeAction.Connect -> onConnect(action.ip, action.isDefault)
        }
    }

    private fun getDefaultIp() {
        scope.launch {
            val ip = getStoredDefaultIpUseCase.invoke()
            _state.emit(HomeState.Ready(ip))
        }
    }

    private fun onConnect(ip: String, isDefault: Boolean) {
        scope.launch {
            if (isDefault) {
                val currentSavedIp = getStoredDefaultIpUseCase()
                if (ip != currentSavedIp) {
                    removeDefaultIpUseCase()
                }
                saveDefaultIpUseCase(ip)
            }
            _state.emit(HomeState.Success(ip))
        }
    }
}
