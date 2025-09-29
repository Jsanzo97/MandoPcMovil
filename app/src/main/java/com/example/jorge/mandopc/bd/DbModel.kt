package com.example.jorge.mandopc.bd

import android.provider.BaseColumns

object DbModel {

    class IpEntry : BaseColumns {
        companion object {
            val TABLE_NAME = "ipes"
            val COLUMN_ID = "id"
            val COLUMN_IP = "ip"
        }
    }
}