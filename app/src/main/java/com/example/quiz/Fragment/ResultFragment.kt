package com.example.quiz.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.quiz.R
import com.example.quiz.Repositories.ResultRepository
import com.example.quiz.ViewModel.ResultViewModel
import com.example.quiz.ViewModel.ResultViewModelFactory

class ResultFragment : Fragment() {

    private val resultViewModel by viewModels<ResultViewModel> {
        ResultViewModelFactory(ResultRepository())
    }

    private lateinit var buttonPlayAgain : Button
    private lateinit var buttonExit : Button
    private lateinit var textCorrect : TextView
    private lateinit var textWrong : TextView

    private lateinit var navController : NavController
    override fun onCreateView(
        inflater : LayoutInflater , container : ViewGroup? ,
        savedInstanceState : Bundle?
    ) : View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result , container , false)
    }

    override fun onViewCreated(view : View , savedInstanceState : Bundle?) {
        super.onViewCreated(view , savedInstanceState)

        buttonPlayAgain = view.findViewById(R.id.btnPlayAgain)
        buttonExit = view.findViewById(R.id.btnExit)
        textCorrect = view.findViewById(R.id.textViewCorrectResult)
        textWrong = view.findViewById(R.id.textViewWrongResult)
        navController = Navigation.findNavController(view)

        buttonPlayAgain.setOnClickListener {
            navController.navigate(R.id.action_resultFragment_to_detailQuestionFragment)
        }

        buttonExit.setOnClickListener {
            navController.navigate(R.id.action_resultFragment_to_beginScreenFragment)
        }

        resultViewModel.correctAnswers.observe(viewLifecycleOwner) { correct ->
            textCorrect.text = "$correct"
        }

        resultViewModel.wrongAnswers.observe(viewLifecycleOwner) { wrong ->
            textWrong.text = "$wrong"
        }

        // Gọi hàm getResult() từ ResultViewModel để lấy dữ liệu
        resultViewModel.getResult()

    }
}