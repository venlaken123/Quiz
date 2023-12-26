package com.example.quiz.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quiz.Repositories.AuthRepository
import com.google.firebase.auth.FirebaseUser

class AuthViewModel(private val authRepository : AuthRepository) : ViewModel() {
    private var _userLiveData : MutableLiveData<FirebaseUser?> = MutableLiveData()
    val userLiveData : LiveData<FirebaseUser?> get() = _userLiveData

    private var _errorMessageLiveData : MutableLiveData<String> = MutableLiveData()
    val errorMessageLiveData : LiveData<String> get() = _errorMessageLiveData
    private var _resetPasswordSuccessLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val resetPasswordSuccessLiveData: LiveData<Boolean> get() = _resetPasswordSuccessLiveData

    private var _resetPasswordErrorLiveData: MutableLiveData<String> = MutableLiveData()
    val resetPasswordErrorLiveData: LiveData<String> get() = _resetPasswordErrorLiveData

    fun signIn(email : String , password : String) {
        authRepository.signIn(email , password ,
            onComplete = { user ->
                _userLiveData.postValue(user)
            } ,
            onError = { errorMessage ->
                _errorMessageLiveData.postValue(errorMessage)
            }
        )
    }

    fun signUp(email : String , password : String) {
        authRepository.signUp(email , password ,
            onComplete = { user ->
                _userLiveData.postValue(user)
            } ,
            onError = { errorMessage ->
                _errorMessageLiveData.postValue(errorMessage)
            }
        )
    }
    fun resetPassword(email: String) {
        authRepository.resetPassword(
            email,
            onSuccess = {
                _resetPasswordSuccessLiveData.postValue(true)
            },
            onError = { errorMessage ->
                _resetPasswordErrorLiveData.postValue(errorMessage)
            }
        )
    }

    fun signOut() {
        authRepository.signOut()
    }
}


