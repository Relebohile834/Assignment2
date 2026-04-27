package com.example.lifehackapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class QuizActivity : AppCompatActivity() {
    var index = 0
    var score = 0
    lateinit var questionText: TextView
    lateinit var feedbackText: TextView
    val questions = arrayOf(
        "Switching your phone to Airplane Mode makes it charge faster",
        "Focusing on one single task at a time is more productive than 'multi-tasking' several projects at once",
        "Closing all background apps saves battery",
        "You have to wake up super early to be successful",
        "Incognito mode makes you completely invisible to websites and your internet provider"
    )

    val answers = arrayOf(true, true, false, false, false)
    val explanations = arrayOf(
        "Hack: In Airplane Mode, your phone stops searching for signals and it stops updating background data, which reduces energy consumption while the battery fills up",
        "Hack: Your brain can only focus on one thing at a time. Every time you switch tasks, it has to drop what it’s doing and spend energy catching up on the new task, which drains your focus and slows you down.",
        "Myth: Android keeps background apps in a ready-state. Closing and restarting them takes much more energy than just leaving them where they are.",
        "Myth: Success isn't about when you wake up, it’s about what you do with the time you’re awake.",
        "Myth: Incognito mode only prevents your local browser from saving history. Your internet provider, your boss/admin, and the websites you visit can still see your activity."
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quiz)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        questionText = findViewById(R.id.questionText)
        feedbackText = findViewById(R.id.feedbackText)

        val hackButton = findViewById<Button>(R.id.hackButton)
        val mythButton = findViewById<Button>(R.id.mythButton)
        val nextButton = findViewById<Button>(R.id.nextButton)

        loadQuestion()

        hackButton.setOnClickListener { checkAnswer(true) }
        mythButton.setOnClickListener { checkAnswer(false) }

        nextButton.setOnClickListener {
            index++

            if (index < questions.size) {
                loadQuestion()
                feedbackText.text = ""
            } else {
                val intent = Intent(this, ScoreActivity::class.java)
                intent.putExtra("score", score)
                intent.putExtra("total", questions.size)
                startActivity(intent)
                finish()
            }
        }
    }

    fun loadQuestion() {
        questionText.text = questions[index]
    }

    fun checkAnswer(userAnswer: Boolean) {
        if (userAnswer == answers[index]) {
            feedbackText.text = "Correct! \n${explanations[index]}"
            score++
        } else {
            feedbackText.text = "Incorrect! \n${explanations[index]}"
        }
    }
}
