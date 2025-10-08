package com.example.jorge.mandopc.di

import com.example.jorge.mandopc.presentation.controller.ControllerPresenter
import com.example.jorge.mandopc.presentation.controller.ControllerPresenterImpl
import com.example.jorge.mandopc.presentation.home.HomePresenter
import com.example.jorge.mandopc.presentation.home.HomePresenterImpl
import com.example.jorge.mandopc.utilities.DragHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.bind
import org.koin.dsl.module

val presentationModule = module {
    single { CoroutineScope(Dispatchers.IO) }
    single { DragHandler() }
    single { HomePresenterImpl(
        scope = get(),
        getStoredDefaultIpUseCase = get(),
        removeDefaultIpUseCase = get(),
        saveDefaultIpUseCase = get()
    ) } bind HomePresenter::class
    single { ControllerPresenterImpl(
        scope = get(),
        handleConnectionUseCase = get(),
        clickMouseUseCase = get(),
        moveMouseUseCase = get(),
        sendKeyUseCase = get(),
        scrollUseCase = get()
    ) } bind ControllerPresenter::class

}