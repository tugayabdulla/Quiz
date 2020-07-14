package com.example.quiz

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LeaderBoardViewModel(context: Context) : ViewModel() {
    private var database = Database.getInstance(context)

    private val databaseCursor = MutableLiveData<Cursor>()
    val cursor: LiveData<Cursor>
        get() = databaseCursor


    fun insert(name: String, score: Int) {
        val values = ContentValues()
        values.apply {
            put(Constants.COLUMN_USERNAME, name)
            put(Constants.COLUMN_SCORE, score)
        }

        viewModelScope.launch {

            database.writableDatabase.insert(Constants.TABLE_NAME, null, values)

        }


    }

    fun loadValues() {
        val projection = arrayOf(Constants.COLUMN_USERNAME, Constants.COLUMN_SCORE)
        val sortOrder = "${Constants.COLUMN_SCORE} DESC"
        viewModelScope.launch {
            val cursor =
                database.readableDatabase.query(
                    Constants.TABLE_NAME,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    sortOrder
                )

            databaseCursor.postValue(cursor)
        }


    }


}