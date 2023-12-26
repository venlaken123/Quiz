package com.example.quiz.ViewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quiz.Model.QuizDetailModel
import com.example.quiz.Repositories.QuizDetailRepository


class QuizListViewModel(private val repository: QuizDetailRepository) : ViewModel() {
    private val _currentQuizIndex = MutableLiveData<Int>()
    val currentQuizIndex: LiveData<Int> get() = _currentQuizIndex

    private val _quizzes = MutableLiveData<List<QuizDetailModel>>()
    val quizzes: LiveData<List<QuizDetailModel>> get() = _quizzes
    init {
        _currentQuizIndex.value = 0 // Set the initial quiz index to start from the first quiz
        getQuizzes()
    }

    fun getQuizzes() {
        repository.getQuizzes { quizzes ->
            _quizzes.postValue(quizzes)
        }
    }

    fun setCurrentQuizIndex(index: Int) {
        _currentQuizIndex.value = index
    }

    fun getCurrentQuiz(): QuizDetailModel? {
        return if (_quizzes.value != null && _quizzes.value!!.isNotEmpty() && _currentQuizIndex.value != null) {
            _quizzes.value!![_currentQuizIndex.value!!]
        } else {
            null
        }
    }
}
