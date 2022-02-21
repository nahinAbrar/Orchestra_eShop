package com.first_android_project.orchestraeshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.first_android_project.orchestraeshop.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //On Click Goes to Register Page
        binding.mainRegisterButton.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        //On Click Goes to Login Page
        binding.mainLoginButton.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }

    }
}