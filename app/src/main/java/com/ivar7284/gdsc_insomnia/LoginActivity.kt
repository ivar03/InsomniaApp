package com.ivar7284.gdsc_insomnia

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {

    override fun onStart() {
        super.onStart()
        val currentUser: FirebaseUser? = auth.currentUser
        if (currentUser != null){
            startActivity(Intent(applicationContext, HomeActivity::class.java))
            finish()
        }
    }

    private lateinit var loginBtn: Button
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var registerBtn: TextView

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginBtn = findViewById(R.id.loginBtn)
        email = findViewById(R.id.mail_et)
        password = findViewById(R.id.pass_et)
        registerBtn = findViewById(R.id.registerBtn)

        registerBtn.setOnClickListener {
            startActivity(Intent(applicationContext, RegisterActivity::class.java))
        }

        loginBtn.setOnClickListener {

            //firebase auth
            auth.signInWithEmailAndPassword(email.text.toString(),password.text.toString())
                .addOnSuccessListener {
                    Toast.makeText(this, "Login Successful..", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(applicationContext, HomeActivity::class.java))
                    finish()
            }.addOnFailureListener { error ->
                    Toast.makeText(this, "Login Failed: ${error.message}", Toast.LENGTH_SHORT).show()
                }
        }

    }
}