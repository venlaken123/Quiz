package com.example.quiz.Fragment

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.quiz.R
import com.example.quiz.ViewModel.AuthViewModel
import com.google.android.material.textfield.TextInputLayout


class SignUpFragment : Fragment() {

    private lateinit var emailEdt : TextInputLayout
    private lateinit var passwordEdt : TextInputLayout
    private lateinit var signUpButton : Button
    private lateinit var backButton : Button
    private val viewModel: AuthViewModel by lazy {
        AuthViewModel(Application())
    }
    private lateinit var navController : NavController

    override fun onCreateView(
        inflater : LayoutInflater , container : ViewGroup? , savedInstanceState : Bundle?
    ) : View? {

        return inflater.inflate(R.layout.fragment_sign_up , container , false)
    }

    override fun onViewCreated(view : View , savedInstanceState : Bundle?) {
        super.onViewCreated(view , savedInstanceState)

        emailEdt = view.findViewById(R.id.emailInputLayout)
        passwordEdt = view.findViewById(R.id.passwordInputLayout)
        signUpButton = view.findViewById(R.id.buttonSignUp)
        backButton = view.findViewById(R.id.buttonBack)

        navController = Navigation.findNavController(view)

        backButton.setOnClickListener {
            navController.navigate((R.id.action_signUpFragment_to_signInFragment))
        }

        signUpButton.setOnClickListener {
            val email = emailEdt.editText?.text.toString()
            val password = passwordEdt.editText?.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.signUp(email , password)
                Toast.makeText(context , "Register successfully" , Toast.LENGTH_SHORT).show()
                viewModel.getFirebaseUserMutableLiveData().observe(viewLifecycleOwner) { firebaseUser ->
                    if (firebaseUser != null) {
                        navController.navigate(R.id.action_signUpFragment_to_signInFragment)
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

