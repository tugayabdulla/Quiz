package com.example.quiz

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        val mScore = intent.getIntExtra(Constants.CORRECT_ANSWERS, 0)
        val mUserName = intent.getStringExtra(Constants.USER_NAME)

        tv_score.text = getString(R.string.score, mScore)
        Log.d("result",mUserName)
        tv_name.text = mUserName
    }

    fun onFinishCLicked(view: View) {
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
}