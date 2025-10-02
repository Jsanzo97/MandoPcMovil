package com.example.jorge.mandopc.di

import com.example.jorge.mandopc.domain.GetStoredDefaultIpUseCase
import com.example.jorge.mandopc.domain.GetStoredDefaultIpUseCaseImpl
import com.example.jorge.mandopc.domain.RemoveDefaultIpUseCase
import com.example.jorge.mandopc.domain.RemoveDefaultIpUseCaseImpl
import com.example.jorge.mandopc.domain.SaveDefaultIpUseCase
import com.example.jorge.mandopc.domain.SaveDefaultIpUseCaseImpl
import org.koin.dsl.bind
import org.koin.dsl.module

val useCaseModule = module {
    single { GetStoredDefaultIpUseCaseImpl(get()) } bind GetStoredDefaultIpUseCase::class
    single { SaveDefaultIpUseCaseImpl(get()) } bind SaveDefaultIpUseCase::class
    single { RemoveDefaultIpUseCaseImpl(get()) } bind RemoveDefaultIpUseCase::class
}
