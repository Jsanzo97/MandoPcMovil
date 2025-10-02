package com.example.jorge.mandopc.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ip")
internal data class IpModel(
    @PrimaryKey val ip: String
)