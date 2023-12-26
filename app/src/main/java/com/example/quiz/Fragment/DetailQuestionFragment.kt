package com.example.quiz.Fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.quiz.Model.QuizDetailModel
import com.example.quiz.R
import com.example.quiz.Repositories.AuthRepository
import com.example.quiz.Repositories.QuizDetailRepository
import com.example.quiz.ViewModel.AuthViewModel
import com.example.quiz.ViewModel.AuthViewModelFactory
import com.example.quiz.ViewModel.QuizDetailViewModelFactory
import com.example.quiz.ViewModel.QuizListViewModel
import com.google.android.material.textfield.TextInputLayout
import kotlin.concurrent.timer


class DetailQuestionFragment : Fragment() {
    private val quizDetailViewModel by viewModels<QuizListViewModel> {
        QuizDetailViewModelFactory(QuizDetailRepository())
    }

    private lateinit var navController : NavController

    private lateinit var optionAButton : Button
    private lateinit var optionBButton : Button
    private lateinit var optionCButton : Button
    private lateinit var optionDButton : Button
    private lateinit var finishButton : Button
    private lateinit var nextButton : Button
    private lateinit var questionTv : TextView
    private lateinit var answerTv : TextView
    private lateinit var questionNumberTv : TextView
    private lateinit var timerCount : TextView



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
        timerCount = view.findViewById(R.id.tvCountTimer)

        quizDetailViewModel.quizzes.observe(viewLifecycleOwner) {  quizzes ->
            // Handle when quizzes are fetched
            if (!quizzes.isNullOrEmpty()) {
                Log.d("LiveDataUpdate", "Quizzes updated: $quizzes")
                // Logic to display the first question when quizzes are loaded
                displayQuizData(quizDetailViewModel.getCurrentQuiz())
                Log.d("OptionTest", "${quizDetailViewModel.getCurrentQuiz()?.optionA}")
            }
        }

        // Load the next question on button click
        nextButton.setOnClickListener {
            val currentIndex = quizDetailViewModel.currentQuizIndex.value ?: 0
            if (currentIndex < (quizDetailViewModel.quizzes.value?.size ?: 0) - 1) {
                quizDetailViewModel.setCurrentQuizIndex(currentIndex + 1)
                displayQuizData(quizDetailViewModel.getCurrentQuiz())
            } else {
                // Handle when all questions are finished
                navController.navigate(R.id.action_detailQuestionFragment_to_resultFragment)
            }
        }

        // Fetch quizzes
        quizDetailViewModel.getQuizzes()
    }

    private fun displayQuizData(quizDetail: QuizDetailModel?) {
        quizDetail?.let { it ->
            // Display the question
            questionTv.text = it.question

            // Display the options
            optionAButton.text = it.optionA
            Log.d("OptionTest", "${it.optionA}")
            optionBButton.text = it.optionB
            optionCButton.text = it.optionC
            optionDButton.text = it.optionD

            // Set up timer
            val timer = object : CountDownTimer(it.timer * 1000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    timerCount.text = (millisUntilFinished / 1000).toString()
                }

                override fun onFinish() {
                    timerCount.text = "Time's up!"
                }
            }
            timer.start()

            // Handle user option selection
        }
    }
//    private fun checkAnswer(selectedAnswer: String) {
//        val currentQuiz = quizDetailViewModel.getCurrentQuiz()
//        currentQuiz?.let { quiz ->
//            val correctAnswer = quiz.answer
//
//            // Check if the selected answer is correct
//            val isCorrect = selectedAnswer == correctAnswer
//
//            // Perform actions based on the correctness of the answer
//            if (isCorrect) {
//                // If the answer is correct, you can display a message or perform any action
//                // For example, display a toast indicating the correct answer
//                Toast.makeText(requireContext(), "Correct Answer!", Toast.LENGTH_SHORT).show()
//            } else {
//                // If the answer is incorrect, you can display a message or perform any action
//                // For example, display a toast indicating the wrong answer and show the correct answer
//                val correctAnswerMessage = "Wrong Answer! Correct Answer is: $correctAnswer"
//                Toast.makeText(requireContext(), correctAnswerMessage, Toast.LENGTH_SHORT).show()
//            }
//
//            // Proceed to the next question if available or navigate to the result fragment
//            val currentIndex = quizDetailViewModel.currentQuizIndex.value ?: 0
//            if (currentIndex < (quizDetailViewModel.quizzes.value?.size ?: 0) - 1) {
//                quizDetailViewModel.setCurrentQuizIndex(currentIndex + 1)
//                displayQuizData(quizDetailViewModel.getCurrentQuiz())
//            } else {
//                navController.navigate(R.id.action_detailQuestionFragment_to_resultFragment)
//            }
//        }
//    }
}