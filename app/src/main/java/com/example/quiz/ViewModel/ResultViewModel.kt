package com.example.quiz.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quiz.Model.QuizDetailModel
import com.example.quiz.Model.ResultModel
import com.example.quiz.Repositories.ResultRepository

class ResultViewModel(private val repository : ResultRepository): ViewModel() {

    private val _correctAnswers = MutableLiveData<Int>()
    val correctAnswers: LiveData<Int> get() = _correctAnswers

    private val _wrongAnswers = MutableLiveData<Int>()
    val wrongAnswers: LiveData<Int> get() = _wrongAnswers
    fun getResult() {
        repository.getQuizResult { quizResults ->
            // Xử lý kết quả từ repository và cập nhật LiveData
            val totalCorrect = quizResults?.sumOf { it.correctAnswers } ?: 0
            val totalWrong = quizResults?.sumOf { it.wrongAnswers } ?: 0

            _correctAnswers.postValue(totalCorrect)
            _wrongAnswers.postValue(totalWrong)
        }
    }
    fun updateQuizResult(correctAnswers: Int, wrongAnswers: Int, completion: (Boolean) -> Unit) {
        val quizResult = ResultModel(correctAnswers, wrongAnswers)
        repository.updateQuizResult(quizResult) { isSuccess ->
            completion(isSuccess)
        }
    }
}



