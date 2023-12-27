package com.example.quiz.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.quiz.Repositories.ResultRepository

class ResultViewModelFactory(private val resultRepository : ResultRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass : Class<T>) : T {
        if (modelClass.isAssignableFrom(ResultViewModel::class.java)) {
            return ResultViewModel(resultRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}