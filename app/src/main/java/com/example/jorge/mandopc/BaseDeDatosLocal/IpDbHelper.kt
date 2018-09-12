package com.example.jorge.mandopc.BaseDeDatosLocal

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class IpDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        println("Creando bd...")
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    @Throws(SQLiteConstraintException::class)
    fun insertIp(ip: String): Boolean {
        val db = writableDatabase
        val values = ContentValues()
        values.put(DbModel.IpEntry.COLUMN_ID, "1")
        values.put(DbModel.IpEntry.COLUMN_IP, ip)
        val newRowId = db.insert(DbModel.IpEntry.TABLE_NAME, null, values)
        return true
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteIp(id: String): Boolean {
        val db = writableDatabase
        val selection = DbModel.IpEntry.COLUMN_ID + " LIKE ?"
        val selectionArgs = arrayOf(id)
        db.delete(DbModel.IpEntry.TABLE_NAME, selection, selectionArgs)
        return true
    }

    fun readIp(id: String): String? {
        var ipUser: String? = null
        val db = writableDatabase
        var cursor: Cursor?
        try {
            cursor = db.rawQuery("select * from " + DbModel.IpEntry.TABLE_NAME + " WHERE " + DbModel.IpEntry.COLUMN_ID + "='" + id + "'", null)
            if (cursor!!.moveToFirst()) {
                while (cursor.isAfterLast == false) {
                    ipUser = cursor.getString(1)
                    cursor.moveToNext()
                }
            }
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
        }
        return ipUser
    }

    companion object {
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "BaseDatosIp.db"

        private val SQL_CREATE_ENTRIES =
                "CREATE TABLE " + DbModel.IpEntry.TABLE_NAME + " (" +
                        DbModel.IpEntry.COLUMN_ID + " TEXT PRIMARY KEY," +
                        DbModel.IpEntry.COLUMN_IP + " TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DbModel.IpEntry.TABLE_NAME
    }
}