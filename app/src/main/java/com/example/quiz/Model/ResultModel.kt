package com.example.quiz.Model

import com.google.firebase.firestore.DocumentId

data class ResultModel(
    var correctAnswers: Int = 0,
    var wrongAnswers: Int = 0
)
