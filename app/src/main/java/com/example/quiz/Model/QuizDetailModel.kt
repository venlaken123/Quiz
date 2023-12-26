package com.example.quiz.Model


data class QuizDetailModel(
    var question: String = "", //tại sao phải khởi tạo
    var answer: String = "",
    var optionA: String = "",
    var optionB: String = "",
    var optionC: String = "",
    var optionD: String = "",
    var timer: Long = 0
)
