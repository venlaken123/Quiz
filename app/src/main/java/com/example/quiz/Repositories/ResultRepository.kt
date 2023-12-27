package com.example.quiz.Repositories

import android.util.Log
import com.example.quiz.Model.ResultModel
import com.google.firebase.firestore.FirebaseFirestore

class ResultRepository {
    private val firebaseFirestore : FirebaseFirestore = FirebaseFirestore.getInstance()
    private val resultCollection = firebaseFirestore.collection("Result")
    fun getQuizResult(callback : (List<ResultModel>?) -> Unit) {
        resultCollection
            .get()
            .addOnSuccessListener { documents ->
                val quizResult = mutableListOf<ResultModel>()
                for (document in documents) {
                    val resultDetail = document.toObject(ResultModel::class.java)
                    resultDetail.correctAnswers = document.getLong("correctAnswers")?.toInt() ?: 0
                    resultDetail.wrongAnswers = document.getLong("wrongAnswers")?.toInt() ?: 0
                    quizResult.add(resultDetail)
                    Log.d("FirestoreTest" , "Quizzes retrieved: $quizResult")
                }
                callback(quizResult)
            }
            .addOnFailureListener { exception ->
                Log.e("FirestoreTest" , "Error getting quizzes" , exception)
                callback(null)
            }
    }

    fun updateQuizResult(quizResult : ResultModel , completion : (Boolean) -> Unit) {
        val resultData = hashMapOf(
            "correctAnswers" to quizResult.correctAnswers ,
            "wrongAnswers" to quizResult.wrongAnswers
        )

        resultCollection.document("D7kMa9bZOkplqYBmEC1I")
            .set(resultData)
            .addOnSuccessListener {
                completion(true)
            }
            .addOnFailureListener {
                completion(false)
            }
    }
}

