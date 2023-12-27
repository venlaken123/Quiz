package com.example.quiz.Fragment

import android.os.Bundle
import android.util.Log
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
import com.example.quiz.Repositories.AuthRepository
import com.example.quiz.ViewModel.AuthViewModel
import com.example.quiz.ViewModel.AuthViewModelFactory
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class ForgotPasswordFragment : Fragment() {

    private val authViewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(AuthRepository())
    }

    private lateinit var emailInputEditText : TextInputLayout
    private lateinit var confirmButton : Button
    private lateinit var navController : NavController

    override fun onCreateView(
        inflater : LayoutInflater , container : ViewGroup? ,
        savedInstanceState : Bundle?
    ) : View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forgot_password , container , false)
    }

    override fun onViewCreated(view : View , savedInstanceState : Bundle?) {
        super.onViewCreated(view , savedInstanceState)

        emailInputEditText = view.findViewById(R.id.emailInputLayout)
        confirmButton = view.findViewById(R.id.buttonConfirm)
        navController = Navigation.findNavController(view)

        confirmButton.setOnClickListener {
            val email = emailInputEditText.editText?.text.toString()
            sendResetPasswordEmail(email)
        }
    }
    private fun sendResetPasswordEmail(email: String) {
        authViewModel.sendPasswordResetEmail(email) { isSuccess ->
            if (isSuccess) {
                Toast.makeText(requireContext(), "Reset password email sent successfully.", Toast.LENGTH_SHORT).show()
                navController.navigate(R.id.action_forgotPasswordFragment_to_signInFragment)
            } else {
                Log.e("ResetTest", "Failed to send email.")
                Toast.makeText(requireContext(), "Failed to send reset password email.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}