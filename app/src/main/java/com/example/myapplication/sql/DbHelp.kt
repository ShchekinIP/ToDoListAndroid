package com.example.myapplication.sql

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelp(context: Context?) : SQLiteOpenHelper(context, Constraint.DB_NAME, null, Constraint.DB_VERSION) {
    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        sqLiteDatabase.execSQL(Constraint.TABLE_STRUCTURE)
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, a: Int, b: Int) {
        if (a < b) {
            sqLiteDatabase.execSQL(Constraint.DROP_TABLE)
            onCreate(sqLiteDatabase)
        }
    }
}