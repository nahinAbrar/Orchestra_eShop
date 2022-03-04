package com.first_android_project.orchestraeshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.first_android_project.orchestraeshop.databinding.ActivityAdminNewProductBinding
import com.google.android.material.snackbar.Snackbar

class AdminNewProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminNewProductBinding
    private lateinit var categoryName:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminNewProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        categoryName = intent.extras?.get("category").toString()

        Snackbar.make(binding.root,categoryName,Snackbar.LENGTH_LONG).show()


    }
}