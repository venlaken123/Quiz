package com.example.quiz.Repositories

import android.util.Log
import com.example.quiz.Model.QuizDetailModel
import com.google.firebase.firestore.FirebaseFirestore


class QuizDetailRepository {
    private val firebaseFirestore : FirebaseFirestore = FirebaseFirestore.getInstance()
    private val quizCollection = firebaseFirestore.collection("QuizList")

    fun getQuizzes(callback : (List<QuizDetailModel>?) -> Unit) {
        quizCollection
            .get()
            .addOnSuccessListener { documents ->
                val quizzes = mutableListOf<QuizDetailModel>()
                for (document in documents) {
                    val quizDetail = document.toObject(QuizDetailModel::class.java)
                    quizzes.add(quizDetail)
                    Log.d("FirestoreTest", "Quizzes retrieved: $quizzes")
                }
                callback(quizzes)
            }
            .addOnFailureListener {exception ->
                Log.e("FirestoreTest", "Error getting quizzes", exception)
                callback(null)
            }
    }
}
