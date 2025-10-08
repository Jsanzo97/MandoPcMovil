package com.example.jorge.mandopc.di

import com.example.jorge.mandopc.domain.controller.ClickMouseUseCase
import com.example.jorge.mandopc.domain.controller.ClickMouseUseCaseImpl
import com.example.jorge.mandopc.domain.controller.HandleConnectionUseCase
import com.example.jorge.mandopc.domain.controller.HandleConnectionUseCaseImpl
import com.example.jorge.mandopc.domain.controller.MoveMouseUseCase
import com.example.jorge.mandopc.domain.controller.MoveMouseUseCaseImpl
import com.example.jorge.mandopc.domain.controller.ScrollUseCase
import com.example.jorge.mandopc.domain.controller.ScrollUseCaseImpl
import com.example.jorge.mandopc.domain.controller.SendKeyUseCase
import com.example.jorge.mandopc.domain.controller.SendKeyUseCaseImpl
import com.example.jorge.mandopc.domain.home.GetStoredDefaultIpUseCase
import com.example.jorge.mandopc.domain.home.GetStoredDefaultIpUseCaseImpl
import com.example.jorge.mandopc.domain.home.RemoveDefaultIpUseCase
import com.example.jorge.mandopc.domain.home.RemoveDefaultIpUseCaseImpl
import com.example.jorge.mandopc.domain.home.SaveDefaultIpUseCase
import com.example.jorge.mandopc.domain.home.SaveDefaultIpUseCaseImpl
import org.koin.dsl.bind
import org.koin.dsl.module

val domainModule = module {
    single { GetStoredDefaultIpUseCaseImpl(get()) } bind GetStoredDefaultIpUseCase::class
    single { SaveDefaultIpUseCaseImpl(get()) } bind SaveDefaultIpUseCase::class
    single { RemoveDefaultIpUseCaseImpl(get()) } bind RemoveDefaultIpUseCase::class
    single { HandleConnectionUseCaseImpl(get()) } bind HandleConnectionUseCase::class
    single { ClickMouseUseCaseImpl(get()) } bind ClickMouseUseCase::class
    single { MoveMouseUseCaseImpl(get()) } bind MoveMouseUseCase::class
    single { SendKeyUseCaseImpl(get()) } bind SendKeyUseCase::class
    single { ScrollUseCaseImpl(get()) } bind ScrollUseCase::class
}
