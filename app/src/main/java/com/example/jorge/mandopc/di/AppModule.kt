package com.example.jorge.mandopc.di

import com.example.jorge.mandopc.data.local.IpDbHelper
import com.example.jorge.mandopc.domain.GetStoredDefaultIpUseCase
import com.example.jorge.mandopc.domain.GetStoredDefaultIpUseCaseImpl
import com.example.jorge.mandopc.domain.RemoveDefaultIpUseCase
import com.example.jorge.mandopc.domain.RemoveDefaultIpUseCaseImpl
import com.example.jorge.mandopc.domain.SaveDefaultIpUseCase
import com.example.jorge.mandopc.domain.SaveDefaultIpUseCaseImpl
import com.example.jorge.mandopc.presentation.home.HomePresenter
import com.example.jorge.mandopc.presentation.home.HomePresenterImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { CoroutineScope(Dispatchers.Main) }
    single { HomePresenterImpl() } bind HomePresenter::class
    single { GetStoredDefaultIpUseCaseImpl() } bind GetStoredDefaultIpUseCase::class
    single { SaveDefaultIpUseCaseImpl() } bind SaveDefaultIpUseCase::class
    single { RemoveDefaultIpUseCaseImpl() } bind RemoveDefaultIpUseCase::class
    single { IpDbHelper(androidContext()) }
}