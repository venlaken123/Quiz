//package com.example.quiz.ViewModel
//
//import android.app.Application
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//
//class AuthViewModelFactory(
//    private val application: Application
//) : ViewModelProvider.NewInstanceFactory() {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
//            return AuthViewModel(application) as T // Trả về AuthViewModel thay vì AuthViewModelFactory
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}