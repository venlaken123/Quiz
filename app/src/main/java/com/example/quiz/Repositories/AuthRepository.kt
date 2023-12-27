package com.example.quiz.Repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

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

    fun signInWithGoogle(idToken: String, onComplete: (Boolean) -> Unit) {
        val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(firebaseCredential)
            .addOnCompleteListener { task ->
                onComplete(task.isSuccessful)
            }
    }

    fun resetPassword(
        email: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onError("Failed to send password reset email")
                }
            }
    }
}

