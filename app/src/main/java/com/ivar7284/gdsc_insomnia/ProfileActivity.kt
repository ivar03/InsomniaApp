package com.ivar7284.gdsc_insomnia

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.MediaController
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import de.hdodenhof.circleimageview.CircleImageView

class ProfileActivity : AppCompatActivity() {

    private lateinit var backBtn: ImageButton
    private lateinit var profilePic: CircleImageView
    private lateinit var name: TextView
    private lateinit var email: TextView
    private lateinit var phone: TextView
    private lateinit var logout: Button

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val fstore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var storageReference: StorageReference
    private lateinit var userId: String
    var picURI: Uri? = null

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        loadProfilePic()

        //back button
        backBtn = findViewById(R.id.backBtn_ib)
        backBtn.setOnClickListener {
            finish()
        }

        profilePic = findViewById(R.id.profilePic)
        name = findViewById(R.id.Pname)
        email = findViewById(R.id.Pemail)
        phone = findViewById(R.id.Pphone)

        //fetching data for textviews
        val userId = auth.currentUser?.uid
        if (userId != null){
            fstore.collection("users").document(userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()){
                        name.text = "Name: ${document.getString("name")}"
                        phone.text = "Phone: ${document.getString("phone")}"
                        email.text = "Email: ${document.getString("email")}"
                    }
                }.addOnFailureListener {
                    name.text = "N/A"
                    phone.text = "N/A"
                    email.text = "N/A"
                }
        }

        //profile pic
        profilePic.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, 0)
        }



        //logout button
        logout = findViewById(R.id.logoutBtn)
        logout.setOnClickListener {
            //firebase logout code
            AuthUI.getInstance()
                .signOut(applicationContext)
                .addOnCompleteListener {
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
        }

    }

    private fun loadProfilePic() {
        val userID = auth.currentUser?.uid
        userID?.let {
            val documentReference: DocumentReference = fstore.collection("users").document(it)
            documentReference.get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        val imageUrl = documentSnapshot.getString("profileImageUrl")
                        if (!imageUrl.isNullOrEmpty()) {

                            Glide.with(this)
                                .load(imageUrl)
                                .placeholder(R.drawable.account_icon)
                                .error(R.drawable.account_icon)
                                .into(profilePic)
                        }
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("image fetching error", "Error fetching profile image URL: ${e.message}")
                }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == RESULT_OK && data != null && data.data != null){
            picURI = data.data
            try {
                val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver,picURI)
                profilePic.setImageBitmap(bitmap)
                //uplaoding to firebase
                storageReference = FirebaseStorage.getInstance().getReference("users/${auth.currentUser?.uid}")
                picURI?.let {
                    storageReference.putFile(it).addOnSuccessListener {
                        storageReference.downloadUrl.addOnSuccessListener { uri ->
                            saveImageUrlToFirestore(uri.toString())
                            Toast.makeText(applicationContext, "Image was uploaded to the firebase", Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener {
                            Toast.makeText(applicationContext, "Error fetching image URL from firebase", Toast.LENGTH_SHORT).show()
                        }
                    }.addOnFailureListener{
                        Toast.makeText(applicationContext, "Error uploading the image to firebase", Toast.LENGTH_SHORT).show()
                    }
                }

            } catch (e: Exception){
                Log.e("myapp", "Error: ${e.message.toString()}")
            }
        }
    }

    private fun saveImageUrlToFirestore(imageUrl: String) {
        val userID = auth.currentUser?.uid
        userID?.let {
            val documentReference: DocumentReference = fstore.collection("users").document(it)
            documentReference.update("profileImageUrl", imageUrl)
                .addOnSuccessListener {
                    Toast.makeText(applicationContext, "Image URL saved to Firestore", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(applicationContext, "Error saving image URL to Firestore", Toast.LENGTH_SHORT).show()
                }
        }
    }
    //
}