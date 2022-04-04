package com.example.ninosapp.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

open class DBHelper(
    context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL("CREATE TABLE $TABLE_PETS (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL,age INTEGER NOT NULL,gender INTEGER NOT NULL,brench TEXT NOT NULL,esteril TEXT NOT NULL,owner TEXT NOT NULL)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL("DROP TABLE $TABLE_PETS")
        onCreate(p0)
    }
    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "pets.db"
        public const val TABLE_PETS = "pets"
    }
}