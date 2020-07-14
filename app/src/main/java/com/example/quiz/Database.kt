package com.example.quiz

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

private const val DATABASE_NAME = "QuizApp.db"
private const val DATABASE_VERSION = 1


 class Database private constructor(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME, null, DATABASE_VERSION
) {
    override fun onCreate(db: SQLiteDatabase) {
        val sSQLCreate = """CREATE TABLE ${Constants.TABLE_NAME} (
            ${Constants.ID} INTEGER PRIMARY KEY NOT NULL,
            ${Constants.COLUMN_USERNAME} TEXT NOT NULL,
            ${Constants.COLUMN_SCORE} INTEGER NOT NULL);
        """.replaceIndent(" ")

        db.execSQL(sSQLCreate)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }



    companion object : SingletonHolder<Database, Context>(::Database)
}