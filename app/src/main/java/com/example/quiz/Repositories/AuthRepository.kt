package com.example.quiz.Repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthRepository {
    private val auth : FirebaseAuth = FirebaseAuth.getInstance()

    fun signIn(
        email : String ,
        password : String ,
        onComplete : (FirebaseUser?) -> Unit ,
        onError : (String) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email , password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    onComplete(user)
                } else {
                    onError("Authentication failed")
                }
            }
    }

    fun signUp(
        email : String ,
        password : String ,
        onComplete : (FirebaseUser?) -> Unit ,
        onError : (String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email , password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    onComplete(user)
                } else {
                    onError("Authentication failed")
                }
            }
    }

    fun signOut(){
        auth.signOut()
    }
}

