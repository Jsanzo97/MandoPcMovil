package com.example.jorge.mandopc.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.jorge.mandopc.data.local.model.IpModel

@Dao
internal interface IpDao {
    @Query("SELECT * FROM ip")
    fun get(): IpModel? // Changed to nullable

    @Insert
    fun insert(ip: IpModel)

    @Query("DELETE FROM ip")
    fun delete()
}