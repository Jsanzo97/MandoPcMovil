package com.example.jorge.mandopc.di

import com.example.jorge.mandopc.data.remote.SocketSender
import org.koin.dsl.module

val dataRemoteModule = module {

    single { SocketSender() }

}