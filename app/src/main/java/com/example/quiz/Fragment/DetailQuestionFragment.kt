package com.example.quiz.Fragment

import android.os.Bundle
import android.os.CountDownTimer
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
    private lateinit var countDownTimer: CountDownTimer

    private lateinit var navController: NavController

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
        answerTv = view.findViewById(R.id.tvAnswer)
        questionNumberTv = view.findViewById(R.id.tvQuestionNumber)
        timerCount = view.findViewById(R.id.tvCountTimer)

    }

//    private fun getQuiz(i: Int){
//        quizDetailViewModel.quizLiveData.observe(viewLifecycleOwner){quizList ->
//            questionTv.text =   quizList.get(i-1).quiz
//            optionAButton.text =   quizList.get(i-1).optionA
//            optionBButton.text =   quizList.get(i-1).optionB
//            optionCButton.text =   quizList.get(i-1).optionC
//            optionDButton.text =   quizList.get(i-1).optionD
//
//        }
//    }


    private fun getQuiz() {
        quizDetailViewModel.quizLiveData.observe(viewLifecycleOwner) { quizList ->
            if (quizList.isNotEmpty()) {
                val quiz = quizList[0]
                questionTv.text = quiz.quiz
                optionAButton.text = quiz.optionA
                optionBButton.text = quiz.optionB
                optionCButton.text = quiz.optionC
                optionDButton.text = quiz.optionD
                optionAButton.setOnClickListener {
                    checkAnswer(quiz.answer , quiz.optionA)
                }
                optionBButton.setOnClickListener {
                    checkAnswer(quiz.answer , quiz.optionB)
                }
                optionCButton.setOnClickListener {
                    checkAnswer(quiz.answer , quiz.optionC)
                }
                optionDButton.setOnClickListener {
                    checkAnswer(quiz.answer , quiz.optionD)
                }
                startTimer(quiz.timer)
            }
        }
    }
    private fun checkAnswer(correctAnswer: String, selectedAnswer: String) {
        if (correctAnswer == selectedAnswer) {
            Toast.makeText(requireContext(), "Correct", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Wrong", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startTimer(time: Long) {
        countDownTimer = object : CountDownTimer(time * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                timerCount.text = "$secondsRemaining"
            }

            override fun onFinish() {
                timerCount.text = "Time out"
            }
        }
        countDownTimer.start()
    }
}
