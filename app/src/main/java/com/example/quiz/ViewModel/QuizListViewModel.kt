package com.example.quiz.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quiz.Model.QuizDetailModel
import com.example.quiz.Repositories.OnQuizLoad
import com.example.quiz.Repositories.QuizDetailRepository
import com.google.firebase.auth.FirebaseUser
import java.lang.Exception

class QuizListViewModel(private val repository: QuizDetailRepository) : ViewModel(), OnQuizLoad {
    private var _quizLiveData: MutableLiveData<List<QuizDetailModel>> = MutableLiveData()
    val quizLiveData: LiveData<List<QuizDetailModel>> get() = _quizLiveData

    fun setQuiz(quiz: String){
        repository.setQuiz(quiz)
    }

    fun getQuiz() {
        repository.getQuiz(this)
    }

    override fun onLoad(quizModel: List<QuizDetailModel>) {
        _quizLiveData.postValue(quizModel)
    }

    override fun onError(e: Exception) {
        Log.d("QuizError", "onError: ${e.message}")
    }
}
