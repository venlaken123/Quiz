package com.example.quiz.Fragment

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
import com.example.quiz.Repositories.AuthRepository
import com.example.quiz.ViewModel.AuthViewModel
import com.example.quiz.ViewModel.AuthViewModelFactory
import com.google.android.material.textfield.TextInputLayout

class BeginScreenFragment : Fragment() {
    private val authViewModel by viewModels<AuthViewModel>{
        AuthViewModelFactory(AuthRepository())
    }
    private lateinit var startQuizButton: Button
    private lateinit var signOutButton : Button
    private lateinit var navController : NavController
    override fun onCreateView(
        inflater : LayoutInflater , container : ViewGroup? ,
        savedInstanceState : Bundle?
    ) : View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_begin_screen , container , false)
    }

    override fun onViewCreated(view : View , savedInstanceState : Bundle?) {
        super.onViewCreated(view , savedInstanceState)

        startQuizButton = view.findViewById(R.id.buttonStart)
        signOutButton = view.findViewById(R.id.buttonSignOut)
        navController = Navigation.findNavController(view)

        signOutButton.setOnClickListener{
            authViewModel.signOut()
            navController.navigate(R.id.action_beginScreenFragment_to_signInFragment)
            Toast.makeText(requireContext(), "Sign out successfully", Toast.LENGTH_SHORT).show()
        }

        startQuizButton.setOnClickListener {
            navController.navigate(R.id.action_beginScreenFragment_to_detailQuestionFragment)
        }
    }

}