package com.example.jorge.mandopc.di

import com.example.jorge.mandopc.presentation.home.HomePresenter
import com.example.jorge.mandopc.presentation.home.HomePresenterImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { CoroutineScope(Dispatchers.IO) }
    single { HomePresenterImpl(
        get(),
        get(),
        get(),
        get())
    } bind HomePresenter::class

}