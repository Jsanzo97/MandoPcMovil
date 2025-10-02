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
    single { HomePresenterImpl(get(), get(), get(), get()) } bind HomePresenter::class
    single { ControllerPresenterImpl(get(), get(), get(), get()) } bind ControllerPresenter::class

}