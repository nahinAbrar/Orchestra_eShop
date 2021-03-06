package com.first_android_project.orchestraeshop

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.first_android_project.orchestraeshop.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var reference: DatabaseReference
    private lateinit var parentName: String




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        parentName = "Users"

        binding.loginAdminPanel.setOnClickListener{
            binding.loginButton.text = "Admin Entry"
            binding.loginAdminPanel.visibility  = View.INVISIBLE
            binding.loginUser.visibility  = View.VISIBLE
            parentName = "Admins"
        }

        binding.loginUser.setOnClickListener{
            binding.loginButton.text = "Log In"
            binding.loginAdminPanel.visibility  = View.VISIBLE
            binding.loginUser.visibility  = View.INVISIBLE
            parentName = "Users"
        }

        binding.loginButton.setOnClickListener {
            val userName = binding.loginUserName.text.toString().trim()
            val passWord = binding.loginPassWord.text.toString().trim()

            reference = FirebaseDatabase.getInstance("https://orchestra-eshop-2122an-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference(parentName)

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

        @Suppress("DEPRECATION")
        val progressDialog = ProgressDialog(this)

        @Suppress("DEPRECATION")
        progressDialog.setTitle("Log In")
        @Suppress("DEPRECATION")
        progressDialog.setMessage("Logging In")
        progressDialog.setCancelable(false)
        progressDialog.show()

        reference.child(userName).get().addOnSuccessListener {
            if(it.exists()) {
                val getPass = it.child("passWord").value
                if (passWord == getPass) {

                    if(parentName == "Admins")
                    {
                        binding.loginUserName.text.clear()
                        binding.loginPassWord.text.clear()
                        startActivity(Intent(this, AdminCategoryActivity::class.java))
                        Toast.makeText(this, "Welcome Admin!", Toast.LENGTH_SHORT).show()
                        if(progressDialog.isShowing) progressDialog.dismiss()

                    }
                    else
                    {
                        binding.loginUserName.text.clear()
                        binding.loginPassWord.text.clear()
                        startActivity(Intent(this, HomeActivity::class.java))
                        Toast.makeText(this, "Logged In", Toast.LENGTH_SHORT).show()
                        if(progressDialog.isShowing) progressDialog.dismiss()
                    }



                }
                else{
                    Toast.makeText(this, "PassWord Does Not Match.", Toast.LENGTH_SHORT).show()
                    progressDialog.dismiss()
                }

            }
            else{

                Toast.makeText(this, "User Not Found", Toast.LENGTH_LONG).show()
                progressDialog.dismiss()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Network Error, Please Try Again",Toast.LENGTH_LONG).show()
            progressDialog.dismiss()
        }

    }
}