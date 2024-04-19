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
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    private lateinit var registerBtn: Button
    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var phone: EditText
    private lateinit var password: EditText
    private lateinit var cpassword: EditText
    private lateinit var loginBtn: TextView

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val fstore: FirebaseFirestore = FirebaseFirestore.getInstance()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        registerBtn = findViewById(R.id.RregisterBtn)
        name = findViewById(R.id.Rname_et)
        phone = findViewById(R.id.Rphone_et)
        email = findViewById(R.id.Rmail_et)
        password = findViewById(R.id.Rpass_et)
        cpassword = findViewById(R.id.Rcpass_et)
        loginBtn = findViewById(R.id.RloginBtn)

        loginBtn.setOnClickListener {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
            finish()
        }

        registerBtn.setOnClickListener {
            //firebase register
            if(cpassword.text.toString() == password.text.toString()){
                auth.createUserWithEmailAndPassword(email.text.toString(),password.text.toString())
                    .addOnSuccessListener { authResult ->
                        //adding the user data to the firestore
                        val userId = authResult.user?.uid
                        userId?.let {
                            val user = hashMapOf(
                                "name" to name.text.toString(),
                                "phone" to phone.text.toString(),
                                "email" to email.text.toString()
                            )

                            fstore.collection("users")
                                .document(it)
                                .set(user)
                                .addOnSuccessListener {
                                    Toast.makeText(this, "Registration Successful..", Toast.LENGTH_SHORT).show()

                                    // Auto signing
                                    auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                                        .addOnSuccessListener {
                                            startActivity(Intent(this, HomeActivity::class.java))
                                            finish()
                                        }
                                        .addOnFailureListener { error ->
                                            Toast.makeText(this, "Login Failed: ${error.message}", Toast.LENGTH_SHORT).show()
                                        }
                                }
                                .addOnFailureListener { error ->
                                    Toast.makeText(this, "Firestore Error: ${error.message}", Toast.LENGTH_SHORT).show()
                                }
                        }?.addOnFailureListener { error ->
                            Toast.makeText(this, "Login Failed: ${error.message}", Toast.LENGTH_SHORT).show()
                        }
                }.addOnFailureListener { error ->
                        Toast.makeText(this, "Registration Failed: ${error.message}", Toast.LENGTH_SHORT).show()
                    }
            }else cpassword.error = "Password doesn't match"
        }

    }
}