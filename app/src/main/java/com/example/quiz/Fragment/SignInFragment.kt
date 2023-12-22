package com.example.quiz.Fragment

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.quiz.R
import com.example.quiz.ViewModel.AuthViewModel
import com.google.android.material.textfield.TextInputLayout


class SignInFragment : Fragment() {

    private val viewModel by lazy {
        AuthViewModel(Application())
    }
    private lateinit var emailEdt: TextInputLayout
    private lateinit var passwordEdt: TextInputLayout
    private lateinit var signInButton: Button
    private lateinit var signUpText: TextView
    private lateinit var navController : NavController

    override fun onCreateView(
        inflater : LayoutInflater , container : ViewGroup? ,
        savedInstanceState : Bundle?
    ) : View? {

        return inflater.inflate(R.layout.fragment_sign_in , container , false)
    }

    override fun onViewCreated(view : View , savedInstanceState : Bundle?) {
        super.onViewCreated(view , savedInstanceState)

        emailEdt = view.findViewById(R.id.emailInputLayout)
        passwordEdt = view.findViewById(R.id.passwordInputLayout)
        signInButton = view.findViewById(R.id.buttonSignIn)
        signUpText = view.findViewById(R.id.textSignUp)

        navController = Navigation.findNavController(view)

        signUpText.setOnClickListener{
            navController.navigate((R.id.action_signInFragment_to_signUpFragment))
        }

        signInButton.setOnClickListener{
            val email = emailEdt.editText?.text.toString()
            val password = passwordEdt.editText?.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.signIn(email , password)
                Toast.makeText(context , "Login successfully" , Toast.LENGTH_SHORT).show()
                viewModel.getFirebaseUserMutableLiveData().observe(viewLifecycleOwner) { firebaseUser ->
                    if (firebaseUser != null) {
                        navController.navigate(R.id.action_signInFragment_to_beginScreenFragment)
                    } else {
                        Toast.makeText(
                            requireContext() , "User data not available" , Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                Toast.makeText(
                    requireContext() , "Please enter email and password" , Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}