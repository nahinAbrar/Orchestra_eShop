package com.first_android_project.orchestraeshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.first_android_project.orchestraeshop.databinding.ActivityRegisterBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerButton.setOnClickListener{
            val firstName = binding.registerFirstName.text.toString()
            val lastName = binding.registerLastName.text.toString()
            val userName = binding.registerUserName.text.toString()
            val email = binding.registerEmail.text.toString()
            val phoneNum = binding.registerPhone.text.toString()
            val passWord = binding.registerPassWord.text.toString()
            val repassWord = binding.registerRePassWord.text.toString()

            reference = FirebaseDatabase.getInstance("https://orchestra-eshop-2122an-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users")
            val user = User(firstName,lastName,userName,email,phoneNum,passWord)

            reference.child(userName).setValue(user).addOnSuccessListener {
                Toast.makeText(this, "Account Created", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, MainActivity::class.java))
            }.addOnFailureListener {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}