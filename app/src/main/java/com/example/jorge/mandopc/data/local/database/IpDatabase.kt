package com.example.jorge.mandopc.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jorge.mandopc.data.local.dao.IpDao
import com.example.jorge.mandopc.data.local.model.IpModel

@Database(entities = [IpModel::class], version = 1)
internal abstract class IpDatabase : RoomDatabase() {
    abstract fun ipDao(): IpDao
}