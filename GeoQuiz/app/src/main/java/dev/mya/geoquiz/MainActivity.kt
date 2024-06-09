package dev.mya.geoquiz

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.OnClickListener
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import dev.mya.geoquiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var score = 0

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.quesion_asia, true)
    )

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.trueButton.setOnClickListener { view: View ->
            binding.trueButton.visibility = GONE
            binding.falseButton.visibility = GONE
            checkAnswer(true)
        }

        binding.falseButton.setOnClickListener { view: View ->
            binding.trueButton.visibility = GONE
            binding.falseButton.visibility = GONE
            checkAnswer(false)

        }

        binding.nextButton.setOnClickListener { view: View ->
            currentIndex = if (currentIndex == questionBank.size - 1) {
                questionBank.size - 1
            } else {
                (currentIndex + 1) % questionBank.size
            }
            updateQuestion()
            binding.trueButton.visibility = VISIBLE
            binding.falseButton.visibility = VISIBLE
        }

        binding.questionTextView.setOnClickListener { view: View ->
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
            binding.trueButton.visibility = VISIBLE
            binding.falseButton.visibility = VISIBLE
        }

        binding.previousButton.setOnClickListener { view: View ->
            currentIndex = if (currentIndex > 0) {
                (currentIndex - 1) % questionBank.size
            } else {
                0
            }
            updateQuestion()
            binding.trueButton.visibility = GONE
            binding.falseButton.visibility = GONE
        }

    }

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        binding.questionTextView.setText(questionTextResId)
        showScore()
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer

        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()

        if (userAnswer == correctAnswer) {
            score++
        }
    }

    private fun showScore() {
        if (currentIndex == questionBank.size - 1) {
            Toast.makeText(
                this,
                "Your total score: ${questionBank.size} / $score",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    
}


