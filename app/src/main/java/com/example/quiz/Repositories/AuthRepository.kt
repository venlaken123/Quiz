package com.example.quiz.Repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

@Suppress("DEPRECATION")
class AuthRepository {
    private val auth : FirebaseAuth = FirebaseAuth.getInstance()

    fun signIn(
        email : String ,
        password : String ,
        onComplete : (FirebaseUser?) -> Unit ,
        onError : (String) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email , password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    onComplete(user)
                } else {
                    onError("Authentication failed")
                }
            }
    }

    fun signUp(
        email : String ,
        password : String ,
        onComplete : (FirebaseUser?) -> Unit ,
        onError : (String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email , password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    onComplete(user)
                } else {
                    onError("Authentication failed")
                }
            }
    }

    fun signOut() {
        auth.signOut()
    }

    /* null là accessToken.
    Access Token: Đây cũng là một chuỗi xác thực nhưng tập trung vào việc xác minh quyền truy cập của người dùng vào tài nguyên
    của Google (ví dụ: dữ liệu trên Google Drive). Trong trường hợp sử dụng Firebase Authentication với Google, thường chỉ cần
    idToken và không cần sử dụng accessToken */
    fun signInWithGoogle(idToken : String , onComplete : (Boolean) -> Unit) {
        val firebaseCredential = GoogleAuthProvider.getCredential(idToken , null)
        auth.signInWithCredential(firebaseCredential)
            .addOnCompleteListener { task ->
                onComplete(task.isSuccessful)
            }
    }

    private fun checkEmailExistence(email : String , onResult : (Boolean) -> Unit) {
        auth.fetchSignInMethodsForEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val signInMethods = task.result?.signInMethods ?: emptyList()
                    // Kiểm tra xem có phương thức đăng nhập nào được liên kết với email này không
                    val exists = signInMethods.isNotEmpty()
                    onResult(exists)
                } else {
                    // Xảy ra lỗi khi kiểm tra sự tồn tại của email
                    onResult(false)
                }
            }
    }

    fun sendPasswordResetEmail(email : String , onComplete : (Boolean) -> Unit) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true)
                } else {
                    onComplete(false)
                }
            }
    }
}

