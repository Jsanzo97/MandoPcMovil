package com.example.jorge.mandopc.di

import androidx.room.Room
import com.example.jorge.mandopc.data.local.database.IpDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            IpDatabase::class.java,
            "ip_database"
        ).build()
    }

    single {
        get<IpDatabase>().ipDao()
    }
}
