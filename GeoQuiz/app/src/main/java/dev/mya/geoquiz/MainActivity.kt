package dev.mya.geoquiz

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.OnClickListener
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import dev.mya.geoquiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val quizViewModel: QuizViewModel by viewModels()

    private val cheatLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            quizViewModel.isCheater =
                result.data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
        }
    }

    private var isButtonClicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.trueButton.setOnClickListener {
            binding.trueButton.visibility = GONE
            binding.falseButton.visibility = GONE
            binding.cheatButton.visibility = GONE
            isButtonClicked = true
            checkAnswer(true)
        }

        binding.falseButton.setOnClickListener {
            binding.trueButton.visibility = GONE
            binding.falseButton.visibility = GONE
            binding.cheatButton.visibility = GONE
            isButtonClicked = true
            checkAnswer(false)

        }

        binding.nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            isButtonClicked = false
            updateQuestion()
            binding.trueButton.visibility = VISIBLE
            binding.falseButton.visibility = VISIBLE
            binding.cheatButton.visibility = VISIBLE

        }

        binding.questionTextView.setOnClickListener {
            quizViewModel.moveToNext()
            isButtonClicked = false
            updateQuestion()
            binding.trueButton.visibility = VISIBLE
            binding.falseButton.visibility = VISIBLE
            binding.cheatButton.visibility = VISIBLE

        }

        binding.previousButton.setOnClickListener {
            quizViewModel.moveToPrevious()
            updateQuestion()
            binding.trueButton.visibility = GONE
            binding.falseButton.visibility = GONE
            binding.cheatButton.visibility = GONE
        }

        binding.cheatButton.setOnClickListener {
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            cheatLauncher.launch(intent)
        }
        updateQuestion()
        isCheater()
    }

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        binding.questionTextView.setText(questionTextResId)
        if (quizViewModel.currentIndex == quizViewModel.getQuestionBankSize() - 1) {
            binding.nextButton.visibility = GONE
        }
    }

    private fun isCheater() {
        if (quizViewModel.isCheater) {
            Toast.makeText(this, R.string.judgment_toast, Toast.LENGTH_SHORT).show()
        }
    }


    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer

        val messageResId = when {
            quizViewModel.isCheater -> R.string.judgment_toast
            userAnswer == correctAnswer -> R.string.correct_toast
            else -> R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()

        if (userAnswer == correctAnswer) {
            quizViewModel.score++
        }
        if (quizViewModel.currentIndex == quizViewModel.getQuestionBankSize() - 1 && isButtonClicked) {
            showScore()
        }
        quizViewModel.isCheater = false
    }

    private fun showScore() {
        Toast.makeText(
            this,
            "Your total score: ${quizViewModel.getQuestionBankSize()} / ${quizViewModel.score}",
            Toast.LENGTH_SHORT
        ).show()
    }
}


