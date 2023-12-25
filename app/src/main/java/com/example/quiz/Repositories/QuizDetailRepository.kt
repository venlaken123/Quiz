package com.example.quiz.Repositories

import com.example.quiz.Model.QuizDetailModel
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception

class QuizDetailRepository {
    private val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val quizCollection = firebaseFirestore.collection("QuizList")
    private lateinit var quiz: String

    fun setQuiz(quiz: String){
        this.quiz = quiz
    }

    fun getQuiz(onQuizLoad: OnQuizLoad) {
        quizCollection.get()
            .addOnSuccessListener { querySnapshot ->
                val quizList = mutableListOf<QuizDetailModel>()
                for (document in querySnapshot.documents) {
                    val question = document.getString("question") ?: ""
                    val answer = document.getString("answer") ?: ""
                    val optionA = document.getString("optionA") ?: ""
                    val optionB = document.getString("optionB") ?: ""
                    val optionC = document.getString("optionC") ?: ""
                    val optionD = document.getString("optionD") ?: ""
                    val timer = document.getLong("timer") ?: 0

                    val quiz = QuizDetailModel(question, answer, optionA, optionB, optionC, optionD, timer)
                    quizList.add(quiz)
                }
                onQuizLoad.onLoad(quizList)
            }
            .addOnFailureListener { exception ->
                onQuizLoad.onError(exception)
            }
    }
}
interface OnQuizLoad {
    fun onLoad(quizModel : List<QuizDetailModel>)
    fun onError(e : Exception)
}