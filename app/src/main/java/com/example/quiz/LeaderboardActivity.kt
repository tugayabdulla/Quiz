package com.example.quiz

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_leaderboard.*

class LeaderboardActivity : AppCompatActivity() {
    private lateinit var viewModel: LeaderBoardViewModel
    private val mAdapter = RecyclerAdapter(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboard)
        title = "Leaderboard"

        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN


        viewModel = LeaderBoardViewModel(this)
        viewModel.loadValues()
        rv_leaderboard.layoutManager = LinearLayoutManager(this)

        rv_leaderboard.adapter = mAdapter

        viewModel.cursor.observe(this, Observer { cursor ->
            mAdapter.swapCursor(cursor)
        })
    }
}