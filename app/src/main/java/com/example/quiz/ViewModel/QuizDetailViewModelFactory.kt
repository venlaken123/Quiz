package com.example.quiz.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.quiz.Repositories.QuizDetailRepository

class QuizDetailViewModelFactory(private val quizDetailRepository : QuizDetailRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass : Class<T>) : T {
        if (modelClass.isAssignableFrom(QuizListViewModel::class.java)) {
            return QuizListViewModel(quizDetailRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}