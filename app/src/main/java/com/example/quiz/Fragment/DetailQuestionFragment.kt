package com.example.quiz.Fragment

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.quiz.Model.QuizDetailModel
import com.example.quiz.R
import com.example.quiz.Repositories.QuizDetailRepository
import com.example.quiz.Repositories.ResultRepository
import com.example.quiz.ViewModel.QuizDetailViewModelFactory
import com.example.quiz.ViewModel.QuizListViewModel
import com.example.quiz.ViewModel.ResultViewModel
import com.example.quiz.ViewModel.ResultViewModelFactory

class DetailQuestionFragment : Fragment() {
    private val quizDetailViewModel by viewModels<QuizListViewModel> {
        QuizDetailViewModelFactory(QuizDetailRepository())
    }

    private val resultViewModel by viewModels<ResultViewModel> {
        ResultViewModelFactory(ResultRepository())
    }

    private lateinit var navController : NavController

    private lateinit var optionAButton : Button
    private lateinit var optionBButton : Button
    private lateinit var optionCButton : Button
    private lateinit var optionDButton : Button
    private lateinit var finishButton : Button
    private lateinit var nextButton : Button
    private lateinit var questionTv : TextView
    private lateinit var correctTv : TextView
    private lateinit var wrongTv : TextView
    private lateinit var timerCount : TextView


    private var timer : CountDownTimer? = null
    private var correctAnswers = 0
    private var wrongAnswers = 0

