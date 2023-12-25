package com.example.quiz.Model

import com.google.firebase.firestore.DocumentId

data class QuizDetailModel(
    @DocumentId
    var quiz: String = "",
    var answer: String = "",
    var optionA: String = "",
    var optionB: String = "",
    var optionC: String = "",
    var optionD: String = "",
    var timer: Long = 0
)
