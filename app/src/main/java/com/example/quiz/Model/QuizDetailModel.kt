package com.example.quiz.Model


data class QuizDetailModel(
    var question: String = "", //tại sao phải khởi tạo: an toàn và ổn định
    var answer: String = "", //đảm bảo mọi đối tượng trong data class sẽ có các thuộc tính khi khởi tạo
    var optionA: String = "", // tránh lỗi runtime error
    var optionB: String = "",
    var optionC: String = "",
    var optionD: String = "",
    var timer: Long = 0
)
