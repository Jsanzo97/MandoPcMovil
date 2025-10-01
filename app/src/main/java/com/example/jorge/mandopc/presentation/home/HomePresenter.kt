package com.example.jorge.mandopc.presentation.home

import com.example.jorge.mandopc.domain.GetStoredDefaultIpUseCase
import com.example.jorge.mandopc.domain.RemoveDefaultIpUseCase
import com.example.jorge.mandopc.domain.SaveDefaultIpUseCaseImpl
import com.example.jorge.mandopc.presentation.home.model.HomeAction
import com.example.jorge.mandopc.presentation.home.model.HomeState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal interface HomePresenter {
    fun invokeAction(action: HomeAction)
    val state: StateFlow<HomeState>
}

internal class HomePresenterImpl: HomePresenter, KoinComponent {
    private val scope: CoroutineScope by inject()
    private val getStoredDefaultIpUseCase: GetStoredDefaultIpUseCase by inject()
    private val removeDefaultIpUseCase: RemoveDefaultIpUseCase by inject()
    private val saveDefaultIpUseCase: SaveDefaultIpUseCaseImpl by inject()

    private val _state = MutableStateFlow<HomeState>(HomeState.Ready()) // Assuming Ready() or Ready(null) is the initial state
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
