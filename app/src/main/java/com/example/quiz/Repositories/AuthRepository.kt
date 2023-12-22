package com.example.quiz.Repositories

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthRepository (private val application : Application) {

    private var firebaseUserMutableLiveData: MutableLiveData<FirebaseUser?> = MutableLiveData()
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    init {
        firebaseUserMutableLiveData = MutableLiveData()
        auth = FirebaseAuth.getInstance()
    }

    fun getFirebaseUserMutableLiveData(): MutableLiveData<FirebaseUser?>{
        return firebaseUserMutableLiveData
    }

    fun getCurrentUser(): FirebaseUser?{
        return auth.currentUser
    }

    fun signUp(email : String , password : String) {
        auth.createUserWithEmailAndPassword(email , password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                firebaseUserMutableLiveData.postValue(auth.currentUser)
            } else {
                Toast.makeText(application , task.exception?.message , Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun signIn(email : String , password : String) {
        auth.signInWithEmailAndPassword(email , password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                firebaseUserMutableLiveData.postValue(auth.currentUser)
            } else {
                Toast.makeText(application , task.exception?.message , Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun signOut(){
        auth.signOut()
    }
}

