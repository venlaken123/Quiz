package com.example.quiz.Fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.quiz.R
import com.example.quiz.ViewModel.AuthViewModel


class SplashScreenFragment : Fragment() {

    override fun onCreateView(
        inflater : LayoutInflater , container : ViewGroup? ,
        savedInstanceState : Bundle?
    ) : View? {

        Handler(Looper.myLooper()!!).postDelayed({
            findNavController().navigate(R.id.action_splashScreenFragment_to_signInFragment)
        }, 5000)
        return inflater.inflate(R.layout.fragment_splash_screen , container , false)
    }
}