    override fun onCreateView(
        inflater : LayoutInflater , container : ViewGroup? ,
        savedInstanceState : Bundle?
    ) : View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_question , container , false)
    }

    override fun onViewCreated(view : View , savedInstanceState : Bundle?) {
        super.onViewCreated(view , savedInstanceState)

        navController = Navigation.findNavController(view)
        optionAButton = view.findViewById(R.id.btnOptionA)
        optionBButton = view.findViewById(R.id.btnOptionB)
        optionCButton = view.findViewById(R.id.btnOptionC)
        optionDButton = view.findViewById(R.id.btnOptionD)
        finishButton = view.findViewById(R.id.btnFinish)
        nextButton = view.findViewById(R.id.btnNext)
        questionTv = view.findViewById(R.id.tvQuizDetail)
        correctTv = view.findViewById(R.id.textViewCorrect)
        wrongTv = view.findViewById(R.id.textViewWrong)
        timerCount = view.findViewById(R.id.tvCountTimer)

        quizDetailViewModel.quizzes.observe(viewLifecycleOwner) { quizzes ->
            // Handle when quizzes are fetched
            if (!quizzes.isNullOrEmpty()) {
                Log.d("LiveDataUpdate" , "Quizzes updated: $quizzes")
                // Logic to display the first question when quizzes are loaded
                displayQuizData(quizDetailViewModel.getCurrentQuiz())
                Log.d("OptionTest" , "${quizDetailViewModel.getCurrentQuiz()?.optionA}")
            }
        }
        // Load the next question on button click
        nextButton.setOnClickListener {
            moveToNextQuestion()
        }
        // Fetch quizzes
        quizDetailViewModel.getQuizzes()

        finishButton.setOnClickListener {
            resultViewModel.updateQuizResult(correctAnswers, wrongAnswers){isSuccess ->
                if (isSuccess){
                    showResultDialog()
                }
                else{
                    Log.d("FirebaseError", "Error updating result")
                }
            }
        }
    }

    private fun moveToNextQuestion() {
        val currentIndex = quizDetailViewModel.currentQuizIndex.value ?: 0
        if (currentIndex < (quizDetailViewModel.quizzes.value?.size ?: 0) - 1) {
            quizDetailViewModel.setCurrentQuizIndex(currentIndex + 1)
            resetUI()
            displayQuizData(quizDetailViewModel.getCurrentQuiz())
        } else {
            resultViewModel.updateQuizResult(correctAnswers, wrongAnswers){isSuccess ->
                if (isSuccess){
                    showResultDialog()
                }
                else{
                    Log.d("FirebaseError", "Error updating result")
                }
            }
        }
    }

    private fun resetUI() {
        optionAButton.setBackgroundColor(Color.WHITE)
        optionBButton.setBackgroundColor(Color.WHITE)
        optionCButton.setBackgroundColor(Color.WHITE)
        optionDButton.setBackgroundColor(Color.WHITE)
        optionAButton.isEnabled = true
        optionBButton.isEnabled = true
        optionCButton.isEnabled = true
        optionDButton.isEnabled = true
        timer?.cancel()
        timerCount.text = ""
    }

    private fun displayQuizData(quizDetail : QuizDetailModel?) {

        quizDetail?.let { detail ->
            // Display the question
            questionTv.text = detail.question

            // Display the options
            optionAButton.text = detail.optionA
            optionBButton.text = detail.optionB
            optionCButton.text = detail.optionC
            optionDButton.text = detail.optionD

            optionAButton.setOnClickListener {
                checkAnswer(detail.optionA , detail.answer)
                disableButtons()
            }
            optionBButton.setOnClickListener {
                checkAnswer(detail.optionB , detail.answer)
                disableButtons()
            }
            optionCButton.setOnClickListener {
                checkAnswer(detail.optionC , detail.answer)
                disableButtons()
            }
            optionDButton.setOnClickListener {
                checkAnswer(detail.optionD , detail.answer)
                disableButtons()
            }

            // Set up timer
            timer = object : CountDownTimer(detail.timer * 1000 , 1000) {
                override fun onTick(millisUntilFinished : Long) {
                    timerCount.text = (millisUntilFinished / 1000).toString()
                }

                override fun onFinish() {
                    timerCount.text = "Time's up!"
                    questionTv.text = "Sorry, Time is up! Continue with next question"
                    wrongAnswers++
                    wrongTv.text = wrongAnswers.toString()
                    disableButtons()
                    //Bổ sung logic khi thời gian hết
                }
            }.start()
        }
    }

    private fun disableButtons() {
        // Vô hiệu hóa tất cả các nút tương ứng
        optionAButton.isEnabled = false
        optionBButton.isEnabled = false
        optionCButton.isEnabled = false
        optionDButton.isEnabled = false
    }

    private fun checkAnswer(selectedAnswer : String , correctAnswer : String) {
        if (selectedAnswer == correctAnswer) {
            correctAnswers++
            correctTv.text = correctAnswers.toString()
            // Handle correct answer UI
            when (selectedAnswer) {
                optionAButton.text.toString() -> setButtonBackground(optionAButton , true)
                optionBButton.text.toString() -> setButtonBackground(optionBButton , true)
                optionCButton.text.toString() -> setButtonBackground(optionCButton , true)
                optionDButton.text.toString() -> setButtonBackground(optionDButton , true)
            }
        } else {
            wrongAnswers++
            wrongTv.text = wrongAnswers.toString()

            when (correctAnswer) {
                optionAButton.text.toString() -> setButtonBackground(optionAButton , true)
                optionBButton.text.toString() -> setButtonBackground(optionBButton , true)
                optionCButton.text.toString() -> setButtonBackground(optionCButton , true)
                optionDButton.text.toString() -> setButtonBackground(optionDButton , true)
            }
            when (selectedAnswer) {
                optionAButton.text.toString() -> setButtonBackground(optionAButton , false)
                optionBButton.text.toString() -> setButtonBackground(optionBButton , false)
                optionCButton.text.toString() -> setButtonBackground(optionCButton , false)
                optionDButton.text.toString() -> setButtonBackground(optionDButton , false)
            }
            // Update UI for wrong answer
            // For example, change the background color of the selected button to red
        }
    }

    private fun setButtonBackground(button : Button , isCorrect : Boolean) {
        if (isCorrect) {
            button.setBackgroundColor(Color.GREEN)
        } else {
            button.setBackgroundColor(Color.RED)
        }
    }

    private fun showResultDialog() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle("Quiz Game")
        alertDialogBuilder.setMessage("Congratulations!!!\nYou have answered all the questions. Do you want to see the results?")

        alertDialogBuilder.setPositiveButton("PLAY AGAIN") { _ , _ ->
            navController.navigate(R.id.detailQuestionFragment)
        }

        alertDialogBuilder.setNegativeButton("RESULT PAGE") { _ , _ ->
            navController.navigate(R.id.action_detailQuestionFragment_to_resultFragment)
        }

        alertDialogBuilder.setCancelable(false)
        alertDialogBuilder.show()
    }
}