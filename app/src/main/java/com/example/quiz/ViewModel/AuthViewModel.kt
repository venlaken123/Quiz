package com.example.quiz.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quiz.Repositories.AuthRepository
import com.google.firebase.auth.FirebaseUser



class AuthViewModel(application : Application) : ViewModel() {

    private var firebaseUserMutableLiveData: MutableLiveData<FirebaseUser?> = MutableLiveData()
    private val currentUser: FirebaseUser?
    private val repository: AuthRepository = AuthRepository(application)

    init {
        currentUser = repository.getCurrentUser()
        firebaseUserMutableLiveData = repository.getFirebaseUserMutableLiveData()
    }


    fun getFirebaseUserMutableLiveData(): MutableLiveData<FirebaseUser?>{
        return firebaseUserMutableLiveData
    }

    fun getCurrentUser(): FirebaseUser?{
        return currentUser
    }

    fun signUp(email : String , password : String){
        repository.signUp(email, password)
    }

    fun signIn(email : String , password : String){
        repository.signIn(email, password)
    }

    fun signOut(){
        repository.signOut()
    }
}


