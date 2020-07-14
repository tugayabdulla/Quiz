package com.example.quiz

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_quiz_questions.*

class QuizQuestionsActivity : AppCompatActivity() {
    private var mCurrentPosition: Int = 0
    private lateinit var mQuestionsList: ArrayList<Question>
    private var mSelectedOptionId = 0
    private var mTrueOptionId = 0
    private var isQuestionAnswered = false
    private var score = 0
    private lateinit var mUserName: String

    private lateinit var viewModel: LeaderBoardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        viewModel = LeaderBoardViewModel(this)

        mQuestionsList = Constants.getQuestions()
        val bundle = intent.extras
        Log.d("quizquestions", "bundle null: ${bundle == null}")
        mUserName = bundle?.getString(Constants.USER_NAME)!!
        Log.d("QuizQuestions", mUserName)

        showQuestion()


    }

    private fun showQuestion() {

        setDefaultOptionsOnTextViews()
        val question: Question = mQuestionsList[mCurrentPosition]
        mCurrentPosition++

        progressBar.progress = mCurrentPosition
        tv_progress.text = getString(R.string.progress_text, mCurrentPosition)

        mTrueOptionId = when (question.correctAnswer) {
            1 -> R.id.tv_option_one
            2 -> R.id.tv_option_two
            3 -> R.id.tv_option_three
            4 -> R.id.tv_option_four
            else -> throw Exception("Error occurred")
        }
        mSelectedOptionId = 0

        tv_question.text = question.question
        iv_image.setImageResource(question.image)
        tv_option_one.text = question.optionOne
        tv_option_two.text = question.optionTwo
        tv_option_three.text = question.optionThree
        tv_option_four.text = question.optionFour
        btn_submit.text = getString(R.string.btn_submit)
    }

    private fun setDefaultOptionsOnTextViews() {
        val options = ArrayList<TextView>()
        options.add(tv_option_one)
        options.add(tv_option_two)
        options.add(tv_option_three)
        options.add(tv_option_four)

        for (tv in options) {
            tv.setTextColor(Color.parseColor("#7A8089"))
            tv.typeface = Typeface.DEFAULT
            tv.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }
    }

    fun changeStyleOnTextViewClicked(view: View) {
        val tv = view as TextView
        mSelectedOptionId = tv.id

        setDefaultOptionsOnTextViews()
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border)
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
    }

    fun onButtonClicked(view: View) {
        if (mSelectedOptionId != 0) {
            if (isQuestionAnswered) {
                setOptionsClickable(true)

                if (mCurrentPosition < 10) {
                    showQuestion()
                } else {
                    val intent = Intent(this, ResultActivity::class.java)
                    intent.putExtra(Constants.USER_NAME, mUserName)
                    intent.putExtra(Constants.CORRECT_ANSWERS, score)
                    viewModel.insert(mUserName, score)

                    startActivity(intent)
                    finish()
                }
            } else {
                checkAnswer()
                setOptionsClickable(false)
            }
        }

        isQuestionAnswered = !isQuestionAnswered
    }

    private fun checkAnswer() {
        btn_submit.text = getString(R.string.btn_next_question)
        val tv = findViewById<TextView>(mTrueOptionId)
        tv.background = ContextCompat.getDrawable(this, R.drawable.correct_option_border_bg)
        tv.setTypeface(tv.typeface, Typeface.BOLD)

        if (mTrueOptionId == mSelectedOptionId) {
            score++
        } else {
            findViewById<TextView>(mSelectedOptionId).background =
                ContextCompat.getDrawable(this, R.drawable.wrong_option_border_bg)
        }
    }

    private fun setOptionsClickable(flag: Boolean) {
        tv_option_one.isClickable = flag
        tv_option_two.isClickable = flag
        tv_option_three.isClickable = flag
        tv_option_four.isClickable = flag
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.apply {
            setTitle(getString(R.string.alert_dialog_title))
            setMessage(getString(R.string.alert_dialog_message))

            setPositiveButton(getString(R.string.yes)) { _, _ ->
                super.onBackPressed()
            }
            setNegativeButton("NO") { _, _ ->

            }

        }
        val alertDialog = builder.create()
        alertDialog.show()
    }
}