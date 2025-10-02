package com.example.jorge.mandopc.di

import android.app.Application
import com.example.mandopcmovil.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import timber.log.Timber

class CustomApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@CustomApplication)
            modules(listOf(
                appModule,
                databaseModule,
                useCaseModule
            ))
        }
    }
}
