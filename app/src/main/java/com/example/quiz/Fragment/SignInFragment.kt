package com.example.quiz.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.quiz.R
import com.example.quiz.Repositories.AuthRepository
import com.example.quiz.ViewModel.AuthViewModel
import com.example.quiz.ViewModel.AuthViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.material.textfield.TextInputLayout

@Suppress("DEPRECATION")
class SignInFragment : Fragment() {

    private val authViewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(AuthRepository())
    }
    private lateinit var emailEdt : TextInputLayout
    private lateinit var passwordEdt : TextInputLayout
    private lateinit var signInButton : Button
    private lateinit var signUpText : TextView
    private lateinit var signInGoogleButton : SignInButton
    private lateinit var forgotPasswordText : TextView
    private lateinit var navController : NavController

    private val googleRequestCode = 123

    override fun onCreateView(
        inflater : LayoutInflater , container : ViewGroup? , savedInstanceState : Bundle?
    ) : View? {

        return inflater.inflate(R.layout.fragment_sign_in , container , false)
    }

    override fun onViewCreated(view : View , savedInstanceState : Bundle?) {
        super.onViewCreated(view , savedInstanceState)

        emailEdt = view.findViewById(R.id.emailInputLayout)
        passwordEdt = view.findViewById(R.id.passwordInputLayout)
        signInButton = view.findViewById(R.id.buttonSignIn)
        signUpText = view.findViewById(R.id.textSignUp)
        signInGoogleButton = view.findViewById(R.id.buttonGoogle)
        forgotPasswordText = view.findViewById(R.id.textForgotPassword)
        navController = Navigation.findNavController(view)

        signUpText.setOnClickListener {
            navController.navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        signInButton.setOnClickListener {
            val email = emailEdt.editText?.text.toString()
            val password = passwordEdt.editText?.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                authViewModel.signIn(email , password)
            } else {
                Toast.makeText(
                    requireContext() , "Please enter email and password" , Toast.LENGTH_SHORT
                ).show()
            }
        }

        authViewModel.userLiveData.observe(viewLifecycleOwner) { firebaseUser -> //tại sao k dùng this được (compile ERROR)
            if (firebaseUser != null) {
                Toast.makeText(context , "Welcome to Quiz Game" , Toast.LENGTH_SHORT).show()
                navController.navigate(R.id.action_signInFragment_to_beginScreenFragment)
            } else {
                Toast.makeText(requireContext() , "User data not available" , Toast.LENGTH_SHORT)
                    .show()
            }
        }

        authViewModel.errorMessageLiveData.observe(viewLifecycleOwner) { errorMessage ->
            // Display error message if login fails
            Toast.makeText(requireContext() , errorMessage , Toast.LENGTH_SHORT).show()
        }

        // Xử lý sign in với Google
        signInGoogleButton.setOnClickListener {
            startSignInWithGoogle()
        }

        authViewModel.signInSuccessLiveData.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                navController.navigate(R.id.beginScreenFragment)
            } else {
                Log.e("GoogleTest" , "Login fail")
            }
        }

        forgotPasswordText.setOnClickListener {
            navController.navigate(R.id.action_signInFragment_to_forgotPasswordFragment)
        }
    }

    private fun startSignInWithGoogle() {
        val signInIntent = getGoogleSignInIntent()
        startActivityForResult(signInIntent , googleRequestCode)
    }

    private fun getGoogleSignInIntent() : Intent {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.my_web_client_id)).requestEmail().build()

        val googleSignInClient = GoogleSignIn.getClient(
            requireActivity() , gso
        )
        return googleSignInClient.signInIntent
    }

    override fun onActivityResult(requestCode : Int , resultCode : Int , data : Intent?) {
        super.onActivityResult(requestCode , resultCode , data)

        if (requestCode == googleRequestCode) {
            if (data != null) {
                val idToken = handleGoogleSignInResult(data)
                idToken?.let {
                    authViewModel.signInWithGoogle(it)
                }
            } else {
                Log.d("GoogleTest" , "Data is null")
            }
        }
    }

    private fun handleGoogleSignInResult(data : Intent?) : String? {
        try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)
            return account?.idToken
        } catch (e : ApiException) {
            // Xử lý lỗi nếu cần thiết
            Log.e("GoogleTest" , "ApiException: ${e.message}")
        }
        return null
    }
}