package com.first_android_project.orchestraeshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.first_android_project.orchestraeshop.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            val userName = binding.loginUserName.text.toString()
            val passWord = binding.loginPassWord.text.toString().trim()



            reference = FirebaseDatabase.getInstance("https://orchestra-eshop-2122an-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users")

            if(userName.isEmpty() || passWord.isEmpty())
            {

                Snackbar.make(it,"Username/PassWord Can not be Empty",Snackbar.LENGTH_LONG).show()

            }
            else
            {
                loginUser(userName, passWord)
            }
        }
    }

    private fun loginUser(userName: String, passWord: String) {
        reference.child(userName).get().addOnSuccessListener {
            if(it.exists()) {
                val getPass = it.child("passWord").value
                if (passWord == getPass) {
                    binding.loginUserName.text.clear()
                    binding.loginPassWord.text.clear()
                    startActivity(Intent(this, HomeActivity::class.java))
                    Toast.makeText(this, "Logged In", Toast.LENGTH_SHORT).show()
                }
                else{

                    Toast.makeText(this, "PassWord Does Not Match.", Toast.LENGTH_SHORT).show()
                }

            }
            else{
                Toast.makeText(this, "User Not Found", Toast.LENGTH_LONG).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Network Error, Please Try Again",Toast.LENGTH_LONG).show()
        }

    }
}